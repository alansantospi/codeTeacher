package codeteacher.behave;

import java.util.HashMap;
import java.util.Map;

public class BehaveAnalist {
	
	private Map<String, TestCase> testCases;  
	private static Map<String, Step> steps = new HashMap<String, Step>();
	
	public static void before(){
		Step step1 = new Step("step1", String.class, StepType.CONSTRUCT, "aString");
		Step step2 = new Step("step2", String.class, StepType.METHOD, "toString").addStep(step1);
		
		for (Step s :step2.getPreviousSteps()){
			if (steps.containsKey(s.getKey())){
				Step step = steps.get(s.getKey());
			}
		}
		step1.exec();
		step2.exec();
		
		
//		testConstructor(baseClazz, 10f);
//		addTest("Automovel", 
//				new MethodCall(new ConstructorCall(10f), "setNivelCombustivel", null, 100f),
//				new MethodCall(new ConstructorCall(10f), "andar", 10f, 20f),
//				new MethodCall(new ConstructorCall("", "", "", 45f, 10f), "getNivelCombustivel", 97f),
//			  
//				new MethodCall(new ConstructorCall(10f), "abastecer", null, 10f),
//				new MethodCall(new ConstructorCall(10f), "getNivelCombustivel", 107f),
//		
//				new MethodCall(new ConstructorCall(10f), "parar", null),
//				new MethodCall(new ConstructorCall(10f), "getVelocidadeAtual", 10f),
//			  	
//				new MethodCall(new ConstructorCall(10f), "acelerar", 10f, 101f),
//				new MethodCall(new ConstructorCall(10f), "getVelocidadeAtual", 10f),
//			
//				new MethodCall(new ConstructorCall(10f), "mostrarCombustivel", 17f),
//				new MethodCall(new ConstructorCall(10f), "getVelocidadeAtual", 10f)
//		
//				);
	}

	private static Object addStep(Step step1) {
		// TODO Auto-generated method stub
		return null;
	}
}
