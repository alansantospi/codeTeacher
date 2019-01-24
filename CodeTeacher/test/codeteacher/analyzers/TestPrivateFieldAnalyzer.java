package codeteacher.analyzers;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TestPrivateFieldAnalyzer {

	private String testCase;
	private Class<?> klazz;
	private boolean declared;
	private String name;
	private boolean regex;
	private boolean matchCase;
	private boolean expected;

	public TestPrivateFieldAnalyzer(String testCase, Class<?> klazz, boolean declared, String name, boolean regex, boolean matchCase, boolean expected) {
		super();
		this.testCase = testCase;
		this.klazz = klazz;
		this.declared = declared;
		this.name = name;
		this.regex = regex;
		this.matchCase = matchCase;
		this.expected = expected;
	}

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{ "declared private attr.", File.class, true, "path", false, false, false},
			{ "declared private valid unique attr regex.", File.class, true, "pa*th", true, false, false},
			{ "declared private valid non unique attr regex.", File.class, true, "separator(Char)*", true, false, true},
			{ "declared private valid attr regex.", File.class, true, "p+ath", true, false, false},
			{ "declared private invalid attr regex.", File.class, true, "x+path", true, false, true},
			{ "declared private valid attr match case.", File.class, true, "path", false, true, false},
			{ "declared private invalid attr match case.", File.class, true, "Path", true, false, true},
			{ "declared non private attr.", File.class, true, "separator", false, false, true},
			{ "declared non private valid unique attr regex.", File.class, true, "sep*arator", true, false, true},
			{ "declared non private valid attr match case.", File.class, true, "separator", false, true, true},
			{ "declared non private invalid attr match case.", File.class, true, "Separator", true, false, true}
		});
	}

	@Test
	public void test_isError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		FieldAnalyzer attrAnalyzr = new FieldAnalyzer(klazz, declared, name, regex, matchCase).addPrivate();
		boolean actual = attrAnalyzr.isError();
		assertEquals(testCase, expected, actual);
	}
}
