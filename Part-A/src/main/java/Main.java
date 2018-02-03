package main.java;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args)
    {
        // Create the GUI window
        CoreWindow splash = new CoreWindow("TarmacSpeedway: Pits Turntable!");
        splash.setWindowSize(500, 600);

        JPanel mainPanel = new JPanel();

        // Create the header label
        HeaderPanel title = new HeaderPanel("Will this do the job?");
        title.setLayout(new FlowLayout());

        // Create the panel that will host the images
        TurntablePanel animator = new TurntablePanel();
        animator.setAnimationSpeed(1000);

        // Create the 'start game' button
        JButton startGame = new JButton("Race!");

        // Add the image panel to the window, and render the window.
        splash.addComponents(title);
        splash.addComponents(startGame);
        splash.addComponents(animator);

        // Render the window frame
        splash.render();
    }
}

