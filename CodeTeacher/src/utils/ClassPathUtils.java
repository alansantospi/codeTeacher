package utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.sun.xml.internal.ws.util.StringUtils;

public class ClassPathUtils {
	private static List<JarFile> jarFiles = new ArrayList<JarFile>();
	private static ArrayList<String> allKlazzes = new ArrayList<>();
	private static boolean trouble;

	private static void getJarFiles() {
		ClassLoader cl = ClassLoader.getSystemClassLoader();
	    URL[] urls = ((URLClassLoader) cl).getURLs();
		for (URL url : urls) {
			
			File file = new File(url.getFile());
			JarFile jar;
			try {
				if (!file.isDirectory()){
//					System.out.println(file.getAbsolutePath());
					jar = new JarFile(file.getAbsolutePath());
					jarFiles.add(jar);
				}
			} catch (IOException e) {
				trouble = true;
			}			
		}
	}

	public static List<String> find3rdPartyKlazzes() {
		if (jarFiles.isEmpty()){
			getJarFiles();
		}
		if (allKlazzes.isEmpty()){
			
			for (JarFile jar : jarFiles) {
				
				Enumeration<JarEntry> entries = jar.entries();
				while (entries.hasMoreElements()) {
					JarEntry cls = entries.nextElement();
					if (!cls.isDirectory()) {
						String fileName = cls.getName();
						if (fileName.endsWith(".class")){
							
							String className = fileName.replaceAll("/", ".")
								.replaceAll(File.pathSeparator, ".")
								.substring(0, fileName.lastIndexOf('.'));
							allKlazzes.add(className);//.substring(pkg.length() + 1));
						}
					}
				}
			}
		}
		return allKlazzes;
	}
	
	public static List<String> match(String pattern){
		List<String> foundClasses = new ArrayList<String>(); 
				find3rdPartyKlazzes();
		
		List<String> klazzes = ClassFinderUtils.getKlazzes();
		allKlazzes.clear();
		allKlazzes.addAll(klazzes);
		
		for (Iterator<String> iterator = allKlazzes.iterator(); iterator.hasNext(); ) {
			String klazzName = iterator.next();
			String simpleName = klazzName.toLowerCase();//.substring(klazzName.lastIndexOf(".") + 1);
			
//			pattern = StringUtils.capitalize(pattern);
			if ((!simpleName.contains("$")) && simpleName.contains(pattern)){
				foundClasses.add(klazzName);
			}
		}
		java.util.Collections.sort(foundClasses);
		return foundClasses;
	}
	
	public static void main(String[] args) {		
		ClassPathUtils util = new ClassPathUtils();
		List<String> foundClasses = util.find3rdPartyKlazzes();
		for (String str : foundClasses) {
			System.out.println(str);
		}
	}
}