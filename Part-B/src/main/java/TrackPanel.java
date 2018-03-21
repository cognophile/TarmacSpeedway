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
    private static final int TOP_BARRIER = 10;
    private static final int BOTTOM_BARRIER = 615;
    private static final int LEFT_BARRIER = 0;
    private static final int RIGHT_BARRIER= 740;

    private Car redCar;
    private Car greenCar;
    private Timer timer = new Timer(175, this);

    public TrackPanel()
    {
        this.addKeyListener(this);
        this.setFocusable(true);

        this.redCar = new Car("red");
        this.greenCar = new Car("green");

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

        this.drawRedCar(g);
        this.drawGreenCar(g);
    }

    private void drawRedCar(Graphics g)
    {
        ImageIcon red = this.redCar.getImage(this.redCar.getImageFilenameByIndex(this.redCar.getImageOrientation()));
        red.paintIcon(this, g, this.redCar.getTrackPosition().x, this.redCar.getTrackPosition().y);

        if (this.redCar.getTrackPosition().x <= LEFT_BARRIER || this.redCar.getTrackPosition().x >= RIGHT_BARRIER
                || this.redCar.getTrackPosition().y <= TOP_BARRIER || this.redCar.getTrackPosition().y >= BOTTOM_BARRIER)
        {
            this.redCar.stop();
        }
        else {
            this.redCar.move();
        }
    }

    private void drawGreenCar(Graphics g)
    {
        ImageIcon green = this.greenCar.getImage(this.greenCar.getImageFilenameByIndex(this.greenCar.getImageOrientation()));
        green.paintIcon(this, g, this.greenCar.getTrackPosition().x, this.greenCar.getTrackPosition().y);

        if (this.greenCar.getTrackPosition().x <= LEFT_BARRIER || this.greenCar.getTrackPosition().x >= RIGHT_BARRIER
                || this.greenCar.getTrackPosition().y <= TOP_BARRIER || this.greenCar.getTrackPosition().y >= BOTTOM_BARRIER)
        {
            this.greenCar.stop();
        }
        else {
            this.greenCar.move();
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_R)
        {
            this.redCar.reset();
            this.greenCar.reset();
        }

        switch(key)
        {
            case KeyEvent.VK_UP:
                this.redCar.increaseSpeed();
                break;
            case KeyEvent.VK_DOWN:
                this.redCar.decreaseSpeed();
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
                break;
            case KeyEvent.VK_S:
                this.greenCar.decreaseSpeed();
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
    public void keyReleased(KeyEvent e)
    {

    }
}
