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
    private Socket senderClientConnection;

    private ObjectInput senderObjectInput;
    private ObjectOutput senderObjectOutput;

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
        this.establishConnections();
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
            while ((received = this.senderObjectInput.readObject()) != null)
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
            this.senderObjectOutput.writeObject(T);
            this.senderObjectOutput.flush();
        }
        catch (Exception ex) {
            ErrorLogger.toConsole(ex);
        }
    }

    private synchronized Object fetchUpdateMessage()
    {
        if (!ThreadCommunicationQueue.isEitherQueueEmpty()) {
            return ThreadCommunicationQueue.dequeue(this.threadId);
        }

        return new CarDTO();
    }

    private synchronized void forwardUpdateMessage(Object updatedState)
    {
        ThreadCommunicationQueue.enqueue(this.threadId, updatedState);
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
