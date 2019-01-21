package codeteacher.analyzers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import utils.ReflectionUtils;

public class TestClassAnalyzer {

	@Test
	public void testExists() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestClassAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		String klazzName = "Aluno";
		boolean caseSensitive = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, caseSensitive, recursive, regex, value);
		assertFalse(classAnalyzer.isError());
	}
	
	@Test
	public void testNotExists() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestClassAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		String klazzName = "Aluna";
		boolean caseSensitive = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, recursive, caseSensitive, regex, value);
		assertTrue(classAnalyzer.isError());
	}
	
	@Test
	public void testFieldExists() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestClassAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		String klazzName = "Aluno";
		boolean caseSensitive = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, recursive, caseSensitive, regex, value);
		assertFalse(classAnalyzer.isError());

		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, true, "nome", false, false);
		assertFalse(fieldAnalyzer.isError());

		classAnalyzer.add(fieldAnalyzer);
		boolean status = classAnalyzer.run();
		assertFalse(status);
	}
	
	@Test
	public void testFieldNotExists() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestClassAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		String klazzName = "Aluno";
		boolean caseSensitive = false;
		boolean recursive = true;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, recursive, caseSensitive, regex, value);
		assertFalse(classAnalyzer.isError());

		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, true, "cep", false, false);
		assertTrue(fieldAnalyzer.isError());
