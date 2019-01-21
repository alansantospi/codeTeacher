package codeteacher.analyzers;

import java.lang.reflect.Method;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import utils.ReflectionUtils;

public class AbstractMethodAnalyzer extends MethodModifierAnalyzer {

	public AbstractMethodAnalyzer(String methodName, Method method, int value) {
		super(methodName, method, value);
	}

	@Override
	protected boolean check() {
		return ReflectionUtils.isAbstract(getMethod());
	}

	@Override
	public Error getError() {
		ErrorType type = ErrorType.METHOD_NOT_ABSTRACT;
		return new Error(type);
	}
}