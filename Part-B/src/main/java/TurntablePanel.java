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

    private Timer timer = new Timer(animationSpeed, this);
    private Car redCar;

    public TurntablePanel()
    {
        this.redCar = new Car("red");
        this.timer.start();
    }

    public void setAnimationSpeed(int animationSpeed)
    {
        this.animationSpeed = animationSpeed;
    }

    @Override
    protected void paintComponent(Graphics graphic)
    {
        super.paintComponent(graphic);

        String imageName = this.redCar.getImageFilenameByIndex(imageIndex);

        ImageIcon current = this.redCar.getImage(imageName);

        current.paintIcon(this, graphic, 212, 212);
        this.setBackground(Color.darkGray);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == this.timer)
        {
            if (this.imageIndex >= 16)
            {
                this.imageIndex = 0;
            }

            try {
                Thread.sleep(125);
            }
            catch (InterruptedException ex) {
                // Inform the user an error occurred.
                JOptionPane.showMessageDialog(null, "ERROR: Process interrupted. Please restart.\n" + ex.getMessage(), "Error!",
                        JOptionPane.ERROR_MESSAGE);

                // Simulate logging the error
                System.out.println(ex.getMessage());
                System.exit(1);
            }

            repaint();
            this.imageIndex++;
        }
    }
}