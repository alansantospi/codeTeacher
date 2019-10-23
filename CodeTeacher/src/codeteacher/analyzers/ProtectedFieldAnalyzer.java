package codeteacher.analyzers;

import utils.ReflectionUtils;

public class ProtectedFieldAnalyzer extends FieldModifierAnalyzer{

	public ProtectedFieldAnalyzer(FieldAnalyzer parent) {
		this.parent = parent;
	}
	
	public ProtectedFieldAnalyzer(FieldAnalyzer fieldAnalyzer, int value) {
		this(fieldAnalyzer);
		this.value = value;
	}

	@Override
	public boolean isError() {
		
		return !ReflectionUtils.isProtected(parent.getKlazz(), parent.getMemberName(), parent.isDeclared(), parent.isRegex(), parent.isMatchCase());
	}
	
	@Override
	public String getModifier() {
		return "protected";
	}
	
}
