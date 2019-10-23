package codeteacher.analyzers;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;

public class SuperClassAnalyzer extends SimpleAnalyzer{
	
	private ClassAnalyzer parent;
	private String superClassName;
	
	public SuperClassAnalyzer(ClassAnalyzer parent, String superClassName, int value) {
		this.parent = parent;
		this.name = parent.getMemberName();
		this.superClassName = superClassName;
		this.value = value;
	}

	@Override
	public boolean isError() {
		klazz = parent.getKlazz();
		Class<?> superKlazz = klazz.getSuperclass();
		
		return klazz.getName().equals(superKlazz.getName());
	}

	@Override
	public Error getError() {
		ErrorType errorType = ErrorType.SUPERCLASS_NOT_FOUND;
		Error error = new Error(errorType, errorType.getMessage(name, superClassName), value);
		return error;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("extends" + " ").append(superClassName);
		
		return sb.toString();
	}
}
