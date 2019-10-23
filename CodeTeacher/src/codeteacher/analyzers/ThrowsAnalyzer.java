package codeteacher.analyzers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;

public class ThrowsAnalyzer extends ModifierAnalyzer {
	
	private MethodAnalyzer parent;
	
	public ThrowsAnalyzer(MethodAnalyzer parent, String name, boolean matchCase, boolean regex, int value) {
		this.parent = parent;
		this.name = name;
		this.matchCase = matchCase;
		this.regex = regex;
		this.value = value;
	}

	@Override
	public boolean isError() {
		
		Method method = parent.getMethod();
		Class<?>[] exceptionTypes = method.getExceptionTypes();
		List<String> exceptionList = new ArrayList<>();
		for (Class<?> ex : exceptionTypes) {
			exceptionList.add(ex.getSimpleName());
		}
		return !exceptionList.contains(name);
	}

	@Override
	public Error getError() {
		ErrorType errorType = ErrorType.EXCEPTION_NOT_THROWN;
		Error error = new Error(errorType, errorType.getMessage(name), getValue());
		return error;
	}
	
	@Override
	public String toString() {
		return "throws " + name; 
	}

	@Override
	public String getModifier() {
		return "throws";
	}

}
