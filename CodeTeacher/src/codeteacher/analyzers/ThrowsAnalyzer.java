package codeteacher.analyzers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import codeteacher.err.Error;

public class ThrowsAnalyzer extends SimpleAnalyzer {
	
	private MethodAnalyzer parent;
	// Exceptions expected to be thrown
	private String[] exceptions;
	
	public ThrowsAnalyzer(MethodAnalyzer parent, boolean matchCase, boolean regex, int value, String... exceptions) {
		this.parent = parent;
		this.matchCase = matchCase;
		this.regex = regex;
		this.value = value;
		this.exceptions = exceptions;
	}

	@Override
	public boolean isError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		Method method = parent.getMethod();
		Class<?>[] exceptionTypes = method.getExceptionTypes();
		List<String> exceptionList = new ArrayList<>();
		for (Class<?> ex : exceptionTypes) {
			exceptionList.add(ex.getSimpleName());
		}
		List<String> expectedExceptions = Arrays.asList(exceptions);
		if (expectedExceptions.size() == 0) {
			return false;
		}
		return !exceptionList.containsAll(expectedExceptions);
	}

	@Override
	public Error getError() {
		// TODO Auto-generated method stub
		return null;
	}

}
