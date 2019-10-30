package codeteacher.analyzers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.StringBufferInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class MyReader extends BufferedReader {

	private BufferedReader buffReader;
	private ByteArrayInputStream inStream;
	private String[] values;
	private int index = 0;

	public MyReader(Reader in, String[] values) {
		super(in);
		buffReader = new BufferedReader(in);

		values = new String[] { "Alan", "Flávia", "Anne" };
		
		StringBuilder b = new StringBuilder();
		
		for (String string : values) {
			b.append(string).append("\n");
		}
		
		inStream = new ByteArrayInputStream(b.toString().getBytes()); 
		
		System.setIn(inStream);
		
	}
	
	@Override
	public String readLine() throws IOException {
//		String str = values[index++];
//		inStream = new ByteArrayInputStream(str.getBytes());

//		System.setIn(inStream);

		String readLine = super.readLine();

		return readLine;
	}
}
