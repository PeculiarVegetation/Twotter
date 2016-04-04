package edu.umw.twotter;

import edu.umw.twotterclient.*;

/**
 * An object that essentially holds a command and any relevant data to the execution of said command.
 * For example, a ClientRequest could hold a command to send the most recent twets from a user's subscriptions, and include the user's AuthData.
 *
 * @author Ben
 */
public class ClientRequest {

    private final String command;
    private final Object[] data;
    private final AuthData auth;
    
    public ClientRequest(String command, Object[] data, AuthData auth)
        {
        this.command = command;
        this.data = data;
        this.auth = auth;
        }
    
    public String getCommand()
        {
        return(this.command);
        }
    
    public Object[] getData()
        {
        return(this.data);
        }
    
    public AuthData getAuthData()
        {
        return(this.auth);
        }
    
}
