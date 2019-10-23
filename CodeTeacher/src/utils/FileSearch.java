package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileSearch {

	private String fileNameToSearch;
	private boolean regex;
	private boolean caseSensitive;
	private boolean recursive;
	private List<File> result = new ArrayList<File>();

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

	public List<File> getResult() {
		return result;
	}
	
	public Map<Object, Object> getResultMap(){
		return result.stream().map( e -> e.toString() ).collect(Collectors.toMap(x -> x.toString(), x -> x));
	}
	
	public List<String> getResultString(){
		return result.stream().map( e -> e.toString() ).collect( Collectors.toList() );
	}

	public void searchDirectory(File file, String fileNameToSearch) {

		setFileNameToSearch(fileNameToSearch);

//		if (file.getName().toLowerCase().endsWith(".jar")) {
//			JarFileSearch jarFileSearch = new JarFileSearch();
//			jarFileSearch.searchDirectory(file, fileNameToSearch);
//			
//		} else 
		if (file.isDirectory()) {
			for (File temp : file.listFiles()) {
				search(temp);
			}
		} else {
			System.out.println(file.getAbsoluteFile() + " is not a directory!");
		}
	}

	private void search(File file) {
		String fileName = file.getName();
		boolean match = compare(fileName);
		if (match) {
			result.add(file);
		}
		
		if (file.isDirectory() && isRecursive()) { 
//			System.out.println("Searching directory ... " + file.getAbsoluteFile());

			if (file.canRead()) {
				for (File temp : file.listFiles()) {
					search(temp);
//					if (!temp.isDirectory()) {
//						match = compare(temp.getName());
//						if (match) {
//							result.add(temp);
//						}
//					} else if (isRecursive()) {
//						search(temp);
//					}
				}
			} else {
				System.out.println(file.getAbsoluteFile() + "Permission Denied");
			}
		}
	}

	private boolean compare(String fileName) {
		boolean match = false;
		String fileNameToSearch = getFileNameToSearch();
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
	
	private void list(File file) {
		System.out.println(file.getName());
		File[] children = file.listFiles();
		for (File child : children) {
			list(child);
		}
	}

	public static void main(String[] args) {

		FileSearch fileSearch = new FileSearch();
		fileSearch.setCaseSensitive(true);

		// try different directory and filename :)
//		String fileName = "TestaInteger.java";
//		String path = "/Users/edina/Downloads/IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_02/";
		String fileName = "WebSkin.xml";
		String path = "/Users/edina/Downloads/weblaf-demo-1.28.jar/";
		fileSearch.searchDirectory(new File(path),
				fileName);

		int count = fileSearch.getResult().size();
		if (count == 0) {
			System.out.println("\nNo result found!");
		} else {
			System.out.println("\nFound " + count + " result!\n");
			for (File file : fileSearch.getResult()) {
				System.out.println("Found : " + file.getAbsoluteFile().toString());
			}
		}
	}
}