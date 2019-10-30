package codeteacher.behave;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codeteacher.analyzers.Analyzer;

public class BehaviorSet {

	private static Map<String, List<Analyzer>> tests = new HashMap<String, List<Analyzer>>();
	
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
}
