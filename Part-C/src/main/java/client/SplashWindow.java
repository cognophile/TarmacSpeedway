package main.java.client;

import main.java.utilities.ErrorLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashWindow implements ActionListener
{
    private BaseWindow splash;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        try {
            NetworkManager remoteConnection = new NetworkManager();
            boolean isAvailable = remoteConnection.send("ahoy");

            if (isAvailable) {
                TrackWindow track = new TrackWindow();
                track.buildWindow();

                this.splash.close();
            }
            else {
                JOptionPane.showMessageDialog(null, "ERROR: Remote Connection Unavailable!",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (Exception ex) {
            ErrorLogger.toConsole(ex);
        }
    }

    public final void start()
    {
        // Create the GUI window
        this.splash = new BaseWindow("TarmacSpeedway: Pitlane");
        this.splash.setWindowSize(500, 600);

        // Create the main and supplementary panels, and components
        JPanel mainPanel = new JPanel();
        HeaderPanel titleLabel = new HeaderPanel("Welcome to the pit lane!");
        JButton selectRedCar = new JButton("Red Car");
        JButton selectGreenCar = new JButton("Green Car");
        JButton startGameButton = new JButton("Race");

        // Create the panel that will host the images
        TurntablePanel animationPanel = new TurntablePanel();
        animationPanel.setAnimationSpeed(1000);

        // Construct the panels into a vertical layout
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        titleLabel.setMaximumSize(new Dimension(600, 50));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        animationPanel.setMaximumSize(new Dimension(600, 500));
        animationPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        selectRedCar.setMaximumSize(new Dimension(120, 25));
        selectRedCar.setAlignmentX(Component.CENTER_ALIGNMENT);

        selectGreenCar.setMaximumSize(new Dimension(120, 25));
        selectGreenCar.setAlignmentX(Component.CENTER_ALIGNMENT);

        startGameButton.setMaximumSize(new Dimension(120, 25));
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGameButton.addActionListener(this);

        // Add panel elements to a container panel
        mainPanel.add(titleLabel);
        mainPanel.add(selectRedCar);
        mainPanel.add(selectGreenCar);
        mainPanel.add(animationPanel);
        mainPanel.add(startGameButton);

        // Add the container to the window frame and render
        this.splash.addComponents(mainPanel);
        this.splash.render();
    }
}
