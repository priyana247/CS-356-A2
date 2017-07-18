/* Priyanka Patel
 * CS 356
 */
import java.util.*;

/*
 *  TwitterDB class
 *  Aggregates all core functionality/memory
 */
public class TwitterDB {
 
  private static TwitterDB instance = null; 
  
  private static HashMap<String,User> users;
  private static HashMap<String,Group> groups;
  private static HashMap<String,UserPanel> userPanels;
  private static List<Tweet> tweets;
  
  // Constructor
  private TwitterDB() {
    groups = new HashMap<String,Group>();
    users = new HashMap<String,User>();
    userPanels = new HashMap<String,UserPanel>();
    tweets = new ArrayList<Tweet>();
  }
  
  // Singleton Accessor
  public static TwitterDB getInstance() {
    if (instance == null)
      instance = new TwitterDB();
    return instance;
  }
  
  // Method to add User to TwitterDB
  public boolean addUser(String user, String group) {
    if (containsUser(user))
      return false;
    users.put(user, new User(user, group));
    if (containsGroup(group))
      getGroup(group).addUser(user);
    UserPanel userPanel = new UserPanel(user);
    userPanel.create();
    userPanels.put(user,userPanel);
    return true;
  }
  
  // Add Group to TwitterDB
  public boolean addGroup(String group) {
    if (containsGroup(group))
      return false;
    groups.put(group, new Group(group));
    return true;
  }
  
  // Add Tweet to TwitterDB
  public void addTweet(Tweet tweet) {
    tweets.add(tweet);
  }
  
  // Check if TwitterDB contains User
  public boolean containsUser(String user) {
    return users.containsKey(user);
  }

  // Check if TwitterDB contains Group
  public boolean containsGroup(String group) {
    return groups.containsKey(group);
  }
  
  // Get User from TwitterDB
  public User getUser(String user) {
    return users.get(user);
  }

  // Get Group from TwitterDB
  public Group getGroup(String group) {
    return groups.get(group);
  }
  
  // Get UserPanel from TwitterDB
  public UserPanel getUserPanel(String user) {
    return userPanels.get(user);
  }
  
  // Get Tweets from TwitterDB
  public List<Tweet> getTweets() {
    return tweets;
  }
  
  // Get count of Tweets
  public int getTweetsCount() {
    return tweets.size();
  }
  
  // Refresh TwitterDB -- and all UserPanels
  public void refresh() {
    for (String user: userPanels.keySet()) {
      userPanels.get(user).refresh();
    }
  }
  
}
