package utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class NewerTest {

	public static void main(String[] args) {
		
		String pkg = "java"; //"codeteacher";//"org/apache/commons";
	    ClassLoader cl = ClassLoader.getSystemClassLoader();
	    URL[] urls = ((URLClassLoader) cl).getURLs();
	    for (URL url : urls) {
	        System.out.println(url.getFile());
	        File jar = new File(url.getFile());
	        if (jar.isDirectory()) {
	            File subdir = new File(jar, pkg);
	            if (!subdir.exists())
	                continue;
	            File[] files = subdir.listFiles();
	            for (File file : files) {
	                if (!file.isFile())
	                    continue;
	                if (file.getName().endsWith(".class"))
	                    System.out.println("Found class: "
	                            + file.getName().substring(0,
	                                    file.getName().length() - 6));
	            }
	        } else {
	            // try to open as ZIP
	            try {
	                ZipFile zip = new ZipFile(jar);
	                for (Enumeration<? extends ZipEntry> entries = zip
	                        .entries(); entries.hasMoreElements();) {
	                    ZipEntry entry = entries.nextElement();
	                    String name = entry.getName();
	                    if (!name.startsWith(pkg))
	                        continue;
	                    name = name.substring(pkg.length() + 1);
	                    if (name.indexOf('/') < 0 && name.endsWith(".class"))
	                        System.out.println("Found class: "
	                                + name.substring(0, name.length() - 6));
	                }
	            } catch (ZipException e) {
	                System.out.println("Not a ZIP: " + e.getMessage());
	            } catch (IOException e) {
	                System.err.println(e.getMessage());
	            }
	        }
	    }  
	}
}
