package codeteacher.err;

import java.text.MessageFormat;

import gui.msg.GuiMsg;
import utils.ReflectionUtils;

public enum ErrorType implements IErrorType, GuiMsg {
	
	CLASS_NOT_FOUND("Classe não encontrada"){
		public String getMessage(){
			return "Classe {0} não encontrada!";
		}
	},
	
	PARAM_CLASS_NOT_FOUND("Parâmetro não encontrado"){
		public String getMessage(){
			return "Não foi possível verificar o método {0} da classe {1} por que o parâmetro do tipo {2} não foi encontrado";
		}
	},
	
	SUPERCLASS_NOT_FOUND("Superclasse não encontrada"){
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
	
	METHOD_NOT_FOUND("Método não encontrado"){
		public String getMessage(){
			return "Método {0} não encontrado na classe {1}";
		}
	},
	
	FIELD_NOT_FOUND("Atributo não encontrado"){
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
			return "Atributo {0} não encontrado na classe {1}";
		}
	},
	
	CONSTRUCTOR_NOT_FOUND("Construtor não encontrado"){
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
			return "Construtor {0} não encontrado na classe {1}";
		}
	},
	
	INTERFACE_NOT_IMPLEMENTED("Interface não implementada"){
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
	
	PROJECT_NOT_FOUND("Projeto não encontrado"){
		@Override
		public boolean isFatal(){
			return true;
		}
		public String getMessage(){
			return "O projeto {0} não foi encontrado!";
		}
	},
	
	PROJECT_UNDEFINED("Projeto indefinido"){
		@Override
		public boolean isFatal(){
			return true;
		}
		public String getMessage(){
			return "Não foi possível definir o projeto {0}";
		}
	},
	
	JAR_NOT_FOUND("Arquivo Jar não encontrado"){
		@Override
		public boolean isFatal(){
			return true;
		}
		public String getMessage(){
			return "O arquivo {0} não foi encontrado!";
		}
	},
	
	FILE_NOT_FOUND("Arquivo não encontrado"){
		@Override
		public boolean isFatal(){
			return true;
		}
		public String getMessage(){
			return "O arquivo {0} não foi encontrado!";
		}
	},
	
	BIN_NOT_FOUND("Pasta bin não encontrada"){
		@Override
		public boolean isFatal(){
			return true;
		}
		public String getMessage(){
			return "A pasta {0} não foi encontrada!";
		}
	},
	
	FOLDER_NOT_FOUND("Pasta não encontrada"){
		@Override
		public boolean isFatal(){
			return true;
		}
		public String getMessage(){
			return "A pasta {0}\\{1} não foi encontrada!";
		}
	},
	
	SRC_FOLDER_NOT_FOUND("Pasta src não encontrada"){
		@Override
		public boolean isFatal(){
			return true;
		}
		public String getMessage(){
			return "A pasta {0} não foi encontrada!";
		}
	},
	
	METHOD_NOT_ABSTRACT("Método não abstrato"), 
	METHOD_NOT_FINAL("Método não final"), 
	METHOD_NOT_PRIVATE("Método não privado"), 
	METHOD_NOT_PROTECTED("Método não protegido"),
	METHOD_NOT_PUBLIC("Método não público"), 
	METHOD_NOT_STATIC("Método não estático"),
	METHOD_MODIFIER_MISMATCH("Modificador incorreto"),
	METHOD_NOT_RETURN("Método incorreto"),
	
	COMPILATION_PROBLEM("Erro de compilação"){
		@Override
		public boolean isFatal(){
			return true;
		}
		public String getMessage(){
			return "Erro de compilação! Classe: {0}";
		}
	}, 
	CLASS_UNDEFINED("Classe indefinida"){
		@Override
		public boolean isFatal(){
			return true;
		}
		public String getMessage(){
			return "Não foi possível definir a classe {0}";
		}
	}, 
	FIELD_NOT_FINAL("Atributo não final"),
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
