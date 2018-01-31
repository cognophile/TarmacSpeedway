package main.java;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ImagePanel extends JPanel
{
    private Map<String, ImageIcon> raceCar;

    public void loadImages()
    {
        try
        {
            this.raceCar = ImageLoader.importImages();
        }
        catch (UnsupportedOperationException ex)
        {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }

    @Override
    protected void paintComponent(Graphics graphic)
    {
        super.paintComponent(graphic);
        ImageIcon current = this.raceCar.get("red_small_0.png");
        current.paintIcon(this, graphic, 212, 212);
    }
}
