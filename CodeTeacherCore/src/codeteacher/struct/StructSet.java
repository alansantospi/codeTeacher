package codeteacher.struct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codeteacher.analyzers.Analyzer;

public class StructSet {
	
	private static Map<String, List<Analyzer>> structs = new HashMap<String, List<Analyzer>>();
	
	public static void addStruct(String key, StructAnalyzer... struct){
		if (!structs.containsKey(key)){
			structs.put(key, new ArrayList<Analyzer>());
		}
		structs.get(key).addAll(Arrays.asList(struct));
	}

	public static Map<String, List<Analyzer>> getStructs() {
		return structs;
	}
	
	public static void reset(){
		structs = new HashMap<String, List<Analyzer>>();
	}
	
	public static void removeByKey(String key) {
		structs.remove(key);		
	}
}
