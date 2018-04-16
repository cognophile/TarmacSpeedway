package main.java.utilities;

public abstract class Helper
{
    /**
     * Determine whether the supplied reference object is Null
     * @param arg
     * @return
     */
    public static boolean isNotNull(Object arg)
    {
        return (arg != null);
    }

    /**
     * Determine whether the supplied String is an Integer
     * @param str A string representing a whole number
     * @return boolean True if the String contents are an Integer
     */
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
