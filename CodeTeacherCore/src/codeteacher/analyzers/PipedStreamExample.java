package codeteacher.analyzers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PipedStreamExample {
	public static void main(String[] args) throws IOException, InterruptedException {

		// create pairs of Piped input and output streasm for std out and std err
		final PipedInputStream outPipedInputStream = new PipedInputStream();
		final PrintStream outPrintStream = new PrintStream(new PipedOutputStream(outPipedInputStream));
		final BufferedReader outReader = new MyReader(new InputStreamReader(outPipedInputStream), new String[0]);
		
		final PipedInputStream errPipedInputStream = new PipedInputStream();
		final PrintStream errPrintStream = new PrintStream(new PipedOutputStream(errPipedInputStream));
		final BufferedReader errReader = new BufferedReader(new InputStreamReader(errPipedInputStream));
		
//		final PipedOutputStream inPipedInputStream = new PipedOutputStream();
////	final InputStream inPrintStream = new BufferedInputStream(new PipedInputStream(inPipedInputStream));
//	final InputStream inPrintStream = new CustomInputStream(new PipedInputStream(inPipedInputStream));
//	final BufferedReader inReader = new BufferedReader(new InputStreamReader(inPrintStream));
		
		final PrintStream originalOutStream = System.out;
		final PrintStream originalErrStream = System.err;
		
		final Thread writingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.setOut(outPrintStream);
					System.setErr(errPrintStream);
					// You could also set the System.in here using a
					// PipedInputStream
					start(outPrintStream, errPrintStream);
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					// may also want to add a catch for exceptions but it is
					// essential to restore the original System output and error
					// streams since it can be very confusing to not be able to
					// find System.out output on your console
					System.setOut(originalOutStream);
					System.setErr(originalErrStream);
					// You must close the streams which will auto flush them
					outPrintStream.close();
					errPrintStream.close();
				}
			} // end run()
		}); // end writing thread
		// Start the code that will write into streams
		writingThread.start();
		String line;
		final List<String> completeOutputStreamContent = new ArrayList<String>();
		while ((line = outReader.readLine()) != null) {
			completeOutputStreamContent.add(line);
		} // end reading output stream
		final List<String> completeErrorStreamContent = new ArrayList<String>();
		while ((line = errReader.readLine()) != null) {
			completeErrorStreamContent.add(line);
		} // end reading output stream
//		outReader.close();
	}

	private static void start(PrintStream outPrintStream, PrintStream errPrintStream) throws NoSuchMethodException, SecurityException {
		System.setOut(outPrintStream);
		System.setOut(errPrintStream);
		Class<?> klazz = MinhaClasse.class;
		Method method = klazz.getMethod("main", String[].class);

		new Thread(new Runnable() {
			@Override
			public void run() {
				Object params = new String[0];
				try {
					method.invoke(null, new Object[] { params });
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}

	static class MinhaClasse {
		public static void main(String[] args) throws IOException {

			InputStream in = System.in;
			Scanner scan = new Scanner(in);

			System.out.println("input a number");
			String s = scan.next();
			System.out.println("You typed " + s);

			System.out.println("Please input another number");
			s = scan.next();
			System.out.println("Now, you typed " + s);

			scan.close();
		}
	}

}
