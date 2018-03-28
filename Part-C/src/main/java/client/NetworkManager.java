package main.java.client;

import main.java.utilities.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class NetworkManager
{
    private Socket socket;
    private static final int remotePort = 5000;
    private static final String remoteAddress = "127.0.0.1";
    private DataOutputStream outputStream;
    private BufferedReader inputStream;

    public NetworkManager()
    {
        try {
            this.socket = new Socket(remoteAddress, remotePort);
            this.outputStream = new DataOutputStream(this.socket.getOutputStream());
            this.inputStream = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        }
        catch (Exception ex) {
            ErrorLogger.toConsole(ex);
        }
    }

    public void send(String request)
    {
        if (Helper.isNotNull(this.socket) && Helper.isNotNull(this.outputStream) && Helper.isNotNull(this.inputStream))
        {
            try {
                outputStream.writeBytes(request);
                outputStream.flush();
            }
            catch (IOException ex) {
                ErrorLogger.toConsole(ex);
            }
        }
    }

    public void listen()
    {
        try {
            String response;
            while ((response = inputStream.readLine()) != null) {
                if (response.contains("exit")) {
                    // Close window and connection
                    this.close();
                    return;
                }

                if (Helper.isInteger(response)) {
                    // call to set car speed
                } else {
                    // call to set car direction
                }
            }
        }
        catch (IOException ex) {
            ErrorLogger.toConsole(ex);
        }
    }

    public void close()
    {
        try {
            this.outputStream.close();
            this.inputStream.close();
            this.socket.close();
        }
        catch (IOException ex) {
            ErrorLogger.toConsole(ex);
        }

    }

    private void reply()
    {

    }
}
