package gui.strategy;

import java.io.File;
import java.util.List;
import java.util.Map;

import codeteacher.Config;
import codeteacher.analyzers.Analyzer;
import codeteacher.analyzers.Tester;
import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import codeteacher.result.Evaluation;
import codeteacher.result.Performance;
import gui.Project;
import utils.ReflectionUtils;

/**
 * @author Alan Santos
 * Loading from project folder
 */
public class FileStrategy extends OpenResourceStrategy {

	public Evaluation eval(Project proj) {

		Config.setValues();
		//cfg = new Config(Config.STUDENT_DIR, Config.PROJECT_NAME);

		Config cfg = proj.getCfg();
		Map<String, List<Analyzer>> tests = proj.getTests();

		String studentDir = cfg.getStudentDir();
		File[] files = new File(studentDir).listFiles();

		for (File studentFolder : files) {
			String folderName = studentFolder.getName();
			String folderPath = folderName + SEPARATOR + cfg.getProjectName() + Config.BIN_FOLDER + SEPARATOR;
			String solutionPath = studentDir + SEPARATOR + folderPath;
			File binFolder = new File(solutionPath);
			if (binFolder.isDirectory()){
				// getting the classloader according to the student directory
				ClassLoader baseLoader = ReflectionUtils.getClassLoader(solutionPath);
//				Collection<File> classFiles = FileUtils.listFiles(new File(solutionPath), new String[] { "class" }, true);
				//			List<File> classFilesList = new LinkedList<File>(classFiles);
				eval.addStudent(folderName);
				Performance perform = Tester.test(folderName, tests, baseLoader);
				eval.mergePerformance(perform);
				
			} else {

				ErrorType errorType = getErrorType();
				Error error = new Error(errorType, errorType.getMessage(folderPath));
				eval.addError(folderName, error);
			}
		}
		calcPerformance();

		return eval;
	}

	private ErrorType getErrorType() {
		return ErrorType.BIN_NOT_FOUND;
	}

}
