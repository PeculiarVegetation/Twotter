package edu.umw.twotterclient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Observable;

/**
 * Basically a tweet
 */
public class Twet extends Observable implements Serializable {
  public static final String HTML_FORMAT = "<div class='twet'><p>%s</p><div class='author'>%s</div></div>";
  public static final int LENGTH_LIMIT = 140;
  
  Identifier id;
  Twot author;
  String text;
  boolean public_access = true;
  
  ArrayList<Twot> likes;
  ArrayList<Twot> dislikes;
  
  public Twet(String text, Twot author)
  {
    assert text != null:   "You passed a null value for 'text' into the constructor of TwotMessage";
    assert author != null: "You passed a null value for 'author' into the constructor of TwotMessage";
    
    this.id = new Identifier();
    this.text = text.substring(0, Math.min(LENGTH_LIMIT, text.length()));
    this.author = author;
    likes = new ArrayList<Twot>();
    dislikes = new ArrayList<Twot>();
  }
  
  public String toString()
  {
    return String.format("%s's twot id %s: '%s'", author, id, text);
  }
  
  public String toHtml()
  {
    return String.format(HTML_FORMAT, author.toHtml(), text);
  }
  
}
