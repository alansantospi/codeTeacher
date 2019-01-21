package utils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class NewestTest {

	public static void main(String[] args) {

//		Class<?>[] foundClasses = new Class<?>[0];
//		final ArrayList<Class<?>> foundClassesDyn = new ArrayList<Class<?>>();
//
//		String curPackage = "codeteacher.err";
//		new java.io.File(NewestTest.class.getResource("/" + curPackage.replace(".", "/"))
//				.getFile()).listFiles(new java.io.FileFilter() {
//			public boolean accept(java.io.File file) {
//				final String classExtension = ".class";
//
//				if (file.isFile() && file.getName().endsWith(classExtension)
//				// avoid inner classes
//						&& !file.getName().contains("$")) {
//					try {
//						String className = file.getName();
//						className = className.substring(0, className.length()
//								- classExtension.length());
//						foundClassesDyn.add(Class.forName(curPackage + "."
//								+ className));
//					} catch (ClassNotFoundException e) {
//						e.printStackTrace(System.out);
//					}
//				}
//
//				return false;
//			}
//		});
//
//		foundClasses = foundClassesDyn.toArray(foundClasses);
	}
}
