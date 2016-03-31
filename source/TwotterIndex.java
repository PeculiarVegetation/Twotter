package edu.umw.twotter;

import java.util.HashMap;
import java.util.Date;

/**
 * The index page for Twotter.
 */
public class TwotterIndex extends HttpPage {
  public TwotterIndex(String location)
  {
    super(location);
  }
  
  public byte[] connect(String received, HashMap<String, String> query, HashMap<String, String> cookies, AuthData auth)
  {
    Twot t = Twotter.getTwot(auth);
    if (t != null) {
      // friendly browser has cached login credentials!
      return new HttpRedirect("/home").connect(received, query, cookies, auth);
    }
    
    byte[] response = handleConnection(received, query, cookies, auth).getBytes();
    return String.format(RESPONSE_TEMPLATE,
      HTTP_DATE.format(new Date()),
      response.length+1,
      new String(response)
    ).getBytes();
  }
  
  public String handleConnection(String received, HashMap<String, String> query, HashMap<String, String> cookies, AuthData auth)
  {
    return Util.readFileAsString("twotter_index.html");
  }
  
}
