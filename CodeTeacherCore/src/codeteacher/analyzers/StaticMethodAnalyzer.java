package codeteacher.analyzers;

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
	public String getModifier() {
		return "static";
	}

}