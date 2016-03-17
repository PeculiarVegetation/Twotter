/**
 * 
 */
public class Twotter {
  public static void main(String... args) {
    Webserver w = new Webserver(9090);
    w.add(new DemoPage("/demo"));
    w.add(new TwotterIndex("/"));
    w.start();
    
  }
}
