package codeteacher.analyzers;

import utils.ReflectionUtils;

public class ConstructorAnalyzer {

	private String[] parameterTypes;
	
	public boolean eval(Class<?> clazz, String name, Class<?>... parameterTypes) {
		try {
			ReflectionUtils.getConstructor(clazz, parameterTypes);
		} catch (NoSuchMethodException e) {
			return true;
		}
		return false;
	}
}
