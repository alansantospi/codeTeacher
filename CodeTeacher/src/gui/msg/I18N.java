package gui.msg;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18N {

	private static Locale vmLocale = Locale.getDefault();
	private static ResourceBundle bundle = ResourceBundle.getBundle("messages", vmLocale);


	public static String getVal(GuiMsg msg){
		
		String key = msg.prefix() + "." + msg.name();
		return bundle.getString(key);
	}
}
