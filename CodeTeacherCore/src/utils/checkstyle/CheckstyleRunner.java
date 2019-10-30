package utils.checkstyle;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;
import com.google.api.services.classroom.model.Attachment;
import com.google.api.services.classroom.model.DriveFile;
import com.google.api.services.classroom.model.StudentSubmission;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.PropertyResolver;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import codeteacher.err.Error;
import codeteacher.result.Performance;
import utils.ClassroomService;
import utils.UnzipUtils;

public class CheckstyleRunner {

//    private final Checker checker;
//    private final List<File> files;
    
	public static void main(String[] args) throws Exception {
//		String dir = System.getProperty("user.dir") + "\\test\\data\\TestPublicMethodAnalyzer\\";
		String dir = "C:\\Users\\edina\\Google Drive IFMA\\IFMA_CN_2018_1_DSOO-INFO3-V_trabs\\IFMA_CN_2018_1_DSOO-INFO3-V_trabs_Atividade_02\\";
		
		File rootDir = new File(dir);
		
		File[] files = rootDir.listFiles();
		DefaultConfiguration myCfg = createConfig();

//		for (File studentFolder : files) {
//			if (studentFolder.isDirectory()) {
//				run(studentFolder, myCfg);
//			}
//		}
		
		String courseId = "37481930934";
		String courseworkId = "37481930964";
		
		run2(myCfg, courseworkId, courseId);

	}

	private static void run(File rootDir, DefaultConfiguration cfg) throws CheckstyleException {
		// Get source directory and check that the project is testable
//        final File sourceDirectory = getSourceDirectory(projectDirectory);
		
		Checker checker = new Checker();

        // Configuration
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        
        // Get all .java files from project's source directory
        List<File>javaFiles = (List<File>) FileUtils.listFiles(rootDir, new String[]{"java"}, true);

        // Set base directory
        checker.setBasedir(rootDir.getAbsolutePath());
        
        // Listener
        final CheckstyleResultListener listener = new CheckstyleResultListener(rootDir.getName());
        checker.addListener(listener);
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        
        // Configuration
		checker.configure(cfg);
		
        try {
            // Process
            checker.process(javaFiles);
        } catch (CheckstyleException ex) {
            throw new RuntimeException(
                    new Exception(
                            "This is a system error while checking code style.", ex));
        }

        // Clean up
        checker.destroy();
        
        Performance result = listener.getResult();
        for (Error err : result.getErrors()) {
			
        	
		}
        System.out.println(result);
	}
	
