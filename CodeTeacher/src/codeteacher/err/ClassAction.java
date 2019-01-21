package codeteacher.err;

import codeteacher.result.Performance;

public class ClassAction implements Action {

	private String klazzName;
	private String canonicalName;
	private Performance perform;
	private ErrorFixer fix;
	
	public ClassAction(ErrorFixer fix, Performance perform, String klazzName, String canonicalName) {
		super();
		this.fix = fix;
		this.perform = perform;
		this.klazzName = klazzName;
		this.canonicalName = canonicalName;
	}

	@Override
	public Performance doAct() {
		perform.addAlias(canonicalName, klazzName);
		fix.setFixed(true);
		perform.getErrorFixers().remove(fix);
		perform.getErrors().remove(fix.getError());
		return perform;
	}
	
	@Override
	public String toString() {
		String str = "Set " + klazzName + " as class for " + canonicalName;
		return str;
	}

}
