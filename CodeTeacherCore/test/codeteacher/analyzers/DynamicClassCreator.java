package codeteacher.analyzers;
import static java.util.Collections.singletonList;
import static javax.tools.JavaFileObject.Kind.SOURCE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

import sun.misc.Unsafe;
import utils.ReflectionUtils;
import utils.StringUtils;

/**
 */
public class DynamicClassCreator {
	
	List<String> fields = new ArrayList<String>();

	public DynamicClassCreator withField(String field) {
		fields.add(field);
		return this;
	}

    public Class create(final String className, final String path) throws ClassNotFoundException, IllegalAccessException, InstantiationException, URISyntaxException, NoSuchFieldException {

    	final StringBuilder source = new StringBuilder();

    	String fullClassName = ""; 
    			
    	if (path == null || !path.equals("")) {
    		fullClassName = path.replace('.', '/') + "/" + className;
    		source.append("package " + path + ";");
    	} else {
    		fullClassName = className;
    	}
        source.append("public class " + className + " {\n");
        
        
        for (String s : fields) {
        	source.append(s +";");
        }
        
        source.append(" public String toString() {\n");
        source.append("     return \"HelloWorld\";");
        source.append(" }\n");
        source.append("}\n");

        // A byte array output stream containing the bytes that would be written to the .class file
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        final SimpleJavaFileObject simpleJavaFileObject
                = new SimpleJavaFileObject(URI.create(fullClassName + ".java"), SOURCE) {

            @Override
            public CharSequence getCharContent(boolean ignoreEncodingErrors) {
                return source;
            }

            @Override
            public OutputStream openOutputStream() throws IOException {
                return byteArrayOutputStream;
            }
        };

        final JavaFileManager javaFileManager = new ForwardingJavaFileManager(
                ToolProvider.getSystemJavaCompiler().getStandardFileManager(null, null, null)) {

            @Override
            public JavaFileObject getJavaFileForOutput(Location location,
                                                       String className,
                                                       JavaFileObject.Kind kind,
                                                       FileObject sibling) throws IOException {
                return simpleJavaFileObject;
            }
        };

        ToolProvider.getSystemJavaCompiler().getTask(
                null, javaFileManager, null, null, null, singletonList(simpleJavaFileObject)).call();

        final byte[] bytes = byteArrayOutputStream.toByteArray();

        // use the unsafe class to load in the class bytes
        final Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        final Unsafe unsafe = (Unsafe) f.get(null);
        final Class aClass = unsafe.defineClass(fullClassName, bytes, 0, bytes.length, null, null);

        final Object o = aClass.newInstance();
        System.out.println(o);
        
        return aClass;
    }

    public static final void main(String... args) throws ClassNotFoundException, URISyntaxException, NoSuchFieldException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        DynamicClassCreator kreator = new DynamicClassCreator()
        		.withField("public String nome")
        		.withField("private int idade");
        
        String klazzName = "MyClass";
		Class dynClass = kreator.create(klazzName, "");
		
		boolean recursive = false;
		boolean matchCase = false;
		boolean regex = false;
		int value = 0;
		
		ClassLoader loader = ReflectionUtils.getClassLoader(System.getProperty("user.dir"));
		ClassAnalyzer classAnalyzer = new ClassAnalyzer(loader, klazzName, recursive, matchCase, regex, value);
		
		boolean error = classAnalyzer.isError();
		
		System.out.println(dynClass);
    }	

}
