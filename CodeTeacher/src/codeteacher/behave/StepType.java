package codeteacher.behave;

public enum StepType implements IStepType {

	CONSTRUCT{
		public StepExecutor getExecutor(){
			return new ConstructorExecutor();
		}
	},
	
	METHOD{
		public StepExecutor getExecutor(){
			return new MethodExecutor();
		}
	}
}
