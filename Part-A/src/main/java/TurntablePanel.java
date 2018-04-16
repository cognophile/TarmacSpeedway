package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class TurntablePanel extends JPanel implements ActionListener
{
    private int imageIndex = 1;
    private Map<String, ImageIcon> redCar;
    private Timer timer = new Timer(125, this);
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
            // Inform the user an error occurred.
            JOptionPane.showMessageDialog(null, "ERROR: This operating system is not supported!\n" + ex.getMessage(), "Error!",
                    JOptionPane.ERROR_MESSAGE);

            // Simulate logging the error
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }

    @Override
    protected void paintComponent(Graphics graphic)
    {
        super.paintComponent(graphic);

        String imageName = this.filePrefix + this.imageIndex + ".png";
        ImageIcon current = redCar.get(imageName);

        current.paintIcon(this, graphic, 212, 212);
        this.setBackground(Color.darkGray);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.timer) {

            if (this.imageIndex >= 16) {
                this.imageIndex = 0;
            }

            repaint();
            this.imageIndex++;
        }
    }
}