package main.java.server;

import main.java.utilities.ErrorLogger;
import main.java.utilities.CarDTO;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;

public class ServerReceiver implements Runnable
{
    private ServerSocket localServer;
    private Socket senderClientConnection;

    private CarDTO remoteCar;
    private ObjectInput senderObjectInput;
    private ObjectOutput senderObjectOutput;

    private int localPort;

    public ServerReceiver(int localPort)
    {
        this.localPort = localPort;
    }

    @Override
    public void run()
    {
        this.createSocket();
        this.establishConnections();
        this.handleRequests();
        this.close();
    }

    public void setState(CarDTO remoteCar)
    {
        this.remoteCar = remoteCar;
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
                        this.respond(this.remoteCar);
                        continue;
                    }

                    if (received.equals("exit")) {
                        this.forwardState("exit");
                        return;
                    }
                }
                else {
                    this.setState((CarDTO)received);
                    // this.forward((CarDTO)received);
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

    private void forwardState(Object T)
    {
        try {
            // Forward message to other client thread
            // Has to set the remoteCar DTO in the other thread
            // Should this obj have a inbound(position, turn, speed) method to set the remoteCar which is the called
            // function in the thread-communication implementation? eg. t1.forwardState() -> TC -> t2.inbound()
            // this.senderOutputStream.writeBytes(outboundMessage + "\n");
            // this.senderOutputStream.flush();
        }
        catch (Exception ex) {
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
