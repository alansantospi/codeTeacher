package gui.strategy;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;

import codeteacher.Config;
import codeteacher.analyzers.Analyzer;
import codeteacher.analyzers.Tester;
import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import codeteacher.result.Evaluation;
import codeteacher.result.Performance;
import gui.Project;
import utils.ReflectionUtils;
import utils.UnzipUtils;

public class ArchiveStrategy extends OpenResourceStrategy {

	@Override
	public Evaluation eval(Project proj) {
		
		Config cfg = proj.getCfg();
		Map<String, List<Analyzer>> tests = proj.getTests();
		String studentDir = cfg.getStudentDir();
		File[] files = new File(studentDir).listFiles();
		
		for (File studentFolder : files) {
			String folderName = studentFolder.getName();
			
			if (studentFolder.isDirectory()){
				String projectName = cfg.getProjectName();
				String zipName = projectName + ".zip";
				String path = studentFolder + SEPARATOR + projectName;
				
				Collection<File> zipFiles = FileUtils.listFiles(new File(studentFolder + ""), new String[] { "zip" }, true);
				
				boolean match = false;
				String fileName = "";

				for (File f : zipFiles) {
			       fileName = f.getName();
			       path = studentFolder + SEPARATOR + fileName;
	                
	                if (proj.isRegex()){
						Pattern pattern = Pattern.compile(zipName);
						Matcher m = pattern.matcher(fileName);
						match = m.find();
						if (match){
							break;
						}
					} else if (proj.isCaseSensitive()){
						match = fileName.equals(zipName); 
					} else {
						match = fileName.equalsIgnoreCase(zipName); 
					}
	            }
	            
	            if (match){
	            	String destDir = studentFolder + SEPARATOR + fileName.substring(0, fileName.lastIndexOf('.'));
					
					try {
						UnzipUtils.unzip(path, destDir);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ClassLoader baseLoader = ReflectionUtils.getClassLoader(destDir);
					
					eval.addStudent(folderName);
					Performance perform = Tester.test(folderName, tests, baseLoader);
					eval.mergePerformance(perform);
				} else {
					ErrorType errorType = ErrorType.FILE_NOT_FOUND;
					Error error = new Error(errorType, errorType.getMessage(zipName));
					eval.addError(folderName, error);
				}
	        }	
			        
		}
		calcPerformance();
		
		return eval;
	}
}
