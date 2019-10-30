package codeteacher.result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import codeteacher.err.Error;
import codeteacher.err.ErrorFixer;

public class Evaluation {

	private Map<String, Performance> perforMap = new HashMap<String, Performance>();

	public Map<String, Performance> getPerforMap() {
		return perforMap;
	}
	
	public void addStudent(String student){
		if (!perforMap.containsKey(student)){
			Performance p = new Performance(student);
			perforMap.put(student, p);
		}
	}
	
	public void addError(String student, Error error){
		addStudent(student);
		perforMap.get(student).addError(error);
		
	}
	
	public void addErrors(String student, Collection<Error> errors){
		addStudent(student);
		perforMap.get(student).addErrors(errors);
		
	}
	
	public void mergePerformance(Performance perform){
		String student = perform.getStudent();
		Collection<Error> errors = perform.getErrors();
		addStudent(student);
		Performance mergePerform = perforMap.get(student);
		mergePerform.addErrors(errors);
		Map<Error, ErrorFixer> fixes = perform.getErrorFixers();
		for (Error err : fixes.keySet()) {
			ErrorFixer fix = fixes.get(err);
			if (!fix.isFixed()) {
				mergePerform.addErrorFixer(err, fix);
			}
		}
		perforMap.put(student, mergePerform);
		
	}

	public void reset(){
		perforMap = new HashMap<String, Performance>();
	}
	
	@Override
	public String toString() {
		Set<String> keySet = perforMap.keySet();
		ArrayList<String> arrayList = new ArrayList<String>(keySet);
		java.util.Collections.sort(arrayList);
		StringBuffer sb = new StringBuffer();
		for (String key : arrayList){
			Performance perform = perforMap.get(key);
			sb.append(perform + "\n");
		}
		return sb.toString();
	}
}
