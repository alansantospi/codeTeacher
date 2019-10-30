package utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;
import com.github.marschall.pathclassloader.PathClassLoader;
import com.sun.tools.javac.nio.JavacPathFileManager;
import com.sun.tools.javac.util.Context;

import codeteacher.IClassLoader;
import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import codeteacher.result.Performance;

/**
 * Dynamic java class compiler and executer <br>
 * Demonstrate how to compile dynamic java source code, <br>
 * instantiate instance of the class, and finally call method of the class <br>
 *
 * http://www.beyondlinux.com
 *
 * @author david 2011/07
 *
 */
public class DynamicCompiler {
	/** where shall the compiled class be saved to (should exist already) */
	private static String classOutputFolder = "C:\\Users\\edina\\Downloads";//"/classes/demo";

	public static class MyDiagnosticListener implements DiagnosticListener<JavaFileObject> {
		public void report(Diagnostic<? extends JavaFileObject> diagnostic) {

			System.out.println("Line Number->" + diagnostic.getLineNumber());
			System.out.println("code->" + diagnostic.getCode());
			System.out.println("Message->" + diagnostic.getMessage(Locale.ENGLISH));
			System.out.println("Source->" + diagnostic.getSource());
			System.out.println(" ");
		}
	}
	
	/**
	 * java File Object represents an in-memory java source file <br>
	 * so there is no need to put the source file on hard disk
	 **/
	public static class InMemoryJavaFileObject extends SimpleJavaFileObject {
		private String contents = null;
		// A byte array output stream containing the bytes that would be written to the .class file
		private ByteArrayOutputStream outputStream;

		public InMemoryJavaFileObject(String className, String contents) throws Exception {
			super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
			this.contents = contents; 
		}
		
		public InMemoryJavaFileObject(String className, String contents, URI filePath) throws Exception {
			super(filePath, Kind.SOURCE);
			this.contents = contents;
			this.outputStream = new ByteArrayOutputStream();
//			OutputStream newOutputStream = Files.newOutputStream(Paths.get(filePath));
//			byte[] byteArray = outputStream.toByteArray();
		}
		
		public InMemoryJavaFileObject(String className, String contents, String fileSystemKey) throws Exception {
//			this(className, contents);
//			URI create = URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension);
//			String uriPath = create.getPath();
//			String string = filePath.toString()  + Kind.SOURCE.extension;
			
//			super(filePath, Kind.SOURCE);
			
			super(URI.create("memory:"+ fileSystemKey +":///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
			
			this.contents = contents;
		}

		public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
			return contents;
		}
		
		//overriding this to provide our OutputStream to which the
	    // bytecode can be written.
	    @Override
	    public OutputStream openOutputStream() throws IOException {
	        return outputStream;
	    }

	    public byte[] getBytes() {
	        return outputStream.toByteArray();
	    }
	}

