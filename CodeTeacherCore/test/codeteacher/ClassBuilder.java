package codeteacher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import net.sf.esfinge.classmock.ClassMock;
import net.sf.esfinge.classmock.api.IClassWriter;
import net.sf.esfinge.classmock.parse.ParseASM;

/**
 * ASM can retransform already loaded classes it basically reads the class on the file and retransform the loaded class in Memory
 * @author Alan Santos
 *
 */
public class ClassBuilder {
	
	public static void build(IClassWriter writer, File file) {
		
		ClassMock mock = (ClassMock) writer;

		final ParseASM asm = new ParseASM(mock);
		byte[] bytes = asm.parse();

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file.getAbsolutePath());
			fos.write(bytes);
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void build(IClassWriter writer, Path dir) throws IOException {
		ClassMock mock = (ClassMock) writer;

		final ParseASM asm = new ParseASM(mock);
		byte[] bytes = asm.parse();
		
		Files.write(dir, bytes);
	}
	
	public static void buildCustom(IClassWriter writer, Path dir) throws IOException {
		ClassMock mock = (ClassMock) writer;

		final CustomParseASM asm = new CustomParseASM(mock);
		byte[] bytes = asm.parse();
		
		Files.write(dir, bytes);
	}
}
