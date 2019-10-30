package codeteacher.analyzers;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import utils.ReflectionUtils;

public class ImplementsAnalyzer extends SimpleAnalyzer {
	
	private ClassAnalyzer parent;
	
	public ImplementsAnalyzer(ClassAnalyzer parent, String name, int value) {
		this.parent = parent;
		this.name = name;
		this.value = value;
	}

	@Override
	public boolean isError() {
		return !match();
	}

	@Override
	public Error getError() {
		ErrorType errorType = ErrorType.INTERFACE_NOT_IMPLEMENTED;
		Error error = new Error(errorType, errorType.getMessage(parent.getMemberName(), name), getValue());
		return error;
	}
	
	
	public boolean match(){
		for (Class<?> anInterface : ReflectionUtils.getInterfaces(parent.getKlazz())) {
			if (anInterface.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("implements" + " ").append(name);
		
		return sb.toString();
	}
	@Override
	public String getMemberName() {
		return name;
	}
}
