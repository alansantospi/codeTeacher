package codeteacher.analyzers;

import java.lang.reflect.InvocationTargetException;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;

public class InterfaceAnalyzer extends AbstractAnalyzer{
	
	private ClassAnalyzer parent;
	
	public InterfaceAnalyzer(ClassAnalyzer parent, int value) {
		this.parent = parent;
		this.value = value;
	}

	@Override
	public boolean isError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		return parent.getKlazz().isInterface();
	}

	@Override
	public Error getError() {
		ErrorType errorType = ErrorType.NOT_AN_INTERFACE;
		Error error = new Error(errorType, errorType.getMessage(name), getValue());
		return error;
	}

	@Override
	public int getTotalValue() {
		return value;
	}

	@Override
	public boolean run()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		return isError();
	}

}
