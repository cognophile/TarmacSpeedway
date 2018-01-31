package main.java;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GraphicalPanel extends JPanel
{
    private static final String RESOURCE_IMAGES = "\\src\\main\\resources\\images\\red";
    private Map<String, ImageIcon> raceCar;

    public void GraphicalPanel()
    {
    }

    public void loadImages()
    {
        this.raceCar = this.importImages();
    }

    /**
     * Import the images found in 'resources\images\red'
     * @return Map of image filesnames (Strings) to a representation of the corresponding image (ImageIcon)
     */
    private Map<String, ImageIcon> importImages()
    {
        // @TODO: Refactor for Linux/Mac paths support
        String path = new File("").getAbsolutePath();
        File imageDirectory = new File(path + RESOURCE_IMAGES);
        Map<String, ImageIcon> iconMap = new HashMap<>();

        for (File file : imageDirectory.listFiles())
        {
            if(file.getName().toLowerCase().endsWith(".png"))
            {
                iconMap.put(file.getName(), new ImageIcon(file.getPath()));
            }
        }

        return iconMap;
    }
}
