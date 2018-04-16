package main.java.utilities;

import main.java.client.Car;

import java.awt.Point;
import java.io.Serializable;

public class CarDTO implements Serializable
{
    public Point position = new Point(0,0);
    public String trajectory = "left";
    public int orientation = 13;
    public int speed = 0;
}
