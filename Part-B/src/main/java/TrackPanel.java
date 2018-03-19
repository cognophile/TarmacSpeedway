package main.java;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TrackPanel extends JPanel implements ActionListener, KeyListener
{
    private Car redCar;
    private Car greenCar;
    private Timer timer = new Timer(250, this);

    public TrackPanel()
    {
        this.addKeyListener(this);
        this.setFocusable(true);

        this.redCar = new Car("red");
        this.greenCar = new Car("green");

        this.redCar.setLocation(425, 490);
        this.greenCar.setLocation(425, 540);

        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.timer)
        {
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setBackground(Color.gray);

        g.setColor(Color.green);
        g.fillRect(150, 200, 550, 300); // centre grass
        g.fillRect(0, 0, 900, 100); // top grass
        g.fillRect(0, 600, 900, 100); // bottom grass
        g.fillRect(0, 0, 50, 700); // left grass
        g.fillRect(800, 0, 50, 700); // right grass

        g.setColor(Color.black);
        g.drawRect(50, 100, 750, 500);  // outer edge
        g.drawRect(150, 200, 550, 300); // inner edge

        g.setColor(Color.yellow);
        g.drawRect(100, 150, 650, 400); // mid-lane marker

        g.setColor(Color.white);
        g.drawLine(425, 500, 425, 600); // start line

        this.drawRedCar(g);
        this.drawGreenCar(g);
    }

    private void drawRedCar(Graphics g)
    {
        ImageIcon red = this.redCar.getImage(this.redCar.getImageFilenameByIndex(this.redCar.getCurrentDirection()));
        red.paintIcon(this, g, this.redCar.getTrackPosition().x , this.redCar.getTrackPosition().y);
    }

    private void drawGreenCar(Graphics g)
    {
        ImageIcon green = this.greenCar.getImage(this.greenCar.getImageFilenameByIndex(this.greenCar.getCurrentDirection()));
        green.paintIcon(this, g, this.greenCar.getTrackPosition().x , this.greenCar.getTrackPosition().y);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /*
         Very primitive attempt to move the cars. The end product, once this is refactored, will
         move the car at a constant speed, handled through the JPanel refresh rate (timer), and
         will change direction by slowly incrementing and decrementing through the car image indexes
         and redefine the current polar direction according to the track.

         This does have some latency issues, and since the client is not multi-threaded, only one
         key press can be registered at a time. Thus, only one car can move at a time.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch(key)
        {
            case KeyEvent.VK_UP:
                System.out.println("Pressed - Red: Speed increase");
                this.redCar.setLocation(this.redCar.getTrackPosition().x, this.redCar.getTrackPosition().y - 10);
                this.redCar.setCurrentDirection(1);
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("Pressed - Red: Speed decrease");
                this.redCar.setLocation(this.redCar.getTrackPosition().x, this.redCar.getTrackPosition().y + 10);
                this.redCar.setCurrentDirection(9);
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("Pressed - Red: Turn left");
                this.redCar.setLocation(this.redCar.getTrackPosition().x - 10, this.redCar.getTrackPosition().y);
                this.redCar.setCurrentDirection(13);
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("Pressed - Red: Turn right");
                this.redCar.setLocation(this.redCar.getTrackPosition().x + 10, this.redCar.getTrackPosition().y);
                this.redCar.setCurrentDirection(5);
                break;
        }

        switch(key)
        {
            case KeyEvent.VK_W:
                System.out.println("Pressed - Green: Speed increase");
                this.greenCar.setLocation(this.greenCar.getTrackPosition().x, this.greenCar.getTrackPosition().y - 10);
                this.greenCar.setCurrentDirection(1);
                break;
            case KeyEvent.VK_S:
                System.out.println("Pressed - Green: Speed decrease");
                this.greenCar.setLocation(this.greenCar.getTrackPosition().x, this.greenCar.getTrackPosition().y + 10);
                this.greenCar.setCurrentDirection(9);
                break;
            case KeyEvent.VK_A:
                System.out.println("Pressed - Green: Turn left");
                this.greenCar.setLocation(this.greenCar.getTrackPosition().x - 10, this.greenCar.getTrackPosition().y);
                this.greenCar.setCurrentDirection(13);
                break;
            case KeyEvent.VK_D:
                System.out.println("Pressed - Green: Turn right");
                this.greenCar.setLocation(this.greenCar.getTrackPosition().x + 10, this.greenCar.getTrackPosition().y);
                this.greenCar.setCurrentDirection(5);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
