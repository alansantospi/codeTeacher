package gui.component.checkboxTree;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.alee.extended.tree.WebCheckBoxTree;

/**
 * CheckBox tree example.
 *
 */
public class WebCheckBoxTreeExample {
	
	public static void main(String[] args) {
		// Checkbox tree
		final WebCheckBoxTree checkBoxTree = new WebCheckBoxTree();
		checkBoxTree.setVisibleRowCount(8);
		
		
		JFrame frame = new JFrame();
		frame.add(checkBoxTree);
		frame.setTitle("TEST OUTPUT");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
}
