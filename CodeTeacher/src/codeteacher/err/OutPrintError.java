package codeteacher.err;

public class OutPrintError extends Error {

	private String content;
	private String expected;

	public OutPrintError(String content, String expected, int value) {
		super(ErrorType.INCORRECT_OUTPRINT, value);
		this.content = content;
		this.expected = expected;
		String msg = ErrorType.INCORRECT_OUTPRINT.getMessage(content, expected);
		this.setMessage(msg);
	}

	public String getContent() {
		return content;
	}

	public String getExpected() {
		return expected;
	}
}
