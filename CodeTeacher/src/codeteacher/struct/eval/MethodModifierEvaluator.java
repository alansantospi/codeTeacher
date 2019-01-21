package codeteacher.struct.eval;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import utils.ReflectionUtils;

public class MethodModifierEvaluator extends MethodEvaluator {

	private int targetMod;
	private int actualMod;

	public MethodModifierEvaluator(Class<?> clazz, String name, int mod, Class<?>... parameterTypes) {
		super(clazz, name, parameterTypes);
		this.targetMod = mod;
	}
	
	public boolean eval(){
		boolean validate;
		try {
			Method method = ReflectionUtils.getMethod(getKlazz(), getName(), getParameterTypes());
			method.setAccessible(true);
			actualMod = method.getModifiers();
			
			validate = actualMod != targetMod;
		} catch (NoSuchMethodException e) {
			validate = true;
		}
		return validate;
	}
	
	public String getMessage(){
		return "Método " + getMethodName() + " da classe " + getKlazzName() + " deveria ser " + Modifier.toString(targetMod) + " mas é " + Modifier.toString(actualMod);
	}
}
