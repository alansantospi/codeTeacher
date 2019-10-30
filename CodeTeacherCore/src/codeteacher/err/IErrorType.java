package codeteacher.err;

public interface IErrorType {

	boolean eval(Class<?> clazz, String name, Class<?>... parameterTypes);
	public String getMessage();
	public int getValue();
	public String getDescription();
	public boolean isFatal();
}
