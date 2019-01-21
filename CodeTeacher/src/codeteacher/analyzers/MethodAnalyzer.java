package codeteacher.analyzers;

import java.lang.reflect.InvocationTargetException;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import utils.ReflectionUtils;

public class MethodAnalyzer extends CompositeAnalyzer {

	private Class<?> klazz;
	private String methodName;
	private Class<?>[] parameterTypes;
	private boolean methodRegex;
	private boolean matchCase;
	private int value;

	public MethodAnalyzer(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
		this.klazz = clazz;
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
	}

	public void setKlazz(Class<?> klazz) {
		this.klazz = klazz;
	}

	public boolean isError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

		try {
			ReflectionUtils.getDeclaredMethod(klazz, methodName, parameterTypes);
		} catch (NoSuchMethodException e) {
			return true;
		}
		return false;
	}

	public String getMemberName() {
		return methodName;
	}

	public boolean isRegex() {
		return methodRegex;
	}

	public Error getError() {
		return new Error(ErrorType.METHOD_NOT_FOUND);
	}

	public Class<?> getKlazz() {
		return klazz;
	}

	@Override
	public int getValue() {
		return value;
	}

}
