package codeteacher.analyzers;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.zip.ZipFile;

import org.junit.Test;

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;

import codeteacher.VirtualClassLoader;
import utils.UnzipUtils;

public class TestClassAnalyzerVirtual {

	@Test
	public void testExists() throws IOException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException {
		String zipFilePath = "C:\\Users\\edina\\Downloads\\IFMA_trabs\\IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_01\\Adson Gustavo da Silva Lima\\";
		String fileName = "EntendendoGetClass.zip";

		String klazzName = "MetodoGetClass";
		boolean recursive = true;
		boolean caseSensitive = false;
		boolean regex = true;
		int value = 1;

		ZipFile zipFile = new ZipFile(zipFilePath + fileName);

		final FileSystem fs = MemoryFileSystemBuilder.newEmpty().build();
		Path path = UnzipUtils.extract(zipFile, fs);

		// Create a new class loader with the directory
		VirtualClassLoader classLoader = new VirtualClassLoader(path);

		ClassAnalyzer classAnalyzer = new ClassAnalyzer(classLoader, klazzName, recursive, caseSensitive, regex, value);
		boolean error = classAnalyzer.isError();
		
		assertFalse(error);

	}
}
