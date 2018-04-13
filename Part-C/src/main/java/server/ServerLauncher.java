package main.java.server;

import main.java.utilities.CarDTO;
import main.java.utilities.ErrorLogger;

import java.awt.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerLauncher
{
    public static void main(String args[])
    {
        try {
            BlockingQueue<CarDTO> carTransactionBlockingQueue = new LinkedBlockingQueue<>();

            Thread clientConnectionOne = new Thread(new ServerReceiver(2000, carTransactionBlockingQueue));
            Thread clientConnectionTwo = new Thread(new ServerReceiver(2001, carTransactionBlockingQueue));

            clientConnectionOne.start();
            clientConnectionTwo.start();
        }
        catch (Exception ex) {
            ErrorLogger.toConsole(ex);
        }
    }
}
