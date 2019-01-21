package codeteacher.behave;

import java.lang.reflect.InvocationTargetException;

import utils.ReflectionUtils;

public class ConstructorCall {

	private Class<?> klazz;
	private String klazzName;
	private Object[] arguments;
	private Object obj;

	public ConstructorCall(Object... arguments) {
		this.arguments = arguments;
	}
	
	public ConstructorCall(Class<?> klazz, Object... arguments) {
		this(arguments);
		this.klazz = klazz;
	}
	
	public ConstructorCall(String className, Object[] arguments, Object obj) {
		super();
		this.klazzName = className;
		this.arguments = arguments;
		this.obj = obj;
	}

	public boolean exec(){

		if (klazz == null){
			try {
				klazz = ReflectionUtils.loadClass(klazzName);
			} catch (ClassNotFoundException e) {
				
				return true;
			}
		}
		
		try {
			obj = ReflectionUtils.instantiateObject(klazz, arguments);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException e) {
			
			return true;
		}
		return false;
	}

	public Object getObj() {
		return obj;
	}

	public void setKlazz(Class<?> klazz) {
		this.klazz = klazz;
	}

	public Class<?> getKlazz() {
		return klazz;
	}

	public String getKlazzName() {
		return klazzName;
	}

	public void setKlazzName(String klazzName) {
		this.klazzName = klazzName;
	}
}
