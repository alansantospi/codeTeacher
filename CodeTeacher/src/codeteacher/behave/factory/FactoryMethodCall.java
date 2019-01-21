package codeteacher.behave.factory;

import java.util.ArrayList;
import java.util.List;

import codeteacher.analyzers.MethodCall;
import codeteacher.behave.ConstructorCall;

public class FactoryMethodCall {
	
	public static MethodCall create(List<ConstructorCall> constructorParams, String methodName, ConstructorCall outputCall, List<ConstructorCall> methodParams){
		List<Object> constructorArgs = new ArrayList<Object>();
		for (ConstructorCall constructorCall : constructorParams) {
			constructorCall.exec();
			Object obj = constructorCall.getObj();
			constructorArgs.add(obj);
		}
		
		List<Object> methodArgs = new ArrayList<Object>();
		for (ConstructorCall constructorCall : methodParams) {
			constructorCall.exec();
			Object obj = constructorCall.getObj();
			methodArgs.add(obj);
		}
		
		outputCall.exec();
		Object output = outputCall.getObj();
		
		return new MethodCall(new ConstructorCall(constructorArgs.toArray()), methodName, output, methodArgs.toArray());
		
//		return new MethodCall(obj, methodName, output, arguments);
		
	}
}
