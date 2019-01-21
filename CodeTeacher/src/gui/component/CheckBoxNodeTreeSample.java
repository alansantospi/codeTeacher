package gui.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

public class CheckBoxNodeTreeSample {
	
  public static void main(String args[]) {
    JFrame frame = new JFrame("CheckBox Tree");

    CheckBoxNode accessibilityOptions[] = {
        new CheckBoxNode(
            "Move system caret with focus/selection changes", false),
        new CheckBoxNode("Always expand alt text for images", true) };
    CheckBoxNode browsingOptions[] = {
        new CheckBoxNode("Notify when downloads complete", true),
        new CheckBoxNode("Disable script debugging", true),
        new CheckBoxNode("Use AutoComplete", true),
        new CheckBoxNode("Browse in a new process", false) };
    Vector accessVector = new NamedVector("Accessibility",
        accessibilityOptions);
    Vector browseVector = new NamedVector("Browsing", browsingOptions);
    Object rootNodes[] = { accessVector, browseVector };
    Vector rootVector = new NamedVector("Root", rootNodes);
    JTree tree = new JTree(rootVector);

    CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
    tree.setCellRenderer(renderer);

    tree.setCellEditor(new CheckBoxNodeEditor(tree));
    tree.setEditable(true);

    JScrollPane scrollPane = new JScrollPane(tree);
    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    frame.setSize(300, 150);
    frame.setVisible(true);
  }
}

class NamedVector extends Vector {
  String name;

  public NamedVector(String name) {
    this.name = name;
  }

  public NamedVector(String name, Object elements[]) {
    this.name = name;
    for (int i = 0, n = elements.length; i < n; i++) {
      add(elements[i]);
    }
  }

  public String toString() {
    return "[" + name + "]";
  }
}