import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

/*
 *  AdminPanel class
 */
public class AdminPanel extends JPanel implements ActionListener {
  
  private static AdminPanel instance = null;
  private static TwitterTree treePanel = TwitterTree.getInstance();;
  private static TwitterDB db = TwitterDB.getInstance();
  
  // Constructor
  private AdminPanel() {
    
    super(new BorderLayout());
    
    /*
     *  Add buttons to AdminPanel
     */
    
    JButton addUserButton = new JButton("Add New User");
    addUserButton.setActionCommand(addUserButton.getText());
    addUserButton.addActionListener(this);
    
    JButton openUserButton = new JButton("Open User View");
    openUserButton.setActionCommand(openUserButton.getText());
    openUserButton.addActionListener(this);
    
    JButton addGroupButton = new JButton("Add New Group");
    addGroupButton.setActionCommand(addGroupButton.getText());
    addGroupButton.addActionListener(this);
    
    JButton totalUsersButton = new JButton("Show Total Users");
    totalUsersButton.setActionCommand(totalUsersButton.getText());
    totalUsersButton.addActionListener(this);
    
    JButton totalGroupsButton = new JButton("Show Total Groups");
    totalGroupsButton.setActionCommand(totalGroupsButton.getText());
    totalGroupsButton.addActionListener(this);
    
    JButton totalMessagesButton = new JButton("Show Total Messages");
    totalMessagesButton.setActionCommand(totalMessagesButton.getText());
    totalMessagesButton.addActionListener(this);
    
    JButton positivityButton = new JButton("Show Positivity");
    positivityButton.setActionCommand(positivityButton.getText());
    positivityButton.addActionListener(this);
    
    /*
     *  Add Tree to AdminPanel
     */
    
    treePanel.setPreferredSize(new Dimension(300, 600));
    add(treePanel, BorderLayout.CENTER);
    
    JPanel panel = new JPanel(new GridLayout(7, 1));
    panel.add(addUserButton);
    panel.add(openUserButton);
    panel.add(addGroupButton);
    panel.add(totalUsersButton);
    panel.add(totalGroupsButton);
    panel.add(totalMessagesButton);
    panel.add(positivityButton);
    add(panel, BorderLayout.EAST);
  }
  
  // Handle actions in the AdminPanel GUI
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    String selected = treePanel.getSelected().toString();
    //System.out.println(">> "+command);
    if (command.equals("Add New User")) {
      if (!selected.equals("Root")) {
        if (selected.toString().substring(0,4).equals("User")) {
          JOptionPane.showMessageDialog(null,
                                        "Cannot add to user. Please select a group.",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
          return;
        }
      }
      String user = JOptionPane.showInputDialog(null, "Enter user name:");
      user = "User: "+user;
      if (user.equals("User: null")) {
        JOptionPane.showMessageDialog(null,
                                        "Invalid user specified.",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
      } else if (db.containsUser(user)) {
        JOptionPane.showMessageDialog(null,
                                        user+" already exists.",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
      } else if (user.length() > 21) {
        JOptionPane.showMessageDialog(null,
                                        "User name is longer than 15 characters.",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
      } else {
        db.addUser(user, selected);
        treePanel.addObject(user);
      }
    } else if (command.equals("Open User View")) {
      if (selected.toString().equals("Root") || !selected.toString().substring(0,4).equals("User")) {
        JOptionPane.showMessageDialog(null,
                                      "Please select a user to open user view.",
                                      "Error",
                                      JOptionPane.ERROR_MESSAGE);
        return;
      }
      String user = selected.toString();
      db.getUserPanel(user).on();
    } else if (command.equals("Add New Group")) {
      if (!selected.equals("Root")) {
        if (selected.toString().substring(0,4).equals("User")) {
          JOptionPane.showMessageDialog(null,
                                        "Cannot add to user. Please select a group.",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
          return;
        }
      }
      String group = JOptionPane.showInputDialog(null, "Enter group name:");
      group = "Group: "+group;
      if (group.equals("Group: null")) {
        JOptionPane.showMessageDialog(null,
                                        "Invalid group specified.",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
      } else if (db.containsGroup(group)) {
        JOptionPane.showMessageDialog(null,
                                        group+" already exists.",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
      } else {
        db.addGroup(group);
        treePanel.addObject(group);
      }
    } else if (command.equals("Show Total Users")) {
      JOptionPane.showMessageDialog(null,
                                    "Total Users: " + treePanel.getUserCount() );
    } else if (command.equals("Show Total Groups")) {
      JOptionPane.showMessageDialog(null,
                                    "Total Groups: " + treePanel.getGroupCount() );
    } else if (command.equals("Show Total Messages")) {
      JOptionPane.showMessageDialog(null,
                                    "Total Messages: " + db.getTweetsCount() );
    } else if (command.equals("Show Positivity")) {
      JOptionPane.showMessageDialog(null,
                                    "Tweet Positivity: " + Positivity.calculatePercentage() + "%" );
    }
    db.refresh();
  }
  
  // display AdminPanel GUI
  public void display() {
    JFrame frame = new JFrame("MiniTwitter Admin Panel");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setOpaque(true);
    frame.setContentPane(this);
    frame.pack();
    frame.setVisible(true);
  }
  
  // Singleton accessor method
  public static AdminPanel getInstance() {
    if (instance == null)
      instance = new AdminPanel();
    instance.display();
    return instance;
  }
  
  
}