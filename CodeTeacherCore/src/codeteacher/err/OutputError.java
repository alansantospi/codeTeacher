package codeteacher.err;

public class OutputError extends Error {

	private String content;
	private String expected;

	public OutputError(ErrorType type, String message, int value, String content, String expected) {
		super(type, message, value);
		this.content = content;
		this.expected = expected;
	}

	public String getContent() {
		return content;
	}

	public String getExpected() {
		return expected;
	}

}
