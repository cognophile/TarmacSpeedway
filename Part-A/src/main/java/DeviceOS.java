package main.java;

public abstract class DeviceOS
{
    public static boolean isUnix()
    {
        return (operatingSystemInfo().toLowerCase().indexOf("linux") >= 0 || operatingSystemInfo().toLowerCase().indexOf("unix") >= 0);
    }

    public static boolean isMac()
    {
        return (operatingSystemInfo().toLowerCase().indexOf("mac os x") >= 0);
    }

    public static boolean isWindows()
    {
        return (operatingSystemInfo().toLowerCase().indexOf("windows") >= 0);
    }

    private static String operatingSystemInfo()
    {
        String osName = "";
        try {
            osName = System.getProperty("os.name");
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }

        return osName;
    }
}
