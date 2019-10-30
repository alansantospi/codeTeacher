package compile.python;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

public class JythonExample {

	public static void main(String a[]) throws ScriptException, IOException {

		example01();
	}

	private static void example01() {
		PythonInterpreter python = new PythonInterpreter();

		int number1 = 10;
		int number2 = 32;

		python.set("number1", new PyInteger(number1));
		python.set("number2", new PyInteger(number2));
		python.exec("number3 = number1+number2");
		PyObject number3 = python.get("number3");
		
		System.out.println("val : " + number3.toString());
	}

	/**
	 * Using ScriptEngine to configure where you want to send your output of the execution
	 * The ScriptEngine needs jython on the class path else 'engine' is null 
	 */
	public static void example02() {

	    StringWriter writer = new StringWriter(); //ouput will be stored here

	    ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptContext context = new SimpleScriptContext();

	    context.setWriter(writer); //configures output redirection
	    ScriptEngine engine = manager.getEngineByName("python");
	    try {
			engine.eval(new FileReader("C:\\Users\\edina\\eclipse-workspace\\CodeTeacher\\src\\numbers.py"), context);
		} catch (FileNotFoundException | ScriptException e) {
			e.printStackTrace();
		}
	    System.out.println(writer.toString()); 
	}
}
