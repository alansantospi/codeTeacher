package utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import com.github.junrar.Archive;
import com.github.junrar.Junrar;
import com.github.junrar.exception.RarException;
import com.github.junrar.extract.ExtractArchive;
import com.github.junrar.rarfile.FileHeader;

import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.IInArchive;
import net.sf.sevenzipjbinding.ISequentialOutStream;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.SevenZipNativeInitializationException;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;

/**
 * This utility extracts files and directories of a standard zip file to a
 * destination directory.
 *
 */
public class UnzipUtils {
	/**
	 * Size of the buffer to read/write data
	 */
	private static final int BUFFER_SIZE = 4096;

	/**
	 * Extracts a zip file specified by the zipFilePath to a directory specified by
	 * destDirectory (will be created if does not exists)
	 * 
	 * @param zipFilePath
	 * @param destDirectory
	 * @throws IOException
	 */
	public static void unzip(String zipFilePath, String destDirectory) throws IOException {
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
		ZipEntry entry = zipIn.getNextEntry();
		// iterates over entries in the zip file
		while (entry != null) {
			String filePath = destDirectory + File.separator + entry.getName();
			if (!entry.isDirectory()) {
				// if the entry is a file, extracts it
				extractFile(zipIn, filePath);
			} else {
				// if the entry is a directory, make the directory
				File dir = new File(filePath);
				dir.mkdir();
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		zipIn.close();
	}

	/**
	 * Extracts a zip entry (file entry)
	 * 
	 * @param zipIn
	 * @param filePath
	 * @throws IOException
	 */
	private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}

	public static Path extract(ZipInputStream zipInputStream, final Path path) throws IOException {
		
		ZipEntry zipEntry = zipInputStream.getNextEntry();
		String entryName = zipEntry.getName();
	    while (zipEntry != null) {
	    	Scanner sc = new Scanner(zipInputStream);
	    	List<String> lines = new ArrayList<>();
            while (sc.hasNextLine()) {
                String nextLine = sc.nextLine();
                lines.add(nextLine);
            }
	    	
            Path dir = path.resolve(zipEntry.toString());
			if (zipEntry.isDirectory()) {
				Files.createDirectory(dir);
			} else {
				Files.write(dir, lines);
			} 

	        zipEntry = zipInputStream.getNextEntry();
	    }
		return path.resolve(entryName);
	}
	
	public static Path extract(ZipFile zipFile, final FileSystem fs) throws IOException {
		List<Path> paths = new ArrayList<Path>();
		for (Enumeration<?> e = zipFile.entries(); e.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) e.nextElement();

			InputStream in = zipFile.getInputStream(entry);
			byte[] data = new byte[in.available()];
			in.read(data);

			Path dir = fs.getPath(entry.getName());
			paths.add(dir);
//			System.out.println(dir);
			if (entry.isDirectory()) {
				Files.createDirectory(dir);
			} else {
				Files.write(dir, data);
			} 
		}
		
		Enumeration<?> en = zipFile.entries();
		ZipEntry entry = (ZipEntry) en.nextElement();
		String entryName = entry.getName();
		
		return fs.getPath(entryName);
	}
	
	public static void main(String[] args) throws IOException, RarException, SevenZipNativeInitializationException {
		
		String rarPath = "C:\\Users\\edina\\Downloads\\breno.rar";
		String destPath = "C:\\Users\\edina\\Downloads\\unrar";
		
//		Junrar.extract(rarPath, destPath);		
//		
//		test2(rarPath);
//		
//		test3(rarPath);
//        
//		test4(rarPath, destPath);
        
		test6(rarPath, destPath);
        
	}

	private static void test6(String rarPath, String destFolder) throws FileNotFoundException, SevenZipException, SevenZipNativeInitializationException {
		File sourceZipFile = new File(rarPath);
		RandomAccessFile randomAccessFile= new RandomAccessFile(sourceZipFile, "r");
		SevenZip.getPlatformList();
		SevenZip.initLoadedLibraries();
	    IInArchive inArchive = SevenZip.openInArchive(null, // autodetect archive type
	            new RandomAccessFileInStream(randomAccessFile));
	    ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();

	    for (int i = 0; i < inArchive.getNumberOfItems(); i++) {
	        ISimpleInArchiveItem archiveItem = simpleInArchive.getArchiveItem(i);

	        final File outFile = new File(destFolder,archiveItem.getPath());
	        outFile.getParentFile().mkdirs();
	        System.out.println(String.format("extract(%s) in progress: %s",sourceZipFile.getName(),archiveItem.getPath()));
	        final BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(outFile));
	        ExtractOperationResult result = archiveItem.extractSlow(new ISequentialOutStream() {
	            public int write(byte[] data) throws SevenZipException {
	                try {
	                    out.write(data);
	                } catch (IOException e) {
	                    throw new SevenZipException(String.format("error in writing extracted data from:%s to:%s ",sourceZipFile.getName(),outFile.getName()),e);
	                }finally{
	                    try{out.close();}catch(Exception e){}
	                }
	                return data.length; // return amount of consumed data
	            }
	        });
	        if(result!=ExtractOperationResult.OK){
	            throw new SevenZipException(String.format(" %s error occured in extracting : %s item of file : %s ",result.name(),archiveItem.getPath(),sourceZipFile.getName()));
	        }
	    }
		
	}

	static void test4(String rarPath, String destPath) {
		try {
		      Runtime.getRuntime().exec("C:\\Program Files\\WinRAR\\WinRAR.exe X "+rarPath+" "+destPath);        
		  } catch (IOException ex) {
		      System.out.println(ex);        
		  }
	}

	static void test3(String rarPath) throws FileNotFoundException, RarException, IOException {
//		File f = new File(rarPath);
//		FileInputStream fis = new FileInputStream(f);
//		
//        Archive archive = new Archive(f);
//        archive.getMainHeader().print();
//        FileHeader fh = archive.nextFileHeader();
//        while(fh!=null){        
//                File fileEntry = new File(fh.getFileNameString().trim());
//                System.out.println(fileEntry.getAbsolutePath());
//                FileOutputStream os = new FileOutputStream(fileEntry);
//                archive.extractFile(fh, os);
//                os.close();
//                fh=archive.nextFileHeader();
//        }
	}

	static void test2(String rarPath) throws IOException, RarException {
		File rar = new File(rarPath);
        File tmpDir = File.createTempFile("bip.",".unrar");
        if(!(tmpDir.delete())){
            throw new IOException("Could not delete temp file: " + tmpDir.getAbsolutePath());
        }
        if(!(tmpDir.mkdir())){
            throw new IOException("Could not create temp directory: " + tmpDir.getAbsolutePath());
        }
        System.out.println("tmpDir="+tmpDir.getAbsolutePath());
        ExtractArchive extractArchive = new ExtractArchive();
        
        extractArchive.extractArchive(rar, tmpDir);
        System.out.println("finished.");
	}
	
}