package utils;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class TestFileSearch {

	@Test
	public void testExistsRecursive() {

		FileSearch fileSearch = new FileSearch();
		fileSearch.setRecursive(true);
		fileSearch.setCaseSensitive(false);

		String fileName = "TestaInteger.java";
		fileSearch.searchDirectory(new File("/Users/edina/Downloads/IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_02/"),
				fileName);

		int count = fileSearch.getResult().size();

		assertEquals(18, count);
	}

	@Test
	public void testExistsRecursiveCaseSensitive() {

		FileSearch fileSearch = new FileSearch();
		fileSearch.setRecursive(true);
		fileSearch.setCaseSensitive(true);

		String fileName = "TestaInteger.java";
		fileSearch.searchDirectory(new File("/Users/edina/Downloads/IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_02/"),
				fileName);

		int count = fileSearch.getResult().size();

		assertEquals(17, count);
	}

	@Test
	public void testExistsRecursiveRegex() {

		FileSearch fileSearch = new FileSearch();
		fileSearch.setRecursive(true);
		fileSearch.setRegex(true);

		String fileName = "TestaInteger.java";
		fileSearch.searchDirectory(new File("/Users/edina/Downloads/IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_02/"),
				fileName);

		int count = fileSearch.getResult().size();

		assertEquals(17, count);
	}

	@Test
	public void testNotExists() {

		FileSearch fileSearch = new FileSearch();
		fileSearch.setCaseSensitive(false);

		String fileName = "testeInteger.java";
		fileSearch.searchDirectory(new File("/Users/edina/Downloads/IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_02/"),
				fileName);

		int count = fileSearch.getResult().size();

		assertEquals(0, count);
	}

	@Test
	public void testNotExistsCaseSensitive() {

		FileSearch fileSearch = new FileSearch();
		fileSearch.setCaseSensitive(true);

		String fileName = "testeInteger.java";
		fileSearch.searchDirectory(new File("/Users/edina/Downloads/IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_02/"),
				fileName);

		int count = fileSearch.getResult().size();

		assertEquals(0, count);
	}

	@Test
	public void testNotExistsRegex() {
		FileSearch fileSearch = new FileSearch();
		fileSearch.setRegex(true);

		String fileName = "testeInteger.java";
		fileSearch.searchDirectory(new File("/Users/edina/Downloads/IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_02/"),
				fileName);

		int count = fileSearch.getResult().size();

		assertEquals(0, count);
	}
}
