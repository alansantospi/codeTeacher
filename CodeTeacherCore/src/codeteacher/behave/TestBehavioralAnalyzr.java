package codeteacher.behave;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import codeteacher.Config;
import codeteacher.StructuralAnalyzr;
import codeteacher.analyzers.MethodCall;
import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import codeteacher.result.Evaluation;
import utils.ReflectionUtils;

public class TestBehavioralAnalyzr {
	
	private static Object obj;
	private static Class<?> baseClazz;
	private static List<MethodCall> testCases = new ArrayList<MethodCall>();
	private static String folderName;
	
	private static Evaluation evalReturn = new Evaluation();

	private static void betaTest() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, SecurityException {
		Collection<File> baseFiles = FileUtils.listFiles(new File(Config.TEACHER_DIR), new String[] { "class" }, true);
		ClassLoader baseLoader = ReflectionUtils.getClassLoader(Config.TEACHER_DIR);
		
		try {
			String name = "Automovel";
			baseClazz = baseLoader.loadClass(FilenameUtils.removeExtension(name));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String json = 
				"construct{"
				+ "float: 10f,"
				+ "},"
				+ "fields {"
				+ "nivelCombustivel: 100f,"
				+ "},"
				+ "callMethods{"
				+ ""
				+ "}"
		;

//		BehavioralAnalyzr.testConstructor(baseClazz, 10f);

		ReflectionUtils.setValue(obj, true, "nivelCombustivel", 100f);
		
		setUp(
//				new MethodCall(obj, "setNivelCombustivel", null, 100f),
				new MethodCall(obj, "andar", null, 20f),
				new MethodCall(obj, "getNivelCombustivel", 97f),
			  
				new MethodCall(obj, "abastecer", null, 10f),
				new MethodCall(obj, "getNivelCombustivel", 107f),
		
				new MethodCall(obj, "parar", null),
				new MethodCall(obj, "getVelocidadeAtual", 10f),
			  	
				new MethodCall(obj, "acelerar", 10f, 101f),
				new MethodCall(obj, "getVelocidadeAtual", 10f),
			
				new MethodCall(obj, "mostrarCombustivel", 17f),
				new MethodCall(obj, "getVelocidadeAtual", 10f)
		
				);
		
		exec();
		System.out.println(evalReturn);

	}

	private static void exec() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		for (MethodCall testCase : testCases) {
			if (testCase.isError()){
				String msg = "O método " + testCase.getMemberName() + " deveria retornar '" + testCase.getOutput() + "' mas retornou '" + testCase.getMethodReturn() + "'";
				Error error = new Error(ErrorType.METHOD_NOT_RETURN, msg);
				evalReturn.addError("student", error);
			}
		}
	}
	
	public static void setUp(MethodCall... cases){

		for (MethodCall testCase : cases) {
			testCases.add(testCase);
		}
	}
	
	
}
