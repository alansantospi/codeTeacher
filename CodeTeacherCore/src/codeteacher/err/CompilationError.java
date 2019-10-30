package codeteacher.err;

public class CompilationError extends Error {

	private String lineNumber;
	private String code;
	private String msg;
	private String src;
//	private String message;
	
	public CompilationError(String lineNumber, String code, String msg, String src, int value) {
		super(ErrorType.COMPILATION_PROBLEM, value);
		this.lineNumber = lineNumber;
		this.code = code;
		this.msg = msg;
		this.src = src;
		
//		this.setMessage(msg);
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getMessage() {
		return ErrorType.COMPILATION_PROBLEM.getMessage(code, msg, src, lineNumber);
	}
	
}
