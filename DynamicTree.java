import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

/*
 * DynamicTree class
 */
class DynamicTree extends JPanel {
  protected DefaultMutableTreeNode rootNode;
  protected DefaultTreeModel treeModel;
  protected JTree tree;

  // Constructor
  public DynamicTree() {
    super(new GridLayout(1, 0));

    rootNode = new DefaultMutableTreeNode("Root");
    treeModel = new DefaultTreeModel(rootNode);

    // Integrated JTree
    tree = new JTree(treeModel);
    tree.setEditable(false);
    tree.getSelectionModel().setSelectionMode(
        TreeSelectionModel.SINGLE_TREE_SELECTION);
    tree.setShowsRootHandles(true);

    JScrollPane scrollPane = new JScrollPane(tree);
    add(scrollPane);
  }

  // add object to DynamicTree
  public DefaultMutableTreeNode addObject(Object child) {
    DefaultMutableTreeNode parentNode = getSelected();  
    return addObject(parentNode, child, true);
  }

  // add object to DynamicTree
  public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
      Object child) {
    return addObject(parent, child, false);
  }

  // add object to DynamicTree
  public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
      Object child, boolean shouldBeVisible) {
    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
    if (parent == null) {
      parent = rootNode;
    }
    treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
    if (shouldBeVisible) {
      tree.scrollPathToVisible(new TreePath(childNode.getPath()));
    }
    return childNode;
  }
  
  // get selected object from DynamicTree
  public DefaultMutableTreeNode getSelected() {
   DefaultMutableTreeNode parentNode = null; 
   TreePath parentPath = tree.getSelectionPath();
   if (parentPath == null) {
    parentNode = rootNode;
   } else {   
    parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
   }
   return parentNode;
  }
  

  // Subclass MyTreeModelListener to observe changes to DynamicTree
  class MyTreeModelListener implements TreeModelListener {
    public void treeNodesChanged(TreeModelEvent e) {
      DefaultMutableTreeNode node;
      node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());

      int index = e.getChildIndices()[0];
      node = (DefaultMutableTreeNode) (node.getChildAt(index));
    }

    public void treeNodesInserted(TreeModelEvent e) {
    }

    public void treeNodesRemoved(TreeModelEvent e) {
    }

    public void treeStructureChanged(TreeModelEvent e) {
    }
  }
}