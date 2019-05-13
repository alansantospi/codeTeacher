package codeteacher.analyzers;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.CodeSource;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.python.objectweb.asm.util.ASMifier;

import compile.CompileUtils;
import net.sf.esfinge.classmock.ClassMock;
import net.sf.esfinge.classmock.MockClassLoader;
import net.sf.esfinge.classmock.api.IClassWriter;
import utils.ReflectionUtils;

public class TestOutPrintAnalyzer {

	private ClassLoader loader;
	private String path;

	@Before
	public void setUp() {
		
		final IClassWriter minhaClasse = ClassMock.of("Cachorro");
        minhaClasse.field("nome", String.class);
        minhaClasse.field("idade", Integer.class);
        minhaClasse.field("cor", String.class);
        
        minhaClasse.method("meuMetodo").returnType(String.class);
        minhaClasse.method("latir").returnTypeAsVoid();
        
		Class<?> klazz = minhaClasse.build(); 
//		String genericString = mock.asClass().toString();
//		
//		MockClassLoader.getInstance().defineClass
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		
		writer.toByteArray();
		
		
		ASMifier as = new ASMifier();
		path = System.getProperty("user.dir") + "\\test\\data\\TestProtectedFieldAnalyzer3\\";
		new File(path).mkdirs();
		CompileUtils.compile(path);
		loader = ReflectionUtils.getClassLoader(path);
	}
	
	@After
	public void clean() throws IOException {
		FileUtils.deleteDirectory(new File(path));
	}
	
	@Test
	public void fieldProtected() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
	
		String klazzName = "MinhaClasse";
		boolean matchCase = false;
		boolean recursive = false;
		boolean regex = false;
		int value = 1;
		
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, matchCase, recursive, regex, value);
		assertFalse(classAnalyzer.isError());
		
		String name = "meuMetodo";
		MethodAnalyzer methodAnalyzer = new MethodAnalyzer(classAnalyzer, true, "java.lang.String", name, regex, matchCase, value);
		assertFalse(methodAnalyzer.isError());
		
		String expected = "Abacaxi com Limão";
		matchCase = false;
		boolean ignoreWhiteSpaces = false;
		boolean ignoreTabs = false;
		boolean ignoreLinebraks = false;
		boolean ignoreExtraSpaces = false;
		OutPrintAnalyzer outPrintAnalyzer = new OutPrintAnalyzer(methodAnalyzer, expected, matchCase, regex, ignoreWhiteSpaces, ignoreTabs, ignoreExtraSpaces, ignoreLinebraks, value);
		
		assertFalse(outPrintAnalyzer.isError());
		
	}
	
}
