package codeteacher.analyzers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
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

public class TestPublicClassAnalyzer {

	@Test
	public void test() throws IOException {

		String klazzName = "Car";
		final IClassWriter minhaClasse = ClassMock.of(klazzName);
		minhaClasse.visibility(VisibilityEnum.PUBLIC);

		final FileSystem fs = MemoryFileSystemBuilder.newEmpty().build();
		Path root = fs.getPath("/test");
		Files.createDirectories(root);
		Path dir = Files.createFile(root.resolve(klazzName + ".class"));
		ClassBuilder.build(minhaClasse, dir);

		VirtualClassLoader virtualLoader = new VirtualClassLoader(root);
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(virtualLoader, klazzName, true, false, false, 1);
		assertFalse(classAnalyzer.isError());

		PublicClassAnalyzer publicClassAnalyzer = new PublicClassAnalyzer(classAnalyzer, 1);
		assertEquals(1, publicClassAnalyzer.getValue());
		assertEquals("public", publicClassAnalyzer.getModifier());
		assertFalse(publicClassAnalyzer.isError());
	}

	@Test
	public void testNegative() throws IOException {

		String klazzName = "Car";
		final IClassWriter minhaClasse = ClassMock.of(klazzName);

		final FileSystem fs = MemoryFileSystemBuilder.newEmpty().build();
		Path root = fs.getPath("/test");
		Files.createDirectories(root);
		Path dir = Files.createFile(root.resolve(klazzName + ".class"));
		ClassBuilder.build(minhaClasse, dir);

		VirtualClassLoader virtualLoader = new VirtualClassLoader(root);
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(virtualLoader, klazzName, true, false, false, 1);
		assertFalse(classAnalyzer.isError());

		PublicClassAnalyzer publicClassAnalyzer = new PublicClassAnalyzer(classAnalyzer, 1);
		assertEquals(1, publicClassAnalyzer.getValue());
		assertEquals("public", publicClassAnalyzer.getModifier());
		assertFalse(publicClassAnalyzer.isError());

	}
}