	/**
	 * Get a simple Java File Object ,<br>
	 * It is just for demo, content of the source code is dynamic in real use case
	 * @throws IOException 
	 */
	private static JavaFileObject getJavaFileObject(FileSystem fs) throws IOException {
		StringBuilder contents = new StringBuilder(
				"package math;" + "public class Calculator { " + "  public void testAdd() { "
						+ "    System.out.println(200+300); " + "  } " + "  public static void main(String[] args) { "
						+ "    Calculator cal = new Calculator(); " + "    cal.testAdd(); " + "  } " + "} ");
		JavaFileObject so = null;
		
		final Path dir = fs.getPath("math");
		
		Files.createDirectory(dir);
		Path resolve = dir.resolve("Calculator"); 
		URI uri = resolve.toUri();
		String scheme = uri.getScheme();
		String specificPart = uri.getSchemeSpecificPart();
		String path = uri.getPath();
		int colonIndex = specificPart.indexOf(":///");
		path = specificPart.substring(colonIndex + 3).concat(".java");
		 
		String fragment = uri.getFragment();
		String className = "math.Calculator";
		
		URI uri2 = null;
		try {
			uri2 = new URI(scheme, specificPart, fragment);
			/* A hack to set the path into the URI */
			ReflectionUtils.setValue(uri2, true, "path", path);
		} catch (URISyntaxException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			so = new InMemoryJavaFileObject(className, contents.toString(), uri2);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return so;
	}

	/** 
	 * Compile files by JavaCompiler 
	 * @param destPath 
	 * @return */
	public static Boolean compile(Iterable<? extends JavaFileObject> files, String outputFolder, Path destPath, DiagnosticListener diagnostics) {
		// get system compiler:
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		
		// for compilation diagnostic message processing on compilation WARNING/ERROR
		if (diagnostics == null) {
			diagnostics = new MyDiagnosticListener();
		}
		
		// a JavaFileManager analogous to JavacFileManager based on the use of java.nio.file.Path
		JavacPathFileManager pathFileManager = new JavacPathFileManager(new Context(), true, null);
		
		// specify classes output folder in memory
		try {
			pathFileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(destPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// specify classes output folder
		Iterable options = Arrays.asList("-d", outputFolder);
		// set compiler's classpath to be same as the runtime's		
//		Iterable options = Arrays.asList("-classpath", System.getProperty("java.class.path"));
		
		JavaCompiler.CompilationTask task = compiler.getTask(
				/*default System.err*/ null,
	            /*std file manager*/ pathFileManager,
	            /*std DiagnosticListener */  diagnostics,
	            /*compiler options*/ null,
	            /*no annotation*/  null,
				files);
		Boolean result = task.call();
		return result;
	}

	/** run class from the compiled byte code file by URLClassloader */
	public static void runIt() {
		// Create a File object on the root of the directory
		// containing the class file
		File file = new File(classOutputFolder);

		try {
			// Convert File to a URL
			URL url = file.toURL(); // file:/classes/demo
			URL[] urls = new URL[] { url };

			// Create a new class loader with the directory
			ClassLoader loader = new URLClassLoader(urls);

			// Load in the class; Class.childclass should be located in
			// the directory file:/class/demo/
			Class thisClass = loader.loadClass("math.Calculator");

			Class params[] = {};
			Object paramsObj[] = {};
			Object instance = thisClass.newInstance();
			Method thisMethod = thisClass.getDeclaredMethod("testAdd", params);

			// run the testAdd() method on the instance:
			thisMethod.invoke(instance, paramsObj);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// TODO migrar a lógica de checagem de compilação pra um CompileAnalyzer?
	public static void compile(Path root, IClassLoader loader) {
		List<Path> javaFiles = new ArrayList<>();
		if (Files.isDirectory(root)) {
			try {
				String suffix = ".java";
				javaFiles = Files.find(root, Integer.MAX_VALUE, (file, attrs) -> file.toString().endsWith(suffix))
//				.map(Object::toString)
				.collect(Collectors.toList());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			// 2.Compile your files by JavaCompiler
			String string = root.toString();
			compile(files, string, root, new MyDiagnosticListener());
//			int status = CompileUtils.compile(file);
//			if (status != 0) {
//			}
			
		} else {
//			throw exception
		}
	}
	
	private static void compile(Iterable<? extends JavaFileObject> files, String outputFolder) {
		// get system compiler:
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		// for compilation diagnostic message processing on compilation WARNING/ERROR
		MyDiagnosticListener c = new MyDiagnosticListener();
		StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(c, Locale.ENGLISH, null);
		
//		JavaFileManager fileManager = createInMemoryFileManager(standardFileManager, byteObject, path);
		
		// specify classes output folder
		Iterable options = Arrays.asList("-d", outputFolder);
		JavaCompiler.CompilationTask task = compiler.getTask(null, standardFileManager, c, options, null, files);
		Boolean result = task.call();
		if (result == true) {
			System.out.println("Succeeded");
		}
	}
	
	public static void main(String[] args) throws Exception {
		FileSystem fs = MemoryFileSystemBuilder.newEmpty().build();
		
		Path path = fs.getPath("/");
		
		// 1.Construct an in-memory java source file from your dynamic code
		JavaFileObject file = getJavaFileObject(fs);
		Iterable<? extends JavaFileObject> files = Arrays.asList(file);

		// 2.Compile your files by JavaCompiler
		compile(files, classOutputFolder, path, new MyDiagnosticListener());

		List<Path> collect = 
//				Files.find(path, Integer.MAX_VALUE, (f, attr) -> f.toString().startsWith("Calculator"))
//		.collect(Collectors.toList());
		
		Files.walk(fs.getPath("/"))
//	     .filter(Files::isRegularFile)
	     .collect(Collectors.toList());
		
		PathClassLoader loader = new PathClassLoader(path);
		Class<?> loadClass = loader.loadClass("math.Calculator");
		
		
		// 3.Load your class by URLClassLoader, then instantiate the instance, and call
		// method by reflection
		runIt();
	}
}