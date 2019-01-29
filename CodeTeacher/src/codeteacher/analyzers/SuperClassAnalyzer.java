package codeteacher.analyzers;

import java.lang.reflect.InvocationTargetException;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;

public class SuperClassAnalyzer extends SimpleAnalyzer{
	
	private ClassAnalyzer parent;
	private String superClassName;
	private String type;
	
	public SuperClassAnalyzer(ClassAnalyzer parent, String superClassName, int value) {
		this.parent = parent;
		this.superClassName = superClassName;
		this.value = value;
	}

	@Override
	public boolean isError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		klazz = parent.getKlazz();
		Class<?> superKlazz = klazz.getSuperclass();
		
		type = klazz.isInterface() ? "interface" : "classe";
		
		return klazz.getName().equals(superKlazz.getName());
	}

	@Override
	public Error getError() {
		ErrorType errorType = ErrorType.SUPERCLASS_NOT_FOUND;
		Error error = new Error(errorType, errorType.getMessage(type, name, superClassName), getValue());
		return error;
	}

}
