package codeteacher;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import codeteacher.result.Evaluation;
import codeteacher.result.Performance;
import codeteacher.struct.eval.AbstractMethodEvaluator;
import codeteacher.struct.eval.ClassEvaluator;
import codeteacher.struct.eval.FinalMethodEvaluator;
import codeteacher.struct.eval.MethodEvaluator;
import codeteacher.struct.eval.MethodModifierEvaluator;
import codeteacher.struct.eval.PrivateMethodEvaluator;
import codeteacher.struct.eval.PublicMethodEvaluator;
import utils.ReflectionUtils;

public class StructuralAnalyzr extends Analyzr {
	
	private static Class<?> baseClazz;
	private static Class<?> otherClazz;
	
	public void evaluate(Collection<File> baseFiles, ClassLoader baseLoader) throws NoSuchFieldException, ClassNotFoundException {
		for (File file : baseFiles){
			    // Load in the class; MyClass.class should be located in
			    // the directory file:/c:/myclasses/com/mycompany
			    
			String name = file.getName();
			baseClazz = baseLoader.loadClass(FilenameUtils.removeExtension(name));
			
			if (!checkClass(baseClazz)){
				
				Field[] baseFields = ReflectionUtils.getFields(baseClazz, true);
				checkFields(baseFields);   
				
				Method[] baseMethods = ReflectionUtils.getDeclaredMethods(baseClazz);
				checkMethods(baseMethods);
				
				Constructor<?>[] baseConstructors = ReflectionUtils.getConstructors(baseClazz);
				checkConstructors(baseConstructors); 
				
				checkSuperClass(baseClazz);
				
				checkInterfaces(baseClazz); 
			} 			
		}	
	}	
	
	/*
	 * Contabilizar pontos
	 */
	private static int scorePoints(Class<?> baseClazz) throws NoSuchFieldException, SecurityException{
		int points = 0;
		points += ErrorType.CLASS_NOT_FOUND.getValue();
		
		Field[] baseFields = ReflectionUtils.getFields(baseClazz, true);
		points += ErrorType.FIELD_NOT_FOUND.getValue() * baseFields.length;
		
		Method[] baseMethods = ReflectionUtils.getDeclaredMethods(baseClazz);
		points += ErrorType.METHOD_NOT_FOUND.getValue() * baseMethods.length;
		
		Constructor<?>[] baseConstructors = ReflectionUtils.getConstructors(baseClazz);
		points += ErrorType.CONSTRUCTOR_NOT_FOUND.getValue() * baseConstructors.length;
		
		points += ErrorType.SUPERCLASS_NOT_FOUND.getValue();
		
		points += ErrorType.INTERFACE_NOT_IMPLEMENTED.getValue();
		
		return points;
	}
	
