package codeteacher.analyzers.build;

import codeteacher.analyzers.AbstractClassAnalyzer;
import codeteacher.analyzers.ClassAnalyzer;
import codeteacher.analyzers.FinalClassAnalyzer;
import codeteacher.analyzers.ImplementsAnalyzer;
import codeteacher.analyzers.PublicClassAnalyzer;
import codeteacher.analyzers.SuperClassAnalyzer;

public class ClassAnalyzerBuilder {

	private ClassAnalyzer classAnalyzer;
	
	public ClassAnalyzerBuilder() {
		classAnalyzer = new ClassAnalyzer();
	}

	public ClassAnalyzerBuilder(ClassLoader loader) {
		this();
		classAnalyzer.setLoader(loader);
	}
	
	public ClassAnalyzer build() {
		return classAnalyzer;
	}
	
	public ClassAnalyzerBuilder klazz(String name) {
		classAnalyzer.setMemberName(name);
		return this;
	}
	
	public ClassAnalyzerBuilder name(String name) {
		classAnalyzer.setMemberName(name);
		return this;
	}
	
	public ClassAnalyzerBuilder recursive(boolean recursive) {
		classAnalyzer.setRecursive(recursive);
		return this;
	}
	
	public MethodAnalyzerBuilder method(String name) {
		MethodAnalyzerBuilder methodAnalyzerBuilder = new MethodAnalyzerBuilder(name);
		classAnalyzer.add(methodAnalyzerBuilder.build());
		return methodAnalyzerBuilder;		
	}

	
	public ClassAnalyzerBuilder(String name, boolean recursive, boolean matchCase, boolean regex, int value) {
		classAnalyzer = new ClassAnalyzer(name, recursive, matchCase, regex, value);
	}
	
	public PublicClassAnalyzer addPublic(int value) {
		PublicClassAnalyzer publicClassAnalyzer = new PublicClassAnalyzer(classAnalyzer, value);
		classAnalyzer.add(publicClassAnalyzer);
		return publicClassAnalyzer;
	}
	
	public AbstractClassAnalyzer addAbstract(int value) {
		AbstractClassAnalyzer abstractClassAnalyzer = new AbstractClassAnalyzer(classAnalyzer, value);
		classAnalyzer.add(abstractClassAnalyzer);
		return abstractClassAnalyzer;
	}
	
	public FinalClassAnalyzer addFinal(int value) {
		FinalClassAnalyzer finalClassAnalyzer = new FinalClassAnalyzer(classAnalyzer, value);
		classAnalyzer.add(finalClassAnalyzer);
		return finalClassAnalyzer;
	}
	
	public SuperClassAnalyzer addSuperClass(String superClassName, int value) {
		SuperClassAnalyzer superClass = new SuperClassAnalyzer(classAnalyzer, superClassName, value);
		classAnalyzer.add(superClass);
		return superClass;
	}
	
	public ImplementsAnalyzer addInterface(String interfaceName, int value) {
		ImplementsAnalyzer inter = new ImplementsAnalyzer(classAnalyzer, interfaceName, value);
		classAnalyzer.add(inter);
		return inter;
	}
	
	public MethodAnalyzerBuilder addMethod(boolean declared, String returnType, String name, boolean regex, boolean matchCase, int value) {
		MethodAnalyzerBuilder methodAnalyzerBuilder = new MethodAnalyzerBuilder(name);
		methodAnalyzerBuilder.build(classAnalyzer, declared, returnType, name, regex, matchCase, value);
		classAnalyzer.add(methodAnalyzerBuilder.build());
		return methodAnalyzerBuilder;
	}
	
}
