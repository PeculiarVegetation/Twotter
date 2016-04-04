package edu.umw.twotter;

import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * A representation of a page which may be requested by browsers
 */
public abstract class HttpPage {
  /** Format-ready string containing HTTP reply headers */
  public static final String RESPONSE_TEMPLATE = ""+
    "HTTP/1.0 200 OK\n"+
    "Date: %s\n"+
    "Content-Type: text/html\n"+
    "Content-Length: %d\n"+
    "\n\n%s";
  
  protected static final SimpleDateFormat HTTP_DATE = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
  
  /** The file path at which this page resides (eg, /index.html) */
  protected String location;
  
  /**
   * Construct a page which may be reached at {@code location}
   */
  public HttpPage(String location)
  {
    if (!location.startsWith("/")) {
      location = "/"+location;
    }
    this.location = location.toLowerCase();
  }
  
  /**
   * Wrap metadata around a response from handleConnection(2).
   * @return a fully-formatted http reply
   */
  public byte[] connect(String received, HashMap<String, String> query, HashMap<String, String> cookies, AuthData auth)
  {
    byte[] response = handleConnection(received, query, cookies, auth).getBytes();
    return String.format(RESPONSE_TEMPLATE,
      HTTP_DATE.format(new Date()),
      response.length+1,
      new String(response)
    ).getBytes();
  }
  
  /**
   * Handles the logic portion of the request: authorizing the user, searching tweets, etc...
   * You know, useful stuff.
   */
  public abstract String handleConnection(String received, HashMap<String, String> query, HashMap<String, String> cookies, AuthData auth);
  
}
