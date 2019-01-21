package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codeteacher.analyzers.AbstractAnalyzer;

public class ExecutionPath {
	

	List<AbstractAnalyzer> path = new ArrayList<>();
	Map<AbstractAnalyzer, List<ExecutionPath>> map = new HashMap<>();
	
	public void put(AbstractAnalyzer a) {
		if (!map.containsKey(a)) {
			map.put(a, new ArrayList<ExecutionPath>());
		}
		List<ExecutionPath> list = map.get(a);
			
	}
	
	public void add(AbstractAnalyzer a) {
		if (a != null && !path.contains(a)) {
			AbstractAnalyzer parent = a.getParent();
			if  (path.contains(parent)) {
			}
		}
	}
}
