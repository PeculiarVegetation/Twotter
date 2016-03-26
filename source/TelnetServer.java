package edu.umw.twotter;
import java.net.Socket;

import java.io.OutputStream;

/**
 * A command-line interface
 */
public class TelnetServer extends Server {
  public TelnetServer(int port)
  {
    super(port);
  }
  
  public void connect(Socket socket) throws java.io.IOException
  {
    OutputStream out = socket.getOutputStream();
    out.write("Hello!\n".getBytes());
    out.close();
  }
  
}
