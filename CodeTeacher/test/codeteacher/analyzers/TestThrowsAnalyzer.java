package codeteacher.analyzers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import utils.ReflectionUtils;

@RunWith(Parameterized.class)
public class TestThrowsAnalyzer {
	
	private static String rootPath;
	private String testCase;
	private boolean declared;
	private String returnType;
	private String name;
	private boolean regex;
	private boolean matchCase;
	private int value;
	private boolean expected;
	private String expectedExceptions;
	
	public TestThrowsAnalyzer(String testCase, boolean declared, String returnType, String name, boolean matchCase, boolean regex, int value, boolean expected, String exceptions) {
		super();
		this.testCase = testCase;
		this.declared = declared;
		this.returnType = returnType;
		this.name = name;
		this.matchCase = matchCase;
		this.regex = regex;
		this.value = value;
		this.expected = expected;
		this.expectedExceptions = exceptions;
	}
	
	@BeforeClass
	public static void setUp(){
		rootPath = System.getProperty("user.dir") + "\\test\\data\\TestThrowsAnalyzer\\";
	}

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
			/* Test case												declared  returnType    methodName	case   regex  value expected exceptions*/
			{ "Any exception - Check any exception.", 			  			true, "void", 		"method1",	false, false,  1, 	false,   ""},
			{ "Any exception - Check one exception.", 		 	  			true, "void", 		"method1",	false, false,  2, 	true,    "Exception"},
			{ "Any exception - Check two exception.", 		 	  			true, "void", 		"method1",	false, false,  2, 	true,    "Exception"},
			{ "Single exception - Check one matching exception.", 	  		true, "void", 		"method2",	false, false,  2, 	false,   "NullPointerException"},
			{ "Single exception - Check one unmatching exception.", 		true, "void", 		"method2",	false, false,  2, 	true,    "ArrayIndexOutOfBoundsException"},
			{ "More than one exception - Check any exception.",  			true, "void", 		"method3",	false, false,  2, 	false,    new String[0]},
			{ "More than one exception - Check one matching exception.",	true, "void", 		"method3",	false, false,  2, 	false,   "RuntimeException"},
			{ "More than one exception - Check one unmatching exception.",  true, "void", 		"method3",	false, false,  2, 	true,    "NullPointerException"},
			{ "More than one exception - Check two matching exceptions.",   true, "void", 		"method3",	false, false,  2, 	false,    "RuntimeException", "IOException"},
			{ "More than one exception - Check two unmatching exceptions.", true, "void", 		"method3",	false, false,  2, 	true,    "NullPointerException", "InterruptedException"},
//			
		});
	}
	
	@Test
	public void test_ThrowsAnalyzer()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		ClassLoader loader = ReflectionUtils.getClassLoader(rootPath);
		String klazzName = "ExceptionTest";
		boolean classCase = false;
		boolean classRecursive = false;
		boolean classRegex = false;
		int classValue = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, classCase, classRecursive, classRegex, classValue);
		assertFalse(testCase, classAnalyzer.isError());
		
		int methodValue = 1;
		String[] paramTypes = new String[0];
		MethodAnalyzer methodAnalyzr = new MethodAnalyzer(classAnalyzer, declared, returnType, name, matchCase, regex, methodValue, paramTypes);
		assertFalse(testCase, methodAnalyzr.isError());
		
		ThrowsAnalyzer throwsAnalyzer = new ThrowsAnalyzer(methodAnalyzr, expectedExceptions, matchCase, regex, value); 
		
//		assertEquals(testCase, name, throwsAnalyzer.getMemberName());
		assertEquals(testCase, matchCase, throwsAnalyzer.isMatchCase());
		assertEquals(testCase, regex, throwsAnalyzer.isRegex());
		assertEquals(testCase, value, throwsAnalyzer.getValue());
	}
	
	@Test
	public void test_isError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		ClassLoader loader = ReflectionUtils.getClassLoader(rootPath);
		String klazzName = "ExceptionTest";
		boolean classCase = false;
		boolean classRecursive = false;
		boolean classRegex = false;
		int classValue = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, classCase, classRecursive, classRegex, classValue);
		assertFalse(testCase, classAnalyzer.isError());
		
		int methodValue = 1;
		String[] paramTypes = new String[0];
		MethodAnalyzer methodAnalyzr = new MethodAnalyzer(classAnalyzer, declared, returnType, name, matchCase, regex, methodValue, paramTypes);
		assertFalse(testCase, methodAnalyzr.isError());
		
		ThrowsAnalyzer throwsAnalyzer = new ThrowsAnalyzer(methodAnalyzr, expectedExceptions, matchCase, regex, value); 
		
		boolean actual = throwsAnalyzer.isError();
		assertEquals(testCase, expected, actual);
	}
//	
//	@Test
//	public void test_getError()
//			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
//		
//		ClassLoader loader = ReflectionUtils.getClassLoader(rootPath);
//		String klazzName = "Veiculo";
//		boolean classCase = false;
//		boolean classRecursive = false;
//		boolean classRegex = false;
//		int classValue = 1;
//		
//		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, classCase, classRecursive, classRegex, classValue);
//		assertFalse(testCase, classAnalyzer.isError());
//		
//		MethodAnalyzer methodAnalyzr = new MethodAnalyzer(classAnalyzer, declared, returnType, name, matchCase, regex, value, paramTypes);
//		
//		boolean actual = methodAnalyzr.isError();
//		assertEquals(testCase, expected, actual);
//		
//		Error error = methodAnalyzr.getError();
//		assertEquals(testCase, value, error.getValue());
//		
//		ErrorType errorType = ErrorType.METHOD_NOT_FOUND;
//		String msg = errorType.getMessage(name, klazzName, Integer.toString(value));
//		assertEquals(testCase, msg, error.getMessage());
//	}
}
