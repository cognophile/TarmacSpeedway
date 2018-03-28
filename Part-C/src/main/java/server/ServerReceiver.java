package main.java.server;

import main.java.utilities.ErrorLogger;

import java.net.*;
import java.io.*;

public class ServerReceiver
{
    private ServerSocket localServer;
    private Socket senderClientConnection;
    private BufferedReader senderInputStream;
    private DataOutputStream senderOutputStream;
    private final int localPort = 5000;

    public ServerReceiver()
    {
        try {
            this.localServer = new ServerSocket(this.localPort);
        }
        catch (IOException ex) {
            ErrorLogger.toConsole(ex);
        }
    }

    public void listen()
    {
        try {
            this.senderClientConnection = this.localServer.accept();

            this.senderInputStream = new BufferedReader(new InputStreamReader(this.senderClientConnection.getInputStream()));
            this.senderOutputStream = new DataOutputStream(this.senderClientConnection.getOutputStream());

            this.processRequest();

            this.close();
        }
        catch (Exception ex) {
            ErrorLogger.toConsole(ex);
        }
    }

    private void processRequest()
    {
        try {
            String receivedMessage;

            while((receivedMessage = this.senderInputStream.readLine()) != null)
            {
                if (receivedMessage.contains("ahoy")) {
                    return;
                }

                if (receivedMessage.contains("exit")) {
                    this.forward("exit");
                    return;
                }

                this.forward(receivedMessage);
            }
        }
        catch (IOException ex) {
            ErrorLogger.toConsole(ex);
        }
    }

    private void reply(String outboundMessage)
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
            // Forward message to other client (multithread?)
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
