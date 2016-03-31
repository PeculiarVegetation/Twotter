package edu.umw.twotter;

import java.util.HashMap;

/**
 * An object holding data about an HTTP request which has reached our server.
 * todo: chrome GET with a query string (eg, index.html?param=1) makes an assertion fail
 */
public class HttpRequest {
  
  /** The raw data given to us */
  private final String text;
  
  public HttpRequest(String text)
  {
    this.text = text;
    /* text will look like:
GET /demo HTTP/1.1
Host: 127.0.0.1:9090
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*;q=0.8
Connection: keep-alive
Cookie: cookie=value
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/601.4.4 (KHTML, like Gecko) Version/9.0.3 Safari/601.4.4
Accept-Language: en-us
Cache-Control: max-age=0
Authorization: Basic aHR0cHdhdGNoOmY=
Accept-Encoding: gzip, deflate
    */
  }
  
  /**
   * parse the received data for the connection type
   * @return type of connection (GET, POST, BREW)
   */
  public String connectionType()
  {
    return text.substring(0, Math.max(text.indexOf(" "), 0));
  }
  
  /**
   * parse the received data for the page/uri/file requested (eg, /index.html, /image.png)
   * @return page or filepath requested
   */
  public String page()
  {
    int endOfFile = text.indexOf("#");
    if (endOfFile < 0) {
      endOfFile = text.indexOf("?");
    }
    if (endOfFile < 0) {
      endOfFile = text.indexOf("HTTP");
    }
    if (endOfFile >= 0) {
      System.err.println("Could not find end of file path in "+text);
      //return "/";
    }
    String tenativePage = text.substring(connectionType().length(), endOfFile).trim();
    if (tenativePage.contains("\n")) {
      // whoops
      return text.substring(connectionType().length(), text.indexOf(" ", connectionType().length()+2)).trim();
    }
    return tenativePage;
  }
  
  /**
   * parse the received data for the query string
   * @return query string (val1=a&val2=b)
   */
  public String queryString()
  {
    int begin = connectionType().length()+page().length();
    int end = text.indexOf("HTTP");
    if (begin > end) {
      begin = end-1;
    }
    String queryString = text.substring(begin, end);
    if (queryString.length() > 1) {
      queryString = queryString.substring(2); // cut off space and '?' character
    }
    return queryString.trim();
  }
  
  /**
   * A more easily used form of query data
   * @return query data stored as keys and values
   */
  public HashMap<String, String> query()
  {
    return Util.parseMapString(queryString());
  }
  
  /**
   * Parse out cookies from web browser
   */
  public String cookieString()
  {
    if (text.contains("Cookie")) {
      int cookieLocation = text.indexOf("Cookie: ") + 8;
      return text.substring(cookieLocation, text.indexOf("\n", cookieLocation)).trim();
    }
    return "";
  }
  
  public AuthData authorization()
  {
    //System.out.println(authorizationString());
    String[] authData = Util.base64Decode(authorizationString()).split(":");
    if (authData.length == 2) {
      return new AuthData(authData[0], Util.hash(authData[1]));
    }
    String username = cookies().get("username");
    String passwordhash = cookies().get("passwordhash");
    String logincookie = cookies().get("logincookie");
    return new AuthData(username, logincookie, passwordhash);
  }
  
  /**
   * Parse out HTTP Authorization data
   */
  public String authorizationString()
  {
    if (text.contains("Authorization")) {
      int authLocation = text.indexOf("Authorization: Basic") + 20;
      return text.substring(authLocation, text.indexOf("\n", authLocation)).trim();
    }
    return "";
  }
  
  /**
   * Easy-to-use cookie data
   */
  public HashMap<String, String> cookies() {
    return Util.parseMapString(cookieString());
  }
  
  /**
   * the data sent to us, without http headers
   * @return body of the request
   */
  public String body()
  {
    return text.substring(Math.max(text.indexOf("\n\n"), 0), text.length());
  }
  
}
