package codeteacher.analyzers;

import utils.ReflectionUtils;

/**
 * Analyzer for package-private fields.
 * In Java, package-private refers to those using no access modifier; also known as default modifier.
 * Basically, package-private are those which are not public, private, or protected. 
 * @author Alan Santos
 *
 */
public class PackageFieldAnalyzer extends FieldModifierAnalyzer {

	public PackageFieldAnalyzer(FieldAnalyzer parent) {
		super.parent = parent;
		this.parent = parent;
	}
	
	public PackageFieldAnalyzer(FieldAnalyzer fieldAnalyzer, int value) {
		this(fieldAnalyzer);
		this.value = value;
	}

	@Override
	public boolean isError() {
		
			return !ReflectionUtils.isPackagePrivate(parent.getKlazz(), parent.getMemberName(), parent.isDeclared(), parent.isRegex(), parent.isMatchCase());
	}
	
	@Override
	public String getModifier() {
		return "package";
	}
}