	protected void calcTotalCriteria(Collection<File> baseFiles, ClassLoader baseLoader) throws NoSuchFieldException {
		for (File file : baseFiles){
			try {
			    
				String name = file.getName();
				baseClazz = baseLoader.loadClass(FilenameUtils.removeExtension(name)); 
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			criteria += ErrorType.CLASS_NOT_FOUND.getValue();
			
			Field[] baseFields = ReflectionUtils.getFields(baseClazz, true);
			criteria += ErrorType.FIELD_NOT_FOUND.getValue() * baseFields.length;
			
			Method[] baseMethods = ReflectionUtils.getDeclaredMethods(baseClazz);
			criteria += ErrorType.METHOD_NOT_FOUND.getValue() * baseMethods.length;
			
			Constructor<?>[] baseConstructors = ReflectionUtils.getConstructors(baseClazz);
			criteria += ErrorType.CONSTRUCTOR_NOT_FOUND.getValue() * baseConstructors.length;
			
			criteria += ErrorType.SUPERCLASS_NOT_FOUND.getValue();
			
			criteria += ErrorType.INTERFACE_NOT_IMPLEMENTED.getValue();
		}	
	}	

	protected void setValues() {
		ErrorType.CLASS_NOT_FOUND.setValue(4);
		ErrorType.SUPERCLASS_NOT_FOUND.setValue(3);
		ErrorType.FIELD_NOT_FOUND.setValue(1);
		ErrorType.METHOD_NOT_FOUND.setValue(5);
		ErrorType.CONSTRUCTOR_NOT_FOUND.setValue(1);
		ErrorType.INTERFACE_NOT_IMPLEMENTED.setValue(3);
		ErrorType.BIN_NOT_FOUND.setValue(3);
		ErrorType.FOLDER_NOT_FOUND.setValue(3);
	}

	private static void checkInterfaces(Class<?> baseClazz) {
		boolean validate = ErrorType.INTERFACE_NOT_IMPLEMENTED.eval(baseClazz, null, otherClazz);
		if (validate){
			Error err = new Error(ErrorType.INTERFACE_NOT_IMPLEMENTED, ErrorType.INTERFACE_NOT_IMPLEMENTED.getMessage());
			evalReturn.addError(folderName, err);
		}
	}

	private static boolean checkClass(Class<?> baseClazz) throws NoSuchFieldException, SecurityException {
		ClassEvaluator evaluator = new ClassEvaluator(baseClazz, Config.STUDENT_DIR + "\\" + folderName + "\\" + Config.PROJECT_NAME + "\\" + Config.BIN_FOLDER);
		boolean validate = evaluator.eval();
		if (validate){
			int points = scorePoints(baseClazz);
			Error err = new Error(ErrorType.CLASS_NOT_FOUND, evaluator.getMessage(), points);
			evalReturn.addError(folderName, err);
		}
		return validate;
	}

	public static void setOtherClazz(Class<?> otherClazz) {
		StructuralAnalyzr.otherClazz = otherClazz;
	}

	private static void checkSuperClass(Class<?> clazz) {
		boolean validate = ErrorType.SUPERCLASS_NOT_FOUND.eval(clazz, null, otherClazz);
		if (validate) {
			Error err = new Error(ErrorType.SUPERCLASS_NOT_FOUND, ErrorType.SUPERCLASS_NOT_FOUND.getMessage());
			evalReturn.addError(folderName, err);
		}
	}

	public static boolean checkMethod(Method method){
		Class<?>[] parameterTypes = method.getParameterTypes();
		MethodEvaluator evaluator = new MethodEvaluator(otherClazz, method.getName(), parameterTypes);
		boolean validate = evaluator.eval();
		if (validate) {
			Error err = new Error(ErrorType.METHOD_NOT_FOUND, evaluator.getMessage());
			evalReturn.addError(folderName, err);
		}
		return validate;
	}
	
	public static void checkMethods(Method[] baseMethods){
		for (Method method : baseMethods){
			if (!checkMethod(method)) {
				checkMethodModifier(method);
			}
//			checkAbstractMethod(method);
//			checkFinalMethod(method);
		}
	}
	
	public static void checkAbstractMethod(Method method){
		if (ReflectionUtils.isAbstract(method)){
			
			Class<?>[] parameterTypes = method.getParameterTypes();
			
			MethodEvaluator evaluator = new AbstractMethodEvaluator(otherClazz, method.getName(), parameterTypes);
			
			boolean validate = evaluator.eval();
			if (validate) {
				Error err = new Error(ErrorType.METHOD_NOT_ABSTRACT, evaluator.getMessage());
				evalReturn.addError(folderName, err);
			}
		}
	}
	
	public static void checkMethodModifier(Method method){
			
		Class<?>[] parameterTypes = method.getParameterTypes();
		MethodEvaluator evaluator = new MethodModifierEvaluator(otherClazz, method.getName(), method.getModifiers(), parameterTypes);
		
		boolean validate = evaluator.eval();
		if (validate) {
			Error err = new Error(ErrorType.METHOD_MODIFIER_MISMATCH, evaluator.getMessage());
			evalReturn.addError(folderName, err);
		}
	}
	
	public static void checkFinalMethod(Method method){
		if (ReflectionUtils.isFinal(method)){
			
			Class<?>[] parameterTypes = method.getParameterTypes();
			
			MethodEvaluator evaluator = new FinalMethodEvaluator(otherClazz, method.getName(), parameterTypes);
			
			boolean validate = evaluator.eval();
			if (validate) {
				Error err = new Error(ErrorType.METHOD_NOT_FINAL, evaluator.getMessage());
				evalReturn.addError(folderName, err);
			}
		}
	}
	
	public static void checkPrivateMethod(Method method){
		if (ReflectionUtils.isPrivate(method)){
			
			Class<?>[] parameterTypes = method.getParameterTypes();
			
			MethodEvaluator evaluator = new PrivateMethodEvaluator(otherClazz, method.getName(), parameterTypes);
			
			boolean validate = evaluator.eval();
			if (validate) {
				Error err = new Error(ErrorType.METHOD_NOT_PRIVATE, evaluator.getMessage());
				evalReturn.addError(folderName, err);
			}
		}
	}
	
	public static void checkPublicMethod(Method method){
		if (ReflectionUtils.isPublic(method)){
			
			Class<?>[] parameterTypes = method.getParameterTypes();
			
			MethodEvaluator evaluator = new PublicMethodEvaluator(otherClazz, method.getName(), parameterTypes);
			
			boolean validate = evaluator.eval();
			if (validate) {
				Error err = new Error(ErrorType.METHOD_NOT_PUBLIC, evaluator.getMessage());
				evalReturn.addError(folderName, err);
			}
		}
	}
	
	public static void checkAbstractMethods(Method[] baseMethods){
		for (Method method : baseMethods){
			checkAbstractMethod(method);
		}
	}
	
	public static void checkFields(Field[] baseFields){
		for (Field field : baseFields){
			ErrorType errorType = ErrorType.FIELD_NOT_FOUND;
			boolean validate = errorType.eval(otherClazz, field.getName());
			if (validate) {
				
				StringBuffer modifiers = new StringBuffer();
				
				final Iterator<Integer> iterator = Arrays.asList(field.getModifiers()).iterator();
				
				while (iterator.hasNext()){
					Integer next = iterator.next();
					
					modifiers.append(Modifier.toString(next));
					if (iterator.hasNext()){
						modifiers.append(" ");
					}
				}
				
				String attr = modifiers + " " + field.getType().getSimpleName() + " " + field.getName();
				Error err = new Error(errorType, errorType.getMessage(attr, otherClazz.getName()));
				evalReturn.addError(folderName, err);
			}
		}
	}
	
	public static void checkConstructors(Constructor<?>[] baseConstructors){
		for (Constructor<?> c : baseConstructors){
//			criterios += ErrorType.CONSTRUCTOR_NOT_FOUND.getValue();
			Class<?>[] parameterTypes = c.getParameterTypes();
			ErrorType erroType = ErrorType.CONSTRUCTOR_NOT_FOUND;
			boolean validate = erroType.eval(otherClazz, c.getName(), parameterTypes);
			if (validate) {
				StringBuffer params = new StringBuffer();
				params.append("(");
				
				Iterator<Class<?>> iterator = Arrays.asList(parameterTypes).iterator();
				
				while (iterator.hasNext()){
					Class<?> next = iterator.next();
					params.append(next.getSimpleName());
					if (iterator.hasNext()){
						params.append(", ");
					}
				}
				params.append(")");
				String constructor = c.getName() + params;
				
				Error err = new Error(erroType, erroType.getMessage(constructor, otherClazz.getName()));
				evalReturn.addError(folderName, err);
			}
		}
	}
	
}
