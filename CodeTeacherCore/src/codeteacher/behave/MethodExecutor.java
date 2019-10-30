package codeteacher.behave;

import java.lang.reflect.InvocationTargetException;

import utils.ReflectionUtils;

public class MethodExecutor extends StepExecutor {

	
	@Override
	public StepReturn exec() {
		StepReturn stepReturn = new StepReturn();
			
		try {
			ReflectionUtils.invokeMethod(getObj(), getKlazz(), getMethodName(), getArguments());
		} catch (IllegalAccessException e) {
			stepReturn.setMessage(e.getMessage());
			
		} catch (IllegalArgumentException e) {
			stepReturn.setMessage(e.getMessage());	
			
		} catch (InvocationTargetException e) {
			stepReturn.setMessage(e.getMessage());
			
		} catch (NoSuchMethodException e) {
			stepReturn.setMessage(e.getMessage());
		}
		
		return stepReturn;
	}
}
