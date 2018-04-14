package main.java.server;

import main.java.utilities.CarDTO;
import main.java.utilities.ErrorLogger;

import java.awt.*;
import java.util.AbstractQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerLauncher
{
    public static void main(String args[])
    {
        try {
            Thread clientConnectionOne = new Thread(new ServerReceiver(1, 2000));
            Thread clientConnectionTwo = new Thread(new ServerReceiver(2, 2001));

            clientConnectionOne.start();
            clientConnectionTwo.start();

            clientConnectionOne.join();
            clientConnectionTwo.join();
        }
        catch (Exception ex) {
            ErrorLogger.toConsole(ex);
        }
    }
}
