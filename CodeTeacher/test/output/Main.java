package output;

import codeteacher.Analyzr;
import codeteacher.StructuralAnalyzr;
import codeteacher.behave.BehaveAnalist;


public class Main {
	
	public static void main(String[] args) throws NoSuchFieldException, ClassNotFoundException {
//		System.out.print("Testando isso aqui!");
		Analyzr structuralAnalyzr = new StructuralAnalyzr();
		structuralAnalyzr.analyze();
//		BehaveAnalist.before();
		
	}
}
