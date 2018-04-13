package main.java.server;

import main.java.utilities.ErrorLogger;
import main.java.utilities.CarDTO;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;
import java.util.concurrent.BlockingQueue;

public class ServerReceiver implements Runnable
{
    private final BlockingQueue<CarDTO> carTransitionBlockingQueue;
    private ServerSocket localServer;
    private Socket senderClientConnection;

    private CarDTO remoteCar;
    private ObjectInput senderObjectInput;
    private ObjectOutput senderObjectOutput;

    private int localPort;

    public ServerReceiver(int localPort, BlockingQueue<CarDTO> queue)
    {
        this.localPort = localPort;
        this.carTransitionBlockingQueue = queue;
    }

    @Override
    public void run()
    {
        this.createSocket();
        this.establishConnections();
        this.handleRequests();
        this.close();
    }

    private synchronized void fetchState()
    {
        try {
            this.remoteCar = this.carTransitionBlockingQueue.take();
        } catch (InterruptedException ex) {
            ErrorLogger.toConsole(ex);
        }
    }

    private void createSocket()
    {
        try {
            this.localServer = new ServerSocket(this.localPort);
        }
        catch (IOException ex) {
            ErrorLogger.toConsole(ex);
        }
    }

    private void establishConnections()
    {
        try {
            this.senderClientConnection = this.localServer.accept();

            this.senderObjectInput = new ObjectInputStream(this.senderClientConnection.getInputStream());
            this.senderObjectOutput = new ObjectOutputStream(this.senderClientConnection.getOutputStream());
        }
        catch (Exception ex) {
            ErrorLogger.toConsole(ex);
        }
    }

    private void handleRequests()
    {
        try {
            Object received;
            while ((received = this.senderObjectInput.readObject()) != null) {
                if (received instanceof String) {
                    if (received.equals("ahoy")) {
                        this.respond("available");
                        continue;
                    }

                    if (received.equals("ready")) {
                        // Check if other threads client is ready
                        this.respond("start");
                        continue;
                    }

                    if (received.equals("dto")) {
                        this.fetchState();
                        this.respond(this.remoteCar);
                        continue;
                    }

                    if (received.equals("exit")) {
                        return;
                    }
                }
                else {
                    this.forwardState((CarDTO)received);
                }
            }
        }
        catch (ClassNotFoundException ex) {
            ErrorLogger.toConsole(ex);
        }
        catch (IOException ex) {
            ErrorLogger.toConsole(ex);
        }
    }

    private void respond(Object T)
    {
        try {
            this.senderObjectOutput.writeObject(T);
            this.senderObjectOutput.flush();
        }
        catch (Exception ex) {
            ErrorLogger.toConsole(ex);
        }
    }

    private synchronized void forwardState(CarDTO T)
    {
        try {
            this.carTransitionBlockingQueue.put(T);
        }
        catch (InterruptedException ex) {
            ErrorLogger.toConsole(ex);
        }
    }

    private void close()
    {
        try {
            this.senderObjectOutput.close();
            this.senderObjectInput.close();
            this.senderClientConnection.close();
        }
        catch (Exception ex) {
            ErrorLogger.toConsole(ex);
        }
    }
}
