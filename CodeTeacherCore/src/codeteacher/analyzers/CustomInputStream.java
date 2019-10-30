package codeteacher.analyzers;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class CustomInputStream extends BufferedInputStream {

	private String[] values;
	
	public CustomInputStream(InputStream in) {
		super(in);
		values = new String[]{"Anne","Flávia","Alan"};
		
		StringBuilder b = new StringBuilder();
		
		for (String string : values) {
			b.append(string).append("\n");
		}
		
		ByteArrayInputStream inStream = new ByteArrayInputStream(b.toString().getBytes()); 
		
		System.setIn(inStream);
		
	}

}
