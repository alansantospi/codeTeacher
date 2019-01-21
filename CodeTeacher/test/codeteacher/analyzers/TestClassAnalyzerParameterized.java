package codeteacher.analyzers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import codeteacher.analyzers.ClassAnalyzer;
import utils.ReflectionUtils;

@RunWith(Parameterized.class)
public class TestClassAnalyzerParameterized {
	private String testCase;
	private String path;
	private String klazzName;
	private boolean recursive;
	private boolean caseSensitive;
	private boolean regex;
	private boolean expected;
	private static String rootPath;
	
	public TestClassAnalyzerParameterized(String testCase, String path, String klazzName, boolean recursive, boolean caseSensitive, boolean regex, boolean expected) {
		super();
		this.testCase = testCase;
		this.path = path;
		this.klazzName = klazzName;
		this.recursive = recursive;
		this.caseSensitive = caseSensitive;
		this.regex = regex;
		this.expected = expected;
	}

	@BeforeClass
	public static void setUp(){
		rootPath = System.getProperty("user.dir") + "\\test\\data\\TestClassAnalyzerParameterized\\";
	}
	
	@Parameters
    public static Collection<Object[]> data() {
		
    	return Arrays.asList(new Object[][] {
    	/* testCase						 	Path 			klazzName   recursive matchCase regex, expected */
    	{"test_IgnoreCase_L1_0_L2_0", 		"IgnoreCase",	"Nada",		false, 	  false, 	false,  true },
    	{"test_IgnoreCase_L1_0_L2_1", 		"IgnoreCase",	"Dados",	false, 	  false, 	false,  true },
    	{"test_IgnoreCase_L1_1_L2_0", 		"IgnoreCase",	"EscolA",	false, 	  false, 	false,  false },
    	{"test_IgnoreCase_L1_1_L2_1", 		"IgnoreCase",	"AlUNo",	false, 	  false, 	false,  false },
    	
    	{"test_IgnoreCase_L1_0_L2_0_R", 	"IgnoreCase",	"Nada",		true, 	  false, 	false,  true },
    	{"test_IgnoreCase_L1_0_L2_1_R", 	"IgnoreCase",	"Dados",	true, 	  false, 	false,  false },
    	{"test_IgnoreCase_L1_1_L2_0_R", 	"IgnoreCase",	"EscolA",	true, 	  false, 	false,  false },
    	{"test_IgnoreCase_L1_1_L2_1_R", 	"IgnoreCase",	"AlUNo",	true, 	  false, 	false,  true },
    	
    	{"test_MatchCase_L1_0_L2_0", 		"MatchCase",	"AlUno",	false, 	  true, 	false,  true },
    	{"test_MatchCase_L1_0_L2_1", 		"MatchCase",	"ALuno",	false, 	  true, 	false,  true },
    	{"test_MatchCase_L1_1_L2_0", 		"MatchCase",	"Aluno",	false, 	  true, 	false,  false },
    	{"test_MatchCase_L1_1_L2_1", 		"MatchCase",	"Conta",	false, 	  true, 	false,  false },
    	
    	{"test_MatchCase_L1_0_L2_0_R", 		"MatchCase",	"AlUno",	true, 	  true, 	false,  true },
    	{"test_MatchCase_L1_0_L2_1_R", 		"MatchCase",	"ALuno",	true, 	  true, 	false,  false },
    	{"test_MatchCase_L1_1_L2_0_R", 		"MatchCase",	"Aluno",	true, 	  true, 	false,  false },
    	{"test_MatchCase_L1_1_L2_1_R", 		"MatchCase",	"Conta",	true, 	  true, 	false,  true },

     	{"test_Regex_L1_0_L2_0", 			"Regex",		"Alun[a]",	false, 	  false, 	true,  true },
    	{"test_Regex_L1_0_L2_1", 			"Regex",		"Ba[N]co",	false, 	  false, 	true,  true },
    	{"test_Regex_L1_0_L2_2", 			"Regex",		"Cont.{1}",	false, 	  false, 	true,  true },
    	{"test_Regex_L1_1_L2_0", 			"Regex",		"A[l]uno",	false, 	  false, 	true,  false },
    	{"test_Regex_L1_1_L2_1", 			"Regex",		"Ba*",		false, 	  false, 	true,  true },
    	{"test_Regex_L1_1_L2_2", 			"Regex",		"Conta",	false, 	  false, 	true,  true },
//    	{"test_Regex_L1_2_L2_0", 			"Regex",		"Conto",	false, 	  false, 	true,  false },
//    	{"test_Regex_L1_2_L2_1", 			"Regex",		"Conta",	false, 	  false, 	true,  true },
//    	{"test_Regex_L1_2_L2_2", 			"Regex",		"Conta",	false, 	  false, 	true,  true },
//    	
    	{"test_Regex_L1_0_L2_0_R", 			"Regex",		"Alun[a]",	true, 	  false, 	true,  true },
    	{"test_Regex_L1_0_L2_1_R", 			"Regex",		"Ba[N]co",	true, 	  false, 	true,  false },
    	{"test_Regex_L1_0_L2_2_R", 			"Regex",		"Cont.{1}",	true, 	  false, 	true,  true },
    	{"test_Regex_L1_1_L2_0_R", 			"Regex",		"A[l]uno",	true, 	  false, 	true,  false },
    	{"test_Regex_L1_1_L2_1_R", 			"Regex",		"Ba*",		true, 	  false, 	true,  true },
//    	{"test_Regex_L1_1_L2_2_R", 			"Regex",		"Conta",	true, 	  false, 	true,  true },
//    	{"test_Regex_L1_2_L2_0_R", 			"Regex",		"Conto",	true, 	  false, 	true,  false },
//    	{"test_Regex_L1_2_L2_1_R", 			"Regex",		"Conta",	true, 	  false, 	true,  true },
//    	{"test_Regex_L1_2_L2_2_R", 			"Regex",		"Conta",	true, 	  false, 	true,  true },
    	
    	});
    }

	@Test
	public void test() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		ClassLoader loader = ReflectionUtils.getClassLoader(rootPath + path);
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, recursive, caseSensitive, regex, 0);
		boolean status = classAnalyzer.isError();
		
		assertEquals(testCase, expected, status);
	}
}
