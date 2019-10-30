package codeteacher.analyzers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;

import codeteacher.IClassLoader;
import codeteacher.VirtualClassLoader;
import codeteacher.err.CompilationError;
import codeteacher.err.CompositeError;
import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import utils.DynamicCompiler;
import utils.DynamicCompiler.InMemoryJavaFileObject;

@SuppressWarnings("serial")
public class CompilationAnalyzer implements Analyzer {

	private Path root; 
	private IClassLoader loader;
	private int value;
	private CompositeError error;
	
	public CompilationAnalyzer(Path dir, VirtualClassLoader classLoader) {
		this.root = dir;
		this.loader = classLoader;
		this.error = new CompositeError(ErrorType.COMPILATION_PROBLEM);
	}

	@Override
	public boolean isError() {
		return compile();
	}

	@Override
	public Error getError() {
		return error;
	}

	@Override
	public String getMemberName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public int getTotalValue() {
		return error.getTotal();
	}

	@Override
	public boolean run()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean compile() {
		List<Path> javaFiles = new ArrayList<>();
		if (Files.isDirectory(root)) {
			try {
				String suffix = ".java";
				javaFiles = Files.find(root, Integer.MAX_VALUE, (file, attrs) -> file.toString().endsWith(suffix))
				.collect(Collectors.toList());
			} catch (IOException e) {
				error.addError(new Error(ErrorType.IO, "Internal error when attempting to find java files"));
			}

			List<JavaFileObject> files = new ArrayList<>();
			for (Path path : javaFiles) {
				try { 
					
					URI filePath = path.toUri();
					String content = new String(Files.readAllBytes(Paths.get(filePath)));
					String canonicalName = loader.getCanonicalName(path.toString());
					JavaFileObject fo = new InMemoryJavaFileObject(canonicalName, content);//, filePath); 
					
					files.add(fo);
					
				} catch (Exception e) {
					error.addError(new Error(ErrorType.IO, "Could not parse to URI: ".concat(path.toString())));
				}
				
			}
			// 2.Compile your files by JavaCompiler
			String string = root.toString();
			boolean compile = DynamicCompiler.compile(files, string, root, new CustomDiagnosticListener(error));

			return !compile;
			
		} else {
			ErrorType errorType = ErrorType.SRC_FOLDER_NOT_FOUND;
			Error newError = new Error(errorType, errorType.getMessage(root.getFileName().toString()));
			error.addError(newError);
			return true;
		}
	}
	
	public class CustomDiagnosticListener implements DiagnosticListener<JavaFileObject> {
		
		private CompositeError error = new CompositeError();
		
		public CustomDiagnosticListener(CompositeError error) {
			this.error = error;
		}
		
		public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
			
			String lineNumber = String.valueOf(diagnostic.getLineNumber());
			String code = diagnostic.getCode();
			String msg = diagnostic.getMessage(Locale.getDefault());
			String src = diagnostic.getSource().getName();
			Error newError = new CompilationError(lineNumber, code, msg, src, value);
			
			error.addError(newError);
		}
		
	}
	
}
