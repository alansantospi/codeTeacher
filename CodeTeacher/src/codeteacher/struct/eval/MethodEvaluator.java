package codeteacher.struct.eval;

import java.util.Arrays;
import java.util.Iterator;

import utils.ReflectionUtils;

public class MethodEvaluator implements StructuralEvaluator {
	
	private Class<?> klazz;
	private String name;
	private Class<?>[] parameterTypes;

	public MethodEvaluator(Class<?> clazz, String name, Class<?>... parameterTypes) {
		this.klazz = clazz;
		this.name = name;
		this.parameterTypes = parameterTypes;
	}
	
	public boolean eval() {
		try {
			ReflectionUtils.getDeclaredMethod(klazz, name, parameterTypes);
		} catch (NoSuchMethodException e) {
			return true;
		}
		return false;
	}
	
	public Class<?> getKlazz() {
		return klazz;
	}


	public String getName() {
		return name;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public String getKlazzName(){
		return klazz.getName();
	}
	
	public String getMethodName(){
		StringBuffer params = new StringBuffer();
		params.append("(");
		
		Iterator<Class<?>> iterator = Arrays.asList(parameterTypes).iterator();
		
		while (iterator.hasNext()){
			Class<?> next = iterator.next();
			params.append(next.getSimpleName());
			if (iterator.hasNext()){
				params.append(", ");
			}
		}
		params.append(")");
		return name + params.toString();
	}
	
	public String getMessage(){
		return "Método " + getMethodName() + " não encontrado na classe " + getKlazzName();
	}
}
