package codeteacher.analyzers;

import utils.ReflectionUtils;

public class ProtectedMethodAnalyzer extends MethodModifierAnalyzer {

	public ProtectedMethodAnalyzer(MethodAnalyzer parent, int value) {
		super(parent, value);
	}

	@Override
	protected boolean check() {
		return ReflectionUtils.isProtected(getMethod());
	}

}