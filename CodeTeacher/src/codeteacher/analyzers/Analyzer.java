package codeteacher.analyzers;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import codeteacher.err.Error;

public interface Analyzer extends Serializable {

	public boolean isError() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException;

	public Error getError();
	
	public String getMemberName();
	
//	public String getMethodNameWithParams();
	
	public int getValue();
	
	public int getTotalValue();
	
//	public void setKlazz(Class<?> klazz);
	
	public boolean run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException ;
}
