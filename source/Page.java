import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;

public abstract class Page {
  public static final String RESPONSE_TEMPLATE = ""+
    "HTTP/1.0 200 OK\n"+
    "Date: %s\n"+
    "Content-Type: text/html\n"+
    "Content-Length: %d\n"+
    "\n\n%s";
  
  public static final SimpleDateFormat HTTP_DATE = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
  
  String location;
  
  public Page(String location) {
    if (!location.startsWith("/")) {
      location = "/"+location;
    }
    this.location = location.toLowerCase();
  }
  
  public byte[] connect(String received, HashMap<String, String> query) {
    byte[] response = handleConnection(received, query).getBytes();
    return String.format(RESPONSE_TEMPLATE,
      HTTP_DATE.format(new Date()),
      response.length,
      new String(response)
    ).getBytes();
  }
  
  public abstract String handleConnection(String received, HashMap<String, String> query);
  
}
