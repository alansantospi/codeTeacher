package codeteacher.behave;

import java.util.LinkedList;
import java.util.Map;

public class TestProcedure {
	
	private Map<String, Object> objects;
	private LinkedList<Step> steps;
	private LinkedList<Object> assertions;

	public void execSteps() {
		for (Step step : steps) {
			String key = step.getKey();
			Object object = objects.get(key);
			step.setObject(object);
			step.exec();
		}
	}
	
	public void execAsserts() {
		for (Step step : steps) {
			String key = step.getKey();
			Object object = objects.get(key);
			step.setObject(object);
			step.exec();
		}
	}

	public Map<String, Object> getObjects() {
		return objects;
	}
	
	public void addObject(String key, Object object) {
		objects.put(key, object);		
	}
	
}
