package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class TurntablePanel extends JPanel implements ActionListener
{
    private int imageIndex = 0;
    private int animationSpeed;
    private Map<String, ImageIcon> redCar;
    private Timer timer = new Timer(animationSpeed, this);
    private String filePrefix = "red_small_";

    public TurntablePanel()
    {
        this.loadImages();
        this.timer.start();
    }

    public void loadImages()
    {
        try
        {
            this.redCar = ImageLoader.loadRedCar();
        }
        catch (UnsupportedOperationException ex)
        {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }

    public void setAnimationSpeed(int animationSpeed)
    {
        this.animationSpeed = animationSpeed;
    }

    @Override
    protected void paintComponent(Graphics graphic)
    {
        super.paintComponent(graphic);

        String imageName = new String(filePrefix + imageIndex + ".png");
        ImageIcon current = redCar.get(imageName);

        current.paintIcon(this, graphic, 212, 212);
        this.setBackground(Color.darkGray);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == this.timer)
        {
            if (imageIndex >= 16)
            {
                imageIndex = 0;
            }

            try {
                Thread.sleep(125);
            }
            catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
                System.exit(1);
            }

            repaint();
            imageIndex++;
        }
    }
}