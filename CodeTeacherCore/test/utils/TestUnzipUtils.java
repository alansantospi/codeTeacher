package utils;

import java.io.IOException;

import org.junit.Test;

public class TestUnzipUtils {

	@Test
	public void testExtractZip(){
		String filePath = "/Users/edina/Downloads/IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_02/Adson Gustavo da Silva Lima/Atividade02.zip";
		String destPath = "/Users/edina/Downloads/";
		try {
			UnzipUtils.unzip(filePath, destPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testExtractJar(){
		String filePath = "/Users/edina/Downloads/IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_01/Adson Gustavo da Silva Lima/EntendendoGetClass.jar";
		String destPath = "/Users/edina/Downloads/EntendendoGetClass";
		try {
			UnzipUtils.unzip(filePath, destPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
