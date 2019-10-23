package codeteacher.analyzers;

import utils.ReflectionUtils;

public class StaticFieldAnalyzer extends FieldModifierAnalyzer{

	private static final long serialVersionUID = 1L;

	public StaticFieldAnalyzer(FieldAnalyzer parent) {
//		super(clazz, declared, name, regex, matchCase);
		this.parent = parent;
	}
	
	public StaticFieldAnalyzer(FieldAnalyzer fieldAnalyzer, int value) {
		this(fieldAnalyzer);
		this.value = value;
	}

	@Override
	public boolean isError() {
		
		return !ReflectionUtils.isStatic(parent.getKlazz(), parent.getMemberName(), parent.isDeclared(), parent.isRegex(), parent.isMatchCase());
	}
	
	@Override
	public String getModifier() {
		return "static";
	}

}
