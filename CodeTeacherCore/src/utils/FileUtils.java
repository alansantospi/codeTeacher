package utils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileUtils {

	private static String readFileToString(File file) throws IOException {

		StringBuilder fileContents = new StringBuilder((int) file.length());
		Scanner scanner = new Scanner(file);
		String lineSeparator = System.getProperty("line.separator");

		try {
			while (scanner.hasNextLine()) {
				fileContents.append(scanner.nextLine() + lineSeparator);
			}
			return fileContents.toString();
		} finally {
			scanner.close();
		}
	}
}