//		
		classAnalyzer.add(fieldAnalyzer);
		boolean status = classAnalyzer.run();
		assertTrue(status);
	}
	
	@Test
	public void testPrivateFieldExists() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestClassAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		String klazzName = "AClass";
		boolean caseSensitive = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, recursive, caseSensitive, regex, value);
		assertFalse(classAnalyzer.isError());

		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, true, "privateField", false, false).addPrivate();
		assertFalse(fieldAnalyzer.isError());

		classAnalyzer.add(fieldAnalyzer);
		boolean status = classAnalyzer.run();
		assertFalse(status);
	}
	
	@Test
	public void testPrivateFieldNotExists() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestClassAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		String klazzName = "AClass";
		boolean caseSensitive = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, recursive, caseSensitive, regex, value);
		assertFalse(classAnalyzer.isError());

		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, true, "publicField", false, false);
		assertFalse(fieldAnalyzer.isError());
		
		fieldAnalyzer.addPrivate();
		assertFalse(fieldAnalyzer.isError());
		assertTrue(fieldAnalyzer.run());

		classAnalyzer.add(fieldAnalyzer);
		boolean status = classAnalyzer.run();
		assertTrue(status);
	}
	
	@Test
	public void testProtectedFieldExists() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestClassAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		String klazzName = "AClass";
		boolean caseSensitive = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, recursive, caseSensitive, regex, value);
		assertFalse(classAnalyzer.isError());

		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer.getKlazz(), true, "protectedField", false, false).addProtected();
		assertFalse(fieldAnalyzer.isError());

		classAnalyzer.add(fieldAnalyzer);
		boolean status = classAnalyzer.run();
		assertFalse(status);
	}

	@Test
	public void testProtectedFieldNotExists() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestClassAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		String klazzName = "AClass";
		boolean caseSensitive = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, recursive, caseSensitive, regex, value);
		assertFalse(classAnalyzer.isError());

		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer.getKlazz(), true, "publicField", false, false).addProtected();
		assertTrue(fieldAnalyzer.isError());

		classAnalyzer.add(fieldAnalyzer);
		boolean status = classAnalyzer.run();
		assertTrue(status);
	}
	
	@Test
	public void testPublicFieldExists() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestClassAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		String klazzName = "AClass";
		boolean caseSensitive = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, recursive, caseSensitive, regex, value);
		assertFalse(classAnalyzer.isError());

		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, true, "publicField", false, false).addPublic();
		assertFalse(fieldAnalyzer.isError());

		classAnalyzer.add(fieldAnalyzer);
		boolean status = classAnalyzer.run();
		assertFalse(status);
	}
	
	@Test
	public void testPublicFieldNotExists() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestClassAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		String klazzName = "AClass";
		boolean caseSensitive = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, recursive, caseSensitive, regex, value);
		assertFalse(classAnalyzer.isError());

		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer.getKlazz(), true, "privateField", false, false).addPublic();
		assertTrue(fieldAnalyzer.isError());

		classAnalyzer.add(fieldAnalyzer);
		boolean status = classAnalyzer.run();
		assertTrue(status);
	}
	
	@Test
	public void testStaticFieldExists() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestClassAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		String klazzName = "AClass";
		boolean caseSensitive = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, recursive, caseSensitive, regex, value);
		assertFalse(classAnalyzer.isError());

		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer.getKlazz(), true, "staticField", false, false).addStatic();
		assertFalse(fieldAnalyzer.isError());

		classAnalyzer.add(fieldAnalyzer);
		boolean status = classAnalyzer.run();
		assertFalse(status);
	}

	@Test
	public void testStaticFieldNotExists() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestClassAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		String klazzName = "AClass";
		boolean caseSensitive = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, recursive, caseSensitive, regex, value);
		assertFalse(classAnalyzer.isError());

		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer.getKlazz(), true, "finalField", false, false).addStatic();
		assertTrue(fieldAnalyzer.isError());

		classAnalyzer.add(fieldAnalyzer);
		boolean status = classAnalyzer.run();
		assertTrue(status);
	}

	@Test
	public void testFinalFieldExists() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestClassAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		String klazzName = "AClass";
		boolean caseSensitive = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, recursive, caseSensitive, regex, value);
		assertFalse(classAnalyzer.isError());

		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, true, "finalField", false, false).addFinal();
		assertFalse(fieldAnalyzer.isError());

		classAnalyzer.add(fieldAnalyzer);
		boolean status = classAnalyzer.run();
		assertFalse(status);
	}
	
	@Test
	public void testFinalFieldNotExists() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestClassAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		String klazzName = "AClass";
		boolean caseSensitive = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, recursive, caseSensitive, regex, value);
		assertFalse(classAnalyzer.isError());

		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, true, "staticField", false, false).addFinal();
		assertTrue(fieldAnalyzer.isError());

		classAnalyzer.add(fieldAnalyzer);
		boolean status = classAnalyzer.run();
		assertTrue(status);
	}
	
	@Test
	public void testPrivateFinalFieldExists() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestClassAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		String klazzName = "AClass";
		boolean caseSensitive = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, recursive, caseSensitive, regex, value);
		assertFalse(classAnalyzer.isError());

		FieldAnalyzer privateFieldAnalyzer = new FieldAnalyzer(classAnalyzer, true, "finalField", false, false).addPrivate();
		assertFalse(privateFieldAnalyzer.isError());
		
		FieldAnalyzer finalFieldAnalyzer = new FieldAnalyzer(classAnalyzer.getKlazz(), true, "finalField", false, false).addFinal();
		assertFalse(finalFieldAnalyzer.isError());

		classAnalyzer.add(privateFieldAnalyzer);
		classAnalyzer.add(finalFieldAnalyzer);
		boolean status = classAnalyzer.run();
		assertFalse(status);
	}
	
	@Test
	public void testAllExists() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestClassAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		String klazzName = "AClass";
		boolean caseSensitive = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, recursive, caseSensitive, regex, value);
		assertFalse(classAnalyzer.isError());

		FieldAnalyzer privateFieldAnalyzer = new FieldAnalyzer(classAnalyzer, true, "privateField", false, false).addPrivate();
		assertFalse(privateFieldAnalyzer.isError());
		
		FieldAnalyzer protectedFieldAnalyzer = new FieldAnalyzer(classAnalyzer, true, "protectedField", false, false).addProtected();
		assertFalse(protectedFieldAnalyzer.isError());
		
		FieldAnalyzer publicFieldAnalyzer = new FieldAnalyzer(classAnalyzer, true, "publicField", false, false).addPublic();
		assertFalse(publicFieldAnalyzer.isError());
		
		FieldAnalyzer finalFieldAnalyzer = new FieldAnalyzer(classAnalyzer, true, "finalField", false, false).addFinal();
		assertFalse(finalFieldAnalyzer.isError());
		
		FieldAnalyzer staticFieldAnalyzer = new FieldAnalyzer(classAnalyzer, true, "staticField", false, false).addStatic();
		assertFalse(staticFieldAnalyzer.isError());

		classAnalyzer.add(privateFieldAnalyzer);
		classAnalyzer.add(protectedFieldAnalyzer);
		classAnalyzer.add(publicFieldAnalyzer);
		classAnalyzer.add(staticFieldAnalyzer);
		classAnalyzer.add(finalFieldAnalyzer);
		boolean status = classAnalyzer.run();
		assertFalse(status);
	}
}
