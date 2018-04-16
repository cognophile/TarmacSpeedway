package main.java.client;

import main.java.utilities.ErrorLogger;

import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashWindow implements ActionListener
{
    private BaseWindow splash;
    private NetworkManager remoteConnection = new NetworkManager();
    private JButton selectRedCarBtn = new JButton("Red Car");
    private JButton selectGreenCarBtn = new JButton("Green Car");
    private JButton startGameButton = new JButton("Race");
    private String selectedColour;

    public final void start()
    {
        // Create the GUI window
        this.splash = new BaseWindow("TarmacSpeedway: Pitlane");
        this.splash.setWindowSize(500, 600);

        // Create the main and supplementary panels, and components
        JPanel mainPanel = new JPanel();
        HeaderPanel titleLabel = new HeaderPanel("Welcome to the pit lane!");

        // Create the panel that will host the images
        TurntablePanel animationPanel = new TurntablePanel();
        animationPanel.setAnimationSpeed(1000);

        // Construct the panels into a vertical layout
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        titleLabel.setMaximumSize(new Dimension(600, 50));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        animationPanel.setMaximumSize(new Dimension(600, 500));
        animationPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.selectRedCarBtn.setMaximumSize(new Dimension(120, 25));
        this.selectRedCarBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.selectRedCarBtn.addActionListener(this);

        this.selectGreenCarBtn.setMaximumSize(new Dimension(120, 25));
        this.selectGreenCarBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.selectGreenCarBtn.addActionListener(this);

        startGameButton.setMaximumSize(new Dimension(120, 25));
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGameButton.addActionListener(this);

        // Add panel elements to a container panel
        mainPanel.add(titleLabel);
        mainPanel.add(this.selectRedCarBtn);
        mainPanel.add(this.selectGreenCarBtn);
        mainPanel.add(animationPanel);
        mainPanel.add(this.startGameButton);

        // Add the container to the window frame and render
        this.splash.addComponents(mainPanel);
        this.splash.render();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(selectRedCarBtn)) {
            this.selectedColour = "red";
            this.remoteConnection.setRemotePort(2000);

            JOptionPane.showMessageDialog(null, "You Control: Red",
                    "Selection Made!", JOptionPane.INFORMATION_MESSAGE);
        }

        if (e.getSource().equals(selectGreenCarBtn)) {
            this.selectedColour = "green";
            remoteConnection.setRemotePort(2001);

            JOptionPane.showMessageDialog(null, "You control: Green",
                    "Selection Made!", JOptionPane.INFORMATION_MESSAGE);
        }

        if (e.getSource().equals(this.startGameButton)) {
            if (this.remoteConnection.isPortSet()) {
                this.establishServerConnection();
            }
            else {
                JOptionPane.showMessageDialog(null, "Please select Car colour first!",
                        "Oops!", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void establishServerConnection()
    {
        this.remoteConnection.open();
        boolean isAvailable = this.remoteConnection.sendAndAwaitConfirmation("ahoy", "available");

        if (isAvailable) {
            this.startGame();
        } else {
            JOptionPane.showMessageDialog(null, "ERROR: Server Unreachable!",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startGame()
    {
        boolean isOtherClientReady = this.remoteConnection.sendAndAwaitConfirmation("ready", "start");

        if (isOtherClientReady) {
            this.loadGamePanel();
        }
        else {
            JOptionPane.showMessageDialog(null, "Can't start game until both clients select a car and click 'Race!'",
                    "Oops!", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void loadGamePanel()
    {
        TrackWindow track = new TrackWindow(this.remoteConnection, this.selectedColour);
        track.buildWindow();

        this.splash.close();
    }
}
