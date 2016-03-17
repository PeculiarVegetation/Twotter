package edu.umw.twotter;

import java.util.HashMap;

/**
 * An object holding data about an HTTP request which has reached our server.
 * @todo parse cookies and offer as hashmap in Page. *munch* *munch* *munch*
 */
public class Request {
  
  /** The raw data given to us */
  private String text;
  
  public Request(String text) {
    this.text = text;
    /* text will look like:
GET /hello.html HTTP/1.1
User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)
Host: www.tutorialspoint.com
Accept-Language: en-us
Accept-Encoding: gzip, deflate
Connection: Keep-Alive
    */
  }
  
  /**
   * parse the received data for the connection type
   * @return type of connection (GET, POST, BREW)
   */
  public String connectionType() {
    return text.substring(0, Math.max(text.indexOf(" "), 0));
  }
  
  /**
   * parse the received data for the page/uri/file requested (eg, /index.html, /image.png)
   * @return page or filepath requested
   */
  public String page() {
    int endOfFile = text.indexOf("#");
    if (endOfFile < 0) {
      endOfFile = text.indexOf("?");
    }
    if (endOfFile < 0) {
      endOfFile = text.indexOf("HTTP");
    }
    assert endOfFile > 0: "Could not find end of file path in "+text;
    return text.substring(connectionType().length(), endOfFile).trim();
  }
  
  /**
   * parse the received data for the query string
   * @return query string (val1=a&val2=b)
   */
  public String queryString() {
    String queryString = text.substring(connectionType().length()+page().length(), text.indexOf("HTTP"));
    if (queryString.length() > 1) {
      queryString = queryString.substring(2); // cut off space and '?' character
    }
    return queryString;
  }
  
  /**
   * A more easily used form of query data
   * @return query data stored as keys and values
   */
  public HashMap<String, String> query() {
    String queryString = queryString();
    HashMap<String, String> data = new HashMap<>();
    for (String pair : queryString.split("&")) {
      String[] ends = pair.split("=");
      if (ends.length > 1) {
        data.put(ends[0], ends[1]);
      } else if (ends.length > 0) {
        data.put(ends[0], "");
      }
    }
    return data;
  }
  
  /**
   * the data sent to us, without http headers
   * @return body of the request
   */
  public String body() {
    return text.substring(Math.max(text.indexOf("\n\n"), 0), text.length());
  }
  
}
