package main.java;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.*;

public class UDPClientDaemon
{
    private static final String REMOTE_ADDRESS = "127.0.0.1";
    private static final int REMOTE_PORT = 2000;

    private DatagramSocket localSocket;
    private ByteArrayOutputStream dataStream;
    private PrintStream printer;

    public UDPClientDaemon()
    {
        System.out.println ("Starting client daemon...");

        try {
            this.localSocket = new DatagramSocket();
        }
        catch (SocketException ex) {
            System.err.println("CLIENT ERROR: " + ex.getMessage() + "\n" + ex.getStackTrace());
        }
        catch(Exception ex) {
            System.err.println("CLIENT ERROR: " + ex.getMessage() + "\n" + ex.getStackTrace());
        }

        this.dataStream = new ByteArrayOutputStream();
        this.printer = new PrintStream(this.dataStream);
    }

    public void send(String payload)
    {
        DatagramPacket dataPacket = this.preparePacket(payload);
        InetAddress destination = this.locateHost();

        // Address packet to sender
        if (destination != null)
        {
            dataPacket.setAddress(destination);
            dataPacket.setPort(REMOTE_PORT);

            // Send the packet - no guarantee  of delivery
            try {
                this.localSocket.send(dataPacket);
            } catch (IOException ex) {
                System.err.println("CLIENT ERROR: " + ex.getMessage() + "\n" + ex.getStackTrace());
            }
        }
        else
        {
            throw new RuntimeException("Client Error: Unable to identify destination (" + destination + ")");
        }
    }

    private DatagramPacket preparePacket(String payload)
    {
        this.printer.print(payload);

        byte[] data = this.dataStream.toByteArray();

        return new DatagramPacket(data, data.length);
    }

    private InetAddress locateHost()
    {
        InetAddress destinationAddress = null;

        try {
            destinationAddress = InetAddress.getByName(REMOTE_ADDRESS);
        } catch (UnknownHostException ex) {
            System.err.println("CLIENT ERROR: " + ex.getMessage() + "\n" + ex.getStackTrace());
        }

        return destinationAddress;
    }
}
