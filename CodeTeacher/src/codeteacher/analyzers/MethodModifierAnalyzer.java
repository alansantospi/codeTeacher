package codeteacher.analyzers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public abstract class MethodModifierAnalyzer extends SimpleAnalyzer {

	private int value;
	private String methodName;
	private Method method;

	public MethodModifierAnalyzer(String methodName, Method method, int value) {
			super(); 
			this.methodName = methodName;
			this.method = method;
			this.value = value;
		}

	@Override
	public boolean isError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		return !check();
	}

	protected abstract boolean check();

	@Override
	public String getMemberName() {
		return methodName;
	}

	@Override
	public int getValue() {
		return value;
	}

	public Method getMethod() {
		return method;
	}

}
