package utils;

import java.util.StringTokenizer;

public class StringUtils {

	public static String stripWhiteSpaces(String str) {
		StringTokenizer st = new StringTokenizer(str, " ");
		StringBuffer sb = new StringBuffer();
		while (st.hasMoreElements()) {
			sb.append(st.nextElement()).append(" ");
		} 
		return sb.toString().trim();
	}

	public static boolean isEmpty(String str) {
		return str != null && !str.isEmpty();
	}
}
