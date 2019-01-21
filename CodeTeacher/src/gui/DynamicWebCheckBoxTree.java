package gui;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import com.alee.extended.tree.WebCheckBoxTree;

public class DynamicWebCheckBoxTree extends WebCheckBoxTree<DefaultMutableTreeNode> {

	protected DefaultMutableTreeNode rootNode;
	protected DefaultTreeModel treeModel;

	public DynamicWebCheckBoxTree(DefaultMutableTreeNode root) {
		super(root);
		treeModel = (DefaultTreeModel) getModel();
		rootNode = (DefaultMutableTreeNode) treeModel.getRoot();

		treeModel.setRoot(root);
	}

	/** Remove all nodes except the root node. */
	public void clear() {

//		DefaultTreeModel model = (DefaultTreeModel) getModel();
//		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		rootNode.removeAllChildren();
		treeModel.reload();

//		getRootNode().removeAllChildren();
//		((DefaultTreeModel) getDefaultTreeModel()).reload();
	}

	/** Remove the currently selected node. */
	public void removeCurrentNode() {
//		DefaultTreeModel model = (DefaultTreeModel) getModel();
		TreePath currentSelection = this.getSelectionPath();
		if (currentSelection != null) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
			MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
			if (parent != null) {
				treeModel.removeNodeFromParent(currentNode);
				return;
			}
		}
	}

	/** Add child to the currently selected node. */
	public DefaultMutableTreeNode addObject(Object child) {
		DefaultMutableTreeNode parentNode = null;
		TreePath parentPath = this.getSelectionPath();

		if (parentPath == null) {
			parentNode = rootNode;
		} else {
			parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
		}

		return addObject(parentNode, child, true);
	}

	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child) {
		return addObject(parent, child, false);
	}

	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible) {

		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
		
		TreePath treePath = new TreePath(childNode.getPath());
		setSelectionPath(treePath);

		if (parent == null) {
			parent = rootNode;
		}

		for (int index = 0; index < treeModel.getChildCount(parent); index++) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeModel.getChild(parent, index);

			if (node.getUserObject().equals(child)) {
				return node;
			}
		}
		// It is key to invoke this on the TreeModel, and NOT
		// DefaultMutableTreeNode
		treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

		// Make sure the user can see the lovely new node.
		if (shouldBeVisible) {
			this.scrollPathToVisible(new TreePath(childNode.getPath()));
		}
		setChecked(childNode, true);
		
		return childNode;
	}

}
