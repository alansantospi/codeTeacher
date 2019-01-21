package codeteacher.analyzers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import codeteacher.err.OutputError;
import utils.ArrayUtils;
import utils.StringUtils;

public class OutputAnalyzer extends SimpleAnalyzer {

	private String content;
	private String expected;
	private Class<?> klazz;
	private String klazzName;
	private boolean klazzRegex;
	private boolean klazzRecursive;
	private boolean klazzCaseSensitive;
	private Set<String> modifiers;
	private String returnType;
	private String methodName;
	private Object[] params;
	private Object methodReturn;
	private boolean methodRegex;
	private boolean outputCaseSensitive;
	private boolean outputRegex;
	private boolean ignoreWhiteSpaces;
	private boolean ignoreLinebreaks;
	private boolean ignoreTabs;
	private boolean ignoreExtraSpaces;
	private int value;

	public OutputAnalyzer(Set<String> modifiers, String returnType, String methodName, boolean methodRegex, String expected,
			int value, Object... params) {
		super();
		this.modifiers = modifiers;
		this.returnType = returnType;
		this.methodName = methodName;
		this.methodRegex = methodRegex;
		this.expected = expected;
		this.value = value;
		this.params = params;
	}

	public void setKlazz(Class<?> klazz) {
		this.klazz = klazz;
		this.klazzName = klazz.getSimpleName();
	}

	public boolean isError()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		PrintStream saidaPadrao = System.out;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		System.setOut(ps);

		new StaticMethodCall(klazz, methodName, methodRegex, methodReturn, params).exec();

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

		if (isOutputRegex()) {
			Pattern pattern = Pattern.compile(target);
			Matcher m = pattern.matcher(actual);
			match = m.find();
		} else if (isOutputCaseSensitive()) {
			match = actual.equals(target);
		} else {
			match = actual.equalsIgnoreCase(target);
		}
		return match;
	}

	private String getMessage() {
		return "Saída do método " + getMethodNameWithParams() + ": \n" + content + " \nEsperado: \n" + expected;
	}

	public String getMemberName() {
		return methodName;
	}

	public boolean isRegex() {
		return methodRegex;
	}

	public Set<String> getModifiers() {
		return modifiers;
	}

	public String getExpected() {
		return expected;
	}

	public String getReturnType() {
		return returnType;
	}

	public Error getError() {
		return new OutputError(ErrorType.METHOD_NOT_RETURN, getMessage(), value, content, expected);
	}

	public String getParamsFormatted() {
		StringBuilder arguments = new StringBuilder();
		List<Object> asList = Arrays.asList(params);

		Iterator<Object> iterator = asList.iterator();
		arguments.append("(");
		while (iterator.hasNext()) {
			Object next = iterator.next();
			arguments.append(next.getClass().getSimpleName());
			if (iterator.hasNext()) {
				arguments.append(", ");
			}
		}
		arguments.append(")");
		return arguments.toString();
	}

	public String getMethodNameWithParams() {

		return getMemberName() + getParamsFormatted();
	}

	public void addArgs(Object arg0) {
		params = ArrayUtils.addElement(params, arg0);
	}

	public Object[] getParams() {
		return params;
	}

	public Class<?> getKlazz() {
		return klazz;
	}

	public String getKlazzName() {
		return klazzName;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		Iterator<String> iterator = modifiers.iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (next != null && !((String) next).isEmpty()) {
				sb.append(next).append(" ");
			}
		}
		sb.append(returnType).append(" ");
		sb.append(getMethodNameWithParams());
		sb.append(": Value: " + value);
//		sb.append(": Expected: " + expected);
		return sb.toString();
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

	public void setIgnoreLinebreaks(boolean ignoreLinebreaks) {
		this.ignoreLinebreaks = ignoreLinebreaks;
	}

	public boolean isIgnoreTabs() {
		return ignoreTabs;
	}

	public void setIgnoreTabs(boolean ignoreTabs) {
		this.ignoreTabs = ignoreTabs;
	}

	public boolean isIgnoreExtraSpaces() {
		return ignoreExtraSpaces;
	}

	public void setIgnoreExtraSpaces(boolean ignoreExtraSpaces) {
		this.ignoreExtraSpaces = ignoreExtraSpaces;
	}

	public boolean isOutputRegex() {
		return outputRegex;
	}

	public void setOutputRegex(boolean outputRegex) {
		this.outputRegex = outputRegex;
	}

	public boolean isOutputCaseSensitive() {
		return outputCaseSensitive;
	}

	public void setOutputCaseSensitive(boolean outputCaseSensitive) {
		this.outputCaseSensitive = outputCaseSensitive;
	}

	public boolean isKlazzRegex() {
		return klazzRegex;
	}

	public void setKlazzRegex(boolean klazzRegex) {
		this.klazzRegex = klazzRegex;
	}

	public boolean isKlazzRecursive() {
		return klazzRecursive;
	}

	public void setKlazzRecursive(boolean klazzRecursive) {
		this.klazzRecursive = klazzRecursive;
	}

	public boolean isKlazzCaseSensitive() {
		return klazzCaseSensitive;
	}

	public void setKlazzCaseSensitive(boolean klazzCaseSensitive) {
		this.klazzCaseSensitive = klazzCaseSensitive;
	}

}
