package output;

import java.util.HashSet;
import java.util.Set;

import codeteacher.analyzers.OutputAnalyzer;
import codeteacher.analyzers.TestSet;

public class TestSetAtividade02 extends TestSet {

	public static void setUp() {
		Set<String> modifiers = new HashSet<String>();
		modifiers.add("public");
		modifiers.add("static");
		addTest("TestaInteger", 
				new OutputAnalyzer(modifiers, "void", "main", false, "diferente", 1, new String[0]),
				new OutputAnalyzer(modifiers, "void","main", false, "Como assim", 1, new String[0]),
				new OutputAnalyzer(modifiers, "void","main", false, "Testando isso aqui!", 1, new String[0]),
				new OutputAnalyzer(modifiers, "void","main", false, "Esse vai dar erro!", 1, new String[0])
			); 
	}
	
}
