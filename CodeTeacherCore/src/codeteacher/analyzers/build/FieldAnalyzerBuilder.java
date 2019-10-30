package codeteacher.analyzers.build;

import codeteacher.analyzers.ClassAnalyzer;
import codeteacher.analyzers.FieldAnalyzer;
import codeteacher.analyzers.FinalFieldAnalyzer;
import codeteacher.analyzers.PrivateFieldAnalyzer;
import codeteacher.analyzers.ProtectedFieldAnalyzer;
import codeteacher.analyzers.PublicFieldAnalyzer;
import codeteacher.analyzers.StaticFieldAnalyzer;

public class FieldAnalyzerBuilder {

	private FieldAnalyzer fieldAnalyzer; 
	
	public FieldAnalyzer build(ClassAnalyzer parent, String type, boolean declared, String name, boolean regex, boolean matchCase, int value) {
		return new FieldAnalyzer(parent, type, declared, name, regex, matchCase, value);		
	}
	
	public FieldAnalyzer addPrivate() {
		fieldAnalyzer.add(new PrivateFieldAnalyzer(fieldAnalyzer));
		return fieldAnalyzer;
	}
	
	public FieldAnalyzer addPrivate(int value) {
		fieldAnalyzer.add(new PrivateFieldAnalyzer(fieldAnalyzer, value));
		return fieldAnalyzer;
	}
	
	public FieldAnalyzer addPublic() {
		fieldAnalyzer.add(new PublicFieldAnalyzer(fieldAnalyzer));
		return fieldAnalyzer;
	}
	
	public FieldAnalyzer addPublic(int value) {
		fieldAnalyzer.add(new PublicFieldAnalyzer(fieldAnalyzer, value));
		return fieldAnalyzer;
	}
	
	public FieldAnalyzer addProtected() {
		fieldAnalyzer.add(new ProtectedFieldAnalyzer(fieldAnalyzer));
		return fieldAnalyzer;
	}
	
	public FieldAnalyzer addProtected(int value) {
		fieldAnalyzer.add(new ProtectedFieldAnalyzer(fieldAnalyzer, value));
		return fieldAnalyzer;
	}
	
	public FieldAnalyzer addStatic() {
		fieldAnalyzer.add(new StaticFieldAnalyzer(fieldAnalyzer));
		return fieldAnalyzer;
	}
	
	public FieldAnalyzer addStatic(int value) {
		fieldAnalyzer.add(new StaticFieldAnalyzer(fieldAnalyzer, value));
		return fieldAnalyzer;
	}
	
	public FieldAnalyzer addFinal() {
		fieldAnalyzer.add(new FinalFieldAnalyzer(fieldAnalyzer));
		return fieldAnalyzer;
	}
	
	public FieldAnalyzer addFinal(int value) {
		fieldAnalyzer.add(new FinalFieldAnalyzer(fieldAnalyzer, value));
		return fieldAnalyzer;
	}
}
