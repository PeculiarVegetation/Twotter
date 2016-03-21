package edu.umw.twotter;


public class SecurityException extends Exception {
  public SecurityException(String mess) {
    super(mess);
    // and what a mess if handled improperly
  }
}
