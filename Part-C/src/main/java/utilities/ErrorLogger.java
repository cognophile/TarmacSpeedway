package main.java.utilities;

import java.util.Arrays;

public abstract class ErrorLogger
{
    /**
     * Log the exception  type, message, and stack trace to the console
     * @param ex The exception object
     */
    public static void toConsole(Exception ex)
    {
        StringBuilder stackTrace = new StringBuilder();

        for (StackTraceElement element : ex.getStackTrace())
        {
            stackTrace.append(element.toString());
            stackTrace.append("\n");
        }

        System.out.println(ex.getMessage() + "\n" + stackTrace.toString());
    }
}

