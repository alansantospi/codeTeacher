package codeteacher.analyzers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;

import codeteacher.ClassBuilder;
import codeteacher.VirtualClassLoader;
import net.sf.esfinge.classmock.ClassMock;
import net.sf.esfinge.classmock.api.IClassWriter;
import net.sf.esfinge.classmock.api.enums.VisibilityEnum;

public class TestPackageFieldAnalyzer {
	
	@Test
	public void test() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String klazzName = "Car";
		final IClassWriter minhaClasse = ClassMock.of(klazzName);
		String fieldName = "color";
		minhaClasse.fieldByParse("java.lang.String color");
		
		final FileSystem fs = MemoryFileSystemBuilder.newEmpty().build();
		Path root = fs.getPath("/test");
		Files.createDirectories(root);
		Path dir = Files.createFile(root.resolve( klazzName + ".class"));
		ClassBuilder.buildCustom(minhaClasse, dir);
		
		VirtualClassLoader virtualLoader = new VirtualClassLoader(root);
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(virtualLoader, klazzName, true, false, false, 1);
		assertFalse(classAnalyzer.isError());

		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, "java.lang.String", true, fieldName, false, false);
		classAnalyzer.addField(fieldAnalyzer);
		assertFalse(fieldAnalyzer.isError());
		
		PackageFieldAnalyzer packageFieldAnalyzer = new PackageFieldAnalyzer(fieldAnalyzer);
		assertFalse(packageFieldAnalyzer.isError());
	}
	
	@Test
	public void testNegative() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String klazzName = "Car";
		final IClassWriter minhaClasse = ClassMock.of(klazzName);
		String fieldName = "color";
		minhaClasse.field(fieldName, String.class).visibility(VisibilityEnum.PRIVATE);
		
		final FileSystem fs = MemoryFileSystemBuilder.newEmpty().build();
		Path root = fs.getPath("/test");
		Files.createDirectories(root);
		Path dir = Files.createFile(root.resolve( klazzName + ".class"));
		ClassBuilder.build(minhaClasse, dir);
		
		VirtualClassLoader virtualLoader = new VirtualClassLoader(root);
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(virtualLoader, klazzName, true, false, false, 1);
		assertFalse(classAnalyzer.isError());

		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, "java.lang.String", true, fieldName, false, false);
		classAnalyzer.addField(fieldAnalyzer);
		assertFalse(fieldAnalyzer.isError());
		
		PackageFieldAnalyzer packageFieldAnalyzer = new PackageFieldAnalyzer(fieldAnalyzer);
		assertTrue(packageFieldAnalyzer.isError());
		
		
	}

}
