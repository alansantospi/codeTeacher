package gui.msg;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18N {

	private Locale vmLocale;// = Locale.getDefault();
	private ResourceBundle bundle;// = ResourceBundle.getBundle("messages", vmLocale);
	private static I18N instance;
	
	private I18N() {
		vmLocale = Locale.getDefault();
		bundle = ResourceBundle.getBundle("messages", vmLocale);
	}

	public static String getVal(GuiMsg msg){
		if (instance == null) {
			instance = new I18N();
		}
		
		String key = msg.prefix() + "." + msg.name();
		return instance.bundle.getString(key);
	}
}
