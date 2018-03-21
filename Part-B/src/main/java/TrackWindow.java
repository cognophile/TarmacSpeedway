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
                "Engines are running! Lights are green! \n\nHit W/UP to step on the gas! If you crash, hit 'R' to restart!",
                "Lights out!", JOptionPane.PLAIN_MESSAGE);
    }
}
