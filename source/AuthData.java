package edu.umw.twotter;

/**
 * Stores data to identify a user. 
 */
public class AuthData {
  protected String userName;
  protected String loginCookie;
  protected String passwordHash;
  
  public AuthData(String userName, String passwordHash)
  {
    this(userName, createLoginCookie(), passwordHash);
  }
  
  public AuthData(String userName, String loginCookie, String passwordHash)
  {
    if (userName == null) userName = "";
    if (loginCookie == null) loginCookie = "";
    if (passwordHash == null) passwordHash = "";
    
    // limit username length to max of 128 characters
    if (userName.length() > 128) {
      userName = userName.substring(0, 128);
    }
    
    this.userName = userName;
    this.loginCookie = loginCookie;
    this.passwordHash = passwordHash;
  }
  
  public String getLoginCookie()
  {
    return loginCookie;
  }
  
  public boolean equals(Object o)
  {
    if (!(o instanceof AuthData)) {
      return false;
    }
    AuthData a = (AuthData) o;
    // Usernames match AND either the password hash matches OR the loginCookie matches
    return userName.equalsIgnoreCase(a.userName) &&
          (passwordHash.equalsIgnoreCase(a.passwordHash) || loginCookie.equalsIgnoreCase(a.loginCookie));
    
  }
  
  public String toString() {
    return userName;
  }
  
  public static String createLoginCookie()
  {
    return Util.randHexData(32); // get 32 characters of entropy (16^32 bits of entropy)
  }
  
}
