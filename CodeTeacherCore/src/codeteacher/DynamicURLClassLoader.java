package codeteacher;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.checkerframework.checker.units.qual.s;

import codeteacher.err.Action;
import codeteacher.err.ClassAction;
import codeteacher.err.Error;
import codeteacher.err.ErrorFixer;
import codeteacher.result.Performance;
import utils.FileSearch;

public class DynamicURLClassLoader extends URLClassLoader {

	public DynamicURLClassLoader(URLClassLoader classLoader) {
		super(classLoader.getURLs());
	}

	public DynamicURLClassLoader(URL[] locations) {
		super(locations);
	}

	@Override
	public void addURL(URL url) {

		super.addURL(url);
	}

	public List<String> find(String name, String alias, boolean recursive, boolean regex, boolean matchCase) {
		String canonicalName = name;
		File src = null;
		File srcAlias = null;
		List<String> result = new ArrayList<>();
		List<File> candidates = new ArrayList<>();
		
		// get root directory
		URL[] locations = ((URLClassLoader) getParent()).getURLs();
		URL url = locations[0];
		String path = url.getFile();
		src = new File(path);

		// handle alias
		srcAlias = src;
//		String alias = perform.getAlias(name);

		if (alias != null) {
			canonicalName = alias;
			if (alias.contains(".")) {
				alias = alias.substring(0, alias.lastIndexOf("."));
				String separator = File.separator;
				alias = alias.replaceAll("\\.", "\\" + separator);
				srcAlias = new File(path + separator + alias);
			}
		}

		// search in file system
		String simpleName = getSimpleName(canonicalName);
		FileSearch search = new FileSearch();
		search.setCaseSensitive(matchCase);
		search.setRecursive(recursive);
		search.setRegex(regex);
		search.searchDirectory(srcAlias, simpleName + ".class");
		candidates = search.getResult();
		result = search.getResultString();
		
		return result;
	}
	
	private String getSimpleName(String canonicalName) {
		int beginIndex = canonicalName.lastIndexOf('.') + 1;
		int endIndex = canonicalName.length();
		String simpleName = canonicalName.substring(beginIndex, endIndex);
		return simpleName;
	}
	
	private File getRoot() {
		// get root directory
		URL[] locations = ((URLClassLoader) getParent()).getURLs();
		URL url = locations[0];
		String path = url.getFile();
		
		return new File(path);
	}
	
	public String getRootAsString() {
		return getRoot().toString();
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
	
	public String getPathSeparator() {
		return File.pathSeparator;
	}
	
	public String getCanonicalName(String name) {
		return getCanonicalName(this.getRoot(), new File(name));
//		return getCanonicalName(this.getRootAsString(), name, this.getPathSeparator());
	}
}