package edu.umw.twotter;

import java.util.HashMap;
import java.util.Date;

/** A simple demonstration of giving a web browser the current date */
public class DemoPage extends Page {
  
  public DemoPage(String location) {
    super(location);
  }
  
  public String handleConnection(String received, HashMap<String, String> query) {
    return String.format("Current time: %s", new Date());
  }
  
}
