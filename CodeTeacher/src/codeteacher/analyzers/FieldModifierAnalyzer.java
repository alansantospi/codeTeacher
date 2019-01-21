package codeteacher.analyzers;

import java.lang.reflect.InvocationTargetException;

public abstract class FieldModifierAnalyzer extends SimpleAnalyzer {
	
	protected FieldAnalyzer parent;
	
	public boolean checkParent()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		if (klazz == null && parent.getKlazz() != null) {
			this.setKlazz(parent.getKlazz());
			return true;
		}
		return false;
	}
	
	@Override
	public String getMemberName() {
		return parent.getMemberName();
	}
	
	@Override
	public Class<?> getKlazz() {
		return parent.getKlazz();
	}	
	
	@Override
	public AbstractAnalyzer getParent() {
		return parent;
	}
}
