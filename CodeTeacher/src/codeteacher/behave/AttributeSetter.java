package codeteacher.behave;

import utils.ReflectionUtils;

public class AttributeSetter {
	
	private Object object;
	private String fieldName;
	private Object value;
	
	public AttributeSetter(Object obj, String fieldName, Object value) {
		this.object = obj;
		this.fieldName = fieldName;
		this.value= value;
	}
	
	public void setObject(Object obj){
		this.object = obj;
	}
	
	public Object getObject(){
		return object;
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public Object getValue() {
		return value;
	}

	public boolean exec() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		
		ReflectionUtils.setValue(getObject(), true, fieldName, value);

		return false;
	}
	
}
