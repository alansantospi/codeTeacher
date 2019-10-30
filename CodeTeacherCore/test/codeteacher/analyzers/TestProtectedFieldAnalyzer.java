package codeteacher.analyzers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import utils.ReflectionUtils;

public class TestProtectedFieldAnalyzer {
	
	@Test
	public void fieldProtected() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		String diretorio = System.getProperty("user.dir") + "\\test\\data\\TestProtectedFieldAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(diretorio);
		String klazzName = "Test";
		boolean matchCase = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, matchCase, recursive, regex, value);
		assertFalse(classAnalyzer.isError());
		
		String name = "fish";
		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, "java.lang.String", true, name, regex, matchCase);
		assertFalse(fieldAnalyzer.isError());
		
		ProtectedFieldAnalyzer protectedFieldAnalyzer = new ProtectedFieldAnalyzer(fieldAnalyzer);
		assertFalse(protectedFieldAnalyzer.isError());
		
	}
	@Test
	public void fieldPublic() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		String diretorio = System.getProperty("user.dir") + "\\test\\data\\TestProtectedFieldAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(diretorio);
		String klazzName = "Test";
		boolean matchCase = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, matchCase, recursive, regex, value);
		assertFalse(classAnalyzer.isError());
		
		String name = "apple";
		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, "java.lang.String", true, name, regex, matchCase);
		assertFalse(fieldAnalyzer.isError());
		
		ProtectedFieldAnalyzer protectedFieldAnalyzer = new ProtectedFieldAnalyzer(fieldAnalyzer);
		assertTrue(protectedFieldAnalyzer.isError());
	}
	@Test
	public void fieldPrivate() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		String diretorio = System.getProperty("user.dir") + "\\test\\data\\TestProtectedFieldAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(diretorio);
		String klazzName = "Test";
		boolean matchCase = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, matchCase, recursive, regex, value);
		assertFalse(classAnalyzer.isError());
		
		String name = "dog";
		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, "java.lang.String", true, name, regex, matchCase);
		assertFalse(fieldAnalyzer.isError());
		
		ProtectedFieldAnalyzer protectedFieldAnalyzer = new ProtectedFieldAnalyzer(fieldAnalyzer);
		assertTrue(protectedFieldAnalyzer.isError());
	}
	@Test
	public void fieldPackager() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		String diretorio = System.getProperty("user.dir") + "\\test\\data\\TestProtectedFieldAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(diretorio);
		String klazzName = "Test";
		boolean matchCase = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, matchCase, recursive, regex, value);
		assertFalse(classAnalyzer.isError());
		
		String name = "bird";
		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, "java.lang.String", true, name, regex, matchCase);
		assertFalse(fieldAnalyzer.isError());
		
		ProtectedFieldAnalyzer protectedFieldAnalyzer = new ProtectedFieldAnalyzer(fieldAnalyzer);
		assertTrue(protectedFieldAnalyzer.isError());
	}
	@Test
	public void fieldFinalProtected() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		String diretorio = System.getProperty("user.dir") + "\\test\\data\\TestProtectedFieldAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(diretorio);
		String klazzName = "Test";
		boolean matchCase = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, matchCase, recursive, regex, value);
		assertFalse(classAnalyzer.isError());
		
		String name = "file";
		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, "java.lang.String", true, name, regex, matchCase);
		assertFalse(fieldAnalyzer.isError());
		
		ProtectedFieldAnalyzer protectedFieldAnalyzer = new ProtectedFieldAnalyzer(fieldAnalyzer);
		assertFalse(protectedFieldAnalyzer.isError());
	}
	@Test
	public void fieldPublicProtected() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		String diretorio = System.getProperty("user.dir") + "\\test\\data\\TestProtectedFieldAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(diretorio);
		String klazzName = "Test";
		boolean matchCase = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, matchCase, recursive, regex, value);
		assertFalse(classAnalyzer.isError());
		
		String name = "winter";
		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, "java.lang.String", true, name, regex, matchCase);
		assertFalse(fieldAnalyzer.isError());
		
		ProtectedFieldAnalyzer protectedFieldAnalyzer = new ProtectedFieldAnalyzer(fieldAnalyzer);
		assertTrue(protectedFieldAnalyzer.isError());
	}
	
	@Test
	public void fieldFinalPrivate() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		String diretorio = System.getProperty("user.dir") + "\\test\\data\\TestProtectedFieldAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(diretorio);
		String klazzName = "Test";
		boolean matchCase = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, matchCase, recursive, regex, value);
		assertFalse(classAnalyzer.isError());
		
		String name = "call";
		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, "java.lang.String", true, name, regex, matchCase);
		assertFalse(fieldAnalyzer.isError());
		
		ProtectedFieldAnalyzer protectedFieldAnalyzer = new ProtectedFieldAnalyzer(fieldAnalyzer);
		assertTrue(protectedFieldAnalyzer.isError());
	}
}
