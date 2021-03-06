package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashWindow implements ActionListener
{
    private BaseWindow splash;
    private JButton startGameButton;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        TrackWindow track = new TrackWindow();
        track.buildWindow();

        this.splash.close();
    }

    public final void start()
    {
        // Create the GUI window
        this.splash = new BaseWindow("TarmacSpeedway: Pitlane");
        this.splash.setWindowSize(500, 600);

        // Create the main and supplementary panels, and components
        JPanel mainPanel = new JPanel();
        HeaderPanel titleLabel = new HeaderPanel("Welcome to the pit lane!");
        this.startGameButton = new JButton("Let's Race!");

        // Create the panel that will host the images
        TurntablePanel animationPanel = new TurntablePanel();

        // Construct the panels into a vertical layout
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        titleLabel.setMaximumSize(new Dimension(600, 50));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        startGameButton.setMaximumSize(new Dimension(120, 25));
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGameButton.addActionListener(this);

        animationPanel.setMaximumSize(new Dimension(600, 500));
        animationPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add panel elements to a container panel
        mainPanel.add(titleLabel);
        mainPanel.add(animationPanel);
        mainPanel.add(startGameButton);

        // Add the container to the window frame and render
        this.splash.addComponents(mainPanel);
        this.splash.render();
    }
}
