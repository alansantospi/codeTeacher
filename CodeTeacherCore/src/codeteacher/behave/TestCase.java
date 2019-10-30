package codeteacher.behave;

import java.util.LinkedList;
import java.util.Map;


public abstract class TestCase {
	
	private Map<String, LinkedList<TestProcedure>> procedures;

	public TestCase(Map<String, LinkedList<TestProcedure>> procedures) {
		super();
		this.procedures = procedures;
	}

	public Map<String, LinkedList<TestProcedure>> getProcedures() {
		return procedures;
	}

	public void addProcedures(String key, TestProcedure procedure) {
		if (!procedures.containsKey(key)){
			procedures.put(key, new LinkedList<TestProcedure>());
		}
		procedures.get(key).add(procedure);		
	} 
	
	public void exec(){
		
	}
	
	

	
}
