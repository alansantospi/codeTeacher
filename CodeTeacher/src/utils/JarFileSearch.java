package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JarFileSearch {

	private String fileNameToSearch;
	private boolean regex;
	private boolean caseSensitive;
	private boolean recursive;
	private List<JarEntry> result = new ArrayList<JarEntry>();

	public String getFileNameToSearch() {
		return fileNameToSearch;
	}

	public void setFileNameToSearch(String fileNameToSearch) {
		this.fileNameToSearch = fileNameToSearch;
	}

	public boolean isRegex() {
		return regex;
	}

	public void setRegex(boolean regex) {
		this.regex = regex;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}
	
	public boolean isRecursive() {
		return recursive;
	}

	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}

	public List<JarEntry> getResult() {
		return result;
	}

	public void searchDirectory(File file, String fileNameToSearch) {

		setFileNameToSearch(fileNameToSearch);
		
		if (file.getName().toLowerCase().endsWith(".jar")) {
			
			JarFile jar = null;
			try {
				jar = new JarFile(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(String.format("Unable to open jar %s", file.getName(), e));
			}			
			if (jar != null) {
				Enumeration<JarEntry> entries = jar.entries();
				while (entries.hasMoreElements()) {
					JarEntry entry = entries.nextElement();
					
					String entryName = entry.getName();
					if (entryName.endsWith(".class")){
						boolean match = compare(entryName);
						if (match) {
							result.add(entry);
						}
					}
				}
			}
		} else {
//			System.out.println(directory.getAbsoluteFile() + " is not a directory!");
		}
	}

	private boolean compare(String fileName) {
		boolean match = false;
		String fileNameToSearch = getFileNameToSearch();
		String separator = "/";//Config.SEPARATOR;
		if (isRecursive() && fileName.contains(separator)) {
			int beginIndex = fileName.lastIndexOf(separator)+1;
			fileName = fileName.substring(beginIndex);
		}
		if (isRegex()) {
			Pattern pattern = Pattern.compile(fileNameToSearch);
			Matcher m = pattern.matcher(fileName);
			match = m.find();
		} else if (isCaseSensitive()) {
			match = fileNameToSearch.equals(fileName);
		} else {
			match = fileNameToSearch.equalsIgnoreCase(fileName);
		}
		
		return match;
	}
	
	public static void main(String[] args) {

		JarFileSearch fileSearch = new JarFileSearch();
		fileSearch.setCaseSensitive(true);
		fileSearch.setRecursive(true);

		// try different directory and filename :)
		String fileName = "MetodoGetClass.class";//"TestaInteger.java";
		String studentDir = "C:\\Users\\edina\\Downloads\\IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_01\\Andressa da Silva Nogueira\\EntendendoGetClass.jar";
		File file = new File(studentDir);
		
		fileSearch.searchDirectory(file, fileName);

		int count = fileSearch.getResult().size();
		if (count == 0) {
			System.out.println("\nNo result found!");
		} else {
			System.out.println("\nFound " + count + " result!\n");
			for (JarEntry entry : fileSearch.getResult()) {
				System.out.println("Found : " + entry.toString());
			}
		}
	}
}