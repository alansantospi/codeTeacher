package codeteacher.analyzers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import utils.ReflectionUtils;

public class TestAbstractAnalyzer {

	@Test
	public void testGetRootParent() {
		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestAbstractAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		String klazzName = "AClass";
		boolean caseSensitive = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, recursive, caseSensitive, regex, value);
		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, true, "staticField", false, false).addFinal();
		
		assertEquals(classAnalyzer, fieldAnalyzer.getRootParent());
		
	}
}
