package edu.umw.twotter;

import java.util.HashMap;
import java.util.Date;
import java.util.Arrays;
import java.util.List;

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
  
  protected static HashMap<AuthData, Twot> twots;
  protected static WebServer webserver;
  protected static ClientServer clientserver;
  protected static TelnetServer telnetserver;
  
  public static void main(String... args)
  {
    // remember data from last time
    twots = rememberUsers();
    
    List arguments = Arrays.asList(args);
    if (arguments.contains("-remove-all-users")) {
      System.out.println("Removing all users...");
      twots = new HashMap<AuthData, Twot>();
    }
    
    // This is a shutdown hook. It does nothing until the JVM exits because System.exit() is called.
    // This code is responsible for saving all out data before exiting
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run()
      {
        System.out.printf("Exiting at %s\n", new Date());
        // save our user's data
        Remember.put("TWOTS", twots);
        
        System.out.printf("Goodbye!\n");
      }
    });
    
    // start web service & setup things which cannot be saved
    webserver = new WebServer(9090);
    webserver.add(new TwotterCSS("/css.css"));
    webserver.add(new TwotterIndex("/"));
    webserver.add(new TwotterAddPage("/add"));
    webserver.add(new TwotHomepage("/home"));
    webserver.start();
    
    clientserver = new ClientServer(9009);
    clientserver.start();
    
    telnetserver = new TelnetServer(9900);
    telnetserver.start();
    
    System.out.printf("All servers started, %d users registered: %s\n", twots.size(), twots.keySet());
    
  }
  
  public static void addTwot(Twot t) throws SecurityException {
    for (AuthData key : twots.keySet()) {
      if (key.equals(t.userName())) throw new SecurityException("Twot already exists");
    }
    twots.put(t.authData, t);
  }
  
  public static Twot getTwot(AuthData a) {
    Twot t = twots.get(a);
    if (t != null) {
      if (!t.auth(a)) return null; // return null if password/cookie is not good
    }
    return t;
  }
  
  /**
   * Remembers the data stored in the users hashmap.
   * todo: options for storing in file / other form
   */
  public static HashMap<AuthData, Twot> rememberUsers()
  {
    HashMap<AuthData, Twot> twots = (HashMap<AuthData, Twot>) Remember.get("TWOTS");
    if (twots != null) {
      return twots;
    }
    return new HashMap<AuthData, Twot>();
  }
  
}
