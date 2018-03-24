package main.java;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class ImageLoader
{
    private static final String RESOURCE_IMAGES_RED_CAR_NIX = "images/red";
    private static final String RESOURCE_IMAGES_GREEN_CAR_NIX = "images/green";
    private static final String RESOURCE_IMAGES_RED_CAR_WIN = "images\\red";
    private static final String RESOURCE_IMAGES_GREEN_CAR_WIN = "images\\green";

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
            imageDirectory = ImageLoader.createPathToImageDirectoryUnix(RESOURCE_IMAGES_RED_CAR_NIX);
        }

        if (DeviceOS.isWindows())
        {
            imageDirectory = ImageLoader.createPathToImageDirectoryWindows(RESOURCE_IMAGES_RED_CAR_WIN);
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
            imageDirectory = ImageLoader.createPathToImageDirectoryUnix(RESOURCE_IMAGES_GREEN_CAR_NIX);
        }

        if (DeviceOS.isWindows())
        {
            imageDirectory = ImageLoader.createPathToImageDirectoryWindows(RESOURCE_IMAGES_GREEN_CAR_WIN);
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

    private final static File createPathToImageDirectoryWindows(String imagePath)
    {
        String resourceUri = ImageLoader.class.getResource("../resources/").toString();
        String resourcePath = resourceUri.substring(resourceUri.indexOf("/")+1);

        return new File(resourcePath + imagePath);
    }

    private final static File createPathToImageDirectoryUnix(String imagePath)
    {
        String resourceUri = ImageLoader.class.getResource("../resources/").toString();
        String resourcePath = resourceUri.substring(resourceUri.indexOf("/")+1);

        return new File("/" + resourcePath + imagePath);
    }
}
