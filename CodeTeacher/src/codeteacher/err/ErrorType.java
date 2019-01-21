package codeteacher.err;

import java.text.MessageFormat;

import gui.msg.GuiMsg;
import utils.ReflectionUtils;

public enum ErrorType implements IErrorType, GuiMsg {
	
	CLASS_NOT_FOUND("Classe n�o encontrada"){
		public String getMessage(){
			return "Classe {0} n�o encontrada!";
		}
	},
	
	PARAM_CLASS_NOT_FOUND("Par�metro n�o encontrado"){
		public String getMessage(){
			return "N�o foi poss�vel verificar o m�todo {0} da classe {1} por que o par�metro do tipo {2} n�o foi encontrado";
		}
	},
	
	SUPERCLASS_NOT_FOUND("Superclasse n�o encontrada"){
		@Override
		public boolean eval(Class<?> clazz, String name, Class<?>... parameterTypes) {
			Class<?> superClazz = ReflectionUtils.getSuperClass(clazz);
			
			if (parameterTypes.length > 0){
				
				Class<?> otherClazz = parameterTypes[0];
				Class<?> otherSuperClazz = ReflectionUtils.getSuperClass(otherClazz);
				String otherSuperClazzName = otherSuperClazz.getName();
				String superClazzName = superClazz.getName();
				
				String type = otherClazz.isInterface() ? "interface" : "classe";
				String superType = superClazz.isInterface() ? "interface" : "superclasse";
				
				if (!superClazzName.equals(otherSuperClazzName)){
					setMessage("A " + type + " " + otherClazz.getName() + " deve extender a " + superType + " " + superClazz.getName());
					return true;
				}
			}
			return false;
		}
	},
	
	METHOD_NOT_FOUND("M�todo n�o encontrado"){
		public String getMessage(){
			return "M�todo {0} n�o encontrado na classe {1}";
		}
	},
	
	FIELD_NOT_FOUND("Atributo n�o encontrado"){
		@Override
		public boolean eval(Class<?> clazz, String name, Class<?>... parameterTypes) {
			try {
				ReflectionUtils.getField(clazz, true, name);
			} catch (NoSuchFieldException e) {
				return true;
			}
			return false;
		}
		public String getMessage(){
			return "Atributo {0} n�o encontrado na classe {1}";
		}
	},
	
	CONSTRUCTOR_NOT_FOUND("Construtor n�o encontrado"){
		@Override
		public boolean eval(Class<?> clazz, String name, Class<?>... parameterTypes) {
			try {
				ReflectionUtils.getConstructor(clazz, parameterTypes);
			} catch (NoSuchMethodException e) {
				return true;
			}
			return false;
		}
		public String getMessage(){
			return "Construtor {0} n�o encontrado na classe {1}";
		}
	},
	
	INTERFACE_NOT_IMPLEMENTED("Interface n�o implementada"){
		@Override
		public boolean eval(Class<?> clazz, String name, Class<?>... parameterTypes) {
			for (Class<?> anInterface : parameterTypes) {
				if (ReflectionUtils.isImplemented(clazz, anInterface)){
					return true;
				}
			}
			return false;
		}
	}, 
	
	PROJECT_NOT_FOUND("Projeto n�o encontrado"){
		@Override
		public boolean isFatal(){
			return true;
		}
		public String getMessage(){
			return "O projeto {0} n�o foi encontrado!";
		}
	},
	
	PROJECT_UNDEFINED("Projeto indefinido"){
		@Override
		public boolean isFatal(){
			return true;
		}
		public String getMessage(){
			return "N�o foi poss�vel definir o projeto {0}";
		}
	},
	
	JAR_NOT_FOUND("Arquivo Jar n�o encontrado"){
		@Override
		public boolean isFatal(){
			return true;
		}
		public String getMessage(){
			return "O arquivo {0} n�o foi encontrado!";
		}
	},
	
	FILE_NOT_FOUND("Arquivo n�o encontrado"){
		@Override
		public boolean isFatal(){
			return true;
		}
		public String getMessage(){
			return "O arquivo {0} n�o foi encontrado!";
		}
	},
	
	BIN_NOT_FOUND("Pasta bin n�o encontrada"){
		@Override
		public boolean isFatal(){
			return true;
		}
		public String getMessage(){
			return "A pasta {0} n�o foi encontrada!";
		}
	},
	
	FOLDER_NOT_FOUND("Pasta n�o encontrada"){
		@Override
		public boolean isFatal(){
			return true;
		}
		public String getMessage(){
			return "A pasta {0}\\{1} n�o foi encontrada!";
		}
	},
	
	SRC_FOLDER_NOT_FOUND("Pasta src n�o encontrada"){
		@Override
		public boolean isFatal(){
			return true;
		}
		public String getMessage(){
			return "A pasta {0} n�o foi encontrada!";
		}
	},
	
	METHOD_NOT_ABSTRACT("M�todo n�o abstrato"), 
	METHOD_NOT_FINAL("M�todo n�o final"), 
	METHOD_NOT_PRIVATE("M�todo n�o privado"), 
	METHOD_NOT_PROTECTED("M�todo n�o protegido"),
	METHOD_NOT_PUBLIC("M�todo n�o p�blico"), 
	METHOD_NOT_STATIC("M�todo n�o est�tico"),
	METHOD_MODIFIER_MISMATCH("Modificador incorreto"),
	METHOD_NOT_RETURN("M�todo incorreto"),
	
	COMPILATION_PROBLEM("Erro de compila��o"){
		@Override
		public boolean isFatal(){
			return true;
		}
		public String getMessage(){
			return "Erro de compila��o! Classe: {0}";
		}
	}, 
	CLASS_UNDEFINED("Classe indefinida"){
		@Override
		public boolean isFatal(){
			return true;
		}
		public String getMessage(){
			return "N�o foi poss�vel definir a classe {0}";
		}
	}, 
	FIELD_NOT_FINAL("Atributo n�o final"),
	;

	private String description;
	private int value;
	private String message;

	private ErrorType(String descricao){
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
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isFatal(){
		return false;
	}
	
	public String toString(){
		
		return this.getDescription() + " | " + this.getValue();
	}

	@Override
	public boolean eval(Class<?> clazz, String name, Class<?>... parameterTypes) {
		return false;
	}
	
	public String getMessage(String... args){
		String msg = MessageFormat.format(getMessage(), args);
		return msg;
	}
	
	@Override
	public String prefix() {
		return "Error";
	}


}
