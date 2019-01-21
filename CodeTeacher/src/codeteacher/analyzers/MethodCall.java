package codeteacher.analyzers;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import codeteacher.behave.ConstructorCall;
import codeteacher.behave.Hit;
import codeteacher.behave.Result;
import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import utils.ReflectionUtils;

public class MethodCall extends SimpleAnalyzer {
	
	private Object[] arguments;
	private Object object;
	private String methodName;
	private Object output;
	private Object methodReturn;
	private ConstructorCall constructorCall;
	private int value;
	
	public MethodCall(Object obj, String methodName, Object output, Object... arguments) {
		this.object = obj;
		this.arguments = arguments;
		this.methodName = methodName;
		this.output = output;
	}
	
	public MethodCall(ConstructorCall constructorCall, String methodName, Object output, Object... arguments) {
		this.constructorCall = constructorCall;
		this.arguments = arguments;
		this.methodName = methodName;
		this.output = output;
	}

	public void setObject(Object obj){
		this.object = obj;
	}
	
	public Object getObject(){
		return object;
	}
	
	public Object[] getArguments(){
		return arguments;
		
	}
	
	public String getMemberName() {
		return methodName;
	}

	public Object getOutput() {
		return output;
	}

	public Object getMethodReturn() {
		return methodReturn;
	}

	public ConstructorCall getConstructorCall() {
		return constructorCall;
	}

	@Override
	public int getValue() {
		return value;
	}
	
	public boolean isError() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		if (!constructorCall.exec()){
		
			methodReturn = ReflectionUtils.invokeMethod(constructorCall.getObj(), constructorCall.getKlazz(), methodName, getArguments());
	
			if (methodReturn == null && output != null){
				return true;
			}
			if (methodReturn != null &&  !methodReturn.equals(output)){
				return true;
			}
		}
		return false;
	}
	
	public Result exec() {
		
		Result result = new Hit();
		
		
		if (!constructorCall.exec()){
		
			try {
				methodReturn = ReflectionUtils.invokeMethod(constructorCall.getObj(), constructorCall.getKlazz(), methodName, getArguments());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			if (methodReturn == null && output != null){
				return result;
			}
			if (methodReturn != null &&  !methodReturn.equals(output)){
				return result;
			}
		}
		return result;
	}
	
	public String getMessage(){
		StringBuilder params = new StringBuilder();
		List<Object> asList = Arrays.asList(arguments);
		
		Iterator<Object> iterator = asList.iterator();
		while (iterator.hasNext() ){
			Object next = iterator.next();
			params.append(next);
			if (iterator.hasNext()){
				params.append(", ");
			}
		}
		return "O método " + methodName + "(" + params + ") deveria retornar '" + output + "' mas retornou '" + methodReturn + "'";
	}
	
	public String getHitMessage(){
		StringBuilder params = new StringBuilder();
		List<Object> asList = Arrays.asList(arguments);
		
		Iterator<Object> iterator = asList.iterator();
		while (iterator.hasNext() ){
			Object next = iterator.next();
			params.append(next);
			if (iterator.hasNext()){
				params.append(", ");
			}
		}
		return "O método " + methodName + "(" + params + ") deveria retornar '" + output + "' e retornou '" + methodReturn + "'";
	}

	public Error getError() {
		return new Error(ErrorType.METHOD_NOT_RETURN, getMessage());
	}
	
	public String getParamsFormatted(){
		StringBuilder params = new StringBuilder();
		List<Object> asList = Arrays.asList(arguments);
		params.append("(");
		Iterator<Object> iterator = asList.iterator();
		while (iterator.hasNext() ){
			Object next = iterator.next();
			params.append(next);
			if (iterator.hasNext()){
				params.append(", ");
			}
		}
		params.append(")");
		return params.toString();
	}
	
	public String getMethodNameWithParams(){
		
		return getMemberName() + getParamsFormatted();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
//		Iterator<String> iterator = modifiers.iterator();
//		while (iterator.hasNext() ){
//			Object next = iterator.next();
//			if (next != null && !((String)next).isEmpty()){
//				sb.append(next).append(" ");
//			}
//		}
		sb.append(getMethodNameWithParams());
		sb.append(": returns: " + output);
		return sb.toString();
	}
}
