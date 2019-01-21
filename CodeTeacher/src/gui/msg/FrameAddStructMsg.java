package gui.msg;


public enum FrameAddStructMsg implements GuiMsg {
	EXPECTED,
	METHOD,
	CLEAR,
	REMOVE,
	PARAMS,
	ADD,
	ADD_TEST, CLASS_NOT_SET, PARAMS_NOT_SET;
	
	public String prefix(){
		return "FrameAddStruct";
	}
	
	@Override
	public String toString() {
		return I18N.getVal(this);
	}
}
