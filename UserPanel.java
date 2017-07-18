import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

/*
 *  UserPanel class
 */
public class UserPanel extends JPanel implements ActionListener {
  
  private TwitterDB db;
  
  private JFrame frame;
  
  private User user;
  private JPanel contentPanel;
  
  private JPanel followsPanel;
  private JTextArea followsText;
  private JScrollPane followsScroll;
  
  private JPanel tweetsPanel;
  private JTextArea tweetsText;
  private JScrollPane tweetsScroll;
  
  // Constructor
  public UserPanel(String user) {
    super(new BorderLayout());
    
    this.db = TwitterDB.getInstance();
    
    this.user = db.getUser(user);
    
    contentPanel = new JPanel();
    
    JLabel userLabel = new JLabel(user);
    userLabel.setFont(new Font("Arial", Font.BOLD, 24));
    
    /*
     * Add Tweets content
     */
    tweetsPanel = new JPanel();
    tweetsText = new JTextArea();
    tweetsText.setEditable(false);
    tweetsScroll = new JScrollPane(tweetsText);
    tweetsScroll.setPreferredSize(new Dimension(480, 300));
    tweetsPanel.add(new JLabel("Tweets:"),BorderLayout.WEST);
    tweetsPanel.add(tweetsScroll,BorderLayout.CENTER);

    /*
     *  Add Follows content
     */
    followsPanel = new JPanel();
    followsText = new JTextArea();
    followsText.setEditable(false);
    followsScroll = new JScrollPane(followsText);
    followsScroll.setPreferredSize(new Dimension(480, 200));
    followsPanel.add(new JLabel("Follows:"),BorderLayout.WEST);
    followsPanel.add(followsScroll,BorderLayout.CENTER);
    
    contentPanel.add(userLabel,BorderLayout.NORTH);
    contentPanel.add(followsPanel,BorderLayout.CENTER);
    contentPanel.add(tweetsPanel,BorderLayout.SOUTH);
    
    /*
     *  Add Buttons
     */
    
    JButton followButton = new JButton("Follow User");
    followButton.setActionCommand(followButton.getText());
    followButton.addActionListener(this);
    
    JButton tweetButton = new JButton("Tweet");
    tweetButton.setActionCommand(tweetButton.getText());
    tweetButton.addActionListener(this);
    
    contentPanel.setPreferredSize(new Dimension(550, 600));
    add(contentPanel, BorderLayout.CENTER);
    
    JPanel panel = new JPanel(new GridLayout(7,1));
    panel.add(new JLabel());
    panel.add(followButton);
    panel.add(new JLabel());
    panel.add(new JLabel());
    panel.add(tweetButton);
    add(panel, BorderLayout.EAST);
  }
  
  // Handle actions in the UserPanel GUI
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    String selected = "NONE";
    //System.out.println(">> "+command);
    if (command.equals("Follow User")) {
      String follow = JOptionPane.showInputDialog(null, "Enter user name:");
      follow = "User: "+follow;
      if (follow.equals("User: null")) {
        JOptionPane.showMessageDialog(null,
                                      "Invalid user specified.",
                                      "Error",
                                      JOptionPane.ERROR_MESSAGE);
      } else if (!db.containsUser(follow)) {
        JOptionPane.showMessageDialog(null,
                                      follow+" does not exist.",
                                      "Error",
                                      JOptionPane.ERROR_MESSAGE);
      } else if (user.isFollowing(follow)) {
        JOptionPane.showMessageDialog(null,
                                      "Already following "+follow+".",
                                      "Error",
                                      JOptionPane.ERROR_MESSAGE);
      }
      user.followUser(follow);
    } else if (command.equals("Tweet")) {
      String text = JOptionPane.showInputDialog(null, "Enter tweet:");
      if (text == null) {
        JOptionPane.showMessageDialog(null,
                                      "Invalid tweet.",
                                      "Error",
                                      JOptionPane.ERROR_MESSAGE);
      } else if (text.length() > 140) {
        JOptionPane.showMessageDialog(null,
                                      "Tweet is longer than 140 characters.",
                                      "Error",
                                      JOptionPane.ERROR_MESSAGE);
      } else {
        db.addTweet(new Tweet(user, text));
      }
    }
    db.refresh();
  }
  
  // Create UserPanel
  public void create() {
    frame = new JFrame("MiniTwitter User Panel");
    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    this.setOpaque(true);
    frame.setContentPane(this);
    frame.pack();
  }
  
  // Show UserPanel
  public void on() {
    frame.setVisible(true);
  }
  
  // Hide UserPanel
  public void off() {
    frame.setVisible(false);
  }
  
  // Refresh UserPanel
  public void refresh() {
    followsText.setText("");
    for (User u: user.getFollows()) {
      if (u != user)
        followsText.append( u.getID() + "\n" );
    }
    tweetsText.setText("");
    for (Tweet tweet: user.getTweets()) {
      tweetsText.append( tweet.getTweet() + "\n" );
    }
  }
  
}