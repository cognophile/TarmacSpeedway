package main.java.utilities;

import java.util.Arrays;

public abstract class ErrorLogger
{
    public static void toConsole(Exception ex)
    {
        System.out.println(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
    }
}
