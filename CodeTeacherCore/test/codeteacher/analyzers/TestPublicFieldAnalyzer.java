package codeteacher.analyzers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import utils.ReflectionUtils;

public class TestPublicFieldAnalyzer {
	
	@Test
	public void atributoPrivado() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		String diretorio = System.getProperty("user.dir") + "\\test\\data\\TestPublicFieldAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(diretorio);
		String klazzName = "Aluno";
		boolean matchCase = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, matchCase, recursive, regex, value);
		assertFalse(classAnalyzer.isError());
		
		String name = "matricula";
		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, "java.lang.String", true, name, regex, matchCase);
		assertFalse(fieldAnalyzer.isError());
		
		PublicFieldAnalyzer publicFieldAnalyzer = new PublicFieldAnalyzer(fieldAnalyzer);
		assertTrue(publicFieldAnalyzer.isError());
		
	}
	
	@Test
	public void atributoPublico() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestPublicFieldAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		String klazzName = "Aluno";
		boolean matchCase = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, matchCase, recursive, regex, value);
		assertFalse(classAnalyzer.isError());
		
		String name = "nome";
		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, "java.lang.String", true, name, regex, matchCase);
		assertFalse(fieldAnalyzer.isError());
		
		PublicFieldAnalyzer publicFieldAnalyzer = new PublicFieldAnalyzer(fieldAnalyzer);
		assertFalse(publicFieldAnalyzer.isError());
		
	}
			
	@Test
	public void atributoProtegido() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		String solutionPath = System.getProperty("user.dir") + "\\test\\data\\TestPublicFieldAnalyzer\\";
		
		ClassLoader loader = ReflectionUtils.getClassLoader(solutionPath);
		String klazzName = "Aluno";
		boolean matchCase = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, matchCase, recursive, regex, value);
		assertFalse(classAnalyzer.isError());
		
		String name = "cpf";
		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, "java.lang.String", true, name, regex, matchCase);
		assertFalse(fieldAnalyzer.isError());
		
		PublicFieldAnalyzer publicFieldAnalyzer = new PublicFieldAnalyzer(fieldAnalyzer);
		assertTrue(publicFieldAnalyzer.isError());
		
	}

}
