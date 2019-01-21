package codeteacher.analyzers;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import codeteacher.err.Action;
import codeteacher.err.ClassAction;
import codeteacher.err.Error;
import codeteacher.err.ErrorFixer;
import codeteacher.err.ErrorType;
import codeteacher.result.Performance;
import utils.FileSearch;
import utils.ReflectionUtils;

public class ClassAnalyzer extends CompositeAnalyzer<FieldAnalyzer> {

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

	@Override
	public boolean isError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

		if (perform == null) {
			perform = new Performance(name);
		}

		String canonicalName = name;

		URL[] locations = ((URLClassLoader) loader).getURLs();
		URL url = locations[0];
		String path = url.getFile();
		File src = new File(path);
		File srcAlias = src;
		String alias = perform.getAlias(name);

		if (alias != null) {
			canonicalName = alias;
			if (alias.contains(".")) {
				alias = alias.substring(0, alias.lastIndexOf("."));
				String separator = File.separator;
				alias = alias.replaceAll("\\.", "\\" + separator);
				srcAlias = new File(path + separator + alias);
			}
		}

		String simpleName = getSimpleName(canonicalName);

		FileSearch search = new FileSearch();
		search.setCaseSensitive(matchCase);
		search.setRecursive(recursive);
		search.setRegex(regex);
		search.searchDirectory(srcAlias, simpleName + ".class");
		List<File> candidates = search.getResult();

		if (candidates.isEmpty()) {
			ErrorType errorType = ErrorType.CLASS_NOT_FOUND;
			Error error = new Error(errorType, errorType.getMessage(name));
			perform.addError(error);
//			continue;

			return true;

		} else if (candidates.size() > 1) {
			ErrorType errorType = ErrorType.CLASS_UNDEFINED;
			Error error = new Error(errorType, errorType.getMessage(name));
			perform.addError(error);
			List<Action> actions = new ArrayList<Action>();
			ErrorFixer fix = new ErrorFixer(error, actions);
			for (File candidate : candidates) {
				canonicalName = getCanonicalName(src, candidate);
				Action act = new ClassAction(fix, perform, canonicalName, name);
				actions.add(act);
			}
			fix.getActions().addAll(actions);
			perform.addErrorFixer(error, fix);

			return true;
		} else {
			File f = candidates.get(0);
			canonicalName = getCanonicalName(src, f);
			perform.addAlias(canonicalName, name);
			try {
				klazz = loader.loadClass(canonicalName);
			} catch (ClassNotFoundException | NoClassDefFoundError e) {
				// Contabilizando todos os pontos envolvendo esta classe
//				List<Analyzer> list = testSearch.get(klazzName);
				int value = 0;
//
//				if (list.size() == 1) {
//					int errorValue = ErrorType.CLASS_NOT_FOUND.getValue();
//					value += list.get(0).getValue() * errorValue;
//				} else {
//					for (Analyzer exec : list) {
//						value += exec.getValue();
//					}
//				}
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

	private String getSimpleName(String canonicalName) {
		int beginIndex = canonicalName.lastIndexOf('.') + 1;
		int endIndex = canonicalName.length();
		String simpleName = canonicalName.substring(beginIndex, endIndex);
		return simpleName;
	}

	private static String getCanonicalName(File src, File f) {
		String canonicalName = "";

		File parentFile = f.getParentFile();
		while (!parentFile.equals(src)) {
			canonicalName += parentFile.getName() + ".";
			parentFile = parentFile.getParentFile();
		}

		canonicalName = canonicalName + f.getName().replace(".class", "");
		return canonicalName;
	}

	@Override
	public Error getError() {
		ErrorType errorType = ErrorType.CLASS_NOT_FOUND;
		Error error = new Error(errorType, errorType.getMessage(name), getValue());
		return error;
	}

	public void addField(FieldAnalyzer child) {
		add(child);
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

}
