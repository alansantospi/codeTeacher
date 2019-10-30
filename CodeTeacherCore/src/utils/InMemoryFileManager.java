package utils;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

import com.github.marschall.pathclassloader.PathClassLoader;
import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.nio.JavacPathFileManager;

import utils.DynamicCompiler.InMemoryJavaFileObject;

public class InMemoryFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {
	private final Iterable<? extends JavaFileObject> files;

	private InMemoryFileManager(StandardJavaFileManager fileManager, Iterable<? extends JavaFileObject> files) {
		super(fileManager);
		this.files = files;
	}

	@Override
	public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind,
			FileObject sibling) throws IOException {
		URI uri = sibling.toUri();
		
//			String path = uri.getPath();
//			String schemeSpecificPart = uri.getSchemeSpecificPart();
		
		Path path = Paths.get(uri);
		
		Stream<? extends JavaFileObject> stream = StreamSupport.stream(files.spliterator(), false);
		List<?> collect = stream.filter( f -> {
			return f.toUri().equals(uri);	
		}).collect(Collectors.toList());
		
		if (Files.isDirectory(path)){
			Files.createDirectory(path);
		} else {
			Path path2 = Paths.get(path.toString().concat(".class"));
			
			byte[] bytes = ((InMemoryJavaFileObject) sibling).getBytes();
//				return new PathClassLoader(path2).defineClass(className, bytes, 0, bytes.length);
			
			Files.write(path2, bytes);
		}
		
		return (InMemoryJavaFileObject) sibling;
	}
//
//	@Override
//	public ClassLoader getClassLoader(Location location) {
//		System.out.println(location);
//		ClassLoader parent = super.getClassLoader(location);
//		return new PathClassLoader(Paths.get("math/"), parent);
//	}
}