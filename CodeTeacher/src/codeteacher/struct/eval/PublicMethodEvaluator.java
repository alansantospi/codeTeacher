package codeteacher.struct.eval;

import java.lang.reflect.Method;

import utils.ReflectionUtils;

public class PublicMethodEvaluator extends MethodEvaluator {

	public PublicMethodEvaluator(Class<?> clazz, String name, Class<?>... parameterTypes) {
		super(clazz, name, parameterTypes);
	}
	
	public boolean eval(){
		boolean validate;
		try {
			Method method = ReflectionUtils.getDeclaredMethod(getClass(), getName(), getParameterTypes());
			validate = ReflectionUtils.isPublic(method);
		} catch (NoSuchMethodException e) {
			validate = true;
		}
		return validate;
	}
	
	public String getMessage(){
		
		return "M�todo " + getMethodName() + " da classe " + getKlazzName() + " deveria ser publico";
	}
}
