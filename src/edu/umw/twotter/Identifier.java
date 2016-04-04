package edu.umw.twotter;

import java.util.HashSet;
import java.util.Date;
import java.io.Serializable;

/**
 * An item which will not create duplicate IDs
 * Also stores the time of construction
 */
public class Identifier implements Serializable {
  /** Stores all of the issued ids to avoid collisions */
  private static HashSet<Long> issuedIds;
  
  long id = 0;
  Date timestamp;
  
  public Identifier()
  {
    if (issuedIds == null) {
      issuedIds = new HashSet<Long>();
      issuedIds.add((Long) 0l);
    }
    // while id has already been issued... (note that id initializes as 0 and we first put 0 in our 'issuedIds' set)
    while (issuedIds.contains((Long) id)) {
      // id is somewhere between 0 and 9223372036854775807
      id = (long) (Math.random() * Long.MAX_VALUE);
    }
    timestamp = new Date();
  }
  
  public boolean equals(Object o)
  {
    if (!(o instanceof Identifier)) {
      return false;
    }
    Identifier i = (Identifier) o;
    return this.id == i.id;
  }
  
  /**
   * Returns id as a base-16 string (eg, id of 123 => 7b)
   */
  public String toString()
  {
    return Long.toString(id, 16);
  }
  
}
