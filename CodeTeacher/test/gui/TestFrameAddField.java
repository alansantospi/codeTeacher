package gui;

import javax.swing.JFrame;
import javax.swing.tree.DefaultMutableTreeNode;

import codeteacher.analyzers.ClassAnalyzer;
import codeteacher.analyzers.FieldAnalyzer;
import codeteacher.analyzers.FinalFieldAnalyzer;
import codeteacher.analyzers.StaticFieldAnalyzer;
import utils.ReflectionUtils;

public class TestFrameAddField {

	boolean show;

	public void test_static_final() {

		String klazzName = "AClass";
		boolean caseSensitive = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;

		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestFrameAddField\\";

		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, recursive, caseSensitive, regex, value);

		boolean declared = false;
		boolean fieldRegex = false;
		boolean fieldCase = false;

		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, "String", declared, "staticField", fieldRegex,
				fieldCase);

		FrameAddField frame = new FrameAddField(fieldAnalyzer);
		DefaultMutableTreeNode node = frame.addToTree(classAnalyzer);
		DefaultMutableTreeNode childNode = frame.addToTree(node, fieldAnalyzer);

		StaticFieldAnalyzer staticFieldAnalyzer = new StaticFieldAnalyzer(fieldAnalyzer);
		frame.addToTree(childNode, staticFieldAnalyzer);

		FinalFieldAnalyzer finalFieldAnalyzer = new FinalFieldAnalyzer(fieldAnalyzer);
		frame.addToTree(childNode, finalFieldAnalyzer);

//		fieldAnalyzer = new FieldAnalyzer(classAnalyzer, "int", declared, "staticField", fieldRegex, fieldCase);
//		DefaultMutableTreeNode addToTree = frame.addToTree(node, fieldAnalyzer);

		frame.setTitle("TEST FRAME ADD FIELD");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(show);

//		testProcedure(fieldAnalyzer);
	}

	private void testProcedure(FieldAnalyzer fieldAnalyzer) {
		FrameAddField frame = new FrameAddField(fieldAnalyzer);

		frame.setTitle("TEST FRAME ADD FIELD");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(show);
	}

	public static void main(String[] args) {
		TestFrameAddField test = new TestFrameAddField();
		test.show = true;
		test.test_static_final();
	}
}
