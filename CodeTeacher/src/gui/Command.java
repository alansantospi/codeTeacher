package gui;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import codeteacher.analyzers.AbstractAnalyzer;
import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import codeteacher.result.Performance;

public class Command {

	protected AbstractAnalyzer current;
	private List<Command> child;
	private Performance perform;
	
	public Command(AbstractAnalyzer analyzer) {
		current = analyzer;
		child = new ArrayList<>();
		perform = new Performance("student");
	}
	
	public void add(Command command) {
		child.add(command);
	}
	
	public Performance execute() {
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
			for (Command command : child) {
				perform.addError(command.current.getError());
			}
			
		} else {
			for (Command command : child) {
				Performance p = command.execute();
				perform.addErrors(p.getErrors());
			}
		}
		return perform;
	}
}
