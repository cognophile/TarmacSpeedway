package main.java.client;

import javax.swing.*;

public class ApplicationLauncher
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run()
            {
                new SplashWindow().start();
            }
        });
    }
}

