package codeteacher.analyzers;

import utils.ReflectionUtils;

public class PublicFieldAnalyzer extends FieldModifierAnalyzer {

	public PublicFieldAnalyzer(FieldAnalyzer parent) {
		this.parent = parent;
	}

	public PublicFieldAnalyzer(FieldAnalyzer fieldAnalyzer, int value) {
		this(fieldAnalyzer);
		this.value = value;
	}

	@Override
	public boolean isError() {

		return !ReflectionUtils.isPublic(getKlazz(), parent.getMemberName(), parent.isDeclared(), parent.isRegex(), parent.isMatchCase());
	}

	@Override
	public String getModifier() {
		return "public";
	}
}
