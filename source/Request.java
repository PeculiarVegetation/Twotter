import java.util.HashMap;

public class Request {
  
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
  
  public String connectionType() {
    return text.substring(0, Math.max(text.indexOf(" "), 0));
  }
  
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
  
  public String queryString() {
    String queryString = text.substring(connectionType().length()+page().length(), text.indexOf("HTTP"));
    if (queryString.length() > 1) {
      queryString = queryString.substring(2); // cut off space and '?' character
    }
    return queryString;
  }
  
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
  
  public String body() {
    return text.substring(Math.max(text.indexOf("\n\n"), 0), text.length());
  }
  
}
