package edu.umw.twotter;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Scanner;
import java.util.Date;
import java.util.HashMap;

/**
 * A simple web server
 */
public class WebServer extends Server {
  
  public static final String NOT_FOUND_TEMPLATE = "Error: there is no data at '%s'.";
  
  HashMap<String, Page> pages;
  
  public WebServer(int port)
  {
    super(port);
    pages = new HashMap<String, Page>();
  }
  
  public void connect(Socket socket)
  {
    String received;
    try {
      received = Util.readAll( new BufferedReader(new InputStreamReader(socket.getInputStream())) );
    } catch (java.io.IOException e) {
      return;
    }
    Request request = new Request(received);
    
    System.out.printf("%s %s %s %s\n", request.connectionType(), request.page(), request.queryString(), request.cookieString());
    
    Page page = pages.get(request.page());
    if (page == null) {
      // ugly hack
      page = new Page(request.page()) {
        public String handleConnection(String received, HashMap<String, String> query) {
          return "There is no page at "+this.location+".";
        }
      };
    }
    byte[] replyBytes = page.connect(request.body(), request.query());
    try {
      OutputStream out = socket.getOutputStream();
      out.write(replyBytes);
      out.flush();
      out.close();
    } catch (java.io.IOException e) {
      e.printStackTrace();
    }
  }
  
  public void add(Page... pages)
  {
    for (Page p : pages) {
      add(p);
    }
  }
  
  public void add(Page p)
  {
    pages.put(p.location, p);
  }
  
}
