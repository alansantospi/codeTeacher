package codeteacher.analyzers;

import java.lang.reflect.InvocationTargetException;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import utils.ReflectionUtils;

public class ImplementsAnalyzer extends SimpleAnalyzer{
	
	private ClassAnalyzer parent;
	private String interFace;
	
	public ImplementsAnalyzer(ClassAnalyzer parent, String interFace, int value) {
		this.parent = parent;
		this.interFace = interFace;
		this.value = value;
	}

	@Override
	public boolean isError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		return !match();
	}

	@Override
	public Error getError() {
		ErrorType errorType = ErrorType.INTERFACE_NOT_IMPLEMENTED;
		Error error = new Error(errorType, errorType.getMessage(name, interFace), getValue());
		return error;
	}
	
	
	public boolean match(){
		for (Class<?> anInterface : ReflectionUtils.getInterfaces(parent.getKlazz())) {
			if (anInterface.equals(interFace)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("implements" + " ").append(interFace);
		
		return sb.toString();
	}
}
