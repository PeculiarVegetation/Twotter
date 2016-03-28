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
 * A simple http web server
 */
public class WebServer extends Server {
  
  HashMap<String, HttpPage> pages;
  
  public WebServer(int port)
  {
    super(port);
    pages = new HashMap<String, HttpPage>();
  }
  
  public void connect(Socket socket) throws java.io.IOException
  {
    String received = Util.readAll( new BufferedReader(new InputStreamReader(socket.getInputStream())) );
    HttpRequest request = new HttpRequest(received);
    
    System.out.printf("%s %s %s %s %s\n",
      request.connectionType(), request.page(), request.queryString(), request.cookieString(), request.authorization());
    
    HttpPage page = pages.get(request.page());
    if (page == null) {
      // redirect home
      page = new HttpRedirect("/");
    }
    byte[] replyBytes = page.connect(request.body(), request.query(), request.cookies(), request.authorization());
    OutputStream out = socket.getOutputStream();
    out.write(replyBytes);
    out.flush();
    out.close();
  }
  
  public void add(HttpPage... pages)
  {
    for (HttpPage p : pages) {
      add(p);
    }
  }
  
  public void add(HttpPage p)
  {
    pages.put(p.location, p);
  }
  
}
