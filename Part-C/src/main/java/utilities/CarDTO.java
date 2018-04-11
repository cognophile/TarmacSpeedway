package main.java.utilities;

import java.awt.Point;
import java.io.Serializable;

public class CarDTO implements Serializable
{
    public Point position;
    public String trajectory;
    public int orientation;
    public int speed;
}
