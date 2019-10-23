package utils;

import java.awt.Composite;
import java.awt.Label;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle.Control;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassFinderUtils {
	
	private static ArrayList<String> classes = new ArrayList<>();
	
    public static void findClasses(Visitor<String> visitor) {
        String classpath = System.getProperty("java.class.path");
        String[] paths = classpath.split(System.getProperty("path.separator"));

        String javaHome = System.getProperty("java.home");
        File file = new File(javaHome + File.separator + "lib");
        if (file.exists()) {
            findClasses(file, file, true, visitor);
        }

        for (String path : paths) {
            file = new File(path);
            if (file.exists()) {
                findClasses(file, file, false, visitor);
            }
        }
    }

    private static boolean findClasses(File root, File file, boolean includeJars, Visitor<String> visitor) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                if (!findClasses(root, child, includeJars, visitor)) {
                    return false;
                }
            }
        } else {
            if (file.getName().toLowerCase().endsWith(".jar") && includeJars) {
                JarFile jar = null;
                try {
                    jar = new JarFile(file);
                } catch (Exception ex) {

                }
                if (jar != null) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        int extIndex = name.lastIndexOf(".class");
                        if (extIndex > 0) {
                            if (!visitor.visit(name.substring(0, extIndex).replace("/", "."))) {
                                return false;
                            }
                        }
                    }
                }
            }
            else if (file.getName().toLowerCase().endsWith(".class")) {
                if (!visitor.visit(createClassName(root, file))) {
                    return false;
                }
            }
        }

        return true;
    }

    private static String createClassName(File root, File file) {
        StringBuffer sb = new StringBuffer();
        String fileName = file.getName();
        sb.append(fileName.substring(0, fileName.lastIndexOf(".class")));
        file = file.getParentFile();
        while (file != null && !file.equals(root)) {
            sb.insert(0, '.').insert(0, file.getName());
            file = file.getParentFile();
        }
        return sb.toString();
    }
    
    public static void findClasses() {
    	ClassFinderUtils.findClasses(new Visitor<String>() {
    	    @Override
    	    public boolean visit(String clazz) {
    	    	classes.add(clazz);
//    	        System.out.println(clazz);
    	        return true; // return false if you don't want to see any more classes
    	    }
    	});
	}
    
    public static List<String> getKlazzes(){
    	if (classes.isEmpty()) {
    		findClasses();
    	}
    	return classes;
    }
    
    public static void main(String[] args) {
    	getKlazzes();
	}
    public interface Visitor<T> {
        /**
         * @return {@code true} if the algorithm should visit more results,
         * {@code false} if it should terminate now.
         */
        public boolean visit(T t);
    }

}