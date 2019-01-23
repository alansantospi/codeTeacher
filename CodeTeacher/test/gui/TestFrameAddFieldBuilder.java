package gui;

import codeteacher.analyzers.ClassAnalyzer;
import codeteacher.analyzers.FieldAnalyzer;
import utils.ReflectionUtils;

public class TestFrameAddFieldBuilder {

	public static FieldAnalyzer build() {

		String klazzName = "AClass";
		boolean caseSensitive = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;

		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestFrameAddField\\";

		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, recursive, caseSensitive, regex, value);

		boolean declared = false;
		boolean fieldRegex = false;
		boolean fieldCase = false;
		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, "String", declared, "staticField", fieldRegex,
				fieldCase).addStatic();
		classAnalyzer.add(fieldAnalyzer);
		
		return fieldAnalyzer;
	}
	
}
