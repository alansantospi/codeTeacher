package codeteacher.analyzers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
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
import net.sf.esfinge.classmock.imp.MethodImp;

public class TestConstructorCall {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void testWithDefaultConstructor() throws FileNotFoundException, IOException {
		String klazzName = "Carro";
		final IClassWriter minhaClasse = ClassMock.of(klazzName);
		Class<?> klazz = minhaClasse.build();
//		minhaClasse.method("setNivelCombustivel").returnTypeAsVoid().parameter("nivel", Integer.class);

		File createdFile = folder.newFile(klazz.getName() + ".class"); // TODO quando tiver pacote, criar os
																		// subdiretórios
		String dir = createdFile.getParent();

		ClassBuilder.build(minhaClasse, createdFile);

		URL url = new File(dir).toURI().toURL();
		URL[] locations = { url };
		DynamicURLClassLoader dynLoader = new DynamicURLClassLoader(locations);

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
	public void testWithNoDefaultConstructor() throws FileNotFoundException, IOException {
		String klazzName = "Carro";
		final IClassWriter minhaClasse = ClassMock.of(klazzName);

		final MethodImp reader = new MethodImp(klazzName);
		reader.returnTypeAsVoid();
		reader.parameter("param1", String.class);
		reader.parameter("param2", Integer.class);
		minhaClasse.method(reader);
		
		Class<?> klazz = minhaClasse.build();
		
		Constructor<?>[] constructors = klazz.getConstructors();
		for (Constructor<?> c : constructors) {
			System.out.println(c);
		}

		Method[] methods = klazz.getMethods();
		for (Method method : methods) {
			System.out.println(method);
		}
		
		String fullName = klazz.getName();
		String[] split = fullName.split(".");
		File newFolder = folder.newFolder(split);
		
		File createdFile = folder.newFile(fullName + ".class"); // TODO quando tiver pacote, criar os
																		// subdiretórios
		newFolder.createNewFile();
		String dir = createdFile.getParent();

		ClassBuilder.build(minhaClasse, createdFile);

		URL url = new File(dir).toURI().toURL();
		URL[] locations = { url };
		DynamicURLClassLoader dynLoader = new DynamicURLClassLoader(locations);

		Object[] arguments = { 1 };
		Object obj = null;
		ConstructorCall constructorCall1 = new ConstructorCall(klazz.getCanonicalName(), dynLoader, arguments, obj);

		assertTrue(constructorCall1.exec());

		String msg = "Construtor Carro(java.lang.Integer) não encontrado na classe Carro";
		assertEquals(msg, constructorCall1.getError().getMessage());

		String description = "Construtor não encontrado | 0 | Construtor Carro(java.lang.Integer) não encontrado na classe Carro";
		assertEquals(description, constructorCall1.getError().toString());

		int value = 0;
		assertEquals(value, constructorCall1.getError().getValue());

	}

	@Test
	public void testWithNoDefaultConstructorFail() throws FileNotFoundException, IOException {
		String klazzName = "Carro";
		final IClassWriter minhaClasse = ClassMock.of(klazzName);
		Class<?> klazz = minhaClasse.build();
		minhaClasse.method("Carro").returnTypeAsSelfType().parameter("arg", String.class);
//		minhaClasse.method("setNivelCombustivel").returnTypeAsVoid().parameter("nivel", Integer.class);

		File createdFile = folder.newFile(klazz.getName() + ".class"); // TODO quando tiver pacote, criar os
																		// subdiretórios
		String dir = createdFile.getParent();

		ClassBuilder.build(minhaClasse, createdFile);

		URL url = new File(dir).toURI().toURL();
		URL[] locations = { url };
		DynamicURLClassLoader dynLoader = new DynamicURLClassLoader(locations);

		Object[] arguments = { 1 };
		Object obj = null;
		ConstructorCall constructorCall1 = new ConstructorCall(klazz.getCanonicalName(), dynLoader, arguments, obj);

		assertTrue(constructorCall1.exec());

		String msg = "Construtor Carro(java.lang.Integer) não encontrado na classe Carro";
		assertEquals(msg, constructorCall1.getError().getMessage());

		String description = "Construtor não encontrado | 0 | Construtor Carro(java.lang.Integer) não encontrado na classe Carro";
		assertEquals(description, constructorCall1.getError().toString());

		int value = 0;
		assertEquals(value, constructorCall1.getError().getValue());

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
