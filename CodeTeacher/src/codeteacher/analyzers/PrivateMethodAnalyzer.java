package codeteacher.analyzers;

import codeteacher.err.ErrorType;
import utils.ReflectionUtils;

public class PrivateMethodAnalyzer extends MethodModifierAnalyzer {
	
	public PrivateMethodAnalyzer(MethodAnalyzer parent, int value) {
		super(parent, value);
	}

	@Override
	protected boolean check() {
		return ReflectionUtils.isPrivate(getMethod());
	}
	
}
