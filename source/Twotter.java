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
public class Twotter {
  
  public static void main(String... args)
  {
    // remember data from last time
    HashMap<AuthData, Twot> users = rememberUsers();
    
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
    Webserver w = new Webserver(9090);
    w.add(new DemoPage("/demo"));
    w.add(new TwotterIndex("/"));
    w.start();
    
    // wait for 12 seconds
    //Util.sleep(12 * 1000);
    // die
    //System.exit(0);
    
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
