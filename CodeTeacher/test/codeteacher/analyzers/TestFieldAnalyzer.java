package codeteacher.analyzers;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import codeteacher.analyzers.FieldAnalyzer;
import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import utils.ReflectionUtils;

@RunWith(Parameterized.class)
public class TestFieldAnalyzer {

	private String testCase;
	private Class<?> klazz;
	private boolean declared;
	private String name;
	private boolean regex;
	private boolean matchCase;
	private boolean expected;
	private static String rootPath;

	public TestFieldAnalyzer(String testCase, Class<?> klazz, boolean declared, String name, boolean regex, boolean matchCase, boolean expected) {
		super();
		this.testCase = testCase;
		this.klazz = klazz;
		this.declared = declared;
		this.name = name;
		this.regex = regex;
		this.matchCase = matchCase;
		this.expected = expected;
	}

	@BeforeClass
	public static void setUp(){
		rootPath = System.getProperty("user.dir") + "\\test\\data\\TestFieldAnalyzer\\";
	}
	
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{ "declared valid attr.", File.class, true, "path", false, false, false},
			{ "declared invalid attr.", File.class, true, "xpath", false, false, true},
			{ "declared valid unique attr regex.", File.class, true, "pa*th", true, false, false},
			{ "declared valid not unique attr regex.", File.class, true, "separator(Char)*", true, false, true},
			{ "declared invalid attr regex.", File.class, true, "x+path", true, false, true},
			{ "declared valid attr match case.", File.class, true, "path", false, true, false},
			{ "declared invalid attr match case.", File.class, true, "Path", true, false, true}
		});
	}

	@Test
	public void testIsError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		ClassLoader loader = ReflectionUtils.getClassLoader(rootPath);
		String klazzName = "Aluno";
		boolean caseSensitive = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, caseSensitive, recursive, regex, value);
		
		FieldAnalyzer attrAnalyzr = new FieldAnalyzer(classAnalyzer, declared, name, regex, matchCase);
		boolean actual = attrAnalyzr.isError();
		assertEquals(testCase, expected, actual);
	}
	
	@Test
	public void testGetError() {

		ClassLoader loader = ReflectionUtils.getClassLoader(rootPath);
		String klazzName = "Aluno";
		boolean caseSensitive = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, caseSensitive, recursive, regex, value);
		
		FieldAnalyzer attrAnalyzr = new FieldAnalyzer(classAnalyzer, declared, name, regex, matchCase);
		
		ErrorType errorType = ErrorType.FIELD_NOT_FOUND;
		Error error = new Error(errorType, errorType.getMessage(name, klazzName), 0);
		
		assertEquals(testCase, error.getMessage(), attrAnalyzr.getError().getMessage());
	}
	
	@Test
	public void testIsRegex() {
		FieldAnalyzer attrAnalyzr = new FieldAnalyzer(klazz, declared, name, regex, matchCase);
		assertEquals(testCase, regex, attrAnalyzr.isRegex());
	}
	
	@Test
	public void testIsMatchCase() {
		FieldAnalyzer attrAnalyzr = new FieldAnalyzer(klazz, declared, name, regex, matchCase);
		assertEquals(testCase, matchCase, attrAnalyzr.isMatchCase());
	}
	
	@Test
	public void testGetKlazz() {
		FieldAnalyzer attrAnalyzr = new FieldAnalyzer(klazz, declared, name, regex, matchCase);
		assertEquals(testCase, klazz, attrAnalyzr.getKlazz());
	}
}
