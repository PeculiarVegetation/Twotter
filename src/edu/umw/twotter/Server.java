package edu.umw.twotter;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Superclass for shared server operations
 */
public abstract class Server {
  
  ServerSocket server;
  int port;
  
  public Server(int port)
  {
    this.port = Math.abs(port);
    
  }
  
  public void start()
  {
    try {
      server = new ServerSocket(port);
      new Thread() {
        public void run() {
          listen();
        }
      }.start();
      
      String fullClassName = this.getClass().getName();
      String packageName = this.getClass().getPackage().getName();
      String shortClassName = fullClassName.substring(packageName.length()+1, fullClassName.length());
      System.err.printf("%s listening at %s on port %d...\n", shortClassName, Util.getLocalIP(), port);
      
    } catch (java.io.IOException e) {
      System.err.printf("Could not create server on port %d. Check that this is allowed by your system and that the port is not currently in use.\n", port);
    }
  }
  
  /**
   * Infinite loop which spawns new threads on new connections
   * todo: rate limiting for DDOS attacks, etc.
   */
  public void listen()
  {
    assert server != null: "Server must be initialized before listen() is called";
    try {
      while (true) {
        final Socket socket = server.accept();
        // launch a new thread so we can serve simultanious connections
        new Thread() {
          public void run() {
            try {
              connect(socket);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }.start();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Handles a single connection to the server
   */
  public abstract void connect(Socket socket) throws Exception;
  
  /**
   * Stops the server
   */
  public void stop()
  {
    try {
      server.close();
    } catch (java.io.IOException e) {
      server = null;
      System.gc(); // invoke garbage collection gods to damn the unresponsive ServerSocket to /dev/null
    }
  }
  
}
