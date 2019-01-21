package codeteacher.analyzers;

import java.lang.reflect.Method;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import utils.ReflectionUtils;

public class ProtectedMethodAnalyzer extends MethodModifierAnalyzer {

	public ProtectedMethodAnalyzer(String methodName, Method method, int value) {
		super(methodName, method, value);
	}

	@Override
	protected boolean check() {
		return ReflectionUtils.isProtected(getMethod());
	}

	@Override
	public Error getError() {
		ErrorType type = ErrorType.METHOD_NOT_PROTECTED;
		return new Error(type);
	}
}