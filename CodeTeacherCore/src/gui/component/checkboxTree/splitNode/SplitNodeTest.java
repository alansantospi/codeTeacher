package gui.component.checkboxTree.splitNode;

import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;
import javax.swing.*;
import javax.swing.tree.*;
  
public class SplitNodeTest {
    private JScrollPane getContent() {
        Icon icon = UIManager.getIcon("Tree.closedIcon");
        DefaultMutableTreeNode root =
            new DefaultMutableTreeNode(
                     new SplitNode("Root", false, icon));
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(
                     new SplitNode("Node 1", false, icon));
		root.add(node1);
		node1.add(new DefaultMutableTreeNode(
                     new SplitNode("Node 1.2", true, icon)));
        root.add(new DefaultMutableTreeNode(
                     new SplitNode("Node 2", true, icon)));
        JTree tree = new JTree(new DefaultTreeModel(root));
        tree.setEditable(true);
        tree.setCellRenderer(new SplitNodeRenderer());
        tree.setCellEditor(new SplitNodeEditor());
        return new JScrollPane(tree);
    }
  
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new SplitNodeTest().getContent());
        f.setSize(360,300);
        f.setLocation(200,200);
        f.setVisible(true);
    }
}
  
class SplitNode {
    String name;
    boolean value;
    Icon icon;
  
    public SplitNode(String s, boolean b, Icon icon) {
        name = s;
        value = b;
        this.icon = icon;
    }
  
    public String toString() {
        return "SplitNode[value: " + value + ", name:" + name +
               "icon: closedIcon" + "]";
    }
}
  
class SplitNodeRenderer implements TreeCellRenderer {
    JCheckBox checkBox;
    JLabel label;
    JPanel panel;
  
    public SplitNodeRenderer() {
        checkBox = new JCheckBox();
        checkBox.setBackground(UIManager.getColor("Tree.background"));
        checkBox.setBorder(null);
        label = new JLabel();
        label.setFont(UIManager.getFont("Tree.font"));
        panel = new JPanel();
        panel.setOpaque(false);
        panel.add(checkBox);
        panel.add(label);
    }
  
    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value,
                                                  boolean selected,
                                                  boolean expanded,
                                                  boolean leaf,
                                                  int row,
                                                  boolean hasFocus) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        SplitNode splitNode = (SplitNode)node.getUserObject();
        checkBox.setSelected(splitNode.value);
        label.setIcon(splitNode.icon);
        label.setText(splitNode.name);
        return panel;
    }
}
  
class SplitNodeEditor extends AbstractCellEditor
                      implements TreeCellEditor, ActionListener {
    JCheckBox checkBox;
    JLabel label;
    JTextField textField;
    SplitNode splitNode;
    JComponent editedComponent;
    JPanel panel;
  
    public SplitNodeEditor() {
        checkBox = new JCheckBox();
        checkBox.addActionListener(this);
        checkBox.setBackground(UIManager.getColor("Tree.background"));
        checkBox.setBorder(null);
        label = new JLabel();
        label.setFont(UIManager.getFont("Tree.font"));
        textField = new JTextField();
        textField.addActionListener(this);
        textField.setBackground(UIManager.getColor("Tree.background"));
        textField.setBorder(null);
        panel = new JPanel();
        panel.setOpaque(false);
        panel.add(checkBox);
        panel.add(label);
        panel.add(textField);
    }
  
    public Component getTreeCellEditorComponent(JTree tree,
                                                Object value,
                                                boolean isSelected,
                                                boolean expanded,
                                                boolean leaf,
                                                int row) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        splitNode = (SplitNode)node.getUserObject();
        checkBox.setSelected(splitNode.value);
        label.setIcon(splitNode.icon);
        textField.setText(splitNode.name);
        return panel;
    }
  
    public Object getCellEditorValue() {
        if(editedComponent == textField)
            splitNode.name = textField.getText();
        else
            splitNode.value = checkBox.isSelected();
        return splitNode;
    }
  
    public boolean isCellEditable(EventObject anEvent) {
        if (anEvent instanceof MouseEvent) {
            return ((MouseEvent)anEvent).getClickCount() >= 1;
        }
        return true;
    }
  
    public void actionPerformed(ActionEvent e) {
        editedComponent = (JComponent)e.getSource();
        super.stopCellEditing();
    }
}