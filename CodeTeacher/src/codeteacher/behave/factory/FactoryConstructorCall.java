package codeteacher.behave.factory;

import codeteacher.behave.ConstructorCall;

public class FactoryConstructorCall {
	
	public static ConstructorCall create(Object... arguments){
		
		return new ConstructorCall(arguments);
	}

}
