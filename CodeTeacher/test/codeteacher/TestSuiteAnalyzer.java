package codeteacher;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import codeteacher.analyzers.TestAbstractAnalyzer;
import codeteacher.analyzers.TestClassAnalyzer;
import codeteacher.analyzers.TestClassAnalyzerParameterized;
import codeteacher.analyzers.TestFieldAnalyzer;
import codeteacher.analyzers.TestMethodAnalyzer;
import codeteacher.analyzers.TestPrivateFieldAnalyzer;
import codeteacher.analyzers.TestPublicFieldAnalyzer;
import codeteacher.analyzers.TestPublicMethodAnalyzer;
import codeteacher.analyzers.TestThrowsAnalyzer;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestAbstractAnalyzer.class,
	TestClassAnalyzer.class,
	TestClassAnalyzerParameterized.class,
	TestFieldAnalyzer.class,
	TestPrivateFieldAnalyzer.class,
	TestMethodAnalyzer.class,
	TestPublicMethodAnalyzer.class,
	TestThrowsAnalyzer.class,
	TestPublicFieldAnalyzer.class,
	
})
public class TestSuiteAnalyzer {

}
