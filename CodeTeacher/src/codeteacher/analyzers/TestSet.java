package codeteacher.analyzers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSet {

	private static Map<String, List<Analyzer>> tests = new HashMap<String, List<Analyzer>>();
	private static Map<String, List<ClassAnalyzer>> fieldTests = new HashMap<String, List<ClassAnalyzer>>();
	
	public static void addFieldTest(String key, ClassAnalyzer... executer){
		if (!fieldTests.containsKey(key)){
			fieldTests.put(key, new ArrayList<ClassAnalyzer>());
		}
		fieldTests.get(key).addAll(Arrays.asList(executer));
	}

	public static Map<String, List<ClassAnalyzer>> getFieldTests() {
		return fieldTests;
	}
	
	public static void addTest(String key, Analyzer... executer){
		if (!tests.containsKey(key)){
			tests.put(key, new ArrayList<Analyzer>());
		}
		tests.get(key).addAll(Arrays.asList(executer));
	}

	public static Map<String, List<Analyzer>> getTests() {
		return tests;
	}

	
	public static void reset(){
		tests = new HashMap<String, List<Analyzer>>();
	}

	public static void removeByKey(String key) {
		tests.remove(key);		
	}
	public static void remove(Analyzer ex){
		for (String key : tests.keySet()) {
			List<Analyzer> list = tests.get(key);
			if (list.contains(ex)) {
				list.remove(ex);
				break;
			}
		}
	}
}
