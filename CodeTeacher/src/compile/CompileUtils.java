package compile;

import java.io.File;
import java.util.Collection;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.apache.commons.io.FileUtils;

public class CompileUtils {
	
	public static int compile(String path) {
		return compile(new File(path));
	}
	
    public static int compile(File file) {
    	JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		String path = file.getAbsolutePath();
		int status = compiler.run(null, null, null, path);
		
		return status;		
    }
    
    private static void compileAll(String path, boolean recursive) {
    	compileAll(new File(path), recursive);
    }
    
    private static void compileAll(File src, boolean recursive) {
		if (src.isDirectory()) {
			Collection<File> javaFiles = FileUtils.listFiles(src, new String[] { "java" }, true);

			for (File file : javaFiles) {
				String path = file.getAbsolutePath();
				int status = CompileUtils.compile(path);
				if (status != 0) {
					System.out.println("Could not compile " + path);
				}
				if (file.isDirectory() && recursive) {
					compileAll(file, recursive);
				}
			}
		} else {
			System.out.println(src.getAbsoluteFile() + " is not a directory!");
		}
	}
    
    public static void main(String[] args) throws ClassNotFoundException {
		
    	
    	String dir = System.getProperty("user.dir") + "\\test\\data\\TestPublicFieldAnalyzer\\";
//		String path = dir + "Endereco.java";
		compileAll(dir, true);
		
//		ReflectionUtils.getClassLoader(dir).loadClass("Endereco");
		
		
		
//		
		
	}
}
