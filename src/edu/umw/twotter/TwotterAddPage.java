package edu.umw.twotter;

import java.util.HashMap;

/**
 * The index page for Twotter.
 */
public class TwotterAddPage extends HttpPage {
  public TwotterAddPage(String location)
  {
    super(location);
  }
  
  public String handleConnection(String received, HashMap<String, String> query, HashMap<String, String> cookies, AuthData auth)
  {
    String username = query.get("username");
    String password = query.get("password");
    if (username == null || password == null) {
      return Util.readFileAsString("new_account.html");
    }
    try {
      // create a new twot
      AuthData a = new AuthData(username, Util.hash(password));
      Twot t = new Twot(a);
      Twotter.addTwot(t);
      return String.format("New account created for user %s with a %d-digit password.", t.userName(), password.length());
      
    } catch (SecurityException e) {
      return e.getMessage();
    }
  }
  
}
