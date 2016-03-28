package edu.umw.twotter;

import java.util.HashMap;

/**
 * The index page for Twotter.
 */
public class TwotHomepage extends HttpAuthPage {
  public TwotHomepage(String location)
  {
    super(location);
  }
  
  public String handleConnection(String received, HashMap<String, String> query, HashMap<String, String> cookies, AuthData auth)
  {
    assert auth != null;
    return "Hello "+auth.userName+"!";
    //return Util.readFileAsString("twotter_index.html");
  }
  
}
