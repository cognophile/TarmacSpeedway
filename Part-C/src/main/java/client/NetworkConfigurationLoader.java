package main.java.client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public abstract class NetworkConfigurationLoader
{
    /**
     * Fetch the IP location of the Server to communicate with, found resources/remoteConfiguration.txt
     * @return String containing remote server IP
     * @throws IOException Cannot parse the contents of the file
     * @throws FileNotFoundException Cannot find or identify the file
     */
    public static String getRemoteAddress() throws IOException, FileNotFoundException
    {
        String fileContents = NetworkConfigurationLoader.readFileContents();
        String[] remoteAddressKey = fileContents.split(":");
        return remoteAddressKey[1];
    }

    private static String readFileContents() throws IOException
    {
        FileReader handle = NetworkConfigurationLoader.loadConfigFilepath();
        BufferedReader fileReader = new BufferedReader(handle);
        StringBuilder contents = new StringBuilder();

        String line;
        while ((line = fileReader.readLine()) != null)
        {
            contents.append(line);
            contents.append("::");
        }

        fileReader.close();
        return contents.toString();
    }

    private static FileReader loadConfigFilepath() throws FileNotFoundException
    {
        String resourceUri = ImageLoader.class.getResource("../../resources/").toString();
        String resourcePath = resourceUri.substring(resourceUri.indexOf("/")+1);

        return new FileReader("/" + resourcePath + "remoteConfiguration.txt");
    }
}
