package main.java.utilities;

public abstract class Helper
{
    public static boolean isNotNull(Object arg)
    {
        return (arg != null);
    }

    public static boolean isInteger(String str)
    {
        try {
            int n = Integer.parseInt(str);
        }
        catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
}
