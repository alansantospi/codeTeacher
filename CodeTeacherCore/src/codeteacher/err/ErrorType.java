package codeteacher.err;

import java.text.MessageFormat;

import codeteacher.GuiMsg;
import codeteacher.I18N;

public enum ErrorType implements IErrorType, GuiMsg {

	IO("IO"),
	
	CLASS_NOT_FOUND("Classe não encontrada"),

	PARAM_CLASS_NOT_FOUND("Parãmetro não encontrado"),

	SUPERCLASS_NOT_FOUND("Superclasse não encontrada"),

	METHOD_NOT_FOUND("Método não encontrado"),

	FIELD_NOT_FOUND("Atributo não encontrado"),

	CONSTRUCTOR_NOT_FOUND("Construtor não encontrado"),

	INTERFACE_NOT_IMPLEMENTED("Interface não implementada"),

	PROJECT_NOT_FOUND("Projeto não encontrado") {
		@Override
		public boolean isFatal() {
			return true;
		}
	},

	PROJECT_UNDEFINED("Projeto indefinido") {
		@Override
		public boolean isFatal() {
			return true;
		}
	},

	JAR_NOT_FOUND("Arquivo Jar não encontrado") {
		@Override
		public boolean isFatal() {
			return true;
		}
	},

	FILE_NOT_FOUND("Arquivo não encontrado") {
		@Override
		public boolean isFatal() {
			return true;
		}
	},

	BIN_NOT_FOUND("Pasta bin não encontrada") {
		@Override
		public boolean isFatal() {
			return true;
		}
	},

	FOLDER_NOT_FOUND("Pasta não encontrada") {
		@Override
		public boolean isFatal() {
			return true;
		}
	},

	SRC_FOLDER_NOT_FOUND("Pasta src não encontrada") {
		@Override
		public boolean isFatal() {
			return true;
		}
	},

	METHOD_NOT_ABSTRACT("Método não abstrato"), METHOD_NOT_FINAL("Método não final"),
	METHOD_NOT_PRIVATE("Método não privado"), METHOD_NOT_PROTECTED("Método não protegido"),
	METHOD_NOT_PUBLIC("Método não público"), METHOD_NOT_STATIC("Método não estático"),
	METHOD_MODIFIER_MISMATCH("Método com modificador incorreto"),
	METHOD_NOT_RETURN("Método incorreto"),

	COMPILATION_PROBLEM("Erro de compilação") {
		@Override
		public boolean isFatal() {
			return true;
		}
	},
	
	CLASS_UNDEFINED("Classe indefinida") {
		@Override
		public boolean isFatal() {
			return true;
		}
	},
	
	CLASS_MODIFIER_MISMATCH("Classe com modificador incorreto"),
	
	FIELD_MODIFIER_MISMATCH("Atributo com modificador incorreto"),
	
	NOT_AN_INTERFACE("Not an interface"),

	INCORRECT_OUTPRINT("Incorrect outprint"),

	CODE_STYLE_ERROR("CODE STYLE ERROR"),

	EXCEPTION_NOT_THROWN("EXCEPTION NOT THROWN"),

	ILLEGAL_ACCESS("ILLEGAL ACCESS"), 
	
	ILLEGAL_ARGUMENT("ILLEGAL ARGUMENT"),
	
	INPUT_ERROR("INPUT ERROR"),
	
	/*
	 * When a method, that has been invoked with reflection API, throws an exception (runtime exception for example), 
	 * the reflection API will wrap the exception into an InvocationTargetException.
	 */
	INVOCATION_TARGET("INVOCATION TARGET"),
	
	/*
	 * Thrown when an application tries to create an instance of a class using the newInstance method in class Class, but the specified class object cannot be instantiated. The instantiation can fail for a variety of reasons including but not limited to:
	* the class object represents an abstract class, an interface, an array class, a primitive type, or void
	* the class has no nullary constructor
	*/
	INSTANTIATION("INSTANTIATION")
	;

	private String description;
	private int value;
	private String message;

	private ErrorType(String descricao) {
		this.description = descricao;
	}

	public String getDescription() {
		return description;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int valor) {
		this.value = valor;
	}

	public String getMessage() {
		if (message == null) {
			return I18N.getVal(this);
		}
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isFatal() {
		return false;
	}

	public String toString() {

		return this.getDescription() + " | " + this.getValue();
	}

	@Override
	public boolean eval(Class<?> clazz, String name, Class<?>... parameterTypes) {
		return false;
	}

	public String getMessage(String... args) {
		String msg = MessageFormat.format(getMessage(), args);
		return msg;
	}

	@Override
	public String prefix() {
		return "ErrorType";
	}

}
