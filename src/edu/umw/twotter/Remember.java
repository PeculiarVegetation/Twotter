package edu.umw.twotter;

import java.io.Serializable;
import java.util.prefs.Preferences;

import java.io.*; // Laziness

/**
 * A class to abstract away storing persistent data using the java prefrences API
 * and an IBM guide at ibm.com/developerworks/library/j-prefapi/
 */ 
/*  Example use:
 *   Person myPerson = new Person("John");
 *   Remember.put("my data", myPerson);
 *   Person samePerson = (Person) Remember.get("my data");
 * 
 * Example with java String class:
 *   Scanner input = new Scanner(System.in);
 *   String newData = input.nextLine();
 *   String oldData = (String) Remember.get("old data");
 *   System.out.println("Last time, you typed: "+ oldData);
 *   Remember.store("old data", newData);
 * 
 * Notes:
 *   Objects stored (eg, Person) must implement the java.io.Serializable class.
 *   This may be added to the class decleration:
 *   
 *     import java.io.Serializable;
 *     public class Person implements Serializable {
 *       public String name;
 *       // code
 *     }
 * 
 *    Also, items stored inside must implement Serializable.
 *    Fortunately, many java objects (Strings, ArrayLists, primitive types...)
 *    implement Serializable already.
 * 
 * @author Jeffrey McAteer
 */
public class Remember {
  
  private static Preferences prefs(String key) {
    Preferences root = Preferences.userRoot();
    Preferences classRoot = Preferences.userNodeForPackage(Remember.class);
    return classRoot.node(key);
  }
  
  private static int byteArrLen() {
    return (int) ((float) Preferences.MAX_VALUE_LENGTH * 0.67);
  }
  
  public static Serializable get(String key) {
    Preferences keyChild = prefs(key);
    
    byte[] bytes = get(keyChild, key);
    try {
      ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
      ObjectInput in = new ObjectInputStream(bis);
      return (Serializable) in.readObject();
      
    } catch (Exception e) {
      //e.printStackTrace();
    }
    
    return null;
  }
  
  public static void put(String key, Serializable o) {
    Preferences keyChild = prefs(key);
    
    byte[] bytes = new byte[0];
    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutput out = new ObjectOutputStream(bos);
      out.writeObject(o);
      bytes = bos.toByteArray();
    } catch (Exception e) {
      //e.printStackTrace();
    }
    
    put(keyChild, key, bytes);
  }
  
  private static void put(Preferences prefs, String key, byte[] bytes) {
    final int maxBytesPerKey = byteArrLen();
    final int keysNeeded = (int) Math.ceil((float) bytes.length / (float) maxBytesPerKey);
    final int remainderBytes = bytes.length % maxBytesPerKey;
    byte[][] splitBytes = new byte[keysNeeded][maxBytesPerKey];
    splitBytes[keysNeeded-1] = new byte[remainderBytes];
    for (int keyNum=0; keyNum < keysNeeded; keyNum++) {
      for (int b=0; b < splitBytes[keyNum].length; b++) {
        splitBytes[keyNum][b] = bytes[(keyNum * maxBytesPerKey) + b];
      }
    }
    // splitBytes now populated properly
    prefs.putInt("keys", keysNeeded);
    prefs.putInt("bytes", (keysNeeded * (maxBytesPerKey-1)) + remainderBytes);
    for (Integer keyNum=0; keyNum < keysNeeded; keyNum++) {
      prefs.putByteArray(keyNum.toString(), splitBytes[keyNum]);
    }
  }
  
  private static byte[] get(Preferences prefs, String key) {
    byte[] bytes = new byte[prefs.getInt("bytes", 1)];
    int bytesIndex = 0;
    int keys = prefs.getInt("keys", 1);
    for (Integer keyNum = 0; keyNum < keys; keyNum++) {
      byte[] chunk = prefs.getByteArray(keyNum.toString(), null);
      if (chunk == null) {
        //new Exception("Chunk is null! Oh noes!").printStackTrace();
        continue;
      }
      for (int i=0; i<chunk.length && bytesIndex < bytes.length; i++) {
        bytes[bytesIndex] = chunk[i];
        bytesIndex++;
      }
    }
    return bytes;
  }
  
}
