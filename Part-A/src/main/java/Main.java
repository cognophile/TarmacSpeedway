package main.java;

import javax.swing.*;

public class Main {

    public static void main(String[] args)
    {
        CoreWindow coreWindow = new CoreWindow("Welcome to TarmacSpeedway!");
        coreWindow.setWindowSize(500, 500);

        ImagePanel panel = new ImagePanel();
        panel.loadImages();

        coreWindow.addComponents(panel);
        coreWindow.render();
    }
}
