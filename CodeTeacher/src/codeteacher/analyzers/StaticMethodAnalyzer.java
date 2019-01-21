package codeteacher.analyzers;

import java.lang.reflect.Method;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import utils.ReflectionUtils;

public class StaticMethodAnalyzer extends MethodModifierAnalyzer {

	public StaticMethodAnalyzer(String methodName, Method method, int value) {
		super(methodName, method, value);
	}

	@Override
	protected boolean check() {
		return ReflectionUtils.isStatic(getMethod());
	}

	@Override
	public Error getError() {
		ErrorType type = ErrorType.METHOD_NOT_STATIC;
		return new Error(type);
	}
}