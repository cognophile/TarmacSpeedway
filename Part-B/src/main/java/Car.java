package main.java;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Car
{
    private static final String RED_CAR_FILE_PREFIX = "red_small_";
    private static final String GREEN_CAR_FILE_PREFIX = "green_small_";
    private static final String IMAGE_SUFFIX = ".png";
    private Map<String, ImageIcon> carImages;
    private String COLOUR;

    public Car(String colour)
    {
        if (colour.toLowerCase() == "red") {
            this.COLOUR = colour.toLowerCase();
            this.carImages = this.loadRedCarImages();
        }

        if (colour.toLowerCase() == "green") {
            this.COLOUR = colour.toLowerCase();
            this.carImages = this.loadGreenCarImages();
        }
    }

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
     * 1 = 0 Deg., 16 = 338 Deg.
     * @param index
     * @return ImageIcon of car colour specified
     */
    public String getImageFilenameByIndex(int index)
    {
        return this.getImagePrefix() + String.valueOf(index) + this.getImageSuffix();
    }

    private String getImagePrefix()
    {
        if (this.COLOUR.toLowerCase() == "red") {
            return this.RED_CAR_FILE_PREFIX;
        }
        else {
            return this.GREEN_CAR_FILE_PREFIX;
        }
    }

    private String getImageSuffix()
    {
        return this.IMAGE_SUFFIX;
    }

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
                    "Error!",
                    JOptionPane.ERROR_MESSAGE);

            // Simulate logging the error
            System.out.println(ex.getMessage());
            return new HashMap<String, ImageIcon>();
        }
    }

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
                    "Error!",
                    JOptionPane.ERROR_MESSAGE);

            // Simulate logging the error
            System.out.println(ex.getMessage());
            return new HashMap<String, ImageIcon>();
        }
    }
}
