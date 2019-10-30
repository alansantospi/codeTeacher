package codeteacher.struct.eval;

import java.lang.reflect.Method;

import utils.ReflectionUtils;

public class FinalMethodEvaluator extends MethodEvaluator {
	
	public FinalMethodEvaluator(Class<?> clazz, String name, Class<?>... parameterTypes) {
		super(clazz, name, parameterTypes);
	}
	
	public boolean eval(){
		boolean validate;
		try {
			Method method = ReflectionUtils.getDeclaredMethod(getClass(), getName(), getParameterTypes());
			validate = ReflectionUtils.isFinal(method);
		} catch (NoSuchMethodException e) {
			validate = true;
		}
		return validate;
	}
	
	public String getMessage(){
		
		return "Método " + getMethodName() + " da classe " + getKlazzName() + " deveria ser final";
	}
}
