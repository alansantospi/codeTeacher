package codeteacher.analyzers;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;

import codeteacher.VirtualClassLoader;
import codeteacher.analyzers.build.ClassAnalyzerBuilder;
import codeteacher.analyzers.build.MethodAnalyzerBuilder;

public class TestConsoleAnalyzer {
	
//	public static void main(String[] args) {
//		String[] values = new String[]{"In"+"\u0020"+"tremor day", "domina"};
//		Class<?> klazz = MinhaClasse.class;
//		String klazzName = "MinhaClasse";
//		String methodName = "main";
//		
//		byte[] bytes = SerializationUtils.serialize(klazz);
//		
//		Files.write(dir, bytes);
//		
//		new ConsoleAnalyzer(parent, inputs, value);
//	}
	
	@Test
	public void test() throws IOException {
		
		String[] values = new String[]{"In"+"\u0020"+"tremor day", "domina"};
		Class<?> klazz = MinhaClasse.class;
		String klazzName = "MinhaClasse";
		String methodName = "main";
		
		String[] paramTypes = new String[0];
		
		byte[] bytes = SerializationUtils.serialize(klazz);
		
		final FileSystem fs = MemoryFileSystemBuilder.newEmpty().build();
		Path root = fs.getPath("/test");
		Files.createDirectories(root);
		Path dir = Files.createFile(root.resolve( klazzName + ".class"));
		VirtualClassLoader virtualLoader = new VirtualClassLoader(root);
		Files.write(dir, bytes);
		
		ClassAnalyzerBuilder classBuilder = new ClassAnalyzerBuilder(virtualLoader)
				.klazz(klazzName)
				.recursive(true);
				
		MethodAnalyzerBuilder methodBuilder = classBuilder.method(methodName)
				.paramTypes(paramTypes)
				.addInputs(values, 3);

		ClassAnalyzer classAnalyzer = classBuilder.build();
		MethodAnalyzer methodAnalyzer = methodBuilder.build();
		
		assertFalse(classAnalyzer.isError());
		assertFalse(methodAnalyzer.isError());
//				.addMethod(true, "void", methodName, false, true, 2)
//			.add;
		
//		new ConsoleAnalyzer().start(klazz, methodName, values);
		
//		final FileSystem fs = MemoryFileSystemBuilder.newEmpty().build();
//		Path root = fs.getPath("/test");
//		Files.createDirectories(root);
//		Path dir = Files.createFile(root.resolve( klazzName + ".class"));
//		ClassBuilder.build(myClass, dir);
//		VirtualClassLoader virtualLoader = new VirtualClassLoader(root);
		
//		ClassLoader loader = new DynamicURLClassLoader((URLClassLoader) ClassLoader.getSystemClassLoader());
//		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, true, false, false, 1);
//		assertFalse(classAnalyzer.isError());
//		
//		AbstractClassAnalyzer abstractClassAnalyzer = new AbstractClassAnalyzer(classAnalyzer, 1);
//		assertEquals(1, abstractClassAnalyzer.getValue());
//		assertEquals("abstract", abstractClassAnalyzer.getModifier());
//		assertFalse(abstractClassAnalyzer.isError());
	}
	
	static class MinhaClasse {
		public static void main(String[] args) {

			InputStream in = System.in;
			Scanner scan = new Scanner(in);

			System.out.println("input a number");
			String s = scan.next();
			System.out.println("You typed: " + s);

			System.out.println("Please input another number");
			s = scan.next();
			System.out.println("Now, you typed: " + s);
			
			System.out.println("Input a number one more time");
			s = scan.next();
			System.out.println("Now is: " + s);

			System.out.println("Just for fun, type another number");
			s = scan.next();
			System.out.println("An error, maibe? " + s);
			
			scan.close();
		}
	}
}
