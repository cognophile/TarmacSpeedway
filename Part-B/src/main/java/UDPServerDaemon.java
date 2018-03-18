package main.java;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServerDaemon
{
    private DatagramSocket localSocket;
    private DatagramPacket dataPacket;
    private ByteArrayInputStream inputStream;
    private InetAddress senderAddress;
    private int senderPort;

    public UDPServerDaemon()
    {
        try {
            // Create a datagram socket, bound to any available port
            this.localSocket = new DatagramSocket(2000);

            // Create a datagram packet containing the byte array
            this.dataPacket = new DatagramPacket(new byte[256], 256);
        }
        catch (SocketException ex) {
            System.err.println("SERVER ERROR: " + ex.getMessage() + "\n" + ex.getStackTrace());
        }
        catch(Exception ex) {
            System.err.println("SERVER ERROR: " + ex.getMessage() + "\n" + ex.getStackTrace());
        }

        this.start();
    }

    private void start()
    {
        System.out.println("Starting server daemon...");

        this.listen();
        this.writeData();
    }

    private void listen()
    {
        System.out.println("Listening...");

        try {
            this.localSocket.receive(this.dataPacket);
            this.localSocket.close();

            this.readPacket();

        } catch (IOException ex) {
            System.err.println("SERVER ERROR: " + ex.getMessage() + "\n" + ex.getStackTrace());
        }
    }

    private void reply() { }

    private void readPacket()
    {
        System.out.println("Reading packet...");
        this.inputStream = new ByteArrayInputStream(this.dataPacket.getData());
        this.senderAddress = this.dataPacket.getAddress();
        this.senderPort = this.dataPacket.getPort();
    }

    private void writeData()
    {
        for (int i = 0; i < this.dataPacket.getLength(); i++)
        {
            int data = inputStream.read();

            if(data == -1 )
                break;
            else
                System.out.print((char)data);
        }
    }
}
