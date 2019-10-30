package codeteacher.analyzers;

import utils.ReflectionUtils;

public class PublicMethodAnalyzer extends MethodModifierAnalyzer {
	
	public PublicMethodAnalyzer(MethodAnalyzer parent, int value) {
		super(parent, value);
	}

	@Override
	protected boolean check() {
		return ReflectionUtils.isPublic(getMethod());
	}
	
	@Override
	public String getModifier() {
		return "public";
	}
	
}