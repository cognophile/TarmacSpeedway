package main.java;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class ImageLoader
{
    private static final String RESOURCE_IMAGES_UNIX = "/src/main/resources/images/red";
    private static final String RESOURCE_IMAGES_WINDOWS = "\\src\\main\\resources\\images\\red";

    /**
     * Import the images found in 'resources\images\red'
     * @throws if the device OS is not Unix-based or Windows, throw an UnsupportedOperationException
     * @return Map of image filesnames (Strings) to a representation of the corresponding image (ImageIcon)
     */
    public static Map<String, ImageIcon> importImages() throws UnsupportedOperationException
    {
        File imageDirectory = null;
        Map<String, ImageIcon> iconMap = new HashMap<>();
        String path = new File("").getAbsolutePath();

        if (DeviceOS.isUnix() || DeviceOS.isMac())
        {
            imageDirectory = new File(path + RESOURCE_IMAGES_UNIX);
        }

        if (DeviceOS.isWindows())
        {
            imageDirectory = new File(path + RESOURCE_IMAGES_WINDOWS);
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
                    iconMap.put(file.getName(), new ImageIcon(file.getPath()));
                }
            }
        }

        return iconMap;
    }
}
