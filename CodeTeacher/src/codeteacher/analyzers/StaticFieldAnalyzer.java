package codeteacher.analyzers;

import java.lang.reflect.InvocationTargetException;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;
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
	public boolean isError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		return !ReflectionUtils.isStatic(parent.getKlazz(), parent.getMemberName(), parent.isDeclared(), parent.isRegex(), parent.isMatchCase());
	}
	
	@Override
	public Error getError() {
		return new Error(ErrorType.METHOD_NOT_STATIC);
	}
	
	@Override
	public String toString() {
		return "static";
	}

}
