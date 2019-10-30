package codeteacher;

import java.io.File;

import codeteacher.err.ErrorType;

public class Config {

//	public static final String STUDENT_DIR = "C:\\Users\\User\\Google Drive\\IFMA\\BAC\\2017.1\\DSOO\\trabs"; 
	public static final String STUDENT_DIR = "C:\\Users\\User\\Google Drive\\IFMA\\BAC\\2016.2\\POO - TIF IV\\trabs";
	public static final String PROJECT_NAME = "Concessionaria"; //"EntendendoGetClass"; 
	public static final String TEACHER_DIR = "C:\\Users\\User\\workspace\\Atividade01\\bin";
	public static final String SEPARATOR = File.separator;

	
//	public static final String STUDENT_DIR = "C:\\Users\\User\\Google Drive\\IFMA\\2016.2\\POO - TIF IV\\trabs";
//	public static final String PROJECT_NAME = "\\Concessionaria";
//	public static final String TEACHER_DIR = "C:\\Users\\User\\Google Drive\\IFMA\\2016.2\\POO - TIF IV\\trabs\\Fernando\\Concessionaria\\bin";
	
	public static final String BIN_FOLDER = "\\bin";
	public static final int CRITERIA = 100;
	//TODO tirar isso daqui e jogar para uma classe de teste
	public static final String CLASS_NAME = "MetodoGetClass";
	public static final String SRC_FOLDER = "\\src";
	
	private String studentDir;
	private String projectName;
	
	public Config(String studentDir, String projectName) {
		super();
		this.studentDir = studentDir;
		this.projectName = projectName;
	}
	
	public String getStudentDir() {
		return studentDir;
	}

	public void setStudentDir(String studentDir) {
		this.studentDir = studentDir;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public static void setValues() {
		ErrorType.CLASS_NOT_FOUND.setValue(2);
		ErrorType.SUPERCLASS_NOT_FOUND.setValue(2);
		ErrorType.FIELD_NOT_FOUND.setValue(2);
		ErrorType.METHOD_NOT_FOUND.setValue(2);
		ErrorType.CONSTRUCTOR_NOT_FOUND.setValue(2);
		ErrorType.INTERFACE_NOT_IMPLEMENTED.setValue(2);
		ErrorType.BIN_NOT_FOUND.setValue(2);
		ErrorType.FOLDER_NOT_FOUND.setValue(2);
		ErrorType.METHOD_NOT_RETURN.setValue(2);
	}
	
}
