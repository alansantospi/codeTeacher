package gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.alee.extended.panel.GroupPanel;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.tabbedpane.WebTabbedPane;

import gui.msg.FrameTestMsg;
import gui.msg.I18N;

public class FrameAddType extends WebFrame {
	
	private static FrameTestField previous;

	public FrameAddType(FrameTestField previous) {

		this.previous = previous;
		WebTabbedPane tabbedPane = new WebTabbedPane();
//		tabbedPane.setPreferredSize(new Dimension(150, 120));
		tabbedPane.setTabPlacement(WebTabbedPane.LEFT);
		setupTabbedPane(tabbedPane);

		GroupPanel groupPanel = new GroupPanel(tabbedPane);
		getContentPane().add(groupPanel);
	}

	private static void setupTabbedPane(JTabbedPane tabbedPane) {
		// Simple tab
		tabbedPane.addTab("Type", new PanelAddType(previous));

		// Disabled tab
		tabbedPane.addTab("Fields", new PanelAddField2(previous));
//		tabbedPane.setEnabledAt(1, false);

		// Selected tab
		tabbedPane.addTab("Methods", new PanelAddMethod(previous));
		tabbedPane.setSelectedIndex(0);

		// Colored tab
//		tabbedPane.addTab("Colored 4", new WebLabel());
//		tabbedPane.setBackgroundAt(3, new Color(255, 212, 161));
	}

	public static void main(String[] args) {
		// Its very important to call this before setting Look & Feel
		I18N.getVal(FrameTestMsg.ADD_BEHAVIOR);

		// Look and Feel
		WebLookAndFeel.install();

		FrameAddType frame = new FrameAddType(null);
		frame.pack();
		frame.center();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
