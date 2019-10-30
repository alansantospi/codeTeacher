package utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TestReflectionUtils {

	@Test
	public void testGetClassLoader_Exists() throws ClassNotFoundException {
		String dir = System.getProperty("user.dir") + "\\test\\data\\SobrepondoToString\\src";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(dir);
		Class<?> klazz = loader.loadClass("Endereco");
		assertNotNull(klazz);
	}
	
	@Test(expected = ClassNotFoundException.class)
	public void testGetClassLoader_NotExists() throws ClassNotFoundException {
		String dir = System.getProperty("user.dir") + "\\test\\data\\SobrepondoToString\\src";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(dir);
		Class<?> klazz;
		klazz = loader.loadClass("Enderec");
		assertNull(klazz);
	}
}
