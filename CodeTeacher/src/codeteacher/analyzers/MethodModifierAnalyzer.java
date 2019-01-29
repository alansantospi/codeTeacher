package codeteacher.analyzers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;

public abstract class MethodModifierAnalyzer extends SimpleAnalyzer {

	private MethodAnalyzer parent;

	public MethodModifierAnalyzer(MethodAnalyzer parent, int value) {
		this.parent = parent;
		this.value = value;
	}

	@Override
	public boolean isError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		return !check();
	}

	protected abstract boolean check();

	@Override
	public int getValue() {
		return value;
	}

	public boolean isDeclared() {
		return parent.isDeclared();
	}
	
	public Method getMethod() {
		return parent.getMethod();
	}
	
	@Override
	public String getMemberName() {
		return parent.getMemberName();
	}
	
	public String getReturnType() {
		return parent.getReturnType();
	}
	
	public abstract String getModifier();
		
	
	public Error getError() {
		ErrorType errorType = getErrorType();
		String className = parent.getParent().getMemberName();
		String msg = errorType.getMessage(parent.getMemberName(), className);
		return new Error(errorType, msg, getValue());
	}

	protected ErrorType getErrorType() {
		return ErrorType.METHOD_MODIFIER_MISMATCH;
	}

}
