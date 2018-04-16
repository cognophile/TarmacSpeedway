package main.java.client;

import main.java.utilities.*;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class NetworkManager
{
    private Socket socket;
    private int remotePort = 0;
    private String remoteAddress;
    private ObjectOutput objectOutput;
    private ObjectInput objectInput;

    public void open()
    {
        if (this.canLoadServerConfiguration()) {
            try {
                this.socket = new Socket(this.remoteAddress, this.remotePort);
                this.objectOutput = new ObjectOutputStream(this.socket.getOutputStream());
                this.objectInput = new ObjectInputStream(this.socket.getInputStream());
            }
            catch (Exception ex) {
                ErrorLogger.toConsole(ex);
            }
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

    public CarDTO getRemoteDTO()
    {
        return this.sendAndAwaitSerializedResponse("fetch");
    }

    public boolean send(Object request) throws RuntimeException
    {
        if (Helper.isNotNull(this.socket) && Helper.isNotNull(this.objectOutput))
        {
            try {
                this.objectOutput.writeObject(request);
                this.objectOutput.flush();
                return true;
            }
            catch (IOException ex) {
                ErrorLogger.toConsole(ex);
                return false;
            }
        }
        throw new RuntimeException("Request Send Failure: remote network is unavailable or unreachable!");
    }

    public boolean sendAndAwaitConfirmation(Object request, Object expectedResponse) throws RuntimeException
    {
        if (Helper.isNotNull(this.socket) && Helper.isNotNull(this.objectOutput) && Helper.isNotNull(this.objectInput))
        {
            try {
                this.objectOutput.writeObject(request);
                this.objectOutput.flush();

                Object response;
                while ((response = this.objectInput.readObject()) != null) {
                    if (response.equals(expectedResponse)) {
                       return true;
                    }
                    else {
                        return false;
                    }
                }
            }
            catch (ClassNotFoundException ex) {
                ErrorLogger.toConsole(ex);
                return false;
            }
            catch (IOException ex) {
                ErrorLogger.toConsole(ex);
                return false;
            }
        }
        throw new RuntimeException("Request Send Failure: remote network is unavailable or unreachable!");
    }

    public CarDTO sendAndAwaitSerializedResponse(Object request) throws RuntimeException
    {
        if (Helper.isNotNull(this.socket) && Helper.isNotNull(this.objectOutput) && Helper.isNotNull(this.objectInput))
        {
            try {
                this.objectOutput.writeObject(request);
                this.objectOutput.flush();

                CarDTO response;
                while ((response = (CarDTO)this.objectInput.readObject()) != null) {
                    return response;
                }
            }
            catch (ClassNotFoundException ex) {
                ErrorLogger.toConsole(ex);

            }
            catch (IOException ex) {
                ErrorLogger.toConsole(ex);
            }
        }
        throw new RuntimeException("Request Send Failure: remote network is unavailable or unreachable!");
    }

    public void close()
    {
        try {
            this.objectOutput.close();
            this.objectInput.close();
            this.socket.close();
        }
        catch (IOException ex) {
            ErrorLogger.toConsole(ex);
        }

    }

    private boolean canLoadServerConfiguration()
    {
        try {
            this.remoteAddress = NetworkConfigurationLoader.getRemoteAddress();
            return true;
        }
        catch (FileNotFoundException ex) {
            ErrorLogger.toConsole(ex);
            JOptionPane.showMessageDialog(null, "Cannot locate required file: " +
                            "\nPlease make sure '../resources/remoteConfiguration.txt' exists!",
                    "Oops!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        catch (IOException ex) {
            ErrorLogger.toConsole(ex);
            JOptionPane.showMessageDialog(null, "Unable to parse network configuration file!",
                    "Oops!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
