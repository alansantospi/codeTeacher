package codeteacher.analyzers;

import java.lang.reflect.Modifier;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;

public class AbstractClassAnalyzer extends ModifierAnalyzer {

	public AbstractClassAnalyzer(ClassAnalyzer parent, int value) {
		this.parent = parent;
		this.value = value;
	}
	
	@Override
	public boolean isError() {
		
		Class<?> parentClass = getParent().getKlazz();
		
		return !Modifier.isAbstract( parentClass.getModifiers());
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
		return "abstract";
	}

}
