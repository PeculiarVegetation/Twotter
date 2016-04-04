package edu.umw.twotter;

import java.util.HashMap;

/**
 * The index page for Twotter.
 */
public class TwotterCSS extends HttpPage {
  public TwotterCSS(String location)
  {
    super(location);
  }
  
  public String handleConnection(String received, HashMap<String, String> query, HashMap<String, String> cookies, AuthData auth)
  {
    return Util.readFileAsString("css.css");
  }
  
}
