package codeteacher.analyzers.build;

import codeteacher.analyzers.AbstractMethodAnalyzer;
import codeteacher.analyzers.ClassAnalyzer;
import codeteacher.analyzers.ConsoleAnalyzer;
import codeteacher.analyzers.FinalMethodAnalyzer;
import codeteacher.analyzers.MethodAnalyzer;
import codeteacher.analyzers.PrivateMethodAnalyzer;
import codeteacher.analyzers.ProtectedMethodAnalyzer;
import codeteacher.analyzers.PublicMethodAnalyzer;
import codeteacher.analyzers.StaticMethodAnalyzer;

public class MethodAnalyzerBuilder {
	
	private ClassAnalyzerBuilder classBuilder;

	private MethodAnalyzer methodAnalyzer;
	
	public MethodAnalyzerBuilder(String name) {
		methodAnalyzer = new MethodAnalyzer();
		methodAnalyzer.setMemberName(name);
	}
	
	public MethodAnalyzer build(ClassAnalyzer parent, boolean declared, String returnType, String name, boolean matchCase, boolean regex, int value, String... parameterTypes) {
		methodAnalyzer = new MethodAnalyzer(parent, declared, returnType, name, matchCase, regex, value, parameterTypes);
		return methodAnalyzer;
	}
	
	public MethodAnalyzerBuilder declared(boolean declared) {
		methodAnalyzer.setDeclared(declared);
		return this;
	}
	
	public MethodAnalyzerBuilder addPublic(int value) {
		methodAnalyzer.add(new PublicMethodAnalyzer(methodAnalyzer, value));
		return this;
	}
	
	public MethodAnalyzerBuilder isPublic(int value) {
		methodAnalyzer.add(new PublicMethodAnalyzer(methodAnalyzer, value));
		return this;
	}
	
	public boolean exists() {
		return methodAnalyzer.isError();
	}
	
	public MethodAnalyzerBuilder addPackage(int value) {
//		methodAnalyzer.add(new PackageMethodAnalyzer(methodAnalyzer, value));
		return this;
	}

	public MethodAnalyzerBuilder addProtected(int value) {
		methodAnalyzer.add(new ProtectedMethodAnalyzer(methodAnalyzer, value));
		return this;
	}
	
	public MethodAnalyzerBuilder addPrivate(int value) {
		methodAnalyzer.add(new PrivateMethodAnalyzer(methodAnalyzer, value));
		return this;
	}
	
	public MethodAnalyzerBuilder addAbstract(int value) {
		methodAnalyzer.add(new AbstractMethodAnalyzer(methodAnalyzer, value));
		return this;
	}
	
	public MethodAnalyzerBuilder addFinal(int value) {
		methodAnalyzer.add(new FinalMethodAnalyzer(methodAnalyzer, value));
		return this;
	}
	
	public MethodAnalyzerBuilder addStatic(int value) {
		methodAnalyzer.add(new StaticMethodAnalyzer(methodAnalyzer, value));
		return this;
	}
	
	public MethodAnalyzerBuilder addThrows(int value) {
//		methodAnalyzer.add(new ThrowsAnalyzer(methodAnalyzer, value));
		return this;
	}

	public MethodAnalyzerBuilder addInputs(String[] inputs, int value) {
		methodAnalyzer.add(new ConsoleAnalyzer(methodAnalyzer, inputs, value));
		return this;
	}
	
	public MethodAnalyzer build() {
		return methodAnalyzer;
	}
	
	public MethodAnalyzer and() {
		return methodAnalyzer;
	}
	
	public boolean execute() {
		return classBuilder.build().isError();
	}

	public MethodAnalyzerBuilder paramTypes(String[] paramTypes) {
		methodAnalyzer.setParamTypes(paramTypes);
		return this;
	}
	
	public MethodAnalyzerBuilder exceptions() {
		return this;
	}

}
