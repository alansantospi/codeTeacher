package codeteacher.analyzers;

import utils.ReflectionUtils;

public class PrivateFieldAnalyzer extends FieldModifierAnalyzer {

	public PrivateFieldAnalyzer(FieldAnalyzer parent) {
		super.parent = parent;
		this.parent = parent;
	}
	
	public PrivateFieldAnalyzer(FieldAnalyzer fieldAnalyzer, int value) {
		this(fieldAnalyzer);
		this.value = value;
	}

	@Override
	public boolean isError() {
		
//		if (checkParent()) {
			return !ReflectionUtils.isPrivate(parent.getKlazz(), parent.getMemberName(), parent.isDeclared(), parent.isRegex(), parent.isMatchCase());
//		}
//		return false;
		
		
//		boolean isError = parent.isError();
//		if (!isError) {
//			isError = !ReflectionUtils.isPrivate(klazz, getMemberName(), parent.isDeclared(), parent.isRegex(), parent.isMatchCase());
//		}
//		return isError;
	}
	
	@Override
	public String getModifier() {
		return "private";
	}
}
