package edu.umw.twotter;
import java.net.Socket;

import java.util.Scanner;
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
    Scanner in = new Scanner(socket.getInputStream());
    OutputStream out = socket.getOutputStream();
    out.write("username: ".getBytes());
    String username = in.nextLine();
    out.write("password: ".getBytes());
    String passwordHash = Util.hash(in.nextLine());
    AuthData a = new AuthData(username, passwordHash);
    
    if (Twotter.twots.get(a) == null) {
      out.write("Invalid login credentials.".getBytes());
      out.close();
      return;
    }
    
    Twot twot = Twotter.twots.get(a);
    out.write(String.format("Hello %s!", twot.userName()).getBytes());
    
    out.close();
  }
  
}
