package main.java;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/* TODO - Refactor class into Red/Green subclasses of Car */
public class Car
{
    private static final String RED_CAR_FILE_PREFIX = "red_small_";
    private static final String GREEN_CAR_FILE_PREFIX = "green_small_";
    private static final String IMAGE_SUFFIX = ".png";

    private static final int DIRECTION_NORTH = 1;
    private static final int DIRECTION_EAST = 5;
    private static final int DIRECTION_SOUTH = 9;
    private static final int DIRECTION_WEST = 13;
    private static final int FINAL_IMAGE_ROTATION_INDEX = 16;

    private Map<String, ImageIcon> carImages;
    private String COLOUR;
    private int speed = 0;
    private int activeOrientation = 13;
    private String trajectory = "left";
    private Point trackPosition = new Point();

    public Car(String colour)
    {
        if (colour.toLowerCase().equals("red"))
        {
            this.COLOUR = colour.toLowerCase();
            this.carImages = this.loadRedCarImages();
            this.setTrackPosition(425, 490);
        }

        if (colour.toLowerCase().equals("green"))
        {
            this.COLOUR = colour.toLowerCase();
            this.carImages = this.loadGreenCarImages();
            this.setTrackPosition(425, 540);
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
        if (this.speed >= 0 && this.speed < 100)
        {
            this.speed += 10;
        }
    }

    /**
     * Decrease the speed at which the car moves
     */
    public void decreaseSpeed()
    {
        if (this.speed > 0 && this.speed < 100)
        {
            this.speed -= 10;
        }
    }

    public void stop()
    {
        this.speed = 0;
    }

    /**
     * Adjust this objects XY position relative to its speed, according to its trajectory.
     */
    public void move()
    {
        if (this.trajectory.equals("up"))
        {
            this.setTrackPosition(this.getTrackPosition().x, this.getTrackPosition().y - 2 * this.speed);
        }

        if (this.trajectory.equals("down"))
        {
            this.setTrackPosition(this.getTrackPosition().x, this.getTrackPosition().y + 2 * this.speed);
        }

        if (this.trajectory.equals("left"))
        {
            this.setTrackPosition(this.getTrackPosition().x - 2 * this.speed, this.getTrackPosition().y);
        }

        if (this.trajectory.equals("right"))
        {
            this.setTrackPosition(this.getTrackPosition().x + 2 * this.speed, this.getTrackPosition().y);
        }
    }

    /**
     * Rotate the cars current image left and direction of travel accordingly
     */
    public void turnLeft()
    {
        // Subtract a scalar constant to have next loaded image be the first non-absolute orientation in this turn
        if (this.activeOrientation == DIRECTION_NORTH)
        {
            for (int i = FINAL_IMAGE_ROTATION_INDEX; i >= DIRECTION_WEST; i--)
            {
                this.setImageOrientation(i);
            }

            this.setTrajectory("left");
            return;
        }

        if (this.activeOrientation == DIRECTION_EAST)
        {
            for (int i = this.activeOrientation - 1; i >= DIRECTION_NORTH; i--)
            {
                this.setImageOrientation(i);
            }

            this.setTrajectory("up");
            return;
        }

        if (this.activeOrientation == DIRECTION_SOUTH)
        {
            for (int i = this.activeOrientation - 1; i >= DIRECTION_EAST; i--)
            {
                this.setImageOrientation(i);
            }

            this.setTrajectory("right");
            return;
        }

        if (this.activeOrientation == DIRECTION_WEST)
        {
            for (int i = this.activeOrientation - 1; i >= DIRECTION_SOUTH; i--)
            {
                this.setImageOrientation(i);
            }

            this.setTrajectory("down");
            return;
        }
    }

    /**
     * Rotate the cars current image right and direction of travel accordingly
     */
    public void turnRight()
    {
        // Add a scalar constant to have next loaded image be the first non-absolute orientation in this turn
        if (this.activeOrientation == DIRECTION_NORTH)
        {
            for (int i = this.activeOrientation + 1; i <= DIRECTION_EAST; i++)
            {
                this.setImageOrientation(i);
            }

            this.setTrajectory("right");
            return;
        }

        if (this.activeOrientation == DIRECTION_EAST)
        {
            for (int i = this.activeOrientation + 1; i <= DIRECTION_SOUTH; i++)
            {
                this.setImageOrientation(i);
            }

            this.setTrajectory("down");
            return;
        }

        if (this.activeOrientation == DIRECTION_SOUTH)
        {
            for (int i = this.activeOrientation + 1; i <= DIRECTION_WEST; i++)
            {
                this.setImageOrientation(i);
            }

            this.setTrajectory("left");
            return;
        }

        if (this.activeOrientation == DIRECTION_WEST)
        {
            for (int i = this.activeOrientation + 1; i <= FINAL_IMAGE_ROTATION_INDEX; i++)
            {
                if (i == FINAL_IMAGE_ROTATION_INDEX) {
                    this.setImageOrientation(DIRECTION_NORTH);
                }
                else {
                    this.setImageOrientation(i);
                }
            }

            this.setTrajectory("up");
            return;
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

    public void setTrackPosition(int x, int y)
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

    public void reset()
    {
        if (this.COLOUR.equals("red"))
        {
            this.setTrackPosition(425, 490);
        }

        if (this.COLOUR.equals("green"))
        {
            this.setTrackPosition(425, 540);
        }

        this.speed = 0;
        this.trajectory = "left";
        this.setImageOrientation(DIRECTION_WEST);
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
