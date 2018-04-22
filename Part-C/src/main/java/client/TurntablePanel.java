package main.java.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TurntablePanel extends JPanel implements ActionListener
{
    private int imageIndex = 1;
    private Timer timer = new Timer(125, this);
    private Car redCar;

    public TurntablePanel()
    {
        this.redCar = new RedCar();
        this.timer.start();
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

        if (e.getSource() == this.timer) {
            if (this.imageIndex >= 16) {
                this.imageIndex = 0;
            }

            repaint();
            this.imageIndex++;
        }
    }
}