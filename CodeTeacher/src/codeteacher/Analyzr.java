package codeteacher;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import codeteacher.result.Evaluation;
import codeteacher.result.Performance;
import utils.ReflectionUtils;

public abstract class Analyzr {
	
	protected int criteria = 0;

	private String teatcherDir = Config.TEACHER_DIR;
	private String studentDir = Config.STUDENT_DIR;
	private String projectName = Config.PROJECT_NAME;
	private String srcFolder = Config.BIN_FOLDER;

	protected static Evaluation evalReturn = new Evaluation();
	protected static String folderName;
	
	public void setUp(String teatcherDir, String studentDir, String projectName, String srcFolder){
		this.teatcherDir = teatcherDir;
		this.studentDir = studentDir;
		this.projectName = projectName;
		this.srcFolder = srcFolder;
	}
	
	public void analyze() throws NoSuchFieldException, ClassNotFoundException {
		Collection<File> baseFiles = FileUtils.listFiles(new File(teatcherDir), new String[] { "class" }, true);
		ClassLoader baseLoader = ReflectionUtils.getClassLoader(teatcherDir);
		
		setValues();
		calcTotalCriteria(baseFiles, baseLoader);
		
		File[] files = new File(studentDir).listFiles();
		for (File studentFolder : files) {
			folderName = studentFolder.getName();
			evalReturn.addStudent(folderName);
			if (studentFolder.isDirectory()){
				File binFolder = new File(studentDir + "\\" + folderName + projectName + srcFolder);
				
				if (binFolder.isDirectory()){
					evaluate(baseFiles, baseLoader);
				} else {
					
					ErrorType errorType = ErrorType.BIN_NOT_FOUND;
					Error err = new Error(errorType, errorType.getMessage(folderName + projectName + srcFolder ));
					evalReturn.addError(folderName, err);
				}
				
			} else {
				ErrorType errorType = ErrorType.FOLDER_NOT_FOUND;
				Error err = new Error(errorType, errorType.getMessage(folderName, projectName));
				evalReturn.addError(folderName, err);
			}
		}

		Map<String, Performance> perforMap = evalReturn.getPerforMap();
		for (String a : perforMap.keySet()){
			Performance performance = perforMap.get(a);
			performance.calcHitsPercentage(criteria);
		}
		
		System.out.println(evalReturn);
	}

	public abstract void evaluate(Collection<File> baseFiles, ClassLoader baseLoader) throws NoSuchFieldException, ClassNotFoundException;

	protected abstract void calcTotalCriteria(Collection<File> baseFiles,
			ClassLoader baseLoader) throws NoSuchFieldException;

	protected abstract  void setValues() ;

}
