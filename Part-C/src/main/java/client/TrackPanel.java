package main.java.client;

import main.java.utilities.CarDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TrackPanel extends JPanel implements ActionListener, KeyListener
{
    public static final int TOP_BARRIER = 10;
    public static final int BOTTOM_BARRIER = 615;
    public static final int LEFT_BARRIER = 0;
    public static final int RIGHT_BARRIER= 760;

    private Car localCar;
    private Car remoteCar;
    private SocketCommunicationManager remoteConnection;
    private Timer timer = new Timer(175, this);

    /**
     * Create a TrackPanel instance to render track and create Cars
     * @param remoteConnection
     * @param selectedColour
     */
    public TrackPanel(SocketCommunicationManager remoteConnection, Color selectedColour)
    {
        this.addKeyListener(this);
        this.setFocusable(true);

        this.remoteConnection = remoteConnection;

        if (selectedColour.equals(Color.red)) {
            this.localCar = new RedCar();
            this.remoteCar = new GreenCar();
        } else {
            this.localCar = new GreenCar();
            this.remoteCar = new RedCar();
        }

        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == this.timer) {
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W:
                this.localCar.increaseSpeed();
                break;
            case KeyEvent.VK_S:
                this.localCar.decreaseSpeed();
                break;
            case KeyEvent.VK_A:
                this.localCar.turnLeft();
                break;
            case KeyEvent.VK_D:
                this.localCar.turnRight();
                break;
            case KeyEvent.VK_R:
                this.remoteConnection.send("reset");
                this.localCar.reset();
                break;
            case KeyEvent.VK_ESCAPE:
                this.remoteConnection.send("exit");
                this.remoteConnection.close();
                this.closeClient();
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

    private void drawLocalCar(Graphics g) {
        String filename = this.localCar.getImageFilenameByIndex(this.localCar.getImageOrientation());
        ImageIcon controlled = this.localCar.getImage(filename);
        controlled.paintIcon(this, g, this.localCar.getTrackPosition().x, this.localCar.getTrackPosition().y);

        CarDTO localCarDTO = new CarDTO();
        if (this.localCar.isCrashed() || this.localCar.hasCollided(this.remoteCar)) {
            this.localCar.stop();
        } else {
            this.localCar.move();
        }

        localCarDTO.speed = this.localCar.getSpeed();
        localCarDTO.position = this.localCar.getTrackPosition();
        localCarDTO.trajectory = this.localCar.getTrajectory();
        localCarDTO.orientation = this.localCar.getImageOrientation();
        this.remoteConnection.send(localCarDTO);
    }


    private void drawRemoteCar(Graphics g)
    {
        Object response = this.remoteConnection.getRemoteState();

        if (response instanceof String) {
            switch ((String)response) {
                case "reset":
                    this.localCar.reset();
                    break;
                case "exit":
                    this.closeClient();
                    break;
            }
        }
        else {
            CarDTO newState = (CarDTO)response;
            this.remoteCar.setTrackPosition(newState.position.x, newState.position.y);
            this.remoteCar.setSpeed(newState.speed);
            this.remoteCar.setImageOrientation(newState.orientation);
        }

        String filename = this.remoteCar.getImageFilenameByIndex(this.remoteCar.getImageOrientation());
        ImageIcon remote = this.remoteCar.getImage(filename);
        remote.paintIcon(this, g, this.remoteCar.getTrackPosition().x, this.remoteCar.getTrackPosition().y);
    }

    private void closeClient()
    {
        JFrame client = (JFrame) SwingUtilities.windowForComponent(this);
        client.dispatchEvent(new WindowEvent(client, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }
}
