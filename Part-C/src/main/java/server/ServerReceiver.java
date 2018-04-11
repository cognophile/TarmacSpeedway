package main.java.server;

import main.java.utilities.ErrorLogger;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class ServerReceiver implements Runnable
{
    private ServerSocket localServer;
    private Socket senderClientConnection;
    private BufferedReader senderInputStream;
    private DataOutputStream senderOutputStream;
    private int localPort;

    public ServerReceiver(int localPort)
    {
        this.localPort = localPort;
    }

    @Override
    public void run()
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
            this.senderClientConnection = this.localServer.accept();

            this.senderInputStream = new BufferedReader(new InputStreamReader(this.senderClientConnection.getInputStream()));
            this.senderOutputStream = new DataOutputStream(this.senderClientConnection.getOutputStream());
        }
        catch (Exception ex) {
            ErrorLogger.toConsole(ex);
        }
    }

    private void handleRequests()
    {
        try {
            String receivedMessage;
            while((receivedMessage = this.senderInputStream.readLine()) != null)
            {
                if (receivedMessage.contains("ahoy")) {
                    this.respond(":available");
                    continue;
                }

                if (receivedMessage.contains("ready")) {
                    // Mark this client as ready to play.
                    // How to communicate other client ready?
                    continue;
                }

                if (receivedMessage.contains("exit")) {
                    this.forward(":exit");
                    return;
                }

                this.forward(receivedMessage);
            }
        }
        catch (IOException ex) {
            ErrorLogger.toConsole(ex);
        }
    }

    private void respond(String outboundMessage)
    {
        try {
            this.senderOutputStream.writeBytes(outboundMessage + "\n");
            this.senderOutputStream.flush();
        }
        catch (Exception ex) {
            ErrorLogger.toConsole(ex);
        }
    }

    private void forward(String outboundMessage)
    {
        try {
            // Forward message to other client thread
            this.senderOutputStream.writeBytes(outboundMessage + "\n");
            this.senderOutputStream.flush();
        }
        catch (Exception ex) {
            ErrorLogger.toConsole(ex);
        }
    }

    private void close()
    {
        try {
            this.senderOutputStream.close();
            this.senderInputStream.close();
            this.senderClientConnection.close();
        }
        catch (Exception ex) {
            ErrorLogger.toConsole(ex);
        }
    }
}
