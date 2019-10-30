package codeteacher.analyzers;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;

import codeteacher.Config;
import codeteacher.behave.ConstructorCall;
import codeteacher.err.Action;
import codeteacher.err.ClassAction;
import codeteacher.err.Error;
import codeteacher.err.ErrorFixer;
import codeteacher.err.ErrorType;
import codeteacher.result.Performance;
import codeteacher.struct.StructAnalyzer;
import codeteacher.struct.eval.MethodEvaluator;
import utils.FileSearch;

public class Tester {

//	private static String folderName;
	private static Performance perform;
	private static Config cfg;
	
	public static void setConfig(Config config) {
		cfg = config;
	}

	public static Performance test(String folderName, Map<String, List<Analyzer>> tests, ClassLoader baseLoader, Performance p) {
		perform = p;
		return test(folderName, tests, baseLoader);
	}
	
	public static Performance test(String folderName, Map<String, List<Analyzer>> tests, ClassLoader baseLoader) {
		if (perform == null || (perform != null && !perform.getStudent().equals(folderName))) {
			perform = new Performance(folderName);
		}
			
		Map<String, List<Analyzer>> testSearch = tests;
		Set<Class<?>> loadedKlasses = loadClasses(tests, baseLoader, testSearch);

		for (Class<?> baseClazz : loadedKlasses) {
			if (baseClazz != null) {
				String klazzName = baseClazz.getName();

				String alias = perform.getAlias(klazzName);
				if (alias != null) {
					klazzName = alias;
				}

				List<Analyzer> list = tests.get(klazzName);

				for (Analyzer analyzer : list) {
					if (analyzer instanceof MethodCall) {
						// TODO Arrumar isso aqui
						ConstructorCall constructorCall = ((MethodCall) analyzer).getConstructorCall();
						constructorCall.setKlazz(baseClazz);
						constructorCall.setKlazzName(klazzName);
					} else if (analyzer instanceof OutputAnalyzer) {
						((OutputAnalyzer) analyzer).setKlazz(baseClazz);
					} else if (analyzer instanceof StructAnalyzer) {
						StructAnalyzer struct = (StructAnalyzer) analyzer;
						struct.setKlazz(baseClazz);
						List<Class<?>> paramTypesList = new ArrayList<Class<?>>();
						String[] paramTypes = struct.getParameterTypes();

						for (String paramType : paramTypes) {

							boolean isArray = paramType.contains("[]");
							if (isArray) {
								int indexOf = paramType.indexOf("[");
								paramType = paramType.substring(0, indexOf);
							}

							Class<?> paramClazz = null;
							String methodName = struct.getMethodNameWithParams();

							try {
								paramClazz = baseLoader.loadClass(paramType);
							} catch (ClassNotFoundException e) {
								// TODO Contabilizar todos os pontos envolvendo este método
								ErrorType errorType = ErrorType.PARAM_CLASS_NOT_FOUND;
								Error error = new Error(errorType,
										errorType.getMessage(methodName, klazzName, paramType));

								perform.addError(error);
							}

							if (paramClazz != null) {
								Class<?> arrayClass = Array.newInstance(paramClazz, 0).getClass();
								paramTypesList.add(arrayClass);

								MethodEvaluator evaluator = new MethodEvaluator(baseClazz, struct.getMemberName(),
										paramTypesList.toArray(new Class<?>[0]));
								struct.setEvaluator(evaluator);
							}
						}
					}
						if (analyzer.isError()) {
							Error error = analyzer.getError();
							perform.addError(error);
						}
						// Contabilizando todos os pontos envolvendo este método
						List<Analyzer> klazzList = testSearch.get(klazzName);
						int value = 0;

						if (klazzList.size() == 1) {
							int errorValue = ErrorType.METHOD_NOT_FOUND.getValue();
							value += list.get(0).getValue() * errorValue;
						} else {
							for (Analyzer ex : klazzList) {
								if (ex.getMemberName().equals(analyzer.getMemberName())) {
									value += ex.getValue();
								}
							}
						}

						ErrorType errorType = ErrorType.METHOD_NOT_FOUND;
						String methodNameWithParams = ((OutputAnalyzer) analyzer).getMethodNameWithParams();
						Error error = new Error(errorType, errorType.getMessage(methodNameWithParams, klazzName),
								value);
						perform.addError(error);
				}
			}
		}
		return perform;
	}

