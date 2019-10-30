package codeteacher.analyzers.build;

import codeteacher.analyzers.FieldAnalyzer;
import codeteacher.analyzers.MemberAnalyzer;
import codeteacher.analyzers.PrivateFieldAnalyzer;

public class MemberAnalyzerBuilder {

	private MemberAnalyzer member;
	
	public MemberAnalyzerBuilder addPrivate() {
		member.add(new PrivateFieldAnalyzer((FieldAnalyzer) member));
		return this;
	}
	
	public MemberAnalyzerBuilder addPrivate(int value) {
		member.add(new PrivateFieldAnalyzer((FieldAnalyzer) member, value));
		return this;
	}
	
	
}
