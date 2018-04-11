package main.java.client;

import main.java.utilities.*;

import java.io.*;
import java.net.Socket;

public class NetworkManager
{
    private Socket socket;
    private int remotePort = 0;
    private static final String remoteAddress = "localhost";
    private ObjectOutput objectOutput;
    private ObjectInput objectInput;

    public void open()
    {
        try {
            this.socket = new Socket(remoteAddress, this.remotePort);
            this.objectOutput = new ObjectOutputStream(this.socket.getOutputStream());
            this.objectInput = new ObjectInputStream(this.socket.getInputStream());
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


    public CarDTO getRemoteDTO()
    {
        return this.sendAndAwaitSerializedResponse("dto");
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
}
