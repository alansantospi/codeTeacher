package codeteacher.analyzers;

public abstract class MemberAnalyzer extends CompositeAnalyzer<ModifierAnalyzer> {
	
	protected ClassAnalyzer parent;
	protected boolean declared;
	
	public AbstractAnalyzer getParent() {
		return parent;
	}
	public void setParent(ClassAnalyzer parent) {
		this.parent = parent;
	}
	public boolean isDeclared() {
		return declared;
	}
	public void setDeclared(boolean declared) {
		this.declared = declared;
	}
}