	private static Set<Class<?>> loadClasses(Map<String, List<Analyzer>> tests, ClassLoader baseLoader,
			Map<String, List<Analyzer>> testSearch) {
		Class<?> klazz;
		Set<Class<?>> loadedKlasses = new HashSet<Class<?>>();
		for (String klazzName : tests.keySet()) {
			String canonicalName = klazzName;
			List<Analyzer> executers = tests.get(klazzName);
			for (Analyzer ex : executers) {
				//TODO vai dar erro de cast quando for outro analyzer
				OutputAnalyzer out = (OutputAnalyzer) ex;
				boolean klazzCaseSensitive = out.isKlazzCaseSensitive();
				boolean klazzRecursive = out.isKlazzRecursive();
				boolean klazzRegex = out.isKlazzRegex();

				URL[] locations = ((URLClassLoader) baseLoader).getURLs();
				URL url = locations[0];
				String path = url.getFile();
				File src = new File(path);
				File srcAlias = src;
				String alias = perform.getAlias(klazzName);
				if (alias != null) {
					canonicalName = alias;
					if (alias.contains(".")) {
						alias = alias.substring(0, alias.lastIndexOf("."));
					}
					String separator = File.separator;
					alias = alias.replaceAll("\\.", "\\"+separator);
					srcAlias = new File(path + separator + alias);
				}
				int beginIndex = canonicalName.lastIndexOf('.')+1;
				int endIndex = canonicalName.length();
				String simpleName = canonicalName.substring(beginIndex, endIndex);
				
				FileSearch search = new FileSearch();
				search.setCaseSensitive(klazzCaseSensitive);
				search.setRecursive(klazzRecursive);
				search.setRegex(klazzRegex);
				search.searchDirectory(srcAlias, simpleName + ".class");
				List<File> candidates = search.getResult();

				if (candidates.isEmpty()) {
					ErrorType errorType = ErrorType.CLASS_NOT_FOUND;
					Error error = new Error(errorType, errorType.getMessage(klazzName));
					perform.addError(error);
					continue;
				} else if (candidates.size() > 1) {
					ErrorType errorType = ErrorType.CLASS_UNDEFINED;
					Error error = new Error(errorType, errorType.getMessage(klazzName));
					perform.addError(error);
					List<Action> actions = new ArrayList<Action>();
					ErrorFixer fix = new ErrorFixer(error, actions);
					for (File candidate : candidates) {
						canonicalName = getCanonicalName(src, candidate);
						Action act = new ClassAction(fix, perform, canonicalName, klazzName);
						actions.add(act);
					}
//					fix.getActions().addAll(actions);
					perform.addErrorFixer(error, fix);
					continue;
				} else {
					File f = candidates.get(0);
					canonicalName = getCanonicalName(src, f);
					perform.addAlias(canonicalName, klazzName);
					try {
						klazz = baseLoader.loadClass(canonicalName);
						loadedKlasses.add(klazz);
					} catch (ClassNotFoundException | NoClassDefFoundError e) {
						// Contabilizando todos os pontos envolvendo esta classe
						List<Analyzer> list = testSearch.get(klazzName);
						int value = 0;

						if (list.size() == 1) {
							int errorValue = ErrorType.CLASS_NOT_FOUND.getValue();
							value += list.get(0).getValue() * errorValue;
						} else {
							for (Analyzer exec : list) {
								value += exec.getValue();
							}
						}
						ErrorType errorType = ErrorType.CLASS_NOT_FOUND;
						Error error = new Error(errorType, errorType.getMessage(klazzName), value);

						perform.addError(error);
					}
				}
			}
		}
		return loadedKlasses;
	}

	private static String getCanonicalName(File src, File f) {
		String canonicalName = "";
		
		File parentFile = f.getParentFile();
		while (!parentFile.equals(src)) {
			canonicalName += parentFile.getName() + ".";
			parentFile = parentFile.getParentFile();
		}

		canonicalName = canonicalName + f.getName().replace(".class", "");
		return canonicalName;
	}

	// Método para avaliar comparando com um projeto template em TEACHER_DIR
	public static void testFromProject(Collection<File> baseFiles, Map<String, List<Analyzer>> tests,
			ClassLoader baseLoader) {
		for (File file : baseFiles) {
			try {
				String name = file.getName();
				String klazzName = FilenameUtils.removeExtension(name);

				if (tests.containsKey(klazzName)) {
					Class<?> baseClazz = baseLoader.loadClass(klazzName);
					List<Analyzer> list = tests.get(klazzName);

					for (Analyzer testCase : list) {
						if (testCase instanceof MethodCall) {
							// TODO Arrumar isso aqui
							((MethodCall) testCase).getConstructorCall().setKlazz(baseClazz);
						} else if (testCase instanceof OutputAnalyzer) {
							((OutputAnalyzer) testCase).setKlazz(baseClazz);
						}
						if (testCase.isError()) {

							Error error = testCase.getError();
							perform.addError(error);
						}
						// TODO Contabilizar todos os pontos envolvendo este método
					}
				}
			} catch (ClassNotFoundException e) {
				// TODO contabilizar todos os pontos que envolvem esta classe
//				e.printStackTrace();
			}
		}
	}

	public static Config getConfig() {
		return cfg;
	}
}
