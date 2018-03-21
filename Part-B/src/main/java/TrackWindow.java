package main.java;

import javax.swing.*;

public class TrackWindow
{
    private BaseWindow trackWindow;
    private TrackPanel track;

    public TrackWindow()
    {
        this.trackWindow = new BaseWindow("TarmacSpeedway: Track");
        this.track = new TrackPanel();
    }

    public void buildWindow()
    {
        this.trackWindow.setWindowSize(860, 735);

        this.track.setLayout(new BoxLayout(this.track, BoxLayout.Y_AXIS));
        this.trackWindow.addComponents(this.track);

        this.trackWindow.render();

        JOptionPane.showMessageDialog(null,
                "Engines are running! Lights are green! " +
                        "\n\nControls: \n  W (Accelerate), A (Turn Left), S (Decelerate), D (Turn Right)" +
                        "\n  UP (Accelerate), LEFT (Turn Left), DOWN (Decelerate), RIGHT (Turn Right)" +
                        "\n\nIf you crash, hit 'R' to restart!",
                "Lights out!", JOptionPane.PLAIN_MESSAGE);
    }
}
