package edu.umw.twotter;

import java.io.Serializable;
import java.util.Date;
import java.util.Observer;
import java.util.Observable;
import java.util.ArrayList;

/**
 * A class resembling our users
 * The Twot class observes every personal Twet and 
 * every feed Twet
 * Extend to SuperTwot for admin functionality
 * admins can remove other accounts, create accounts, delete individual twots (messages)
 * build foonish twots
 * give twots titles
 */
public class Twot extends Observable implements Observer, Serializable {
  
  protected AuthData authData;
  protected Identifier id;
  protected ArrayList<Twet> personalTwets;
  
  protected ArrayList<Twot> followed;
  
  // prefname is used in place of auth name when prefname is not null
  String preferredName;
  
  String profileImageUrl;
  boolean isFoonish;
  
  public Twot(AuthData authData)
  {
    assert authData != null: "You passed a null value for 'authdata' into the Twot constructor";
    this.id = new Identifier();
    this.authData = authData;
    personalTwets = new ArrayList<Twet>();
    followed = new ArrayList<Twot>();
  }
  
  public boolean equals(Object o) {
    if (o instanceof String) {
      return ((String) o).equalsIgnoreCase(authData.userName);
      
    } else if (o instanceof Long) {
      return ((Long) o) == id.id;
    }
    return false;
  }
  
  public String userName() {
    return authData.userName;
  }
  
  /**
   * Function to check login data for a user
   */
  public boolean auth(AuthData credentials)
  {
    return authData.equals(credentials);
  }
  
  public void updatePassword(AuthData credentials, String newPassword) throws SecurityException {
    if (!auth(credentials)) {
      throw new SecurityException("Invalid login data");
    }
    if (newPassword.length() <= 6) {
      throw new SecurityException("New password is too short");
    }
    authData = new AuthData(authData.userName, newPassword, authData.loginCookie);
  }
  
  public void update(Observable o, Object arg)
  {
    if (o instanceof Twet) {
      // one of our Twets has changed (someone likes our Twet)
      
      
    } else if (o instanceof Twot) {
      // a twot we are following has changed (posted a twot)
      
      
    } else {
      assert false: String.format("Received strange update from %s: %s\n", o, arg);
    }
  }
  
  /**
   * @return all the twets in this
   */
  public ArrayList<Twet> getFeed() {
    
    return null;
  }
  
  // candy for the Twet contructor
  public void postTwet(String text)
  {
    postTwet(new Twet(text, this));
  }
  
  public void postTwet(Twet personalTwot)
  {
    personalTwets.add(personalTwot);
    
  }
  
  public Date getAccountCreationTime()
  {
    return this.id.timestamp;
  }
  
  public String toString()
  {
    return String.format("Twot named %s with id %s", authData.userName, id);
  }
  
  public String toHtml()
  {
    return String.format("<a href='/twots?id=%s'>%s</a>", id, authData.userName);
  }
}
