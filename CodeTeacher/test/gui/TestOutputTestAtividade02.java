package gui;
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
import gui.strategy.JavaStrategy;
import gui.strategy.OpenResourceStrategy;


public class TestOutputTestAtividade02 {

	public static void main(String[] args) {
		
//		testTestaIntegerRecursive();
//		testTestaIntegerRegex();
		testTesteIgualdadeStringsProjectRecursive();

	}
	
	private static void testTestaIntegerRecursive() {
		String studentDir = "C:\\Users\\edina\\Downloads\\IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_02 - Copia\\";
		String projectName = "ComparandoObjetos";
		String className = "TestaInteger";
		boolean regex = false;
		boolean caseSensitive = false;
		boolean recursive = true;
		String[] args = new String[0];
		
		procedure(args, studentDir, projectName, className, regex, caseSensitive, recursive);
	}

	private static void testTesteIgualdadeStringsProjectRecursive() {
		String studentDir = "C:\\Users\\edina\\Downloads\\IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_02 - Copia\\";
		String projectName = "ComparandoObjetos";
		String className = "TesteIgualdadeStrings";
		boolean regex = false;
		boolean caseSensitive = false;
		boolean recursive = true;
		String[] args = new String[0];
		
		Config cfg = new Config(studentDir, projectName);
		OpenResourceStrategy strategy = new JavaStrategy();
		
		Set<String> modifiers = new HashSet<String>();
		modifiers.add("public");
		modifiers.add("static");
		String returnType = "void";
		String methodName = "main";
		String expected = "String1: Curso de Java\n"
						+ "String2: Curso de Java\n"
						+ "Mesmo objeto? true\n"
						+ "String1: Curso de Java\n"
						+ "String2: Curso de Java\n"
						+ "Mesmo objeto? false\n"
						+ "Mesmo valor? true\n";
		int value = 25;
		String[] paramArray = {"java.lang.String[]"};
		OutputAnalyzer analyzer = FactoryOutputAnalyzr.create(modifiers, returnType , methodName, regex, expected, value, args, paramArray);
		boolean klazzRecursive = true; 
		analyzer.setKlazzRecursive(klazzRecursive);
		
		boolean ignoreWhiteSpaces = true;
		boolean ignoreLinebreaks = true;
		boolean ignoreTabs = true;
		analyzer.setIgnoreWhiteSpaces(ignoreWhiteSpaces);
		analyzer.setIgnoreLinebreaks(ignoreLinebreaks);
		analyzer.setIgnoreTabs(ignoreTabs);
		
		Map<String, List<Analyzer>> tests = new HashMap<String, List<Analyzer>>();
		tests.put(className, new ArrayList<Analyzer>());
		tests.get(className).add(analyzer);

		Project proj = new Project(cfg, tests, caseSensitive, regex, recursive);
		proj.setStrategy(strategy );
		
		TestSet.addTest(className, analyzer);
		
		FrameTest frame = new FrameTest(proj);
		
		frame.setTitle("TEST OUTPUT");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.getContentPane().add(new FrameTest());
//		frame.setSize(700, 600);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setLocationRelativeTo(null);

		frame.updateButtons();
		frame.updateTreeOutput();
//		frame.pack();
		frame.setVisible(true);
		
	}
	
	private static void testTestaIntegerRegex() {
		String studentDir = "C:\\Users\\edina\\Downloads\\IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_02 - Copia\\";
		String projectName = ".*";
		String className = "TestaInteger";
		boolean regex = true;
		boolean caseSensitive = false;
		boolean recursive = true;
		String[] args = new String[0];
		
		procedure(args, studentDir, projectName, className, regex, caseSensitive, recursive);
	}

	private static void procedure(String[] args, String studentDir, String projectName, String className, boolean regex,
			boolean caseSensitive, boolean recursive) {
		Config cfg = new Config(studentDir, projectName);
		
		Set<String> modifiers = new HashSet<String>();
		modifiers.add("public");
		modifiers.add("static");
		String returnType = "void";
		String methodName = "main";
		String expected = "diferente\n";
		int value = 25;
		String[] paramArray = {"java.lang.String[]"};
		Analyzer analyzer = FactoryOutputAnalyzr.create(modifiers, returnType , methodName, regex, expected, value, args, paramArray);
		
		Map<String, List<Analyzer>> tests = new HashMap<String, List<Analyzer>>();
		tests.put(className, new ArrayList<Analyzer>());
		tests.get(className).add(analyzer);

		Project proj = new Project(cfg, tests, caseSensitive, regex, recursive);
		
		TestSet.addTest(className, analyzer);
		
		FrameTest frame = new FrameTest(proj);
		
		frame.setTitle("TEST OUTPUT");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.getContentPane().add(new FrameTest());
//		frame.setSize(700, 600);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setLocationRelativeTo(null);

		frame.updateButtons();
		frame.updateTreeOutput();
//		frame.pack();
		frame.setVisible(true);
	}
}
