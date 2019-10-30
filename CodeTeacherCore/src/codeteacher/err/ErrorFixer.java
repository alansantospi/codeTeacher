package codeteacher.err;

import java.util.List;
import codeteacher.err.Error;
public class ErrorFixer {

	private Error error;
	private boolean fixed;
	private List<Action> actions;

	public ErrorFixer(Error error, List<Action> actions) {
		super();
		this.error = error;
		this.actions = actions;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	public Error getError() {
		return error;
	}
}
