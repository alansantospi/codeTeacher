package codeteacher.behave;

import java.util.LinkedList;

public class Step {
	
	private String key;
	private Object object;
	private IStepType type;
	private Class<?> klazz;
	private Object[] arguments;
	private StepReturn stepReturn = new StepReturn();
	private String methodName;
	
	private LinkedList<Step> previousSteps = new LinkedList<Step>();
	
	public Step(String key, Class<?> klazz, IStepType type, String name, Object... arguments) {
		super();
		this.key = key;
		this.klazz = klazz;
		this.methodName = name;
		this.arguments = arguments;
		this.type = type;
	}
	
	public Step addStep(Step step){
		previousSteps.add(step);
		return this;
	}
	
	public Step(String key, Object object, IStepType type) {
		super();
		this.key = key;
		this.object = object;
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public IStepType getType() {
		return type;
	}

	public void exec() {
		StepExecutor executor = type.getExecutor();
		executor.setKlazz(klazz);
		executor.setArguments(arguments);
		executor.setMethodName(methodName);
		stepReturn = executor.exec();
	}
	
	public StepReturn getReturn(){
		return stepReturn;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public LinkedList<Step> getPreviousSteps() {
		return previousSteps;
	}
}
