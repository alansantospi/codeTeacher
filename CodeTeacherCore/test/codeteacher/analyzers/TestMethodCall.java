package codeteacher.analyzers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import codeteacher.ClassBuilder;
import codeteacher.DynamicURLClassLoader;
import codeteacher.behave.ConstructorCall;
import codeteacher.behave.Executor;
import net.sf.esfinge.classmock.ClassMock;
import net.sf.esfinge.classmock.api.IClassWriter;

public class TestMethodCall {

	@Rule
    public TemporaryFolder folder = new TemporaryFolder();
	
	@Test
	public void test() throws FileNotFoundException, IOException {
		String klazzName = "Carro"; 
		final IClassWriter minhaClasse = ClassMock.of(klazzName);
		minhaClasse.field("modelo", String.class);
		minhaClasse.field("ano", Integer.class);
		minhaClasse.field("cor", String.class);

		minhaClasse.method("getNivelCombustivel").returnType(Integer.class);
		minhaClasse.method("setNivelCombustivel").returnTypeAsVoid().parameter("nivel", Integer.class);
		Class<?> klazz = minhaClasse.build();

		File createdFile = folder.newFile(klazz.getName() + ".class"); //TODO quando tiver pacote, criar os subdiretórios
		String dir = createdFile.getParent();
		
		ClassBuilder.build(minhaClasse, createdFile);
		
		URL url = new File(dir).toURI().toURL();
		URL[] locations = {url};
		DynamicURLClassLoader dynLoader = new DynamicURLClassLoader(locations);

		ClassAnalyzer classAnalyzer = new ClassAnalyzer(dynLoader, klazzName, false, false, false, 1);
		boolean error = classAnalyzer.isError();

//		Começando a testar outra parada
		Map<String, Object> heap = new HashMap<>();
		Map<String, Executor> stack = new HashMap<>();
		String ref = "A";
		Object[] arguments = new Object[0];
		Object obj = null;
		ConstructorCall constructorCall1 = new ConstructorCall(klazz.getCanonicalName(), dynLoader, arguments, obj);
		heap.put(ref, constructorCall1);
		
		stack.put(ref, constructorCall1);
		
		for (String key : stack.keySet()) {
			Executor exe = stack.get(key);
			exe.exec();
		}
		
		constructorCall1.exec();
		Object newObj = constructorCall1.getObj();
		System.out.println(newObj);
				
		
		
	}
	
	@Test
	public void test2() {
		Map<String, Object> tests = new HashMap<>();
		String refClassName = "anInteger";
		String className = "java.lang.Integer";
		Object[] arguments = { 1 };
		Object obj = null;
		ConstructorCall constructorCall1 = new ConstructorCall(className, arguments, obj);
		tests.put(refClassName, constructorCall1);

		refClassName = "otherInteger";
		className = "java.lang.Integer";
		arguments = new Integer[] { 2 };
		obj = null;
		ConstructorCall constructorCall2 = new ConstructorCall(className, arguments, obj);
		tests.put(refClassName, constructorCall1);

		String methodName = "compareTo";
		Object output = null;
		Object methodArgs = constructorCall2;
		MethodCall methodCall = new MethodCall(constructorCall1, methodName, output, methodArgs);

		methodCall.exec(); 
		
		constructorCall1.exec();
		Object obj2 = constructorCall1.getObj();
		System.out.println(obj2);


	}
}
