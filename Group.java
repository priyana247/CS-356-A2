/* Priyanka Patel
 * CS 356
 */
import java.util.*;

/*
 *  Group class
 */
public class Group {
  
  private TwitterDB db;
  
  private String id;
  private HashMap<String,User> users;

  // Constructor
  public Group(String id) {
    this.db = TwitterDB.getInstance();
    this.id = id;
    this.users = new HashMap<String,User>();
  }
  
  // Add User to Group
  public boolean addUser(String user) {
    if (db.containsUser(user)) {
      users.put(user,db.getUser(user));
      return true;
    } else {
      return false;
    }
  }
  
  // Get Users in Group
  public List<User> getUsers() {
    List<User> result = new ArrayList<User>();
    for (String user: users.keySet()) {
      result.add(users.get(user));
    }
    return result;
  }
  
  
}
