package edu.umw.twotter;

import java.util.HashMap;

/**
 * The index page for Twotter.
 */
public class TwotterIndex extends HttpPage {
  public TwotterIndex(String location)
  {
    super(location);
  }
  
  public String handleConnection(String received, HashMap<String, String> query, HashMap<String, String> cookies)
  {
    return Util.readFileAsString("twotter_index.html");
  }
  
}
