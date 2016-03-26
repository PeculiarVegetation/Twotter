package edu.umw.twotter;

import java.util.HashMap;
import java.util.Date;

public class HttpRedirect extends HttpPage {
  /** Format-ready string containing HTTP reply headers */
  public static final String RESPONSE_TEMPLATE = ""+
    "HTTP/1.0 301 Moved Permanently\n"+
    "Location: %s\n"+
    "Date: %s\n"+
    "Content-Type: text/html\n"+
    "Content-Length: 0\n"+
    "\n\n";
  
  public HttpRedirect(String location) {
    super(location);
  }
  
  /**
   * Wrap metadata around a response from handleConnection(2).
   * @return a fully-formatted http reply
   */
  public byte[] connect(String received, HashMap<String, String> query, HashMap<String, String> cookies)
  {
    return String.format(RESPONSE_TEMPLATE,
      new String(this.location),
      HTTP_DATE.format(new Date())
    ).getBytes();
  }
  
  public String handleConnection(String received, HashMap<String, String> query, HashMap<String, String> cookies) {
    assert false: "handleConnection(3) not used in HttpRedirect class";
    return null;
  }
}
