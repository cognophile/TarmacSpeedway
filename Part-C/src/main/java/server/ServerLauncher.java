package main.java.server;

import main.java.utilities.ErrorLogger;

public class ServerLauncher
{
    public static void main(String args[])
    {
        try {
            Thread clientConnectionOne = new Thread(new ServerReceiver(2000));
            Thread clientConnectionTwo = new Thread(new ServerReceiver(2001));

            clientConnectionOne.start();
            clientConnectionTwo.start();
        }
        catch (Exception ex) {
            ErrorLogger.toConsole(ex);
        }
    }
}
