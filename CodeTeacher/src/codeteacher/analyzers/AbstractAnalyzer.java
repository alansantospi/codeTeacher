package codeteacher.analyzers;

public abstract class AbstractAnalyzer implements Analyzer {

	protected AbstractAnalyzer parent;
	protected Class<?> klazz;
	protected String name;
	protected boolean regex;
	protected boolean matchCase;
	protected int value;

	public void setKlazz(Class<?> klazz) {
		this.klazz = klazz;
	}
	
	public Class<?> getKlazz(){
		if (klazz == null) {
			return parent.getKlazz();
		}
		return klazz;
	}
	
	public boolean isRegex() {
		return regex;
	}
	
	public boolean isMatchCase() {
		return matchCase;
	}
	
	@Override
	public int getValue() {
		return value;
	}
	
	@Override
	public String getMemberName() {
		return name;
	}
	
	public AbstractAnalyzer getParent() {
		return parent;
	}
	
	public AbstractAnalyzer getRootParent() {
		if (parent == null) {
			return this;
		}
		return parent.getRootParent();
	}
}
