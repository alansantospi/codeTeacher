package codeteacher.analyzers;

import java.lang.reflect.Method;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import utils.ReflectionUtils;

public class PrivateMethodAnalyzer extends MethodModifierAnalyzer {
	
	public PrivateMethodAnalyzer(String methodName, Method method, int value) {
		super(methodName, method, value);
	}

	@Override
	protected boolean check() {
		return ReflectionUtils.isPrivate(getMethod());
	}
	
	@Override
	public Error getError() {
		ErrorType type = ErrorType.METHOD_NOT_PRIVATE;
		return new Error(type);
	}
}