	private static void run2(DefaultConfiguration cfg, String courseworkId, String courseId) throws CheckstyleException, IOException {
		// Get source directory and check that the project is testable
//        final File sourceDirectory = getSourceDirectory(projectDirectory);
		
		Checker checker = new Checker();

        // Configuration
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        
        FileSystem fs = MemoryFileSystemBuilder.newEmpty().build();
		
		List<StudentSubmission> submissions = ClassroomService.getStudentSubmissions(courseId, courseworkId);
		
		submissions.forEach(sub -> {
			String userId = sub.getUserId();
			List<Attachment> attachments = sub.getAssignmentSubmission().getAttachments();
			
			if (attachments == null || attachments.isEmpty()) {
				System.out.println("No attached files found!");
				return;
			}
			
			List<Attachment> zipAttachments = attachments.stream()
					.filter( a -> { 
						return a.getDriveFile().getTitle().endsWith(".Zip"); 
						})
					.collect(Collectors.toList());
			
			if (zipAttachments == null || zipAttachments.isEmpty()) {
				System.out.println("No zip files found!");
				return;
			} else {
				Path dir = fs.getPath(courseworkId, userId); 
				zipAttachments.forEach(attach -> {
					DriveFile driveFile = attach.getDriveFile();
					String fileId = driveFile.getId();

					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					try {
						ClassroomService.download(fileId, byteArrayOutputStream);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					ByteArrayInputStream inStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
					ZipInputStream zip = new ZipInputStream(inStream);
					
					try {
					
						Files.createDirectories(dir);
						UnzipUtils.extract(zip, dir);
        
						List<Path> matches = Files.find(dir, Integer.MAX_VALUE, (file, basicFileAttributes) -> {
							String fileName = file.getFileName().toString();
							boolean endsWith = fileName.endsWith(".java");
							return endsWith;
						})
								.collect(Collectors.toList());
						List<File> javaFiles = new ArrayList<>();
						Path temp = Files.createTempDirectory(userId);
						matches.forEach( p -> {
							byte[] bytes;
							try {
								bytes = Files.readAllBytes(p);
								String content = new String(bytes);
								
								
//								Path write = Files.write(temp, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
								
								//write data on temporary file
								String name = temp + File.separator + p.getFileName().toString();
								FileOutputStream fos = new FileOutputStream(name);
								fos.write(bytes);
								
								javaFiles.add(new File(name));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						});
						// Get all .java files from project's source directory
//						List<File>javaFiles = (List<File>) FileUtils.listFiles(rootDir, new String[]{"java"}, true);
						
						// Set base directory
						checker.setBasedir(temp.toAbsolutePath().toString());
						
						// Listener
						final CheckstyleResultListener listener = new CheckstyleResultListener(dir.toString());
						checker.addListener(listener);
						checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
						
						// Configuration
						checker.configure(cfg);
						
						try {
							// Process
							checker.process(javaFiles);
						} catch (CheckstyleException ex) {
							throw new RuntimeException(
									new Exception(
											"This is a system error while checking code style.", ex));
						}
						
						// Clean up
						checker.destroy();
						
						Performance result = listener.getResult();
						for (Error err : result.getErrors()) {
							
							
						}
						System.out.println(result);
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (CheckstyleException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			}
		});
	}

	private static DefaultConfiguration createConfig() {
		
//		 StyleChecks checks = StyleChecks.google().maxLineLen(120);
//       Configuration cfg = ConfigurationLoader.loadConfiguration(checks.location, createPropertyResolver(checks));
		
		DefaultConfiguration myCfg = new DefaultConfiguration("CodeTeacher");
        DefaultConfiguration treeWalker = new DefaultConfiguration("TreeWalker");
        
        DefaultConfiguration paramName = new DefaultConfiguration("ParameterName");
        paramName.addAttribute("format", "^[a-z][a-z0-9][a-zA-Z0-9_]*$");
        paramName.addMessage("name.invalidPattern", "Parameter name ''{0}'' must match pattern ''{1}''.");
        treeWalker.addChild(paramName);
        
        DefaultConfiguration methodName = new DefaultConfiguration("MethodName");
        methodName.addAttribute("format", "^[a-z][a-z0-9][a-zA-Z0-9_]*$");
        methodName.addMessage("name.invalidPattern", "Parameter name ''{0}'' must match pattern ''{1}''.");
        treeWalker.addChild(methodName);
        
        DefaultConfiguration methodCount = new DefaultConfiguration("MethodCount");
        methodCount.addAttribute("maxTotal", "0");
        treeWalker.addChild(methodCount);
        
        DefaultConfiguration needBraces = new DefaultConfiguration("WhitespaceAround");
        treeWalker.addChild(needBraces);
        
        DefaultConfiguration emptyLineSeparator = new DefaultConfiguration("EmptyLineSeparator");
        treeWalker.addChild(emptyLineSeparator);
        
        DefaultConfiguration whitespaceAfter = new DefaultConfiguration("WhitespaceAfter");
        treeWalker.addChild(whitespaceAfter);
        
        DefaultConfiguration separatorWrap = new DefaultConfiguration("SeparatorWrap");
        treeWalker.addChild(separatorWrap);
        
        DefaultConfiguration booleanExpressionComplexity = new DefaultConfiguration("BooleanExpressionComplexity");
        treeWalker.addChild(booleanExpressionComplexity);
        
        DefaultConfiguration cyclomaticComplexity = new DefaultConfiguration("CyclomaticComplexity");
        cyclomaticComplexity.addAttribute("max", "1");
        treeWalker.addChild(cyclomaticComplexity);
        
		myCfg.addChild(treeWalker);
		return myCfg;
	}
	
	private static void print(Configuration cfg) {
		String name = cfg.getName();
		System.out.println(name);
		for (Configuration child : cfg.getChildren()) {
	       	print(child);
		}
	
	}

	private static File getSourceDirectory(final File projectDirectory) throws Exception {

        // Ant-project
        File sourceDirectory = new File(projectDirectory, "src/");

        // Maven-project
        if (new File(projectDirectory, "pom.xml").exists()) {
            sourceDirectory = new File(projectDirectory, "src/main/java/");
        }

        // Invalid directory
        if (!sourceDirectory.exists() || !sourceDirectory.isDirectory()) {
            throw new Exception("Path does not contain a testable project.");
        }

        return sourceDirectory;
    }

	private static PropertyResolver createPropertyResolver(StyleChecks checks) {
        final Properties p = new Properties();
        for (final Map.Entry<String, Object> param : checks.params.entrySet()) {
            p.setProperty(param.getKey(), propertyValue(param.getKey(), param.getValue()));
        }
        return new PropertiesExpander(p);
    }

    private static String propertyValue(String name, Object value) {
        if (name.endsWith("-tokens")) {
            final StringBuilder tokens = new StringBuilder();
            for (final Integer val : (List<Integer>) value) {
                for (final Field f : TokenTypes.class.getFields()) {
                    try {
                        if (val.equals(f.get(null))) {
                            tokens.append(tokens.length() == 0 ? "" : ",").append(f.getName());
                        }
                    } catch (IllegalAccessException e) {
                        //ignore
                    }
                }
            }
            return tokens.toString();
        }
        return value.toString();
    }
}
