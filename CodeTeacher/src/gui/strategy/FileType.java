package gui.strategy;

import gui.msg.GuiMsg;

public enum FileType implements GuiMsg {

	JAVA,
	CLASS, JAR;
	
	public String prefix(){
		return "FileType";
	}
}
