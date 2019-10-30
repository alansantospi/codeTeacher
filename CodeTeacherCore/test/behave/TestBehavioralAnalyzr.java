package behave;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import codeteacher.Config;
import codeteacher.DynamicURLClassLoader;
import codeteacher.analyzers.Analyzer;
import codeteacher.analyzers.ClassAnalyzer;
import codeteacher.analyzers.MethodCall;
import codeteacher.behave.ConstructorCall;
import codeteacher.result.Evaluation;
import net.sf.esfinge.classmock.ClassMock;
import net.sf.esfinge.classmock.api.IClassWriter;
import net.sf.esfinge.classmock.parse.ParseASM;

public class TestBehavioralAnalyzr {

	private static Map<String, List<Analyzer>> tests = new HashMap<String, List<Analyzer>>();

	private static Evaluation evalReturn = new Evaluation();

	public static void addTest(String key, Analyzer... methodCall) {
		if (!tests.containsKey(key)) {
			tests.put(key, new ArrayList<Analyzer>());
		}
		tests.get(key).addAll(Arrays.asList(methodCall));
	}

	public static void before() {
//		testConstructor(baseClazz, 10f);
		addTest("Automovel", new MethodCall(new ConstructorCall(10f), "setNivelCombustivel", null, 100f),
				new MethodCall(new ConstructorCall(10f), "andar", 10f, 20f),
				new MethodCall(new ConstructorCall("", "", "", 45f, 10f), "getNivelCombustivel", 97f),

				new MethodCall(new ConstructorCall(10f), "abastecer", null, 10f),
				new MethodCall(new ConstructorCall(10f), "getNivelCombustivel", 107f),

				new MethodCall(new ConstructorCall(10f), "parar", null),
				new MethodCall(new ConstructorCall(10f), "getVelocidadeAtual", 10f),

				new MethodCall(new ConstructorCall(10f), "acelerar", 101f, 101f),
				new MethodCall(new ConstructorCall(10f), "getVelocidadeAtual", 10f),

				new MethodCall(new ConstructorCall(10f), "mostrarCombustivel", 17f),
				new MethodCall(new ConstructorCall(10f), "getVelocidadeAtual", 10f)

		);
	}

	public void testProcedure() {
		Config.setValues();
		before();

		String studentDir = "C:\\Users\\edina\\Downloads\\IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_02\\";
		String projectName = "Veiculo";
		boolean regex = true;
		boolean caseSensitive = false;
		boolean recursive = true;

		Config cfg = new Config(studentDir, projectName);

//		Project proj = new Project(cfg, tests, caseSensitive, regex, recursive);
//
//		evalReturn = new FileStrategy().eval(proj);
//		// TODO calcular os criterios
//		Map<String, Performance> perforMap = evalReturn.getPerforMap();
//		for (String a : perforMap.keySet()) {
//			Performance performance = perforMap.get(a);
//			performance.calcHitsPercentage(Config.CRITERIA);
//		}
//		System.out.println(evalReturn);
	}

	@Rule
    public static TemporaryFolder folder = new TemporaryFolder();
	
	private static void test() throws FileNotFoundException, IOException {

		final IClassWriter minhaClasse = ClassMock.of("Automovel");
		minhaClasse.field("modelo", String.class);
		minhaClasse.field("ano", Integer.class);
		minhaClasse.field("cor", String.class);

		minhaClasse.method("getNivelCombustivel").returnType(Integer.class);
		minhaClasse.method("setNivelCombustivel").returnTypeAsVoid().parameter("nivel", Integer.class);

		ClassMock mock = (ClassMock) minhaClasse;
		final ParseASM asm = new ParseASM(mock);

		Class<?> klazz = minhaClasse.build();

		String dir = System.getProperty("user.dir") + "\\test\\" + "Automovel.class";
		 File createdFile = folder.newFile("myfile.txt");
		 File createdFolder = folder.newFolder("subfolder");

		byte[] bytes = asm.parse();

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(dir);
			fos.write(bytes);
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		DynamicURLClassLoader dynLoader = new DynamicURLClassLoader(urlClassLoader);

		URL url = new File(dir).toURI().toURL();
		dynLoader.addURL(url);

		ClassAnalyzer classAnalyzer = new ClassAnalyzer(dynLoader, "Automovel", false, false, false, 1);
		boolean error = classAnalyzer.isError();

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

	@After
	private void after() {
		folder.delete();
	}
	
	public static void main(String[] args)
			throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, FileNotFoundException, IOException {
		test();
	}

}
