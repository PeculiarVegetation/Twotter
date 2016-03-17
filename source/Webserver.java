package edu.umw.twotter;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;

import java.util.Scanner;
import java.util.Date;
import java.util.HashMap;

/**
 * A simple web server
 */
public class Webserver {
  
  public static final String NOT_FOUND_TEMPLATE = "Error: there is no data at '%s'.";
  
  ServerSocket server;
  int port;
  
  HashMap<String, Page> pages;
  
  public Webserver(int port) {
    this.port = Math.abs(port);
    pages = new HashMap<String, Page>();
  }
  
  public void start() {
    try {
      server = new ServerSocket(port);
      new Thread() {
        public void run() {
          listen();
        }
      }.start();
      System.err.printf("Listening at %s on port %d...\n", getLocalIP(), port);
    } catch (java.io.IOException e) {
      System.err.printf("Could not create server on port %d. Check that this is allowed by your system and that the port is not currently in use.\n", port);
    }
  }
  
  public void listen() {
    assert server != null: "Server must be initialized before listen() is called";
    try {
      while (true) {
        final Socket socket = server.accept();
        // launch a new thread so we can serve simultanious connections
        new Thread() {
          public void run() {
            connect(socket);
          }
        }.start();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void connect(Socket socket) {
    String received;
    try {
      received = readAll(socket.getInputStream());
    } catch (java.io.IOException e) {
      return;
    }
    Request request = new Request(received);
    
    System.out.printf("%s %s %s\n", request.connectionType(), request.page(), request.queryString());
    
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
  
  public void stop() {
    try {
      server.close();
    } catch (java.io.IOException e) {
      server = null;
      System.gc(); // invoke garbage collection gods to damn the unresponsive ServerSocket to /dev/null
    }
  }
  
  public void add(Page... pages) {
    for (Page p : pages) {
      add(p);
    }
  }
  
  public void add(Page p) {
    pages.put(p.location, p);
  }
  
  public static String getLocalIP() {
    try {
      return java.net.InetAddress.getLocalHost().getHostAddress();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "127.0.0.1";
  }
  
  public static String readAll(InputStream is) {
    Scanner in = new Scanner(is);
    StringBuilder data = new StringBuilder();
    while (in.hasNext()) {
      data.append(in.nextLine());
      data.append("\n");
      try {
        if (is.available() < 1) {
          break;
        }
      } catch (java.io.IOException e) {}
    }
    return data.toString();
  }
  
  public static String readFileAsString(String path) {
    try {
      File f = new File(path);
      return readAll(new FileInputStream(f));
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }
  
}
