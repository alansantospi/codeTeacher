package codeteacher.analyzers;

import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import codeteacher.DynamicURLClassLoader;
import codeteacher.IClassLoader;
import codeteacher.err.Action;
import codeteacher.err.ClassAction;
import codeteacher.err.Error;
import codeteacher.err.ErrorFixer;
import codeteacher.err.ErrorType;
import codeteacher.result.Performance;
import utils.ReflectionUtils;

public class ClassAnalyzer extends CompositeAnalyzer<AbstractAnalyzer> {

	private static final long serialVersionUID = 1L;

	private ClassLoader loader;
	private String path;
	private boolean recursive;
	private Performance perform;

	public ClassAnalyzer(String klazzName, boolean recursive, boolean matchCase, boolean regex, int value) {
		this.name = klazzName;
		this.matchCase = matchCase;
		this.recursive = recursive;
		this.regex = regex;
		this.value = value;
	}

	public ClassAnalyzer(String path, String klazzName, boolean recursive, boolean caseSensitive, boolean regex,
			int value) {
		this(klazzName, recursive, caseSensitive, regex, value);
		this.path = path;
		this.loader = ReflectionUtils.getClassLoader(path);
	}

	public ClassAnalyzer(ClassLoader loader, String klazzName, boolean recursive, boolean caseSensitive, boolean regex,
			int value) {
		this(klazzName, recursive, caseSensitive, regex, value);
		this.loader = loader;
		this.name = klazzName;
	}

	public ClassAnalyzer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isError() {

		if (perform == null) {
			perform = new Performance(name);
		}

		if (loader == null) {
			loader = new DynamicURLClassLoader((URLClassLoader) ClassLoader.getSystemClassLoader());
		}

		String canonicalName = name;
		List<String> result = new ArrayList<>();
		String alias = perform.getAlias(name);

		result = ((IClassLoader) loader).find(name, alias, recursive, regex, matchCase); 
		
		if (result.isEmpty()) {
			ErrorType errorType = ErrorType.CLASS_NOT_FOUND;
			Error error = new Error(errorType, errorType.getMessage(name));
			perform.addError(error);
			
			return true;
			
		} else if (result.size() > 1) {
			ErrorType errorType = ErrorType.CLASS_UNDEFINED;
			Error error = new Error(errorType, errorType.getMessage(name));
			perform.addError(error);
			List<Action> actions = new ArrayList<Action>();
			ErrorFixer fix = new ErrorFixer(error, actions);
			for (String candidate : result) {
				canonicalName = ((IClassLoader) loader).getCanonicalName(candidate);
				Action act = new ClassAction(fix, perform, canonicalName, name);
				actions.add(act);
			}
			fix.getActions().addAll(actions);
			perform.addErrorFixer(error, fix);
			
			return true;
			
		} else {
			
			String name = result.get(0);
			canonicalName = ((IClassLoader) loader).getCanonicalName(name);
			
			perform.addAlias(canonicalName, name);
			try {
				klazz = loader.loadClass(canonicalName);
			} catch (ClassNotFoundException | NoClassDefFoundError e) {
				ErrorType errorType = ErrorType.CLASS_NOT_FOUND;
				Error error = new Error(errorType, errorType.getMessage(name), value);
				
				perform.addError(error);
				return true;
			}
		}
		return false;
	}

	public boolean isRecursive() {
		return recursive;
	}

	@Override
	public Error getError() {
		ErrorType errorType = ErrorType.CLASS_NOT_FOUND;
		Error error = new Error(errorType, errorType.getMessage(name), getValue());
		return error;
	}

	public FieldAnalyzer addField(FieldAnalyzer child) {
		add(child);
		return child;
	}
	
	public SuperClassAnalyzer addSuperClass(String superClassName, int value) {
		SuperClassAnalyzer superClass = new SuperClassAnalyzer(this, superClassName, value);
		add(superClass);
		return superClass;
	}

	public ImplementsAnalyzer addInterface(String interfaceName, int value) {
		ImplementsAnalyzer inter = new ImplementsAnalyzer(this, interfaceName, value);
		add(inter);
		return inter;
	}
	
	public PublicClassAnalyzer addPublic(int value) {
		PublicClassAnalyzer publicClassAnalyzer = new PublicClassAnalyzer(this, value);
		add(publicClassAnalyzer);
		return publicClassAnalyzer;
	}

	@Override
	public String toString() {
		return "class " + name;
	}

	public void setLoader(ClassLoader loader) {
		this.loader = loader;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassAnalyzer other = (ClassAnalyzer) obj;

		if (!getMemberName().equals(other.getMemberName())) {
			return false;
		}
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path)) {
			return false;
		}
		return true;
	}

	public void setRecursive(boolean recursive) {
		this.recursive = recursive;		
	}

}
