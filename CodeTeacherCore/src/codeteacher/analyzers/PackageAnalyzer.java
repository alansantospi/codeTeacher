package codeteacher.analyzers;

import utils.ReflectionUtils;

public class PackageAnalyzer extends CompositeAnalyzer<AbstractAnalyzer>{

	private ClassLoader loader;
	private String path;
	private boolean recursive;
	
	public PackageAnalyzer(ClassLoader loader, String path, boolean recursive, boolean caseSensitive, boolean regex,
			int value) {
		this.loader = ReflectionUtils.getClassLoader(path);
		this.path = path;
		this.recursive = recursive;
		this.matchCase = caseSensitive;
		this.regex = regex;
	}
	
	@Override
	public boolean isError() {
		return loader.getResource(path) != null;
	}
}
