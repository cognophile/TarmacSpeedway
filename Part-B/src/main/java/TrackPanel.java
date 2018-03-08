package main.java;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class TrackPanel extends JPanel
{
    private Map<String, ImageIcon> redCar;
    private Map<String, ImageIcon> greenCar;

    public TrackPanel()
    {
        this.loadCars();
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

        ImageIcon red = redCar.get("red_small_13.png");
        ImageIcon green = greenCar.get("green_small_13.png");
        red.paintIcon(this, g, 425, 490);
        green.paintIcon(this, g, 425, 540);
    }

    public void loadCars()
    {
        try
        {
            this.redCar = ImageLoader.loadRedCar();
            this.greenCar = ImageLoader.loadGreenCar();
        }
        catch (UnsupportedOperationException ex)
        {
            // Inform the user an error occurred.
            JOptionPane.showMessageDialog(null, "ERROR: This operating system is not supported!\n" + ex.getMessage(), "Error!",
                    JOptionPane.ERROR_MESSAGE);

            // Simulate logging the error
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }
}
