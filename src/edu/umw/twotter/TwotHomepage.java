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
    Twot t = Twotter.getTwot(auth);
    assert t != null;
    return String.format(Util.readFileAsString("twotter_home.html"),
      t.toHtml()
    );
  }
  
}
