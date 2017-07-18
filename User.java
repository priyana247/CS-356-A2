/* Priyanka Patel
 * CS 356
 */
import java.util.*;

/*
 *  User class
 */
public class User {
  
  private TwitterDB db;
  
  private String id;
  private HashMap<String,User> follows;
  private Group group;
  

  // Constructor
  public User(String id, String group) {
    this.db = TwitterDB.getInstance();
    this.id = id;
    this.follows = new HashMap<String,User>();
    follows.put(id, this);
    if (!group.equals("Root") && db.containsGroup(group)) {
      this.group = db.getGroup(group);
      for (User user: this.group.getUsers()) {
        follows.put(user.getID(),user);
      }
    }
  }
  
  // Get ID of User
  public String getID() {
    return id;
  }
  
  // Get Users who follow this User
  public List<User> getFollows() {
    if (group != null) {
      for (User user: this.group.getUsers()) {
        if (!follows.containsValue(user))
          follows.put(user.getID(),user);
      }
    }
    List<User> result = new ArrayList<User>();
    for (String user: follows.keySet()) {
      result.add(follows.get(user));
    }
    return result;
  }
  
  // Determine if User is following other User
  public boolean isFollowing(String user) {
    return getFollows().contains(db.getUser(user));
  }
  
  // Have User follow other User
  public boolean followUser(String user) {
    if (!db.containsUser(user))
      return false;
    else if (isFollowing(user))
      return false;
    follows.put(user, db.getUser(user));
    return true;
  }
  
  // Get Tweets visible to User
  public List<Tweet> getTweets() {
    List<Tweet> allTweets = db.getTweets();
    List<Tweet> tweets = new ArrayList<Tweet>();
    for (Tweet tweet: allTweets) {
      if (isFollowing(tweet.getUser().getID())) {
        tweets.add(tweet);
      }
    }
    return tweets;
  }
  
  
}
