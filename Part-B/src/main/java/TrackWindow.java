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
        this.trackWindow.setWindowSize(850, 700);

        this.track.setLayout(new BoxLayout(this.track, BoxLayout.Y_AXIS));
        this.trackWindow.addComponents(this.track);

        this.trackWindow.render();
    }
}
