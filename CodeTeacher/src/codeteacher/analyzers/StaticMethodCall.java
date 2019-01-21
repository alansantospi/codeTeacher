package codeteacher.analyzers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import utils.ReflectionUtils;
import codeteacher.err.Error;
import codeteacher.err.ErrorType;

public class StaticMethodCall {
	
	private Object[] arguments;
	private String methodName;
	private Object output;
	private Object methodReturn;
	private Class<?> klazz;
	boolean regex;
	
	public StaticMethodCall(Class<?> klazz, String methodName, boolean regex, Object output, Object... arguments) {
		this.klazz = klazz;
		this.methodName = methodName;
		this.regex = regex;
		this.output = output;
		this.arguments = arguments;
	}
	
	public Object[] getArguments(){
		return arguments;
	}
	
	public String getMethodName() {
		return methodName;
	}

	public Object getOutput() {
		return output;
	}

	public Object getMethodReturn() {
		return methodReturn;
	}

	public boolean exec() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		List<Method> someMethods = new ArrayList<Method>();
		String name = methodName;
		if (regex) {
			Pattern p = Pattern.compile(methodName);
			for(Method aMethod : klazz.getMethods()) {
			  if(p.matcher(aMethod.getName()).matches()) {
				  someMethods.add(aMethod);
				  
				  name = aMethod.getName();
			  }
			}
		} 
		
		methodReturn = ReflectionUtils.invokeMethod(klazz, name, getArguments());

		if (methodReturn == null && output != null){
			return true;
		}
		if (methodReturn != null && !methodReturn.equals(output)){
			return true;
		}
		return false;
	}
	
	public String getMessage(){
		return "O método " + methodName + " deveria retornar '" + output + "' mas retornou '" + methodReturn + "'";
	}

	public Error getError() {
		return new Error(ErrorType.METHOD_NOT_RETURN, getMessage());
	}
}

