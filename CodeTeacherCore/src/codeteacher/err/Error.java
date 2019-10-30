package codeteacher.err;

public class Error implements IError {
	
	private IErrorType type;
	private String message;
	private int value; 
	
	public Error() {
		
	}
	
	public Error(IErrorType type) {
		this.type = type;
		this.message = type.getMessage();
		this.value = type.getValue();
	}
	
	public Error(IErrorType type, String message) {
		this.type = type;
		this.message = message;
		this.value = type.getValue();
	}
	
	public Error(IErrorType type, int value) {
		this.type = type;
		this.value = value;
	}
	
	public Error(IErrorType type, String message, int value) {
		this(type, message);
		this.value = value;
	}
	
	public String getDescription(){
		return type.getDescription();
	}

	public void setMessage(String msg){
		message = msg;
	}
	
	public String getMessage(){
		return message;
	}
	
	public int getValue(){
		return value;
	}
	
	public String toString(){
		
		return this.getDescription() + " | " + this.getValue() + " | " + this.getMessage();
	}

	public boolean isFatal() {
		return type.isFatal();
	}
}
