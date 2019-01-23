package codeteacher.analyzers;

import utils.ReflectionUtils;

public class AbstractMethodAnalyzer extends MethodModifierAnalyzer {

	public AbstractMethodAnalyzer(MethodAnalyzer parent, int value) {
		super(parent, value);
	}

	@Override
	protected boolean check() {
		return ReflectionUtils.isAbstract(getMethod());
	}
}