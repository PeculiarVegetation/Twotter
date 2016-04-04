package edu.umw.twotterclient;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.BufferedReader;

import java.util.Scanner;
import java.util.HashMap;

import java.security.MessageDigest;

/**
 * Misc utility functions which may be used in multiple classes
 */
public class Util {
  
  public static String urlDecode(String s) {
    try {
      return java.net.URLDecoder.decode(s, "UTF-8");
    } catch (Exception e) {}
    return s;
  }
  
  public static String base64Decode(String s)
  {
    return new String(java.util.Base64.getDecoder().decode(s));
  }
  
  /**
   * @param characters the number of characters to return
   * @return           a hex string full of entropy
   */
  public static String randHexData(int characters)
  {
    if (characters < 1) return "";
    String data = randHexLong();
    if (data.length() > characters) {
      data = data.substring(0, characters);
    }
    return data + randHexData(characters - data.length());
  }
  
  /**
   * Note: no gaurantees are given for the return value's length
   * @return a hex-encoded number between 0 and 2^63-1
   */
  public static String randHexLong()
  {
    return Long.toString((int) (Math.random() * Long.MAX_VALUE), 16).toLowerCase();
  }
  
  /**
   * Currently uses MD5 because it is fast and lightweight. This also makes
   * it rather insecure. If security ever becomes a priority, this may
   * be changed to SHA-1 or SHA-256.
   * @param s String to be hashed
   * @return  The hash of the string
   */
  public static String hash(String s)
  {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      messageDigest.update(s.getBytes());
      return new String(messageDigest.digest());
      
    } catch (Exception e) {
      e.printStackTrace();
      assert false: "The MD5 hash function is not available! (Seriously, how did you manage to break this?)";
    }
    return "";
  }
  
  /**
   * Abstract out catching interrupted exceptions
   * @param ms the number of milliseconds to sleep for
   * @return the number of milliseconds we actually slept for
   */
  public static int sleep(int ms)
  {
    if (ms < 1) return 0;
    long begin = System.currentTimeMillis();
    try {
      Thread.sleep(ms);
    } catch (Exception e) {}
    long diff = System.currentTimeMillis() - begin;
    // recursive way to ensure we sleep for the appropriate amount of time
    return ((int) diff) + sleep((int) diff);
  }
  
  /**
   * @return the local IP of this machine
   */
  public static String getLocalIP()
  {
    try {
      return java.net.InetAddress.getLocalHost().getHostAddress();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "127.0.0.1";
  }
  
  /**
   * @param is source to read from
   * @return   Contents of the source as a string
   */
  public static String readAll(InputStream is)
  {
    Scanner in = new Scanner(is);
    StringBuilder data = new StringBuilder();
    while (in.hasNext()) {
      data.append(in.nextLine());
      data.append("\n");
      /*try {
        if (is.available() < 1) {
          break;
        }
      } catch (java.io.IOException e) {}*/
    }
    return data.toString();
  }
  
  /**
   * @param in source to read from
   * @return   Contents of the source as a string
   */
  public static String readAll(BufferedReader in) {
    return readAll(in, true);
  }
  
  /**
   * @param in                  source to read from
   * @param stopOnTwoLineBreaks Stop reading the stream when two linebreaks are ecountered. This is mostly for HTTP requests.
   * @return                    Contents of the source as a string
   */
  public static String readAll(BufferedReader in, boolean stopOnTwoLineBreaks) {
    StringBuilder data = new StringBuilder();
    while (true) {
      String line = null;
      try {
        line = in.readLine();
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
      data.append(line);
      data.append("\n");
      
      boolean twoLinebreaks = false;
      if (stopOnTwoLineBreaks && data.length() > 2) {
        char[] lastTwo = new char[2];
        data.getChars(data.length()-2, data.length(), lastTwo, 0);
        twoLinebreaks = lastTwo[0] == '\n' && lastTwo[1] == '\n';
      }
      if (line == null || line.equals("") || twoLinebreaks) {
        break;
      }
    }
    return data.toString();
  }
  
  /**
   * @param path filepath to read from
   * @return     file contents as a string
   */
  public static String readFileAsString(String path)
  {
    try {
      File f = new File(path);
      return readAll(new FileInputStream(f));
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }
  
  /**
   * Parses strings of the form key=val&key2=val2 into a hashmap
   * where map.get("key") == "val" and map.get("key2") == "val2"
   */
  public static HashMap<String, String> parseMapString(String s)
  {
    HashMap<String, String> data = new HashMap<>();
    for (String pair : s.split("&")) {
      String[] ends = pair.split("=");
      for (int i=0; i<ends.length && i < 3; i++) {
        ends[i] = urlDecode(ends[i]);
      }
      if (ends.length > 1) {
        data.put(ends[0], ends[1]);
      } else if (ends.length > 0) {
        data.put(ends[0], "");
      }
    }
    return data;
  }
  
}
