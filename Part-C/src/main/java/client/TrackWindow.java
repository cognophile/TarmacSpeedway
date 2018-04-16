package main.java.client;

import javax.swing.*;
import java.awt.*;

public class TrackWindow
{
    private BaseWindow trackWindow;
    private TrackPanel track;

    public TrackWindow(NetworkManager remoteConnection, Color selectedColour)
    {
        this.trackWindow = new BaseWindow("TarmacSpeedway: Track");
        this.track = new TrackPanel(remoteConnection, selectedColour);
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
                        "\n\nIf you crash, hit 'R' to restart!",
                "Lights out!", JOptionPane.PLAIN_MESSAGE);
    }
}
