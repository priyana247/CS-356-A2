import java.util.*;
import javax.swing.tree.*;

/*
 *  TwitterTree class
 */
public class TwitterTree extends DynamicTree {
  
  private static TwitterTree instance = null;
 
  // Constructor
  private TwitterTree() {
	  super();
  }
 
  // Singleton Accessor Method
  public static TwitterTree getInstance() {
	  if (instance == null)
		  instance = new TwitterTree();
	  return instance;
  }
 
  // Get TwitterTree User count
  public int getUserCount() {
	  return getUserCount(rootNode);
  }
 
  // Get TwitterTree User count (recursive)
  @SuppressWarnings("unchecked")
  private int getUserCount(MutableTreeNode node) {
	  int count = 0;
	  for (Enumeration<MutableTreeNode> e = node.children(); e.hasMoreElements();) {
		  MutableTreeNode child = e.nextElement();
		  if (child.toString().substring(0,4).equals("User"))
			  count += 1;
		  else
			  count += getUserCount(child);
	  }
	  return count;
  }
 
  // Get TwitterTree Group count
  public int getGroupCount() {
	  return getGroupCount(rootNode);
  }
 
  // Get TwitterTree Group count (recursive)
  @SuppressWarnings("unchecked")
  private int getGroupCount(MutableTreeNode node) {
	  int count = 0;
	  for (Enumeration<MutableTreeNode> e = node.children(); e.hasMoreElements();) {
		  MutableTreeNode child = e.nextElement();
		  if (child.toString().substring(0,5).equals("Group"))
			  count += 1 + getGroupCount(child);
	  }
	  return count;
  }

}
