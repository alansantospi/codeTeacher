package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by LINKOR on 26.05.2017 in 15:12. Date: 2017.05.26
 */
public class FileClassFinder {
	private JarFile file;
	private boolean trouble;

	public FileClassFinder(String filePath) {
		try {
			file = new JarFile(filePath);
		} catch (IOException e) {
			trouble = true;
		}
	}

	public List<String> findClasses(String pkg) {
		ArrayList<String> classes = new ArrayList<>();
		Enumeration<JarEntry> entries = file.entries();
		while (entries.hasMoreElements()) {
			JarEntry cls = entries.nextElement();
			if (!cls.isDirectory()) {
				String fileName = cls.getName();
				String className = fileName.replaceAll("/", ".")
						.replaceAll(File.pathSeparator, ".")
						.substring(0, fileName.lastIndexOf('.'));
				if (className.startsWith(pkg))
					classes.add(className.substring(pkg.length() + 1));
			}
		}
		return classes;
	}
	
	public static void main(String[] args) {
		FileClassFinder fileClassFinder = new FileClassFinder("C:\\Users\\User\\workspace\\CodeTeacher\\libs\\commons-io-1.3.2.jar");
		String pkg = "org";
		fileClassFinder.findClasses(pkg);
	}
}