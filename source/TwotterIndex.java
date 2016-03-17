package edu.umw.twotter;

import java.util.HashMap;

/**
 * The index page for Twotter.
 */
public class TwotterIndex extends Page {
  public TwotterIndex(String location) {
    super(location);
  }
  
  public String handleConnection(String received, HashMap<String, String> query) {
    return Webserver.readFileAsString("twotter_index.html");
  }
  
}
