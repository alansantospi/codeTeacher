package utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
		boolean search3rdParty = false;
		if (search3rdParty) {
			find3rdPartyKlazzes();
		}
		
		List<String> klazzes = ClassFinderUtils.getKlazzes();
		allKlazzes.clear();
		allKlazzes.addAll(klazzes);
		Map<String, String> map = new HashMap<String, String>();
		for (Iterator<String> iterator = allKlazzes.iterator(); iterator.hasNext(); ) {
			String klazzName = iterator.next();
			String simpleName = klazzName.substring(klazzName.lastIndexOf(".") + 1);
			
//			pattern = StringUtils.capitalize(pattern);
			if ((!simpleName.contains("$")) && simpleName.toLowerCase().startsWith(pattern)) {
//			if ((!simpleName.contains("$")) && simpleName.contains(pattern)){
				map.put(simpleName, klazzName);
			}
		}
		
		Set<String> keySet = map.keySet();
		ArrayList<String> list = new ArrayList<String>(keySet);
		java.util.Collections.sort(list);
		
		for (String key : list) {
			String name = map.get(key);
			foundClasses.add(name);
		}
		
//		java.util.Collections.sort(foundClasses);
		return foundClasses;
	}
	
	public static void main(String[] args) {		
//		ClassPathUtils util = new ClassPathUtils();
//		List<String> foundClasses = util.find3rdPartyKlazzes();
		
		List<String> foundClasses = ClassFinderUtils.getKlazzes();
		for (String str : foundClasses) {
			int beginIndex = str.lastIndexOf('.')+1;
			 String data = str.substring(beginIndex); 
			System.out.println("{ value: '"+ str + "', data: '"+data+"'},");
		}
	}
}