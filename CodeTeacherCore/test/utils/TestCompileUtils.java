package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.junit.Test;

import compile.CompileUtils;

public class TestCompileUtils {

	@Test
	public void test(){
        int result = CompileUtils.compile("test/output/TestaInteger.java");

        assertEquals("Compile result code", 0, result);
	}
	
	@Test
	public void test2() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		String conteudoDeUmaClasse = 
		        "public class UmaClasse {"
		        + "public UmaClasse() {"
		        + "System.out.println(\"Construtor de UmaClasse instanciado.\");"
		        + "}}";
		    
		    byte[] conteudoComoArrayDeBytes = conteudoDeUmaClasse.getBytes();
		    String property = System.getProperty("user.dir") + "\\test\\";
		    Path arquivo = Paths.get(property + "UmaClasse.java");
		    Path write = Files.write(arquivo, conteudoComoArrayDeBytes);
		    
		    int statusDeSaida = CompileUtils.compile(write.toString());
		    if (statusDeSaida != 0)
		      System.out.println("Erro de compilação!");
		    		
		    Class<?> classeCompilada = Class.forName("UmaClasse");
		    classeCompilada.getDeclaredConstructor().newInstance();
		    
		    assertNotNull(classeCompilada);
	}
}
