package main.java;

import javax.swing.*;

public class Main {

    public static void main(String[] args)
    {
        GraphicalWindow coreWindow = new GraphicalWindow("Welcome!");
        coreWindow.setWindowSize(500, 500);

        GraphicalPanel initialPanel = new GraphicalPanel();
        initialPanel.add(new JLabel("Welcome to TarmacSpeedway!", JLabel.CENTER));

        coreWindow.addComponents(initialPanel);
        coreWindow.render();
    }
}
