package codeteacher.behave;

public abstract class StepExecutor {

	private Class<?> klazz;
	private Object[] arguments;
	private Object obj;
	private String methodName;
	
	public abstract StepReturn exec();

	public Class<?> getKlazz() {
		return klazz;
	}

	public void setKlazz(Class<?> klazz) {
		this.klazz = klazz;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getMethodName() {
		return methodName;
	}
	
	public void setMethodName(String methodName) {
		this.methodName = methodName;
		
	}
}
