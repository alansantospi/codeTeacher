package codeteacher.analyzers;

import utils.ReflectionUtils;

public class FinalFieldAnalyzer extends FieldModifierAnalyzer {

	public FinalFieldAnalyzer(FieldAnalyzer parent) {
		this.parent = parent;
	}
	
	public FinalFieldAnalyzer(FieldAnalyzer fieldAnalyzer, int value) {
		this(fieldAnalyzer);
		this.value = value;
	}
	
	@Override
	public boolean isError() {
		
		return !ReflectionUtils.isFinal(parent.getKlazz(), parent.getMemberName(), parent.isDeclared(), parent.isRegex(), parent.isMatchCase());
	}
	
	@Override
	public String getModifier() {
		return "final";
	}
}
