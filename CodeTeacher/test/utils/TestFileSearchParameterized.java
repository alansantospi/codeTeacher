package utils;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TestFileSearchParameterized {
	private String testCase;
	private String fileNameToSearch;
	private boolean recursive;
	private boolean caseSensitive;
	private boolean regex;
	private int expected;
	
	public TestFileSearchParameterized(String testCase, String fileNameToSearch, boolean recursive, boolean caseSensitive, boolean regex,	int expected) {
		super();
		this.testCase = testCase;
		this.fileNameToSearch = fileNameToSearch;
		this.recursive = recursive;
		this.caseSensitive = caseSensitive;
		this.regex = regex;
		this.expected = expected;
	}

	@Parameters
    public static Collection<Object[]> data() {
    	return Arrays.asList(new Object[][] {
    	/* testCase,						 		 fileNameToSearch,    recursive, caseSensitive, regex, expected */
    	{"testExists", 					 			 "Alan", 			  false, 	  false, 		false,  1 },
    	{"testExistsRecursive", 					 "TestaInteger.java", true, 	  false, 		false, 18 },
		{"testExistsCaseSensitive", 				 "Alan", 			  false, 	  true, 		false,  1 },
		{"testExistsRegex", 				  	     "Ala{1}n",   		  false, 	  false, 		true,   1 },
		{"testExistsRecursiveCaseSensitive",		 "TestaInteger.java", true, 	  true, 		false, 17 },
		{"testExistsRecursiveRegex",		 		 "TestaInteger.java", true, 	  false, 		true,  17 },
		{"testNotExists"			,		 		 "TestaInteger.java", false, 	  false, 		false,  0 },
		{"testNotExistsRecursive", 			 		 "testaInt.java", 	  true, 	  false, 		false,  0 },
		{"testNotExistsCaseSensitive",		 		 "alan", 			  false, 	  true, 		false,  0 },
		{"testNotExistsRegex",						 "TestaInteger.java", false, 	  false, 		true,   0 },
		{"testNotExistsRecursiveCaseSensitive", 	 "Testainteger.java", true, 	  true, 		false,  0 },
		{"testNotExistsRecursiveCaseSensitiveRegex", "testaint.*.java",   true, 	  true, 		true,   0 },
		{"testNotExistsCaseSensitiveRegex", 		 "testaInt.java", 	  false, 	  true, 		true,   0 },
    	});
    }

	@Test
	public void test() {
		FileSearch fileSearch = new FileSearch();
		fileSearch.setFileNameToSearch(fileNameToSearch);
		fileSearch.setRecursive(recursive);
		fileSearch.setCaseSensitive(caseSensitive);
		fileSearch.setRegex(regex);
		
		fileSearch.searchDirectory(new File("/Users/edina/Downloads/IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_02/"),
				fileNameToSearch);
		
		List<File> result = fileSearch.getResult();
		int count = result.size();
		
		assertEquals(testCase, expected, count);
	}
}
