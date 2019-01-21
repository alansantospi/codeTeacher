package codeteacher.struct.eval;

import java.lang.reflect.Method;

import utils.ReflectionUtils;

public class AbstractMethodEvaluator extends MethodEvaluator {
	
	public AbstractMethodEvaluator(Class<?> clazz, String name, Class<?>... parameterTypes) {
		super(clazz, name, parameterTypes);
	}
	
	public boolean eval(){
		boolean validate;
		try {
			Method method = ReflectionUtils.getDeclaredMethod(getClass(), getName(), getParameterTypes());
			validate = ReflectionUtils.isAbstract(method);
		} catch (NoSuchMethodException e) {
			validate = true;
		}
		return validate;
	}
	
	public String getMessage(){
		
		return "Método " + getMethodName() + " da classe " + getKlazzName() + " deveria ser abstrato";
	}

}
