package main.java.server;

import main.java.utilities.ErrorLogger;
import main.java.utilities.CarDTO;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;

public class ServerReceiver implements Runnable
{
    private int localPort;
    private int threadId;

    private ServerSocket localServer;
    private Socket clientConnection;

    private ObjectInput clientObjectInput;
    private ObjectOutput clientObjectOutput;

    public ServerReceiver(int threadId, int localPort)
    {
        this.threadId = threadId;
        this.localPort = localPort;
    }

    /**
     * Called within Thread.start()
     */
    @Override
    public final void run()
    {
        this.createSocket();
        this.establishConnection();
        this.handleRequests();
        this.close();
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

    private void establishConnection()
    {
        try {
            this.clientConnection = this.localServer.accept();

            this.clientObjectInput = new ObjectInputStream(this.clientConnection.getInputStream());
            this.clientObjectOutput = new ObjectOutputStream(this.clientConnection.getOutputStream());
        }
        catch (Exception ex) {
            ErrorLogger.toConsole(ex);
        }
    }

    private void handleRequests()
    {
        try {
            Object received;
            while ((received = this.clientObjectInput.readObject()) != null)
            {
                if (received instanceof String) {
                    if (received.equals("ahoy")) {
                        this.respond("available");
                        continue;
                    }

                    if (received.equals("ready")) {
                        this.respond("start");
                        continue;
                    }

                    if (received.equals("fetch")) {
                        this.respond(this.fetchUpdateMessage());
                        continue;
                    }

                    if (received.equals("reset")) {
                        this.forwardUpdateMessage(received);
                        continue;
                    }

                    if (received.equals("exit")) {
                        this.forwardUpdateMessage(received);
                        return;
                    }
                }
                else {
                    this.forwardUpdateMessage(received);
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
            this.clientObjectOutput.writeObject(T);
            this.clientObjectOutput.flush();
        }
        catch (Exception ex) {
            ErrorLogger.toConsole(ex);
        }
    }

    private synchronized Object fetchUpdateMessage()
    {
        if (!ThreadCommunicationQueues.isEitherQueueEmpty()) {
            return ThreadCommunicationQueues.dequeue(this.threadId);
        }

        return new CarDTO();
    }

    private synchronized void forwardUpdateMessage(Object updatedState)
    {
        ThreadCommunicationQueues.enqueue(this.threadId, updatedState);
    }

    private void close()
    {
        try {
            this.clientObjectOutput.close();
            this.clientObjectInput.close();
            this.clientConnection.close();
        }
        catch (Exception ex) {
            ErrorLogger.toConsole(ex);
        }
    }
}
