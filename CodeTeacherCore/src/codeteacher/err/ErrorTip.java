package codeteacher.err;

public enum ErrorTip {

	METHOD_NAME_MISMATCH("Verifique se o nome do m�todo est� correto"),
	METHOD_PARAMS_UNORDERED("Verifique se os par�metros do m�todo est�o na ordem correta"),
	METHOD_PARAMS_UNTYPED("Verifique se os tipos dos par�metros do m�todo est�o corretos")
//	METHOD_UNIMPLEMENTED("Verifique se o m�todo possui implementa��o!")
	
	;
	private String message;
	
	private ErrorTip(String msg) {
		this.message = msg;
	}
	
}
