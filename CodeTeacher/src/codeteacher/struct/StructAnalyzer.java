package codeteacher.struct;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import codeteacher.analyzers.Analyzer;
import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import codeteacher.struct.eval.MethodEvaluator;


/**
 * @author Alan Santos
 *
 */
public class StructAnalyzer implements Analyzer {

	private Class<?> klazz;
	private String className;
	private String methodName;
	private String returnType;
	private String[] parameterTypes;
	private Set<String> modifiers;
	private MethodEvaluator evaluator;
	private int value;
	
	public void setEvaluator(MethodEvaluator evaluator) {
		this.evaluator = evaluator;
	}

	public StructAnalyzer(String className, Set<String> modifiers, String methodName,
			String returnType, String... parameterTypes) {
		this.className = className;
		this.modifiers = modifiers;
		this.methodName = methodName;
		this.returnType = returnType;
		this.parameterTypes = parameterTypes;
		//this.evaluator = new MethodEvaluator(klazz, methodName, parameterTypes.toArray(new Class<?>[0]));
	}
	
	public String getParamsFormatted(){
		StringBuilder arguments = new StringBuilder();
		List<String> paramList = Arrays.asList(parameterTypes);
		
		Iterator<String> iterator = paramList.iterator();
		arguments.append("(");
		while (iterator.hasNext() ){
			Object next = iterator.next();
			arguments.append(next);
			if (iterator.hasNext()){
				arguments.append(", ");
			}
		}
		arguments.append(")");
		return arguments.toString();
	}
	
	@Override
	public String getMemberName() {
		return methodName;
	}

	public String getMethodNameWithParams(){
		return getMemberName() + getParamsFormatted();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		Iterator<String> iterator = modifiers.iterator();
		while (iterator.hasNext() ){
			Object next = iterator.next();
			if (next != null && !((String)next).isEmpty()){
				sb.append(next).append(" ");
			}
		}
		sb.append(returnType).append(" ");
		sb.append(getMethodNameWithParams());
		return sb.toString();
	}

	@Override
	public int getValue() {
		return value;
	}
	
	@Override
	public boolean isError() throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException {
		
		return evaluator.eval();
	}

	@Override
	public Error getError() {
		ErrorType errorType = ErrorType.METHOD_NOT_FOUND;
		return new Error(errorType, errorType.getMessage(methodName, className));
	}
	
	public String[] getParameterTypes() {
		return parameterTypes;
	}

	public void setKlazz(Class<?> baseClazz) {
		this.klazz = baseClazz;
	}
	
	@Override
	public int getTotalValue() {		
		return getValue();
	}

	@Override
	public boolean run()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		return isError();
	}
}
