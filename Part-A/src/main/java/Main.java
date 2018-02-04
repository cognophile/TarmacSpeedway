package main.java;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args)
    {
        // Create the GUI window
        CoreWindow splash = new CoreWindow("TarmacSpeedway: Pits Turntable!");
        splash.setWindowSize(500, 600);

        // Create the supplementary panels
        JPanel mainPanel = new JPanel();
        JButton startGameButton = new JButton("Let's Race!");
        HeaderPanel titleLabel = new HeaderPanel("Welcome!");

        // Create the panel that will host the images
        TurntablePanel animationPanel = new TurntablePanel();
        animationPanel.setAnimationSpeed(1000);

        // Construct the panels into a vertical layout
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        titleLabel.setMaximumSize( new Dimension(600, 50));
        startGameButton.setMaximumSize( new Dimension(120, 30));
        animationPanel.setMaximumSize( new Dimension(600, 500));

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        animationPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add panel elements to a container panel
        mainPanel.add(titleLabel);
        mainPanel.add(animationPanel);
        mainPanel.add(startGameButton);

        // Add the container to the window frame and render
        splash.addComponents(mainPanel);
        splash.render();
    }
}

