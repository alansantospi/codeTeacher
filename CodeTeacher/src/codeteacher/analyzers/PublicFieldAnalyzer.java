package codeteacher.analyzers;

import java.lang.reflect.InvocationTargetException;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;
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
	public boolean isError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

		return !ReflectionUtils.isPublic(getKlazz(), parent.getMemberName(), parent.isDeclared(), parent.isRegex(), parent.isMatchCase());
	}

	@Override
	public Error getError() {
		return new Error(ErrorType.METHOD_NOT_PUBLIC);
	}

	@Override
	public String toString() {
		return "public";
	}
}
