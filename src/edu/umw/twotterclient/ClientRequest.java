package edu.umw.twotterclient;

/**
 * An object that essentially holds a command and any relevant data to the execution of said command.
 * For example, a ClientRequest could hold a command to send the most recent twets from a user's subscriptions, and include the user's AuthData.
 * This is designed to work both ways, so both the Client and ClientServer will be sending these.
 *
 * @author Ben
 */
public class ClientRequest {

    private final String command;
    private final Object[] data;  //This can send twots, twets, or even other ClientRequests to be executed in a particular order or something.
    
    public ClientRequest(String command, Object[] data)
        {
        this.command = command;
        this.data = data;
        }
    
    public String getCommand()
        {
        return(this.command);
        }
    
    public Object[] getData()
        {
        return(this.data);
        }
    
}
