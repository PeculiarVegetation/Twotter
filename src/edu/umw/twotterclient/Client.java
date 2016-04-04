package edu.umw.twotterclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Ben
 */
public class Client
    {

    private int port;
    private ServerSocket server;

    Client(int port)
        {
        this.port = Math.abs(port);
        try
        {
            this.server = new ServerSocket(this.port);
        }
        catch (java.io.IOException e)
        {
            System.err.printf("Could not create server on port %d. Check that this is allowed by your system and that the port is not currently in use.\n", this.port);
        }
        }

    public void makeConnection(Socket socket) throws IOException
        {
        //Socket s = new Socket("cs.umw.edu", 9091);

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        }

    }
