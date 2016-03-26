package edu.umw.twotter;

import java.util.HashMap;
import java.util.Date;

/**
 * Humanity has ascended to an age where knowledge flows
 * like torrents of rocks down an unstable mountainside.
 * In this age four reasonable, sane persons have
 * somehow concluded that the world of learning would be amiss
 * without this great contribution.
 * 
 * Twotter.
 * 
 * The insanity begins.
 */
/**
 * This is the main method to run the server
 */
public class Twotter {
  
  protected static HashMap<AuthData, Twot> users;
  
  public static void main(String... args)
  {
    // remember data from last time
    users = rememberUsers();
    
    // This is a shutdown hook. It does nothing until the JVM exits because System.exit() is called.
    // This code is responsible for saving all out data before exiting
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run()
      {
        System.out.printf("Exiting at %s\n", new Date());
        // save our user's data
        Remember.put("TWOTS", users);
        
        System.out.printf("Goodbye!\n");
      }
    });
    
    // start web service & setup things which cannot be saved
    WebServer w = new WebServer(9090);
    w.add(new TwotterIndex("/"));
    w.start();
    
    ClientServer c = new ClientServer(9009);
    c.start();
    
    TelnetServer t = new TelnetServer(9900);
    t.start();
    
    System.out.println("All servers started!");
    
  }
  
  
  
  /**
   * Remembers the data stored in the users hashmap.
   * todo: options for storing in file / other form
   */
  public static HashMap<AuthData, Twot> rememberUsers()
  {
    HashMap<AuthData, Twot> users = (HashMap<AuthData, Twot>) Remember.get("TWOTS");
    if (users != null) {
      return users;
    }
    return new HashMap<AuthData, Twot>();
  }
  
}
