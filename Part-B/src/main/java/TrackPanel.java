package main.java;

import javax.swing.*;
import java.awt.*;

public class TrackPanel extends JPanel
{
    private Car redCar;
    private Car greenCar;

    public TrackPanel()
    {
        this.redCar = new Car("red");
        this.greenCar = new Car("green");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setBackground(Color.gray);

        Color c1 = Color.green;
        g.setColor(c1);
        g.fillRect(150, 200, 550, 300); //grass

        Color c2 = Color.black;
        g.setColor(c2);
        g.drawRect(50, 100, 750, 500);  // outer edge
        g.drawRect(150, 200, 550, 300); // inner edge

        Color c3 = Color.yellow;
        g.setColor( c3 );
        g.drawRect(100, 150, 650, 400); // mid-lane marker

        Color c4 = Color.white;
        g.setColor(c4);
        g.drawLine(425, 500, 425, 600); // start line

        ImageIcon red = this.redCar.getImage(this.redCar.getImageFilenameByIndex(13));
        ImageIcon green = this.greenCar.getImage(this.greenCar.getImageFilenameByIndex(13));
        red.paintIcon(this, g, 425, 490);
        green.paintIcon(this, g, 425, 540);
    }
}
