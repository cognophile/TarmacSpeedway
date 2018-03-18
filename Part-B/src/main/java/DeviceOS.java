package main.java;

import javax.swing.*;

public abstract class DeviceOS
{
    public static boolean isUnix()
    {
        return (operatingSystemInfo().toLowerCase().contains("linux") || operatingSystemInfo().toLowerCase().contains("unix"));
    }

    public static boolean isMac()
    {
        return (operatingSystemInfo().toLowerCase().contains("mac os x"));
    }

    public static boolean isWindows()
    {
        return (operatingSystemInfo().toLowerCase().contains("windows"));
    }

    private static String operatingSystemInfo()
    {
        String osName = "";
        try {
            osName = System.getProperty("os.name");
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR: Could not determine host platform!\n" + ex.getMessage(), "Error!",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        return osName;
    }
}
