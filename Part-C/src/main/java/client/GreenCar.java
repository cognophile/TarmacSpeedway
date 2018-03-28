package main.java.client;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class GreenCar extends Car
{
    private static final String GREEN_CAR_FILE_PREFIX = "green_small_";

    public GreenCar()
    {
        this.images = this.loadImages();
        this.setStartPosition();
        this.setTrackPosition(this.startPosition.x, this.startPosition.y);
    }

    private final void setStartPosition()
    {
        this.startPosition.setLocation(425, 540);
    }

    /**
     * Load all green car images into the object
     * @return Map<String, ImageIcon> Map of all green car filenames to their image icon objects
     */
    private final Map<String, ImageIcon> loadImages()
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

    /**
     * Get the name of an image, by index number
     * 1 = 0 Deg., 16 = 338 Deg., by 22 Deg.
     * @param index Number index of the image to load
     * @return ImageIcon of car colour specified
     */
    public final String getImageFilenameByIndex(int index)
    {
        return GREEN_CAR_FILE_PREFIX + String.valueOf(index) + super.getImageSuffix();
    }
}
