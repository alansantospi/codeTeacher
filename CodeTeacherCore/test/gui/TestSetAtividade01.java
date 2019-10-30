package gui;

import java.util.HashSet;
import java.util.Set;

import codeteacher.analyzers.MethodAnalyzer;
import codeteacher.analyzers.OutputAnalyzer;
import codeteacher.analyzers.TestSet;



public class TestSetAtividade01 extends TestSet {
	
	public static void setUp() {
		Set<String> modifiers = new HashSet<String>();
		String visibility = "public";
		modifiers.add(visibility);
		addTest("EntendendoGetClass", 
				new OutputAnalyzer(modifiers, "void",
						"main", false,
						"A classe do objeto str1 eh: class java.lang.String" +
						"A classe do objeto str1 eh: java.lang.String" +
						"A classe do objeto int1 eh: class java.lang.Integer" + 
						"A classe do objeto int1 eh: java.lang.Integer", 1, new String[0]),
				new OutputAnalyzer(modifiers, "void","main", false, "Como assim?", 1, new String[0]),
				new OutputAnalyzer(modifiers, "void","main", false, "Testando isso aqui!", 1, new String[0]),
				new OutputAnalyzer(modifiers, "void","main", false, "Esse vai dar erro!", 1, new String[0])
			); 
		
		
		addTest("OperadorInstanceOf", 
				new OutputAnalyzer(
						modifiers, "void","main", false,
						"str1 eh do tipo String: false\r" + "\n" +
						"str1 eh do tipo Object: true\r" + "\n" +
						"int1 eh do tipo Integer: true\r" + "\n" +
						"int1 eh do tipo Object: true\r" + "\n" +
						"int1 eh do tipo Number: true\r\n", 1, new String[0])
		);
		
		addTest("MetodoGetClass", 
				new OutputAnalyzer(
						modifiers, "void","main", false,
						"A classe do objeto str1 eh:class java.lang.String\r\n" +
						"A classe do objeto str1 eh:java.lang.String\r\n" +
						"A classe do objeto int1 eh:class java.lang.Integer\r\n" +
						"A classe do objeto int1 eh: java.lang.Integer\r" + "\n" +
						"A classe do objeto int1 eh:class java.util.Date\r\n" +
						"str1 eh do tipo String: false\r\n" +
						"str1 eh do tipo Object: true\r\n" +
						"int1 eh do tipo Integer: true\r\n" +
						"int1 eh do tipo Object: true\r\n" +
						"int1 eh do tipo Number: true\r\n", 1, new String[0])
		);
	}
}
