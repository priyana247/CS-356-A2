/*
 *  Tweet Class
 */
public class Tweet {
 
  private User user;
  private String text;
  
  // Constructor
  public Tweet(User user, String text) {
    this.user = user;
    this.text = text;
  }
  
  // get User of Tweet
  public User getUser() {
    return user;
  }
  
  // get Text of Tweet
  public String getText() {
    return text;
  }
  
  // get Tweet Text
  public String getTweet() {
    return user.getID() + " > " + text;
  }
  
}