package codeteacher.analyzers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.alee.utils.CollectionUtils;

import codeteacher.err.Error;

public class CompositeAnalyzer<T extends AbstractAnalyzer> extends AbstractAnalyzer {
	
	protected List<T> analyzers = new ArrayList<T>();
	
	public void add(T a) {
		analyzers.add(a);		
	}

	public void remove(int index) {
		analyzers.remove(index);		
	}

	public Analyzer getChild(int i) {
		return analyzers.get(i);
	}
	
	public List<T> getChildren(){
		return analyzers;
	}
	
	@Override
	public int getTotalValue() {
		int totalValue = getValue();
		for (Analyzer analyzer : analyzers) {
			totalValue += analyzer.getTotalValue();
		}
		return totalValue;
	}

	@Override
	public boolean isError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		return false;
	}

	@Override
	public int getValue() {
		return 0;
	}
	
	public boolean run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		boolean isError = this.isError();
		
		if (isError) {
			for (AbstractAnalyzer analyzer : analyzers) {
				
			}
		} else {
			for (AbstractAnalyzer analyzer : analyzers) {
				analyzer.setKlazz(klazz);
				if (analyzer.run()) {
					return true;
				}
			}
		}
		return isError;
	}

	@Override
	public Error getError() {
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((analyzers == null) ? 0 : analyzers.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompositeAnalyzer<?> other = (CompositeAnalyzer<?>) obj;
		if (analyzers == null) {
			if (other.analyzers != null)
				return false;
		} else if (!CollectionUtils.areEqual(getChildren(), other.getChildren())) {
			return false;
		}
		return true;
	}
	
	
}
