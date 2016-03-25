package edu.umw.twotter;

import java.net.Socket;

/**
 * Sends objects to native client
 */
public class ClientServer extends Server {
  
  public ClientServer(int port) {
    super(port);
  }
  
  public void connect(Socket socket) {
    
  }
  
}
