package gui.strategy;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.apache.commons.io.FileUtils;

import codeteacher.Config;
import codeteacher.analyzers.Analyzer;
import codeteacher.analyzers.Tester;
import codeteacher.err.Action;
import codeteacher.err.Error;
import codeteacher.err.ErrorFixer;
import codeteacher.err.ErrorType;
import codeteacher.err.SolutionPathAction;
import codeteacher.result.Evaluation;
import codeteacher.result.Performance;
import compile.CompileUtils;
import gui.Project;
import utils.FileSearch;
import utils.ReflectionUtils;

public class JavaStrategy extends OpenResourceStrategy {

	public JavaStrategy() {
		eval = new Evaluation();
	}

	public JavaStrategy(Evaluation eval) {
		this.eval = eval;
	}

	public Evaluation eval(Project proj) {

		Config cfg = proj.getCfg();
		Map<String, List<Analyzer>> tests = proj.getTests();

		String rootPath = cfg.getStudentDir();
		File rootDir = new File(rootPath);
		File[] files = rootDir.listFiles();

		for (File studentFolder : files) {
			String projName = cfg.getProjectName();
			String folderName = studentFolder.getName();

			Map<String, Performance> perforMap = eval.getPerforMap();
			Performance perform = perforMap.get(folderName);
			if (perform != null) {
				perform.getErrors().clear();
				perform.getErrorFixers().clear();
			} else {
				perform = new Performance(folderName);
			}

			List<File> candidates = new ArrayList<File>();
			String solutionPath = perform.getSolutionPath();// solutionPaths.get(folderName);
			String folderPath = folderName + SEPARATOR + projName + Config.SRC_FOLDER + SEPARATOR;

			if (solutionPath == null) {
				File[] subFiles = studentFolder.listFiles();
				FileSearch search = new FileSearch();
//				search.setFileNameToSearch(projName);
				search.setRecursive(proj.isRecursive());
				search.setRegex(proj.isRegex());
				search.setCaseSensitive(proj.isCaseSensitive());
				
				search.searchDirectory(studentFolder, projName);
				candidates.addAll(search.getResult());
			
				if (candidates.isEmpty()) {
					ErrorType errorType = ErrorType.PROJECT_NOT_FOUND;
					Error error = new Error(errorType, errorType.getMessage(projName));
					eval.addError(folderName, error);
					continue;
				} else {
					solutionPath = rootPath + folderPath;
					perform.setSolutionPath(solutionPath);
					if (candidates.size() == 1) {
						File file = candidates.get(0);
						projName = file.getName();
					} else {
						ErrorType errorType = ErrorType.PROJECT_UNDEFINED;
						Error error = new Error(errorType, errorType.getMessage(projName));
//					eval.addError(folderName, error);
						List<Action> actions = new ArrayList<Action>();
						for (File file : candidates) {
							Action act = new SolutionPathAction(perform, file);
							actions.add(act);
						}
						ErrorFixer fixer = new ErrorFixer(error, actions);
//					eval.addErrorFixer(folderName, fix);
						perform.addError(error);
						perform.addErrorFixer(error, fixer);
						eval.getPerforMap().put(folderName, perform);
						continue;
					}
				}
			}
//				solutionPaths.put(folderName, solutionPath);

			ClassLoader baseLoader = ReflectionUtils.getClassLoader(solutionPath);
			File srcFolder = new File(solutionPath);

			if (fileType.equals(FileType.JAVA.toString())) {
				compile(srcFolder, folderName, folderPath);
			}

			perform = Tester.test(folderName, tests, baseLoader, perform);
//			perforMap.put(folderName, perform);
			eval.getPerforMap().put(folderName, perform);
		}
		// proj.setSolutionPaths(solutionPaths);
		calcPerformance();
		return eval;
	}

	private void compile(File srcFolder, String folderName, String folderPath) {
		if (srcFolder.isDirectory()) {
			Collection<File> javaFiles = FileUtils.listFiles(srcFolder, new String[] { "java" }, true);

			eval.addStudent(folderName);
			for (File file : javaFiles) {
				String path = file.getAbsolutePath();
				int status = CompileUtils.compile(path);
				if (status != 0) {
					ErrorType errorType = ErrorType.COMPILATION_PROBLEM;
					Error error = new Error(errorType, errorType.getMessage(file.getName()));
					eval.addError(folderName, error);
				}
			}
		} else {
			ErrorType errorType = ErrorType.SRC_FOLDER_NOT_FOUND;
			Error error = new Error(errorType, errorType.getMessage(folderPath));
			eval.addError(folderName, error);
		}
	}
}
