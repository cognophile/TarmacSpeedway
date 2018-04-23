package main.java.client;

import javax.swing.*;
import java.awt.*;

public class TrackWindow
{
    private BaseWindow trackWindow;
    private TrackPanel track;

    public TrackWindow(SocketCommunicationManager remoteConnection, Color selectedColour)
    {
        this.trackWindow = new BaseWindow("TarmacSpeedway: Track");

        if (selectedColour.equals(Color.red)) {
            this.track = new TrackPanel(remoteConnection, new RedCar(), new GreenCar());
        }
        else {
            this.track = new TrackPanel(remoteConnection, new GreenCar(), new RedCar());
        }
    }

    /**
     * Build the Window with pre-defined window sizes, layout and components
     */
    public void buildWindow()
    {
        this.trackWindow.setWindowSize(860, 735);
        this.track.setLayout(new BoxLayout(this.track, BoxLayout.Y_AXIS));

        this.trackWindow.addComponents(this.track);
        this.trackWindow.render();

        JOptionPane.showMessageDialog(null,
                "Engines are running! Lights are green! " +
                        "\n\nControls: \n  W (Accelerate), A (Turn Left), S (Decelerate), D (Turn Right)" +
                        "\n\nIf either player crashes, press 'R' to restart!" +
                        "\n\nTo end the game, press 'ESC' to exit!",
                "Lights out!", JOptionPane.PLAIN_MESSAGE);
    }
}
