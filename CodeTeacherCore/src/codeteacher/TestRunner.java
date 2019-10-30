package codeteacher;

import java.util.ArrayList;
import java.util.List;

import codeteacher.analyzers.AbstractAnalyzer;
import codeteacher.analyzers.Analyzer;
import codeteacher.analyzers.CompositeAnalyzer;
import codeteacher.err.Error;
import codeteacher.result.Performance;

public class TestRunner {

	protected Analyzer current;
	private List<TestRunner> child;
	private Performance perform;
	
	public TestRunner() {
		current = new CompositeAnalyzer<>();
		child = new ArrayList<>();
		perform = new Performance("student");
	}
	
	public TestRunner(Analyzer analyzer) {
		current = analyzer;
		child = new ArrayList<>();
		perform = new Performance("student");
	}
	
	public void add(TestRunner command) {
		child.add(command);
	}
	
	public TestRunner buildCommand(Analyzer analyzer) {
		TestRunner slave = new TestRunner(analyzer);
		this.add(slave);
		
		if (!(analyzer instanceof CompositeAnalyzer<?>)) {
			return this;
		}
		
		CompositeAnalyzer<AbstractAnalyzer> compositeAnalyzer = (CompositeAnalyzer<AbstractAnalyzer>) analyzer;
		List<AbstractAnalyzer> children = compositeAnalyzer.getChildren();
		int childCount = children.size();

		if (childCount == 0) {
			return this;
		}

		for (int index = 0; index < childCount; index++) {
			Analyzer child = compositeAnalyzer.getChild(index);
			slave.buildCommand(child);
		}
		return this;
	}
	
	public Performance execute() {
		perform = new Performance("student");
		boolean isError = false;
//		try {
			isError = current.isError();
//		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
//				| NoSuchMethodException e) {
//			e.printStackTrace();
			// TODO Auto-generated catch block
//			ErrorType errorType = ErrorType.PARAM_CLASS_NOT_FOUND;
//			Error error = new Error(errorType);
//			perform.addError(error);
//		}
		if (isError) {
//			perform.addError(current.getError());
//			for (TestRunner command : child) {
//				perform.addError(command.current.getError());
//			}
			perform.addErrors(getErrors(this));
			
		} else {
			for (TestRunner command : child) {
				Performance p = command.execute();
				perform.addErrors(p.getErrors());
			}
		}
		return perform;
	}
	
	public List<Error> getErrors(TestRunner command) {
		List<Error> errors = new ArrayList<>(); 
		errors.add(command.current.getError());
		command.child.stream().forEach(child -> {
			errors.addAll(getErrors(child));
		});
		return errors;
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
