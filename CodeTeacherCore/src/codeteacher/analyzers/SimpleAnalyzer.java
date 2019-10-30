package codeteacher.analyzers;

import java.lang.reflect.InvocationTargetException;

public abstract class SimpleAnalyzer extends AbstractAnalyzer {
	
	@Override
	public int getTotalValue() {
		return getValue();
	}
	
	@Override
	public boolean run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		return isError();
	}

}
