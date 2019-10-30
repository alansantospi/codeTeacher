package gui.strategy;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import codeteacher.Config;
import codeteacher.analyzers.Analyzer;
import codeteacher.analyzers.ClassAnalyzer;
import codeteacher.analyzers.TestSet;
import codeteacher.analyzers.Tester;
import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import codeteacher.result.Evaluation;
import codeteacher.result.Performance;
import gui.Project;
import utils.ReflectionUtils;
import utils.UnzipUtils;

public class JarStrategy extends OpenResourceStrategy {

	public Evaluation eval(Project proj) {
		
//		Config.setValues();
		//cfg = new Config(Config.STUDENT_DIR, Config.PROJECT_NAME);
		
		Config cfg = proj.getCfg();
		Map<String, List<Analyzer>> tests = proj.getTests();
		String studentDir = cfg.getStudentDir();
		File[] files = new File(studentDir).listFiles();
		
		for (File studentFolder : files) {
			String folderName = studentFolder.getName();
			
			if (studentFolder.isDirectory()){
				String projectName = cfg.getProjectName();
				String jarName = projectName + ".jar";
				String jarPath = studentFolder + SEPARATOR + projectName;
				
				Collection<File> jarFiles = FileUtils.listFiles(new File(studentFolder + ""), new String[] { "jar" }, true);
				
				boolean match = false;
				String fileName = "";

				/* Loading from jar file...*/
				for (File f : jarFiles) {
					fileName = f.getName();
					jarPath = studentFolder + SEPARATOR + fileName;
					if (proj.isRegex()){
						Pattern pattern = Pattern.compile(jarName);
						Matcher m = pattern.matcher(fileName);
						match = m.find();
						if (match){
							break;
						}
					} else if (proj.isCaseSensitive()){
						match = fileName.equals(jarName); 
					} else {
						match = fileName.equalsIgnoreCase(jarName); 
					}
				}
				
				if (match){
					String destDir = studentFolder + SEPARATOR + fileName.substring(0, fileName.lastIndexOf('.'));
					
					try {
						UnzipUtils.unzip(jarPath, destDir);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ClassLoader loader = ReflectionUtils.getClassLoader(destDir);
					
					eval.addStudent(folderName);
					
//					Map<String, List<Analyzer>> fieldTests = TestSet.getFieldTests();
//					List<ClassAnalyzer> list = fieldTests.get(klazzName);
					for (String key : tests.keySet()) {
						List<Analyzer> list = tests.get(key);
						for (Analyzer a : list) {
							if (a instanceof ClassAnalyzer) {
								((ClassAnalyzer) a).setLoader(loader);
							}
						}
					}

					Performance perform = Tester.test(folderName, tests, loader);
					
					eval.mergePerformance(perform);
				} else {
					ErrorType errorType = ErrorType.JAR_NOT_FOUND;
					Error error = new Error(errorType, errorType.getMessage(jarName));
					eval.addError(folderName, error);
				}
			}
		}
		calcPerformance();
		
		return eval;
	}
}
