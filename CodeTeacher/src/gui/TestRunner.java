package gui;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import codeteacher.analyzers.AbstractAnalyzer;
import codeteacher.analyzers.Analyzer;
import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import codeteacher.result.Performance;

public class TestRunner {

	protected AbstractAnalyzer current;
	private List<TestRunner> child;
	private Performance perform;
	
	public TestRunner(AbstractAnalyzer analyzer) {
		current = analyzer;
		child = new ArrayList<>();
		perform = new Performance("student");
	}
	
	public void add(TestRunner command) {
		child.add(command);
	}
	
	public Performance execute() {
		perform = new Performance("student");
		boolean isError = false;
		try {
			isError = current.isError();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			ErrorType errorType = ErrorType.PARAM_CLASS_NOT_FOUND;
			Error error = new Error(errorType);
			perform.addError(error);
		}
		if (isError) {
			perform.addError(current.getError());
			for (TestRunner command : child) {
				perform.addError(command.current.getError());
			}
			
		} else {
			for (TestRunner command : child) {
				Performance p = command.execute();
				perform.addErrors(p.getErrors());
			}
		}
		return perform;
	}
	
	public List<Analyzer> getTests() {
		List<Analyzer> tests = new ArrayList<>();
		tests.add(current);
		for (TestRunner runner : child) {
//			AbstractAnalyzer curr = runner.current;
			tests.addAll(runner.getTests());
		}
		return tests;
	}
}
