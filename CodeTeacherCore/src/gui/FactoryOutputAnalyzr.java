package gui;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import codeteacher.analyzers.MethodAnalyzer;
import codeteacher.analyzers.OutputAnalyzer;
import utils.ReflectionUtils;

public class FactoryOutputAnalyzr {

	public static OutputAnalyzer create(Set<String> modifiers, String returnType, String methodName, boolean regex, String expected, int value, Object[] args, String[] split){
		OutputAnalyzer analyzer = new OutputAnalyzer(modifiers, returnType, methodName, regex, expected, value, args);
		
		for (String param : split){
			if (param.contains("[]")){
				int indexOf = param.indexOf("[");
				param = param.substring(0, indexOf);
				
				try {
					Class<?> clazz = ReflectionUtils.loadClass(param);
//					Object obj = ReflectionUtils.instantiateWithoutConstructor(clazz);
//					Object arg0 = ArrayUtils.newArrayOf(new String[0], 0);
					Object arg0 = Array.newInstance(clazz, 0);
					if (split.length == 1){
						analyzer = new OutputAnalyzer(modifiers, returnType, methodName, regex, expected, value, arg0);
					} else {
//						Class<?> clazz = ReflectionUtils.loadClass(param);
//						Object arg1 = Array.newInstance(clazz, 0);
						analyzer = new OutputAnalyzer(modifiers, returnType, methodName, regex, expected, value, args);
//						analyzer.addArgs(arg1);
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Class<?> clazz;
				try {
					clazz = ReflectionUtils.loadClass(param);
					Object arg1;
					try { 
						arg1 = ReflectionUtils.instantiateWithoutConstructor(clazz);
//							clazz.newInstance();
						//ReflectionUtils.instantiateObject(clazz, 0);
						analyzer.addArgs(arg1);
					} catch (InstantiationException
							| IllegalAccessException
							| IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return analyzer;
	}
}
