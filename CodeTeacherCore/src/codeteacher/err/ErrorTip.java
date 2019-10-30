package codeteacher.err;

public enum ErrorTip {

	METHOD_NAME_MISMATCH("Verifique se o nome do método está correto"),
	METHOD_PARAMS_UNORDERED("Verifique se os parâmetros do método estão na ordem correta"),
	METHOD_PARAMS_UNTYPED("Verifique se os tipos dos parâmetros do método estão corretos")
//	METHOD_UNIMPLEMENTED("Verifique se o método possui implementação!")
	
	;
	private String message;
	
	private ErrorTip(String msg) {
		this.message = msg;
	}
	
}
