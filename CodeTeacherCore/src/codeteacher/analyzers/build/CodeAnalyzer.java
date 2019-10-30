package codeteacher.analyzers.build;

public class CodeAnalyzer {

	private static ClassAnalyzerBuilder classBuilder;
	
	public static ClassAnalyzerBuilder load() {
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		return CodeAnalyzer.loader(loader);
	}
	
	public static ClassAnalyzerBuilder load(ClassLoader loader) {
		if (loader == null) {
			return load();
		}
		return CodeAnalyzer.loader(loader);
	}
	
	private static ClassAnalyzerBuilder loader(ClassLoader loader) {
		classBuilder = new ClassAnalyzerBuilder(loader);
		return classBuilder;
	}
	
	public static ClassAnalyzerBuilder from(ClassLoader loader) {
		return load(loader);
	}
	
	public static ClassAnalyzerBuilder checkIf() {
		
		return classBuilder;
	}
	
	public static ClassAnalyzerBuilder klazz(String className) {
		return classBuilder.klazz(className);
	}
	
	public static void main(String[] args) {
		boolean result = CodeAnalyzer.checkIf().klazz("MinhaClasse").method("meuMetodo").isPublic(10).execute();
		
	}
	
	public static boolean execute() {
		return classBuilder.build().isError();
	}
	
}
