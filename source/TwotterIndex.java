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
  
  public String handleConnection(String received, HashMap<String, String> query, HashMap<String, String> cookies, AuthData auth)
  {
    String action = query.get("action");
    if (action == null) action = "none";
    switch (action.toLowerCase()) {
      // each case should return something.
      // because we are returning, the break; statement may be omitted (I think it's rather elegant)
      case "none":
        return Util.readFileAsString("twotter_index.html");
        
      case "new":
        return "";
        
      default:
        return String.format("Unknown action '%s'", action);
        
    }
  }
  
}
