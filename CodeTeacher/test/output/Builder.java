package output;

import java.lang.reflect.InvocationTargetException;

import codeteacher.analyzers.FieldAnalyzer;
import utils.ReflectionUtils;

public class Builder {
	
	private static Class<?> klazz;
	private static Object obj;
	private static Builder builder;
	
	private Builder(Object object) {
		obj = object;
	}

	public static Builder build(Class<?> type) {
	
		try {
			klazz = type;
			obj = ReflectionUtils.instantiateWithoutConstructor(klazz);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
		builder = new Builder(obj);
		return builder;
	}
	
	public static Builder with(String fieldName, Object value) {
		
//		try {
//			ReflectionUtils.getField(klazz, false, fieldName);
//		} catch (NoSuchFieldException | SecurityException e) {
//			e.printStackTrace();
//		}
		
		try {
			ReflectionUtils.setValue(obj, false, fieldName, value);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder;
	}

	private Class<?> getKlazz() {
		return klazz;
	}

	public static void main(String[] args) {
		Builder.build(FieldAnalyzer.class).with("parent", null);
	}
}
