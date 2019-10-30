package gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codeteacher.Config;
import codeteacher.analyzers.Analyzer;
import gui.strategy.OpenResourceStrategy;

public class Project {
	
	private Config cfg;
	private Map<String, List<Analyzer>> tests;
	private boolean regex;
	private boolean caseSensitive;
	private boolean recursive;
	private OpenResourceStrategy strategy;
	
	public Project(Config cfg, Map<String, List<Analyzer>> tests, boolean regex, boolean caseSensitive,
			boolean recursive) {
		super();
		this.cfg = cfg;
		this.tests = tests;
		this.regex = regex;
		this.caseSensitive = caseSensitive;
		this.recursive = recursive;
	}

	public Config getCfg() {
		return cfg;
	}

	public void setCfg(Config cfg) {
		this.cfg = cfg;
	}

	public Map<String, List<Analyzer>> getTests() {
		return tests;
	}

	public void setTests(Map<String, List<Analyzer>> tests) {
		this.tests = tests;
	}

	public boolean isRegex() {
		return regex;
	}

	public void setRegex(boolean regex) {
		this.regex = regex;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public boolean isRecursive() {
		return recursive;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}

	public OpenResourceStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(OpenResourceStrategy strategy) {
		this.strategy = strategy;
	}
}
