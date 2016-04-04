package edu.umw.twotter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Sends objects to native client
 */
public class ClientServer extends Server
    {

    public ClientServer(int port)
        {
        super(port);
        }

    public void connect(Socket socket) throws Exception
        {
        // will be receiving objects from clients

        //String received = Util.readAll( new BufferedReader(new InputStreamReader(socket.getInputStream())) );
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        }

    private void parseCommand(ClientRequest request) throws CommandParseException
        {
        String command = request.getCommand();

        switch (command)  //There's probably a better way to parse a command, but whatever
        {
            case "get_recent_twets":
            {
                //getRecentTwets();
                break;
            }

            case "get_twot_info":
            {
                {
                if (request.getData()[0] instanceof AuthData)
                {
                    try
                    {
                        Twotter.getTwot((AuthData) request.getData()[0]);
                    }
                    catch (Exception e)
                    {
                        System.err.printf("Could not retrieve Twot %s", ((AuthData) request.getData()[0]).userName);
                    }
                }
                else
                {
                    throw new CommandParseException("Incorrect data format: " + request.getData()[0].toString() + " is not an object of type AuthData");
                }

                break;
            }
            }

            case "reply_to_twet":
            {
                //replyToTwet(request.getData()[0];
                break;
            }

            case "add_twot":
            {
                if (request.getData()[0] instanceof Twot)
                {
                    try
                    {
                        Twotter.addTwot((Twot) request.getData()[0]);
                    }
                    catch (SecurityException e)
                    {
                        System.err.printf("Could not create new Twot %s", (Twot) request.getData()[0]);
                    }
                }
                else
                {
                    throw new CommandParseException("Incorrect data format: " + request.getData()[0].toString() + " is not an object of type Twot");
                }

                break;
            }
            
            case "":
            {
                
                
                
                break;
            }
            
            default:
                throw new CommandParseException(command + " is not a recognized command");
        }

        }

    }
