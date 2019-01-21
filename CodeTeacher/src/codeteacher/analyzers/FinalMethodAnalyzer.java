package codeteacher.analyzers;

import java.lang.reflect.Method;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import utils.ReflectionUtils;

public class FinalMethodAnalyzer extends MethodModifierAnalyzer {

	public FinalMethodAnalyzer(String methodName, Method method, int value) {
		super(methodName, method, value);
	}

	@Override
	protected boolean check() {
		return ReflectionUtils.isFinal(getMethod());
	}

	@Override
	public Error getError() {
		return new Error(ErrorType.METHOD_NOT_FINAL);
	}
}