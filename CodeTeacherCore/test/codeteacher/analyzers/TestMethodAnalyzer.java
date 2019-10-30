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
public class TestMethodAnalyzer {
	
	private static String rootPath;
	private String testCase;
	private boolean declared;
	private String returnType;
	private String name;
	private boolean regex;
	private boolean matchCase;
	private int value;
	private boolean expected;
	private String[] paramTypes;
	
	public TestMethodAnalyzer(String testCase, boolean declared, String returnType, String name, boolean matchCase, boolean regex, int value, boolean expected, String... paramTypes) {
		super();
		this.testCase = testCase;
		this.declared = declared;
		this.returnType = returnType;
		this.name = name;
		this.matchCase = matchCase;
		this.regex = regex;
		this.value = value;
		this.expected = expected;
		this.paramTypes = paramTypes;
	}
	
	@BeforeClass
	public static void setUp(){
		rootPath = System.getProperty("user.dir") + "\\test\\data\\TestMethodAnalyzer\\";
	}

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
			/* Test case						declared  returnType    methodName			    case   regex  value expected paramTypes*/
			{ "Declared valid method.", 			 true, "float", 	"getVelocidadeAtual",	false, false,  1, 	false,   new String[0]},
			{ "Declared invalid method.", 			 true, "float", 	"getVelocidade",		false, false,  2, 	true,    new String[0]},
			{ "Declared valid method match case.", 	 true, "void", 		"setVelocidadeAtual",	true,  false,  1, 	false,   new String[]{"float"}},
			{ "Declared invalid method match case.", true, "void", 		"setvelocidadeatual",	true,  false,  2, 	true,    new String[]{"float"}},
			{ "Declared valid method regex.", 		 true, "void", 		"setVelocidade(Atual)*",false,  true,  1, 	false,   new String[]{"float"}},
			{ "Declared invalid method regex.", 	 true, "void", 		"set*Atual",			false,  true,  2, 	true,    new String[]{"float"}},
		});
	}
	
	@Test
	public void test_MethodAnalyzer()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		ClassLoader loader = ReflectionUtils.getClassLoader(rootPath);
		String klazzName = "Veiculo";
		boolean classCase = false;
		boolean classRecursive = false;
		boolean classRegex = false;
		int classValue = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, classCase, classRecursive, classRegex, classValue);
		assertFalse(testCase, classAnalyzer.isError());
		
		MethodAnalyzer methodAnalyzr = new MethodAnalyzer(classAnalyzer, declared, returnType, name, matchCase, regex, value, paramTypes);
		assertEquals(testCase, declared, methodAnalyzr.isDeclared());
		assertEquals(testCase, name, methodAnalyzr.getMemberName());
		assertEquals(testCase, matchCase, methodAnalyzr.isMatchCase());
		assertEquals(testCase, regex, methodAnalyzr.isRegex());
		assertEquals(testCase, value, methodAnalyzr.getValue());
	}
	
	@Test
	public void test_isError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		ClassLoader loader = ReflectionUtils.getClassLoader(rootPath);
		String klazzName = "Veiculo";
		boolean classCase = false;
		boolean classRecursive = false;
		boolean classRegex = false;
		int classValue = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, classCase, classRecursive, classRegex, classValue);
		assertFalse(testCase, classAnalyzer.isError());
		
		MethodAnalyzer methodAnalyzr = new MethodAnalyzer(classAnalyzer, declared, returnType, name, matchCase, regex, value, paramTypes);
		
		boolean actual = methodAnalyzr.isError();
		assertEquals(testCase, expected, actual);
	}
	
	@Test
	public void test_getError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		ClassLoader loader = ReflectionUtils.getClassLoader(rootPath);
		String klazzName = "Veiculo";
		boolean classCase = false;
		boolean classRecursive = false;
		boolean classRegex = false;
		int classValue = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, classCase, classRecursive, classRegex, classValue);
		assertFalse(testCase, classAnalyzer.isError());
		
		MethodAnalyzer methodAnalyzr = new MethodAnalyzer(classAnalyzer, declared, returnType, name, matchCase, regex, value, paramTypes);
		
		boolean actual = methodAnalyzr.isError();
		assertEquals(testCase, expected, actual);
		
		Error error = methodAnalyzr.getError();
		assertEquals(testCase, value, error.getValue());
		
		ErrorType errorType = ErrorType.METHOD_NOT_FOUND;
		String msg = errorType.getMessage(name, klazzName, Integer.toString(value));
		assertEquals(testCase, msg, error.getMessage());
	}
	
	@Test
	public void test_checkParams() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		ClassLoader loader = ReflectionUtils.getClassLoader(rootPath);
		String klazzName = "Veiculo";
		boolean classCase = false;
		boolean classRecursive = false;
		boolean classRegex = false;
		int classValue = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, classCase, classRecursive, classRegex, classValue);
		assertFalse(testCase, classAnalyzer.isError());
		
		MethodAnalyzer methodAnalyzr = new MethodAnalyzer(classAnalyzer, declared, returnType, name, matchCase, regex, value, paramTypes);
		assertEquals(testCase, expected, methodAnalyzr.isError());
		
	}
}
