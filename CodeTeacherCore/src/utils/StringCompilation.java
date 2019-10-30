package utils;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;

public class StringCompilation {

	public static void main(String[] args) throws Exception {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

		String className = "Test";

		FileSystem fs = MemoryFileSystemBuilder.newEmpty().build();
		final Path dir = fs.getPath("utils");
		Files.createDirectory(dir);
		final JavaByteObject byteObject = new JavaByteObject(className);

		StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(diagnostics, null, null);

		JavaFileManager fileManager = createFileManager(standardFileManager, byteObject);

		Iterable options = Arrays.asList("-d", "C:\\Users\\edina\\Downloads");
		

		
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null,
				getCompilationUnits(className));

		if (!task.call()) {
			diagnostics.getDiagnostics().forEach(System.out::println);
		}
		fileManager.close();

		// loading and using our compiled class
		final ClassLoader inMemoryClassLoader = createClassLoader(byteObject);
		Class<ITest> test = (Class<ITest>) inMemoryClassLoader.loadClass(className);
		ITest iTest = test.newInstance();
		iTest.doSomething();
	}

	private static JavaFileManager createFileManager(StandardJavaFileManager fileManager, JavaByteObject byteObject) {
		return new ForwardingJavaFileManager<StandardJavaFileManager>(fileManager) {
			@Override
			public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind,
					FileObject sibling) throws IOException {
				return byteObject;
			}
		};
	}
	
	private static JavaFileManager createInMemoryFileManager(StandardJavaFileManager fileManager, JavaByteObject byteObject) {
		return new ForwardingJavaFileManager<StandardJavaFileManager>(fileManager) {
			@Override
			public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind,
					FileObject sibling) throws IOException {
				URI uri = sibling.toUri();
				return byteObject;
			}
		};
	}

	private static ClassLoader createClassLoader(final JavaByteObject byteObject) {
		return new ClassLoader() {
			@Override
			public Class<?> findClass(String name) throws ClassNotFoundException {
				// no need to search class path, we already have byte code.
				byte[] bytes = byteObject.getBytes();
				return defineClass(name, bytes, 0, bytes.length);
			}
		};
	}

	public static Iterable<? extends JavaFileObject> getCompilationUnits(String className) {
		JavaStringObject stringObject = new JavaStringObject(className, getSource());
		return Arrays.asList(stringObject);
	}

	public static String getSource() {
		return "public class Test implements utils.ITest{" + "public void doSomething(){"
				+ "System.out.println(\"testing\");}}";
	}
}