package codeteacher.behave;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import utils.ReflectionUtils;

public class ConstructorCall extends ExecutorImpl {

	private String klazzName;
	private Object[] arguments;
	private Object obj;
	private ClassLoader loader;
	private Error error;
	private boolean useConstructor;

	public ConstructorCall(String alias, String type) {
		super(alias, type);
		klazzName = type;
		setMemberName(type);
		arguments = new Object[0];
	}
	
	public ConstructorCall(String className, ClassLoader loader) {
		this.klazzName = className;
		this.loader = loader;
	}
	
	public ConstructorCall(Object... arguments) {
		this.arguments = arguments;
	}
	
	public ConstructorCall(Class<?> klazz, Object... arguments) {
		this(arguments);
		this.klazz = klazz;
	}
	
	public ConstructorCall(String className, Object[] arguments, Object obj) {
		super();
		this.klazzName = className;
		setMemberName(className);
		this.arguments = arguments;
		this.obj = obj;
	}
	
	public ConstructorCall(String className, ClassLoader loader, Object[] arguments, Object obj) {
		super();
		this.klazzName = className;
		setMemberName(className);
		this.loader = loader;
		this.arguments = arguments;
		this.obj = obj;
	}
	
	public void setLoader(ClassLoader loader) {
		this.loader = loader;
	}
	
	@Override
	public boolean exec(){

		ErrorType errorType;
		if (klazz == null){
			try {
				if (loader != null) {
					klazz = loader.loadClass(klazzName);
				} else {
					klazz = ReflectionUtils.loadClass(klazzName);
				}
			} catch (ClassNotFoundException e) {
				
				errorType = ErrorType.CLASS_NOT_FOUND;
				error = new Error(errorType, errorType.getMessage(name), getValue());
				
				return true;
			}
		}
		
		try {
			if (useConstructor) {
				obj = ReflectionUtils.instantiateObject(klazz, arguments);
			} else {
				obj = ReflectionUtils.instantiateWithoutConstructor(klazz);
			}
		} catch (InstantiationException e) {
			errorType = ErrorType.INSTANTIATION;
			error = new Error(errorType, getValue());
			return true;
		} catch (IllegalAccessException e) {
			errorType = ErrorType.ILLEGAL_ACCESS;
			error = new Error(errorType, getValue());
			return true;
		} catch (IllegalArgumentException e) {
			errorType = ErrorType.ILLEGAL_ARGUMENT;
			error = new Error(errorType, errorType.getMessage(name), getValue());
			return true;
		} catch (InvocationTargetException e) {
			errorType = ErrorType.INVOCATION_TARGET;
			error = new Error(errorType, errorType.getMessage(name), getValue());
			return true;
		} catch (NoSuchMethodException e) {
			errorType = ErrorType.CONSTRUCTOR_NOT_FOUND;
			
			StringBuffer params = new StringBuffer();
			params.append("(");
			if (arguments != null) {
				Iterator<Object> iterator = Arrays.asList(arguments).iterator();
				
				while (iterator.hasNext()){
					Object next = iterator.next();
					params.append(next.getClass().getName());
					if (iterator.hasNext()){
						params.append(", ");
					}
				}
			}
			params.append(")");
			String constructor = klazz.getSimpleName() + params;
			
			error = new Error(errorType, errorType.getMessage(constructor, name), getValue());
			return true;
		}
		return false;
	}

	public Object getObj() {
		return obj;
	}

	public String getKlazzName() {
		return klazzName;
	}

	public void setKlazzName(String klazzName) {
		this.klazzName = klazzName;
	}

	@Override
	public boolean isError() {
		return exec();
	}

	@Override
	public Error getError() {
		return error;
	}
}
