package codeteacher.analyzers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import codeteacher.err.CompositeError;
import codeteacher.err.Error;
import codeteacher.err.ErrorType;

@SuppressWarnings("serial")
public class ConsoleAnalyzer extends MethodModifierAnalyzer {
	
	private String[] inputs;
	private CompositeError error;
	
	public ConsoleAnalyzer(MethodAnalyzer parent, String[] inputs, int value) {
		super(parent, value);
		this.inputs = inputs;
		error = new CompositeError(ErrorType.INPUT_ERROR);
	}
	
	public boolean check()  {
		
		StringBuilder b = new StringBuilder();
		
		for (String string : inputs) {
			b.append(string).append("\n");
		}
		
		ByteArrayInputStream inStream = new ByteArrayInputStream(b.toString().getBytes());
		
		Method method = getMethod();
		
		final InputStream stdInStream = System.in;
		CustomThread thread = new CustomThread(inStream, method, stdInStream);
		thread.run();
		
		return thread.getResult();
	}

	@Override
	public boolean isError() {
		return check();
	}

	@Override
	public Error getError() {
		return error;
	}

	@Override
	public String getModifier() {
		return "console";
	}

	public class CustomThread implements Runnable {
		
		private boolean result = false;
		private InputStream inStream;
		private InputStream stdInStream;
		private Method method;
		
		public CustomThread(InputStream stream, Method method, InputStream stdInStream) {
			this.inStream = stream;
			this.method = method;
			this.stdInStream = stdInStream;
		}
	
		@Override
		public void run() {
			System.setIn(inStream);
			Object params = new String[0];
			try {
				method.invoke(null, new Object[] { params });
			} catch (IllegalAccessException e) {
				ErrorType errorType = ErrorType.ILLEGAL_ACCESS;
				Error newError = new Error(errorType, errorType.getMessage(method.getName(), e.getCause().toString()));
				error.addError(newError); 
				result = true;
			} catch (IllegalArgumentException e) {
				ErrorType errorType = ErrorType.ILLEGAL_ARGUMENT;
				Error newError = new Error(errorType, errorType.getMessage(method.getName(), e.getCause().toString()));
				error.addError(newError);
				result = true;
			} catch (InvocationTargetException e) {
				ErrorType errorType = ErrorType.INVOCATION_TARGET;
				Error newError = new Error(errorType, errorType.getMessage(method.getName(), e.getCause().toString()));
				error.addError(newError);
				result = true;
			}
			System.setIn(stdInStream);
		}
		public boolean getResult() {
			return result;
		}
	}
}
