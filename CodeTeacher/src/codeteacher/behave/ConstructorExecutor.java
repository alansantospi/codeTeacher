package codeteacher.behave;

import java.lang.reflect.InvocationTargetException;

import utils.ReflectionUtils;

public class ConstructorExecutor extends StepExecutor {

	@Override
	public StepReturn exec() {
		StepReturn stepReturn = new StepReturn();
		Object obj;
		try {
			obj = ReflectionUtils.instantiateObject(getKlazz(), getArguments());
			setObj(obj);
			stepReturn.setObject(obj);
		} catch (InstantiationException e) {
			stepReturn.setMessage(e.getMessage());
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
