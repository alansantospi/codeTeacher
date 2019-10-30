package codeteacher.behave;

import java.lang.reflect.InvocationTargetException;

import codeteacher.analyzers.SimpleAnalyzer;
import codeteacher.err.Error;

public class ExecutorImpl extends SimpleAnalyzer implements Executor {

	private String alias;
	private String type;
	
	public ExecutorImpl() {
		
	}
	public ExecutorImpl(String alias, String type) {
		super();
		this.alias = alias;
		this.type = type;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public boolean isError() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Error getError() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exec() {
		
		return false;
	}
	
}
