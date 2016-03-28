package edu.umw.twotter;

import java.util.HashMap;
import java.util.Date;

/**
 * An http page which requires authentication.
 * uses the ietf.org/rfc/rfc2617.txt specification.
 * users are authenticated through the data found in Main.
 */
public abstract class HttpAuthPage extends HttpPage {
  /** Format-ready string containing HTTP reply headers */
  public static final String DENIED_RESPONSE_TEMPLATE = ""+
    "HTTP/1.0 401 Access Denied\n"+
    "Location: %s\n"+
    "Date: %s\n"+
    "WWW-Authenticate: Basic realm=\"%s\"\n"+
    "Content-Type: text/html\n"+
    "Content-Length: 0\n"+
    "\n\n";
  
  public HttpAuthPage(String location) {
    super(location);
  }
  
  /**
   * Wrap metadata around a response from handleConnection(2).
   * @return a fully-formatted http reply
   */
  public byte[] connect(String received, HashMap<String, String> query, HashMap<String, String> cookies, AuthData auth)
  {
    if (auth == null || Twotter.twots.get(auth) == null) {
      return String.format(DENIED_RESPONSE_TEMPLATE,
        new String(this.location),
        HTTP_DATE.format(new Date()),
        "Twotter Server"
      ).getBytes();
    }
    // the twot exists and is known to us
    byte[] response = handleConnection(received, query, cookies, auth).getBytes();
    return String.format(RESPONSE_TEMPLATE,
      HTTP_DATE.format(new Date()),
      response.length,
      new String(response)
    ).getBytes();
  }
  
  public abstract String handleConnection(String received, HashMap<String, String> query, HashMap<String, String> cookies, AuthData auth);
  
}
