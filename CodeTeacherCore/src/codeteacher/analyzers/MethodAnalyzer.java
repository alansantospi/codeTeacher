package codeteacher.analyzers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;

public class MethodAnalyzer extends MemberAnalyzer {

	private String returnType;
	private String[] parameterTypes;
	private Method method;

	public MethodAnalyzer(ClassAnalyzer parent, boolean declared, String returnType, String methodName, boolean matchCase, boolean regex, int value, String... parameterTypes) {
		this.parent = parent;
		this.declared = declared;
		this.returnType = returnType;
		this.name = methodName;
		this.matchCase = matchCase;
		this.regex = regex;
		this.value = value;
		this.parameterTypes = parameterTypes;
	}

	public MethodAnalyzer() {
		
	}
	
	public boolean isError() {

		return !match();
	}


	public Error getError() {
		ErrorType errorType = ErrorType.METHOD_NOT_FOUND;
		String msg = errorType.getMessage(toString(), parent.getMemberName());
		return new Error(errorType, msg, getValue());
	}
	
	private boolean match() {
		klazz = parent.getKlazz();
		if (klazz == null) {
			System.err.println("MethodAnalyzer.match(): " + "Class not set!");
			return true;
		}
		List<Method> someMethods = new ArrayList<Method>();
		Method[] methods; 
		if (declared) {
			methods = klazz.getDeclaredMethods();
		} else {
			methods = klazz.getMethods();
		}
		
		for(Method aMethod : methods) {
			if (checkName(aMethod.getName()) 
					&& checkType(aMethod.getReturnType())
					&& checkParams(aMethod.getParameterTypes())) {
				someMethods.add(aMethod);
			}
		}
		
		boolean exists = someMethods.size() == 1;
		if (exists) {
			method = someMethods.get(0);
		}
		return exists;
	}
	
	private boolean checkName(String methodName) {
		if (regex) {
			Pattern p = Pattern.compile(name);
			return p.matcher(methodName).matches(); 
		} else if (matchCase) {
			return name.equals(methodName);
		} else {
			return name.equalsIgnoreCase(methodName);
		}
	}
	
	private boolean checkType(Class<?> t1) {
		 
		return t1.getName().equals(returnType);
	}
	
	private boolean checkParams(Class<?>[] classes) {
		if (classes.length != parameterTypes.length) {
			return false;
		}
		
		for (int i = 0; i < parameterTypes.length; i++) {
			Class<?> paramType = classes[i];
			if (!paramType.getName().equals(parameterTypes[i])){
				return false;
			}
			
		}
		return true;
	}
	
	public String getReturnType() {
		return returnType;
	}
	
	public boolean isDeclared() {
		return declared;
	}
	
	public Method getMethod() {
		return method;
	}
	
	public void setParamTypes(String[] paramTypes) {
		this.parameterTypes = paramTypes;
	}
	
	@Override
	public AbstractAnalyzer getParent() {
		return parent;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name).append("(");
		List<String> paramList = Arrays.asList(parameterTypes);
		Iterator<String> iter = paramList.iterator();
		while (iter.hasNext()) {
			builder.append(iter.next());
			if (iter.hasNext()) builder.append(", ");
		}
		builder.append(")");
		return builder.toString();
	}
}
