package codeteacher.analyzers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import codeteacher.err.Error;
import codeteacher.err.OutPrintError;
import utils.ReflectionUtils;
import utils.StringUtils;

public class OutPrintAnalyzer extends SimpleAnalyzer {

	private MethodAnalyzer parent;
	private String expected;
	private String content;
	private boolean ignoreWhiteSpaces; 
	private boolean ignoreTabs; 
	private boolean ignoreExtraSpaces; 
	private boolean ignoreLinebreaks;
	
	public OutPrintAnalyzer(MethodAnalyzer parent, String expected, boolean matchCase, boolean regex, 
			boolean ignoreWhiteSpaces, boolean ignoreTabs, boolean ignoreExtraSpaces, boolean ignoreLinebraks, int value) {
		this.parent = parent;
		this.expected = expected;
		this.matchCase = matchCase;
		this.regex = regex;
		this.ignoreWhiteSpaces = ignoreWhiteSpaces;
		this.ignoreTabs = ignoreTabs;
		this.ignoreExtraSpaces = ignoreExtraSpaces;
		this.ignoreLinebreaks = ignoreLinebraks;
		this.value = value;
	}
	
	@Override
	public boolean isError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		PrintStream saidaPadrao = System.out;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		System.setOut(ps);

//		ReflectionUtils.invokeMethod(klazz, name, getArguments());
		
		Method method = parent.getMethod();
//		method.invoke(null, )

		content = new String(baos.toByteArray(), StandardCharsets.UTF_8);
		content = content.replace("\r\n", "\n");
		System.setOut(saidaPadrao);
//		expected = new String(expected.getBytes(), StandardCharsets.UTF_8);
		return !match();

	}

	private boolean match() {
		String target = expected;
		String actual = content;
		boolean match = false;
		if (isIgnoreWhiteSpaces()) {
			target = target.replaceAll(" ", "");
			actual = actual.replaceAll(" ", "");
		} else if (isIgnoreExtraSpaces()) {
			target = StringUtils.stripWhiteSpaces(target);
			actual = StringUtils.stripWhiteSpaces(actual);
		}
		if (isIgnoreLinebreaks()) {
			target = target.replaceAll("\n", "");
			actual = actual.replaceAll("\n", "");
		}
		if (isIgnoreTabs()) {
			target = target.replaceAll("\t", "");
			actual = actual.replaceAll("\t", "");
		}

		if (isRegex()) {
			Pattern pattern = Pattern.compile(target);
			Matcher m = pattern.matcher(actual);
			match = m.find();
		} else if (isMatchCase()) {
			match = actual.equals(target);
		} else {
			match = actual.equalsIgnoreCase(target);
		}
		return match;
	}

	public boolean isIgnoreWhiteSpaces() {
		return ignoreWhiteSpaces;
	}

	public void setIgnoreWhiteSpaces(boolean ignoreWhiteSpaces) {
		this.ignoreWhiteSpaces = ignoreWhiteSpaces;
	}

	public boolean isIgnoreLinebreaks() {
		return ignoreLinebreaks;
	}
	
	public boolean isIgnoreTabs() {
		return ignoreTabs;
	}
	
	public boolean isIgnoreExtraSpaces() {
		return ignoreExtraSpaces;
	}
	
	@Override
	public Error getError() {
		return new OutPrintError(content, expected, value);
	}
}
