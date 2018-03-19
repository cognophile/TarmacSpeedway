package main.java;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Car
{
    private static final String RED_CAR_FILE_PREFIX = "red_small_";
    private static final String GREEN_CAR_FILE_PREFIX = "green_small_";
    private static final String IMAGE_SUFFIX = ".png";

    private Map<String, ImageIcon> carImages;
    private String COLOUR;
    private int speed = 0;
    private int activeOrientation = 13;
    private Point trackPosition = new Point();
    private String trajectory = "left";

    public Car(String colour)
    {
        if (colour.toLowerCase().equals("red")) {
            this.COLOUR = colour.toLowerCase();
            this.carImages = this.loadRedCarImages();
        }

        if (colour.toLowerCase().equals("green")) {
            this.COLOUR = colour.toLowerCase();
            this.carImages = this.loadGreenCarImages();
        }
    }

    /**
     * Retrieve all car images for the set colour of car
     * @return Map<String, ImageIcon> Map of image filenames to their image icon objects
     */
    public Map<String, ImageIcon> getAllImages()
    {
        return this.carImages;
    }

    /**
     * Get an image of the car, by filename.
     * @param key
     * @return ImageIcon of car colour specified
     */
    public ImageIcon getImage(String key)
    {
        return this.carImages.get(key);
    }

    /**
     * Get the name of an image, by index number
     * 1 = 0 Deg., 16 = 338 Deg., by 22 Deg.
     * @param index Number index of the image to load
     * @return ImageIcon of car colour specified
     */
    public String getImageFilenameByIndex(int index)
    {
        return this.getImagePrefix() + String.valueOf(index) + this.getImageSuffix();
    }

    /**
     * Increase the speed at which the cars moves
     */
    public void increaseSpeed()
    {
        if (this.speed >= 0 && this.speed < 100) {
            this.speed += 10;
        }
    }

    /**
     * Decrease the speed at which the car moves
     */
    public void decreaseSpeed()
    {
        if (this.speed > 0 && this.speed < 100) {
            this.speed -= 10;
        }
    }

    /**
     * Set the direction of travel for this car object.
     * @param trajectory String The direction for the car image to move (up, down, left, right)
     */
    public void setTrajectory(String trajectory)
    {
        this.trajectory = trajectory;
    }

    /**
     * Adjust this objects XY position relative to its speed, according to its trajectory.
     */
    public void drive()
    {
        if (this.trajectory.equals("up"))
        {
            this.setLocation(this.getTrackPosition().x, this.getTrackPosition().y - 2 * this.speed);
        }

        if (this.trajectory.equals("down"))
        {
            this.setLocation(this.getTrackPosition().x, this.getTrackPosition().y + 2 * this.speed);
        }

        if (this.trajectory.equals("left"))
        {
            this.setLocation(this.getTrackPosition().x - 2 * this.speed, this.getTrackPosition().y);
        }

        if (this.trajectory.equals("right"))
        {
            this.setLocation(this.getTrackPosition().x + 2 * this.speed, this.getTrackPosition().y);
        }
    }

    /**
     * Rotate the cars current image left and direction of travel accordingly
     */
    public void turnLeft()
    {
        // Todo: Iterate through the images between these bounds to make the turn gradual
        if (this.activeOrientation == 1) {
            this.setImageOrientation(13);
            this.setTrajectory("left");
            return;
        }

        if (this.activeOrientation == 5) {
            this.setImageOrientation(1);
            this.setTrajectory("up");
            return;
        }

        if (this.activeOrientation == 9) {
            this.setImageOrientation(5);
            this.setTrajectory("right");
            return;
        }

        if (this.activeOrientation == 13) {
            this.setImageOrientation(9);
            this.setTrajectory("down");
            return;
        }
    }

    /**
     * Rotate the cars current image right and direction of travel accordingly
     */
    public void turnRight()
    {
        // Todo: Iterate through the images between these bounds to make the turn gradual
        if (this.activeOrientation == 1) {
            this.setImageOrientation(5);
            this.setTrajectory("right");

            return;
        }

        if (this.activeOrientation == 5) {
            this.setImageOrientation(9);
            this.setTrajectory("down");
            return;
        }

        if (this.activeOrientation == 9) {
            this.setImageOrientation(13);
            this.setTrajectory("left");
            return;
        }

        if (this.activeOrientation == 13) {
            this.setImageOrientation(1);
            this.setTrajectory("up");
            return;
        }
    }

    public void setLocation(int x, int y)
    {
        this.trackPosition.setLocation(x, y);
    }

    public Point getTrackPosition()
    {
        return this.trackPosition.getLocation();
    }

    public int getImageOrientation()
    {
        return this.activeOrientation;
    }

    private void setImageOrientation(int orientation)
    {
        this.activeOrientation = orientation;
    }

    /**
     * Determine which filename prefix this object has by the colour car it represents
     * @return String car image filename prefix
     */
    private String getImagePrefix()
    {
        if (this.COLOUR.toLowerCase().equals("red")) {
            return RED_CAR_FILE_PREFIX;
        }
        else {
            return GREEN_CAR_FILE_PREFIX;
        }
    }

    private String getImageSuffix()
    {
        return IMAGE_SUFFIX;
    }

    /**
     * Load all red car images into the object
     * @return Map<String, ImageIcon> Map of all red car filenames to their image icon objects
     */
    private Map<String, ImageIcon> loadRedCarImages()
    {
        try
        {
            return ImageLoader.loadRedCar();
        }
        catch (UnsupportedOperationException ex)
        {
            // Inform the user an error occurred.
            JOptionPane.showMessageDialog(null,
                    "ERROR: This operating system is not supported!\n" + ex.getMessage(),
                    "Error!", JOptionPane.ERROR_MESSAGE);

            // Simulate logging the error
            System.out.println(ex.getMessage());
            return new HashMap<String, ImageIcon>();
        }
    }

    /**
     * Load all green car images into the object
     * @return Map<String, ImageIcon> Map of all green car filenames to their image icon objects
     */
    private Map<String, ImageIcon> loadGreenCarImages()
    {
        try
        {
            return ImageLoader.loadGreenCar();
        }
        catch (UnsupportedOperationException ex)
        {
            // Inform the user an error occurred.
            JOptionPane.showMessageDialog(null,
                    "ERROR: This operating system is not supported!\n" + ex.getMessage(),
                    "Error!", JOptionPane.ERROR_MESSAGE);

            // Simulate logging the error
            System.out.println(ex.getMessage());
            return new HashMap<String, ImageIcon>();
        }
    }
}
