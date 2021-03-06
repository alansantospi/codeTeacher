package gui.component.checkboxTree.jcheckboxtree;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.tree.TreePath;

public class JCheckboxTreeDemo extends JFrame {

	private static final long serialVersionUID = 4648172894076113183L;

	public JCheckboxTreeDemo() {
		super();
		setSize(500, 500);
		this.getContentPane().setLayout(new BorderLayout());
		final JCheckBoxTree cbt = new JCheckBoxTree();
		this.getContentPane().add(cbt);
		cbt.addCheckChangeEventListener(new JCheckBoxTree.CheckChangeEventListener() {
			public void checkStateChanged(JCheckBoxTree.CheckChangeEvent event) {
				System.out.println("event");
				TreePath[] paths = cbt.getCheckedPaths();
				for (TreePath tp : paths) {
					for (Object pathPart : tp.getPath()) {
						System.out.print(pathPart + ",");
					}
					System.out.println();
				}
			}
		});
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String args[]) {
		JCheckboxTreeDemo m = new JCheckboxTreeDemo();
		m.setVisible(true);
	}
}
