package main.java.utilities;

import java.util.Arrays;

public abstract class ErrorLogger
{
    public static void toConsole(Exception ex)
    {
        StringBuilder stackTrace = new StringBuilder();
        for (StackTraceElement element : ex.getStackTrace()) {
            stackTrace.append(element.toString());
            stackTrace.append("\n");
        }
        System.out.println(ex.getMessage() + "\n" + stackTrace.toString());
    }
}

