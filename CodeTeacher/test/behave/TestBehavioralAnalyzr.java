package behave;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codeteacher.Config;
import codeteacher.analyzers.Analyzer;
import codeteacher.analyzers.MethodCall;
import codeteacher.behave.ConstructorCall;
import codeteacher.result.Evaluation;
import codeteacher.result.Performance;
import gui.Project;
import gui.strategy.FileStrategy;

public class TestBehavioralAnalyzr {
	
	private static Map<String, List<Analyzer>> tests = new HashMap<String, List<Analyzer>>();
	
	private static Evaluation evalReturn = new Evaluation();
	
	public static void addTest(String key, Analyzer... methodCall){
		if (!tests.containsKey(key)){
			tests.put(key, new ArrayList<Analyzer>());
		}
		tests.get(key).addAll(Arrays.asList(methodCall));
	}
	
	public static void before(){
//		testConstructor(baseClazz, 10f);
		addTest("Automovel", 
				new MethodCall(new ConstructorCall(10f), "setNivelCombustivel", null, 100f),
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
	
	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException{
		Config.setValues();
		before();
		
		String studentDir = "C:\\Users\\edina\\Downloads\\IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_02\\";
		String projectName = "Veiculo";
		boolean regex = true;
		boolean caseSensitive = false;
		boolean recursive = true;

		Config cfg = new Config(studentDir, projectName);
		
		Project proj = new Project(cfg, tests, caseSensitive, regex, recursive);
		
		evalReturn = new FileStrategy().eval(proj);
		//TODO calcular os criterios
		Map<String, Performance> perforMap = evalReturn.getPerforMap();
		for (String a : perforMap.keySet()){
			Performance performance = perforMap.get(a);
			performance.calcHitsPercentage(Config.CRITERIA);
		}
		System.out.println(evalReturn);
	}
	
	}
