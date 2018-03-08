package main.java;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class ImageLoader
{
    private static final String RESOURCE_RED_CAR_IMAGES_UNIX = "/src/main/resources/images/red";
    private static final String RESOURCE_GREEN_CAR_IMAGES_UNIX = "/src/main/resources/images/green";
    private static final String RESOURCE_RED_CAR_IMAGES_WINDOWS = "\\src\\main\\resources\\images\\red";
    private static final String RESOURCE_GREEN_CAR_IMAGES_WINDOWS = "\\src\\main\\resources\\images\\green";

    /**
     * Import the images found in 'resources\images\green', independent of platform.
     * @throws if the device OS is not Unix-based or Windows, throw an UnsupportedOperationException
     * @return Map of image filesnames (Strings) to a representation of the corresponding image (ImageIcon)
     */
    public final static Map<String, ImageIcon> loadRedCar() throws UnsupportedOperationException
    {
        File imageDirectory = null;
        Map<String, ImageIcon> carImageMap = new HashMap<>();

        if (DeviceOS.isUnix() || DeviceOS.isMac())
        {
            imageDirectory = ImageLoader.getRedCarImagePath(RESOURCE_RED_CAR_IMAGES_UNIX);
        }

        if (DeviceOS.isWindows())
        {
            imageDirectory = ImageLoader.getRedCarImagePath(RESOURCE_RED_CAR_IMAGES_WINDOWS);
        }

        if (imageDirectory == null)
        {
            throw new UnsupportedOperationException("This operating system is not supported.");
        }
        else
        {
            for (File file : imageDirectory.listFiles())
            {
                if(file.getName().toLowerCase().endsWith(".png"))
                {
                    carImageMap.put(file.getName(), new ImageIcon(file.getPath()));
                }
            }
        }

        return carImageMap;
    }

    /**
     * Import the images found in 'resources\images\green', independent of platform.
     * @throws if the device OS is not Unix-based or Windows, throw an UnsupportedOperationException
     * @return Map of image filesnames (Strings) to a representation of the corresponding image (ImageIcon)
     */
    public final static Map<String, ImageIcon> loadGreenCar() throws UnsupportedOperationException
    {
        File imageDirectory = null;
        Map<String, ImageIcon> carImageMap = new HashMap<>();

        if (DeviceOS.isUnix() || DeviceOS.isMac())
        {
            imageDirectory = ImageLoader.getGreenCarImagePath(RESOURCE_GREEN_CAR_IMAGES_UNIX);
        }

        if (DeviceOS.isWindows())
        {
            imageDirectory = ImageLoader.getGreenCarImagePath(RESOURCE_GREEN_CAR_IMAGES_WINDOWS);
        }

        if (imageDirectory == null)
        {
            throw new UnsupportedOperationException("This operating system is not supported.");
        }
        else
        {
            for (File file : imageDirectory.listFiles())
            {
                if(file.getName().toLowerCase().endsWith(".png"))
                {
                    carImageMap.put(file.getName(), new ImageIcon(file.getPath()));
                }
            }
        }

        return carImageMap;
    }

    private final static File getRedCarImagePath(String resourcePath)
    {
        String systemPath = new File("").getAbsolutePath();
        return new File(systemPath + resourcePath);
    }

    private final static File getGreenCarImagePath(String resourcePath)
    {
        String systemPath = new File("").getAbsolutePath();
        return new File(systemPath + resourcePath);
    }
}
