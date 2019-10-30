package codeteacher.analyzers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

import org.junit.Test;

import utils.ReflectionUtils;

public class TestFinalFieldAnalyzer {
	
	@Test
	public void atributoFinal()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

		String diretorio = System.getProperty("user.dir") + "\\test\\data\\TestFinalFieldAnalyzer\\";

		ClassLoader loader = ReflectionUtils.getClassLoader(diretorio);
		String klazzName = "Aluno";
		boolean matchCase = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;

		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, matchCase, recursive, regex, value);
		assertFalse(classAnalyzer.isError());

		String name = "matricula";
		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, "int", true, name, regex,
				matchCase);
		assertFalse(fieldAnalyzer.isError());

		FinalFieldAnalyzer finalFieldAnalyzer = new FinalFieldAnalyzer(fieldAnalyzer);
		assertFalse(finalFieldAnalyzer.isError());

	}
	@Test
	public void atributoNaoFinal()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

		String diretorio = System.getProperty("user.dir") + "\\test\\data\\TestFinalFieldAnalyzer\\";

		ClassLoader loader = ReflectionUtils.getClassLoader(diretorio);
		String klazzName = "Aluno";
		boolean matchCase = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;

		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, matchCase, recursive, regex, value);
		assertFalse(classAnalyzer.isError());

		String name = "numero";
		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, "int", true, name, regex,
				matchCase);
		assertFalse(fieldAnalyzer.isError());

		FinalFieldAnalyzer finalFieldAnalyzer = new FinalFieldAnalyzer(fieldAnalyzer);
		assertTrue(finalFieldAnalyzer.isError());

	}
	@Test
	public void atributoPrivadoFinal()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

		String diretorio = System.getProperty("user.dir") + "\\test\\data\\TestFinalFieldAnalyzer\\";

		ClassLoader loader = ReflectionUtils.getClassLoader(diretorio);
		String klazzName = "TestFinalFieldAnalyzer.Aluno";
		boolean matchCase = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;

		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, matchCase, recursive, regex, value);
		assertFalse(classAnalyzer.isError());

		String name = "cpf";
		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, "int", true, name, regex,
				matchCase);
		assertFalse(fieldAnalyzer.isError());

		FinalFieldAnalyzer finalFieldAnalyzer = new FinalFieldAnalyzer(fieldAnalyzer);
		assertFalse(finalFieldAnalyzer.isError());

	}
	public void atributoPublicNaoFinal()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

		String diretorio = System.getProperty("user.dir") + "\\test\\data\\TestFinalFieldAnalyzer\\";

		ClassLoader loader = ReflectionUtils.getClassLoader(diretorio);
		String klazzName = "Aluno";
		boolean matchCase = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;

		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, matchCase, recursive, regex, value);
		assertFalse(classAnalyzer.isError());

		String name = "telefone";
		FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, "int", true, name, regex,
				matchCase);
		assertFalse(fieldAnalyzer.isError());

		FinalFieldAnalyzer finalFieldAnalyzer = new FinalFieldAnalyzer(fieldAnalyzer);
		assertFalse(finalFieldAnalyzer.isError());

	}

}

