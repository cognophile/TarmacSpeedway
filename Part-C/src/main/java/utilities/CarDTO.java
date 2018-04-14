package main.java.utilities;

import main.java.client.Car;

import java.awt.Point;
import java.io.Serializable;

public class CarDTO implements Serializable
{
    public Point position;
    public String trajectory;
    public int orientation;
    public int speed;

    public CarDTO()
    {
    }

    public CarDTO(Point position, String trajectory, int orientation, int speed)
    {
        this.position = position;
        this.trajectory = trajectory;
        this.orientation = orientation;
        this.speed = speed;
    }
}
