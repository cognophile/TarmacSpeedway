package main.java.client;

import main.java.utilities.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class NetworkManager
{
    private Socket socket;
    private int remotePort = 0;
    private static final String remoteAddress = "127.0.0.1";
    private DataOutputStream outputStream;
    private BufferedReader inputStream;

    public void open()
    {
        try {
            this.socket = new Socket(remoteAddress, this.remotePort);
            this.outputStream = new DataOutputStream(this.socket.getOutputStream());
            this.inputStream = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        }
        catch (Exception ex) {
            ErrorLogger.toConsole(ex);
        }
    }

    public void setRemotePort(int port)
    {
        this.remotePort = port;
    }

    public boolean isPortSet()
    {
        return this.remotePort != 0;
    }

    public boolean send(String request) throws RuntimeException
    {
        if (Helper.isNotNull(this.socket) && Helper.isNotNull(this.outputStream) && Helper.isNotNull(this.inputStream))
        {
            try {
                outputStream.writeBytes(request);
                outputStream.flush();
                return true;
            }
            catch (IOException ex) {
                ErrorLogger.toConsole(ex);
                return false;
            }
        }

        throw new RuntimeException("Request Send Failure: remote network is unavailable or unreachable!");
    }

//    public void listen()
//    {
//        try {
//            String response;
//            while ((response = inputStream.readLine()) != null) {
//                if (response.contains(":exit")) {
//                    // Close window and connection
//                    this.close();
//                    return;
//                }
//
//                if (Helper.isInteger(response)) {
//                    // call to set car speed
//                }
//
//                if (response.contains(":position:")) {
//                    // call to set car direction
//                }
//
//                if (response.contains(":orientation:")) {
//                    // call to set car direction
//                }
//            }
//        }
//        catch (IOException ex) {
//            ErrorLogger.toConsole(ex);
//        }
//    }
//
//    public void close()
//    {
//        try {
//            this.outputStream.close();
//            this.inputStream.close();
//            this.socket.close();
//        }
//        catch (IOException ex) {
//            ErrorLogger.toConsole(ex);
//        }
//
//    }
}
