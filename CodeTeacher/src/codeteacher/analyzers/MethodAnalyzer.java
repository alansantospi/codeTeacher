package codeteacher.analyzers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;

public class MethodAnalyzer extends CompositeAnalyzer<FieldModifierAnalyzer> {

	private ClassAnalyzer parent;
	private boolean declared;
	private String returnType;
	private String methodName;
	private Class<?>[] parameterTypes;
	private Method method;

	public MethodAnalyzer(ClassAnalyzer parent, boolean declared, String returnType, String methodName, Class<?>... parameterTypes) {
		this.parent = parent;
		this.declared = declared;
		this.returnType = returnType;
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
	}

	public boolean isError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

		return !match();
	}

	public String getMemberName() {
		return methodName;
	}

	public Error getError() {
		ErrorType errorType = ErrorType.METHOD_NOT_FOUND;
		String msg = errorType.getMessage(methodName, parent.getMemberName());
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
			if (checkName(aMethod) && checkType(aMethod.getReturnType())) {
				someMethods.add(aMethod);
			}
		}
		
		boolean exists = someMethods.size() == 1;
		if (exists) {
			method = someMethods.get(0);
		}
		return exists;
	}
	
	private boolean checkName(Method aMethod) {
		if (regex) {
			Pattern p = Pattern.compile(name);
			return p.matcher(aMethod.getName()).matches(); 
		} else if (matchCase) {
			return name.equalsIgnoreCase(aMethod.getName());
		} else {
			return name.equals(aMethod.getName());
		}
	}
	
	private boolean checkType(Class<?> t1) {
		 
		return t1.getName().equals(returnType);
	}
	
	public Method getMethod() {
		return method;
	}
}
