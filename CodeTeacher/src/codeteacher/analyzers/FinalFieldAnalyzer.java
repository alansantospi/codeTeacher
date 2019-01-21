package codeteacher.analyzers;

import java.lang.reflect.InvocationTargetException;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import utils.ReflectionUtils;

public class FinalFieldAnalyzer extends FieldModifierAnalyzer {

	public FinalFieldAnalyzer(FieldAnalyzer parent) {
//		super(clazz, declared, name, regex, matchCase);
		this.parent = parent;
	}
	
	public FinalFieldAnalyzer(FieldAnalyzer fieldAnalyzer, int value) {
		this(fieldAnalyzer);
		this.value = value;
	}
	
	@Override
	public boolean isError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		return !ReflectionUtils.isFinal(parent.getKlazz(), parent.getMemberName(), parent.isDeclared(), parent.isRegex(), parent.isMatchCase());
	}
	
	@Override
	public Error getError() {
		return new Error(ErrorType.FIELD_NOT_FINAL);
	}

	@Override
	public String toString() {
		return "final";
	}
}
