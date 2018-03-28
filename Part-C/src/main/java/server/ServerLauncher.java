package main.java.server;

import main.java.utilities.ErrorLogger;

public class ServerLauncher
{
    public static void main(String args[])
    {
        try {
            ServerReceiver server = new ServerReceiver();
            server.listen();
        }
        catch (Exception ex) {
            ErrorLogger.toConsole(ex);
        }
    }
}
