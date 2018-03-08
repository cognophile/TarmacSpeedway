package main.java;

import javax.swing.*;
import java.awt.*;

public class ClientTrackWindow
{
    private ClientBaseWindow trackWindow;
    private TrackPanel track;

    public ClientTrackWindow()
    {
        this.trackWindow = new ClientBaseWindow("TarmacSpeedway: Track");
        this.track = new TrackPanel();
    }

    public void buildWindow()
    {
        this.trackWindow.setWindowSize(900, 700);

        this.track.setLayout(new BoxLayout(this.track, BoxLayout.Y_AXIS));
        this.trackWindow.addComponents(this.track);

        this.trackWindow.render();
    }
}
