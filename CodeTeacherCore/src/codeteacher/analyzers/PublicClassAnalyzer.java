package codeteacher.analyzers;

import java.lang.reflect.Modifier;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;

public class PublicClassAnalyzer extends ModifierAnalyzer {

	public PublicClassAnalyzer(ClassAnalyzer parent, int value) {
		this.parent = parent;
		this.value = value;
	}
	
	@Override
	public boolean isError() {
		
		Class<?> parentClass = getParent().getKlazz();
		
		return !Modifier.isPublic(parentClass.getModifiers());
	}

	@Override
	public Error getError() {
		ErrorType errorType = ErrorType.CLASS_MODIFIER_MISMATCH;
		String message = errorType.getMessage(parent.getMemberName(), getModifier());
		Error error = new Error(errorType, message, getValue());
		return error;
	}

	@Override
	public String getModifier() {
		return "public";
	}

}
