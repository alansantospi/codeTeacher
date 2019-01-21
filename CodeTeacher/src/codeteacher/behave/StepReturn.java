package codeteacher.behave;

public class StepReturn {

	private String msg;
	private Object object;

	public void setObject(Object obj) {
		this.object = obj;
		
	}

	public void setMessage(String msg) {
		this.msg = msg;
		
	}

	public Object getObject() {
		return object;
	}

}
