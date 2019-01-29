package gui.component.jtreefiltering;

import java.awt.Component;
import java.util.function.Supplier;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import codeteacher.analyzers.ClassAnalyzer;
import codeteacher.analyzers.FieldAnalyzer;
import codeteacher.analyzers.MethodAnalyzer;
import codeteacher.analyzers.MethodModifierAnalyzer;
import gui.component.jtreefiltering.example.HtmlHighlighter;

public class AnalyzerTreeRenderer extends DefaultTreeCellRenderer {

	private static final String SPAN_FORMAT = "<span style='color:%s;'>%s</span>";
	private Supplier<String> filterTextSupplier;

	public AnalyzerTreeRenderer(Supplier<String> filterTextSupplier) {
		this.filterTextSupplier = filterTextSupplier;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		Object userObject = node.getUserObject();
		
		if (userObject instanceof MethodAnalyzer) {

			MethodAnalyzer ma = (MethodAnalyzer) userObject;
			String text = String.format(SPAN_FORMAT, "rgb(0, 0, 150)", renderFilterMatch(node, ma.getReturnType()));
			text += " " + String.format(SPAN_FORMAT, "rgb(0, 0, 150)", renderFilterMatch(node, ma.getMemberName()));
			text += " [" + String.format(SPAN_FORMAT, "rgb(90, 70, 0)", renderFilterMatch(node, String.valueOf(ma.getValue())))
					+ "]";
			this.setText("<html>" + text + "</html>");

		} else if (userObject instanceof MethodModifierAnalyzer) {

			MethodModifierAnalyzer mma = (MethodModifierAnalyzer) userObject;
			String text = String.format(SPAN_FORMAT, "rgb(0,70,0)", renderFilterMatch(node, mma.getModifier()));
			text += " [" + String.format(SPAN_FORMAT, "rgb(90, 70, 0)",
					renderFilterMatch(node, String.valueOf(mma.getValue()))) + "]";
			this.setText("<html>" + text + "</html>");
			
		} else if (userObject instanceof FieldAnalyzer) {

			FieldAnalyzer ca = (FieldAnalyzer) userObject;
			String text = String.format(SPAN_FORMAT, "rgb(0,70,0)", renderFilterMatch(node, ca.getMemberName()));
			text += " [" + String.format(SPAN_FORMAT, "rgb(90, 70, 0)",
					renderFilterMatch(node, String.valueOf(ca.getValue()))) + "]";
			this.setText("<html>" + text + "</html>");

		} else if (userObject instanceof ClassAnalyzer) {

			ClassAnalyzer ca = (ClassAnalyzer) userObject;
			String text = String.format(SPAN_FORMAT, "rgb(0,70,0)", renderFilterMatch(node, ca.getMemberName()));
//			text += ca.isMatchCase() ? "Match Case" : "";
//			text += ca.isRegex() ? "Regex" : "";
//			text += ca.isRecursive() ? "Recursive" : "";
			text += " [" + String.format(SPAN_FORMAT, "rgb(90, 70, 0)",
					renderFilterMatch(node, String.valueOf(ca.getValue()))) + "]";
			
			this.setText("<html>" + text + "</html>");

		} else {
			String text = String.format(SPAN_FORMAT, "rgb(120,0,0)", renderFilterMatch(node, userObject.toString()));
			this.setText("<html>" + text + "</html>");
		}
		return this;
	}

	private String renderFilterMatch(DefaultMutableTreeNode node, String text) {
		if (node.isRoot()) {
			return text;
		}
		String textToFilter = filterTextSupplier.get();
		return HtmlHighlighter.highlightText(text, textToFilter);
	}
}