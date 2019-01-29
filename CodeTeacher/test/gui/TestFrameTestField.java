package gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.tree.DefaultMutableTreeNode;

import com.alee.laf.WebLookAndFeel;

import codeteacher.Config;
import codeteacher.analyzers.Analyzer;
import codeteacher.analyzers.ClassAnalyzer;
import codeteacher.analyzers.FieldAnalyzer;
import codeteacher.analyzers.FinalFieldAnalyzer;
import codeteacher.analyzers.FinalMethodAnalyzer;
import codeteacher.analyzers.MethodAnalyzer;
import codeteacher.analyzers.PrivateMethodAnalyzer;
import codeteacher.analyzers.StaticFieldAnalyzer;
import codeteacher.analyzers.StaticMethodAnalyzer;
import gui.msg.FrameTestMsg;
import gui.msg.I18N;

public class TestFrameTestField {
	private static String studentDir = "C:\\Users\\edina\\Downloads\\IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_01\\";
//	private static String studentDir = "C:\\Users\\edina\\Downloads\\IFMA_BAC_2016_2_POO_TIF_IV\\";

	public static void main(String[] args) {
		testOutputRegexJar();
	}

	private static void testOutputCaseSensitiveJar() {
		String projectName = "EntendendoGetClass";
		String className = "MetodoGetClass";
		boolean caseSensitive = false;
		boolean regex = true;
		String[] args = new String[0];

		testProcedure(args, studentDir, projectName, className, caseSensitive, regex);

	}

	private static void testOutputRegexJar() {
		String projectName = "Enten.*GetClas*";
		String className = "MetodoGetClass";
		boolean caseSensitive = false;
		boolean regex = true;
		String[] args = new String[0];

		testProcedure(args, studentDir, projectName, className, caseSensitive, regex);

	}

	private static void testProcedure(String[] args, String studentDir, String projectName, String className,
			boolean caseSensitive, boolean regex) {
		
		Config cfg = new Config(studentDir, projectName);
		Map<String, List<Analyzer>> tests = new HashMap<String, List<Analyzer>>();
		Project proj = new Project(cfg, tests, regex, caseSensitive, true);

		// Its very important to call this before setting Look & Feel
		I18N.getVal(FrameTestMsg.ADD_BEHAVIOR);

		// Look and Feel
		WebLookAndFeel.install();
		
		FrameTestField frame = new FrameTestField(proj);
		
		String klazzName = "TestClass";
		boolean recursive = false;
		
		boolean declared = false;
		boolean fieldRegex = false;
		boolean fieldCase = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(klazzName, recursive, caseSensitive, regex, value);
		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, "String", declared, "staticField", fieldRegex, fieldCase, 3);

		DefaultMutableTreeNode node = frame.addToTree(classAnalyzer);
		DefaultMutableTreeNode fieldNode = frame.addToTree(node, fieldAnalyzer);
		
		StaticFieldAnalyzer staticFieldAnalyzer = new StaticFieldAnalyzer(fieldAnalyzer, 2);
		frame.addToTree(fieldNode, staticFieldAnalyzer);
		
		FinalFieldAnalyzer finalFieldAnalyzer = new FinalFieldAnalyzer(fieldAnalyzer, 1);
		frame.addToTree(fieldNode, finalFieldAnalyzer);

		MethodAnalyzer methodAnalyzer = new MethodAnalyzer(classAnalyzer, declared, "String", "myMethod", fieldRegex, fieldCase, 3);
		DefaultMutableTreeNode methodNode = frame.addToTree(node, methodAnalyzer);
		
		PrivateMethodAnalyzer privateMethodAnalyzer = new PrivateMethodAnalyzer(methodAnalyzer, 4);
		frame.addToTree(methodNode, privateMethodAnalyzer);
		
		StaticMethodAnalyzer staticMethodAnalyzer = new StaticMethodAnalyzer(methodAnalyzer, 4);
		frame.addToTree(methodNode, staticMethodAnalyzer);
		
		FinalMethodAnalyzer finalMethodAnalyzer = new FinalMethodAnalyzer(methodAnalyzer, 4);
		frame.addToTree(methodNode, finalMethodAnalyzer);
		
		frame.setTitle("TEST FRAME TEST");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.center();
		frame.setVisible(true);
	}
}
