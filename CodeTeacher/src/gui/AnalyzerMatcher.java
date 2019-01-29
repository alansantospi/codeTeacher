package gui;

import java.util.function.BiPredicate;

import codeteacher.analyzers.AbstractAnalyzer;
import codeteacher.analyzers.FieldAnalyzer;
import codeteacher.analyzers.FieldModifierAnalyzer;
import codeteacher.analyzers.MethodAnalyzer;
import codeteacher.analyzers.MethodModifierAnalyzer;

public class AnalyzerMatcher {

	public static BiPredicate<Object, String> create() {
		return (userObject, textToFilter) -> {
			if (userObject instanceof AbstractAnalyzer) {
				AbstractAnalyzer aa = (AbstractAnalyzer) userObject;

				Class<? extends AbstractAnalyzer> class1 = aa.getClass();
				if (FieldModifierAnalyzer.class.isAssignableFrom(class1)) {
					FieldModifierAnalyzer fma = (FieldModifierAnalyzer) userObject;
					return fma.toString().toLowerCase().contains(textToFilter);
				}

				if (FieldAnalyzer.class.isAssignableFrom(class1)) {
					FieldAnalyzer fa = (FieldAnalyzer) userObject;
					return fa.getMemberName().toLowerCase().contains(textToFilter)
							|| fa.getType().toLowerCase().contains(textToFilter);
				}
				
				if (MethodAnalyzer.class.isAssignableFrom(class1)) {
					MethodAnalyzer ma = (MethodAnalyzer) userObject;
					return ma.getMemberName().toLowerCase().contains(textToFilter)
							|| ma.getReturnType().toLowerCase().contains(textToFilter);
				}
				
				if (MethodModifierAnalyzer.class.isAssignableFrom(class1)) {
					MethodModifierAnalyzer mma = (MethodModifierAnalyzer) userObject;
					return mma.getModifier().toLowerCase().contains(textToFilter);
				}
				return aa.getMemberName().toLowerCase().contains(textToFilter);

			} else {
				return userObject.toString().toLowerCase().contains(textToFilter);
			}
		};
	}
}
