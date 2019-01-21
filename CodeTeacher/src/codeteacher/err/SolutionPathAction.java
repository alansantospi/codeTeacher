package codeteacher.err;

import java.io.File;

import codeteacher.result.Performance;
import gui.Project;

public class SolutionPathAction implements Action {

	private Performance perform;
	private File solutionPath;
	
	public SolutionPathAction(Performance perform, File solutionPath) {
		super();
		this.perform = perform;
		this.solutionPath = solutionPath;
	}

	@Override
	public Performance doAct() {
		perform.setSolutionPath(solutionPath.getAbsolutePath());
		return perform;
	}
	
	@Override
	public String toString() {
		String str = "Set " + solutionPath.getName() + " as solution path";
		return str;
	}

}
