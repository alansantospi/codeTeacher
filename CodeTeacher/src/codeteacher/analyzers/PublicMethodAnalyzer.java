package codeteacher.analyzers;

import java.lang.reflect.Method;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import utils.ReflectionUtils;

public class PublicMethodAnalyzer extends MethodModifierAnalyzer {
	
	public PublicMethodAnalyzer(String methodName, Method method, int value) {
		super(methodName, method, value);
	}

	@Override
	protected boolean check() {
		return ReflectionUtils.isPublic(getMethod());
	}
	
	@Override
	public Error getError() {
		ErrorType type = ErrorType.METHOD_NOT_PUBLIC;
		return new Error(type);
	}
}