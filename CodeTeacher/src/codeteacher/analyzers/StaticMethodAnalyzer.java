package codeteacher.analyzers;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import utils.ReflectionUtils;

public class StaticMethodAnalyzer extends MethodModifierAnalyzer {

	public StaticMethodAnalyzer(MethodAnalyzer parent, int value) {
		super(parent, value);
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