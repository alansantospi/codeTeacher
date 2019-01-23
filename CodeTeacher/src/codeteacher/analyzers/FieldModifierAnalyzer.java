package codeteacher.analyzers;

import java.lang.reflect.InvocationTargetException;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;

public abstract class FieldModifierAnalyzer extends SimpleAnalyzer {
	
	protected FieldAnalyzer parent;
	
	public boolean checkParent()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		if (klazz == null && parent.getKlazz() != null) {
			this.setKlazz(parent.getKlazz());
			return true;
		}
		return false;
	}
	
	@Override
	public String getMemberName() {
		return parent.getMemberName();
	}
	
	@Override
	public Class<?> getKlazz() {
		return parent.getKlazz();
	}	
	
	@Override
	public AbstractAnalyzer getParent() {
		return parent;
	}
	
	@Override
	public Error getError() {
		ErrorType errorType = ErrorType.FIELD_MOFIDIER_MISMATCH;
		String message = errorType.getMessage(name, parent.getMemberName(), getModifier());
		Error error = new Error(errorType, message, getValue());
		return error;
	}

	protected abstract String getModifier();
	
	@Override
	public String toString() {
		return getModifier();
	}
}
