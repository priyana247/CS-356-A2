/*
 *  Positivity Class
 */
public class Positivity {
 
  public static final String[] POSITIVE_WORDS = {"good", "great", "excellent", "happy"};
  
  // calculate Positivity Percentage
  public static double calculatePercentage() {
    TwitterDB db = TwitterDB.getInstance();
    double positiveCount = 0.0;
    for (Tweet tweet: db.getTweets()) {
      for (String positiveWord: POSITIVE_WORDS) {
        if (tweet.getText().toLowerCase().contains(positiveWord)) {
          positiveCount += 1.0;
          break;
        }
      }
    }
    if (db.getTweetsCount() == 0)
      return 0.0;
    double positiveRatio = positiveCount / db.getTweetsCount();
    double positivePercentage = 100.0 * positiveRatio;
    positivePercentage = Math.round(positivePercentage*100.0)/100.0;
    return positivePercentage;
  }
  
}