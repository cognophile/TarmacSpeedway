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
                this.redCar.increaseSpeed();

                this.redCar.setLocation(this.redCar.getTrackPosition().x, this.redCar.getTrackPosition().y - 10);
                break;
            case KeyEvent.VK_DOWN:
                this.redCar.decreaseSpeed();

                this.redCar.setLocation(this.redCar.getTrackPosition().x, this.redCar.getTrackPosition().y + 10);
                break;
            case KeyEvent.VK_LEFT:
                this.redCar.turnLeft();
                break;
            case KeyEvent.VK_RIGHT:
                this.redCar.turnRight();
                break;
        }

        switch(key)
        {
            case KeyEvent.VK_W:
                this.greenCar.increaseSpeed();

                this.greenCar.setLocation(this.greenCar.getTrackPosition().x, this.greenCar.getTrackPosition().y - 10);
                break;
            case KeyEvent.VK_S:
                this.greenCar.decreaseSpeed();

                this.greenCar.setLocation(this.greenCar.getTrackPosition().x, this.greenCar.getTrackPosition().y + 10);
                break;
            case KeyEvent.VK_A:
                this.greenCar.turnLeft();
                break;
            case KeyEvent.VK_D:
                this.greenCar.turnRight();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
