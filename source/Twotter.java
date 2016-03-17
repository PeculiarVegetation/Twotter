package edu.umw.twotter;

/**
 * Humanity has ascended to an age where knowledge flows
 * like torrents of rocks down an unstable mountainside.
 * In this age four reasonable, sane persons have
 * somehow concluded that the world of learning would be amiss
 * without this great contribution.
 * 
 * Twotter.
 * 
 * The insanity begins.
 */
public class Twotter {
  public static void main(String... args) {
    Webserver w = new Webserver(9090);
    w.add(new DemoPage("/demo"));
    w.add(new TwotterIndex("/"));
    w.start();
    
  }
}
