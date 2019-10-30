package gui.component;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.io.File;

public class TestTree extends JFrame {
	JTree jt = new JTree();
	DefaultTreeModel dtm = (DefaultTreeModel) jt.getModel();
	JButton newDir = new JButton("new Dir"), newFile = new JButton("new File");

	public TestTree() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container content = getContentPane();
		newDir.setEnabled(false);
		newFile.setEnabled(false);
		jt.setShowsRootHandles(true);
		content.add(new JScrollPane(jt), BorderLayout.CENTER);
		File c = new File("C:");
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(c);
		dtm.setRoot(root);
		addChildren(root);
		jt.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent tse) {
				select(tse);
			}
		});
		JPanel jp = new JPanel();
		content.add(jp, BorderLayout.SOUTH);
		jp.add(newDir);
		jp.add(newFile);
		newDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				TreePath tp = jt.getSelectionPath();
				if (tp != null) {
					DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) tp.getLastPathComponent();
					File newFile = new File((File) dmtn.getUserObject(), "foo");
					if (newFile.mkdir()) {
						dmtn.add(new DefaultMutableTreeNode(newFile));
						dtm.nodeStructureChanged(dmtn);
					} else
						System.out.println("No Dir");
				}
			}
		});
		newFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				TreePath tp = jt.getSelectionPath();
				if (tp != null) {
					DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) tp.getLastPathComponent();
					File newFile = new File((File) dmtn.getUserObject(), "foo.txt");
					try {
						if (newFile.createNewFile()) {
							dmtn.add(new DefaultMutableTreeNode(newFile));
							dtm.nodeStructureChanged(dmtn);
						} else
							System.out.println("No File");
					} catch (java.io.IOException ioe) {
						ioe.printStackTrace();
					}
				}
			}
		});
		setSize(300, 300);
		setVisible(true);
	}

	void select(TreeSelectionEvent tse) {
		TreePath tp = jt.getSelectionPath();
		newDir.setEnabled(false);
		newFile.setEnabled(false);
		if (tp != null) {
			DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) tp.getLastPathComponent();
			File f = (File) dmtn.getUserObject();
			if (f.isDirectory()) {
				newDir.setEnabled(true);
				newFile.setEnabled(true);
			}
		}
	}

	void addChildren(DefaultMutableTreeNode parent) {
		File parentFile = (File) parent.getUserObject();
		File[] children = parentFile.listFiles();
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(children);
				parent.add(child);
				if (children[i].isDirectory())
					addChildren(child);

			}
		}
	}

	public static void main(String[] args) {
		new TestTree();
	}
}