package codeteacher.analyzers;

import utils.ReflectionUtils;

public class FinalMethodAnalyzer extends MethodModifierAnalyzer {

	public FinalMethodAnalyzer(MethodAnalyzer parent, int value) {
		super(parent, value);
	}

	@Override
	protected boolean check() {
		return ReflectionUtils.isFinal(getMethod());
	}
	
	@Override
	public String getModifier() {
		return "final";
	}

}