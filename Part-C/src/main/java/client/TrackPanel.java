package main.java.client;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TrackPanel extends JPanel implements ActionListener, KeyListener
{
    private static final int TOP_BARRIER = 10;
    private static final int BOTTOM_BARRIER = 615;
    private static final int LEFT_BARRIER = 0;
    private static final int RIGHT_BARRIER= 760;

    private Car locallyControlledCar;
    private Car remoteControlledCar;
    private Timer timer = new Timer(175, this);

    public TrackPanel(String selectedColour)
    {
        this.addKeyListener(this);
        this.setFocusable(true);

        if (selectedColour.equals("red")) {
            this.locallyControlledCar = new RedCar();
            this.remoteControlledCar = new GreenCar();
        } else {
            this.locallyControlledCar = new GreenCar();
            this.remoteControlledCar = new RedCar();
        }

        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == this.timer)
        {
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        this.setBackground(Color.gray);

        g.setColor(Color.green);
        g.fillRect(150, 200, 550, 300); // centre grass
        g.fillRect(0, 0, 900, 100); // top grass
        g.fillRect(0, 600, 900, 100); // bottom grass
        g.fillRect(0, 0, 50, 700); // left grass
        g.fillRect(800, 0, 100, 700); // right grass

        g.setColor(Color.black);
        g.drawRect(50, 100, 750, 500);  // outer edge
        g.drawRect(150, 200, 550, 300); // inner edge

        g.setColor(Color.yellow);
        g.drawRect(100, 150, 650, 400); // mid-lane marker

        g.setColor(Color.white);
        g.drawLine(425, 500, 425, 600); // start line

        g.setColor(Color.red);
        g.fillRect(0, 0, 900, 10); // top barrier
        g.fillRect(0, 690, 900, 10); // bottom barrier
        g.fillRect(0, 0, 10, 700); // left barrier
        g.fillRect(835, 0, 10, 700); // right barrier

        this.drawLocalCar(g);
        this.drawRemoteCar(g);
    }

    private void drawLocalCar(Graphics g)
    {
        String filename = this.locallyControlledCar.getImageFilenameByIndex(this.locallyControlledCar.getImageOrientation());
        ImageIcon controlled = this.locallyControlledCar.getImage(filename);
        controlled.paintIcon(this, g, this.locallyControlledCar.getTrackPosition().x, this.locallyControlledCar.getTrackPosition().y);

        if (this.locallyControlledCar.getTrackPosition().x <= LEFT_BARRIER ||
                this.locallyControlledCar.getTrackPosition().x >= RIGHT_BARRIER ||
                this.locallyControlledCar.getTrackPosition().y <= TOP_BARRIER ||
                this.locallyControlledCar.getTrackPosition().y >= BOTTOM_BARRIER)
        {
            this.locallyControlledCar.stop();
        }
        else {
            this.locallyControlledCar.move();
        }
    }

    private void drawRemoteCar(Graphics g)
    {
        String filename = this.remoteControlledCar.getImageFilenameByIndex(this.remoteControlledCar.getImageOrientation());
        ImageIcon remote = this.remoteControlledCar.getImage(filename);
        remote.paintIcon(this, g, this.remoteControlledCar.getTrackPosition().x, this.remoteControlledCar.getTrackPosition().y);

        if (this.remoteControlledCar.getTrackPosition().x <= LEFT_BARRIER ||
                this.remoteControlledCar.getTrackPosition().x >= RIGHT_BARRIER ||
                this.remoteControlledCar.getTrackPosition().y <= TOP_BARRIER ||
                this.remoteControlledCar.getTrackPosition().y >= BOTTOM_BARRIER)
        {
            this.remoteControlledCar.stop();
        }
        else {
            this.remoteControlledCar.move();
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_R)
        {
            this.locallyControlledCar.reset();
            this.remoteControlledCar.reset();
        }

        switch(key)
        {
            case KeyEvent.VK_W:
                this.locallyControlledCar.increaseSpeed();
                break;
            case KeyEvent.VK_S:
                this.locallyControlledCar.decreaseSpeed();
                break;
            case KeyEvent.VK_A:
                this.locallyControlledCar.turnLeft();
                break;
            case KeyEvent.VK_D:
                this.locallyControlledCar.turnRight();
                break;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }
}
