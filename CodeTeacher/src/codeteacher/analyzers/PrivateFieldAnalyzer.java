package codeteacher.analyzers;

import java.lang.reflect.InvocationTargetException;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;
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
	public boolean isError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
//		if (checkParent()) {
			return !ReflectionUtils.isPrivate(klazz, parent.getMemberName(), parent.isDeclared(), parent.isRegex(), parent.isMatchCase());
//		}
//		return false;
		
		
//		boolean isError = parent.isError();
//		if (!isError) {
//			isError = !ReflectionUtils.isPrivate(klazz, getMemberName(), parent.isDeclared(), parent.isRegex(), parent.isMatchCase());
//		}
//		return isError;
	}
	
	@Override
	public Error getError() {
		return new Error(ErrorType.METHOD_NOT_PRIVATE);
	}

	@Override
	public String toString() {
		return "private";
	}
}
