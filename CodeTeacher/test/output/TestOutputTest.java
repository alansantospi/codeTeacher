package output;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;

import codeteacher.Config;
import codeteacher.analyzers.Analyzer;
import codeteacher.analyzers.OutputAnalyzer;
import codeteacher.analyzers.TestSet;
import gui.FactoryOutputAnalyzr;
import gui.FrameTest;
import gui.Project;


public class TestOutputTest {
//	String studentDir = "C:\\Users\\User\\Google Drive\\IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_01\\";
	private static String studentDir = "C:\\Users\\edina\\Downloads\\IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_01\\";

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
		
		Set<String> modifiers = new HashSet<String>();
		modifiers.add("public");
		modifiers.add("static");
		String returnType = "void";
		String methodName = "main";
		String expected = "A classe do objeto strl eh: class java.lang.String"
				+ "\nA classe do objeto strl eh: java.lang.String"
				+ "\nA classe do objeto intl eh: class java.lang.Integer"
				+ "\nA classe do objeto intl eh: java.lang.Integer"
				+ "\nA classe do objeto intl eh: class java.util.Date"
				+ "\nA classe do objeto intl eh: java.util.Date"
				+ "\n";
		int value = 25;
		String[] paramArray = {"java.lang.String[]"};
		OutputAnalyzer analyzer = FactoryOutputAnalyzr.create(modifiers, returnType, methodName, regex, expected, value, args, paramArray);
		analyzer.setKlazzRecursive(true);
		
		Map<String, List<Analyzer>> tests = new HashMap<String, List<Analyzer>>();
		tests.put(className, new ArrayList<Analyzer>());
		tests.get(className).add(analyzer);

		Project proj = new Project(cfg, tests, regex, caseSensitive, true);
		
		TestSet.addTest(className, analyzer);
		
		FrameTest frame = new FrameTest(proj);
		
		frame.setTitle("TEST OUTPUT");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setLocationRelativeTo(null);
		
		

		frame.updateButtons();
		frame.updateTreeOutput();
		frame.setVisible(true);
	}
}
