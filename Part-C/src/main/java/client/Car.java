package main.java.client;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

abstract public class Car
{
    private static final String IMAGE_SUFFIX = ".png";

    private static final int DIRECTION_NORTH = 1;
    private static final int DIRECTION_EAST = 5;
    private static final int DIRECTION_SOUTH = 9;
    private static final int DIRECTION_WEST = 13;
    private static final int FINAL_IMAGE_ROTATION_INDEX = 16;

    private int speed = 0;
    private int activeOrientation = 13;
    private String trajectory = "left";
    private Point trackPosition = new Point();

    protected Map<String, ImageIcon> images;
    protected Point startPosition = new Point();

    abstract public String getImageFilenameByIndex(int index);

    /**
     * Retrieve all car images for the set colour of car
     * @return Map<String, ImageIcon> Map of image filenames to their image icon objects
     */
    public final Map<String, ImageIcon> getAllImages()
    {
        return this.images;
    }

    /**
     * Get an image of the car, by filename.
     * @param key integer index of car to retrieve
     * @return ImageIcon of car colour specified
     */
    public final ImageIcon getImage(String key)
    {
        return this.images.get(key);
    }

    /**
     * Reset this car to the start line
     * @return void
     */
    public final void reset()
    {
        this.speed = 0;
        this.trajectory = "left";
        this.setImageOrientation(DIRECTION_WEST);
        this.setTrackPosition(this.startPosition.x, this.startPosition.y);
    }

    /**
     * Increase the speed at which the cars moves
     * @return void
     */
    public final void increaseSpeed()
    {
        if (this.speed >= 0 && this.speed < 100)
        {
            this.speed += 10;
        }
    }

    /**
     * Decrease the speed at which the car moves
     * @return void
     */
    public final void decreaseSpeed()
    {
        if (this.speed > 0 && this.speed < 100)
        {
            this.speed -= 10;
        }
    }

    public final void stop()
    {
        this.speed = 0;
    }

    /**
     * Adjust this objects XY position relative to its speed, according to its trajectory.
     * @return void
     */
    public final void move()
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
     * @return void
     */
    public final void turnLeft()
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
     * @return void
     */
    public final void turnRight()
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
     * Determine whether this car has crashed into a safety barrier
     * @return boolean
     */
    public boolean isCrashed()
    {
        if (this.getTrackPosition().x <= TrackPanel.LEFT_BARRIER || this.getTrackPosition().x >= TrackPanel.RIGHT_BARRIER ||
                this.getTrackPosition().y <= TrackPanel.TOP_BARRIER || this.getTrackPosition().y >= TrackPanel.BOTTOM_BARRIER)
        {
            return true;
        }

        return false;
    }

    /**
     * Determine whether this car has collided with another
     * @param Car otherCar
     * @return boolean
     */
    public boolean hasCollided(Car otherCar)
    {
        Rectangle firstCarRectangle = new Rectangle(
                this.getTrackPosition().x,
                this.getTrackPosition().y,
                45, 25
        );

        Rectangle secondCarRectangle  = new Rectangle(
                otherCar.getTrackPosition().x,
                otherCar.getTrackPosition().y,
                45, 25
        );

        if (firstCarRectangle.intersects(secondCarRectangle) || secondCarRectangle.intersects(firstCarRectangle)) {
            return true;
        }

        return false;
    }

    /**
     * Set the direction of travel for this car object.
     * @param trajectory String The direction for the car image to move (up, down, left, right)
     * @return void
     */
    public final void setTrajectory(String trajectory)
    {
        this.trajectory = trajectory;
    }

    public final String getTrajectory()
    {
        return this.trajectory;
    }

    public final void setTrackPosition(int x, int y)
    {
        this.trackPosition.setLocation(x, y);
    }

    public final Point getTrackPosition()
    {
        return this.trackPosition.getLocation();
    }

    public final int getImageOrientation()
    {
        return this.activeOrientation;
    }

    public final int getSpeed()
    {
        return this.speed;
    }

    public final void setSpeed(int speed)
    {
        this.speed = speed;
    }

    protected final void setImageOrientation(int orientation)
    {
        this.activeOrientation = orientation;
    }

    protected final String getImageSuffix()
    {
        return IMAGE_SUFFIX;
    }
}
