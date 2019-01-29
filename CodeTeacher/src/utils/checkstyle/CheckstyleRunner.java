package utils.checkstyle;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.PropertyResolver;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import codeteacher.result.Performance;
import codeteacher.err.Error;

public class CheckstyleRunner {

//    private final Checker checker;
//    private final List<File> files;
    
	public static void main(String[] args) throws Exception {
//		String dir = System.getProperty("user.dir") + "\\test\\data\\TestPublicMethodAnalyzer\\";
		String dir = "C:\\Users\\edina\\Google Drive\\IFMA_CN_2018_1_DSOO-INFO3-V_trabs\\IFMA_CN_2018_1_DSOO-INFO3-V_trabs_Atividade_02\\";
		
		File rootDir = new File(dir);
		
		File[] files = rootDir.listFiles();

		for (File studentFolder : files) {
			if (studentFolder.isDirectory()) {
				run(studentFolder);
			}
		}

	}

	private static void run(File rootDir) throws CheckstyleException {
		// Get source directory and check that the project is testable
//        final File sourceDirectory = getSourceDirectory(projectDirectory);
		
		Checker checker = new Checker();

        // Configuration
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        
        // Get all .java files from project’s source directory
        List<File>javaFiles = (List<File>) FileUtils.listFiles(rootDir, new String[]{"java"}, true);

        // Set base directory
        checker.setBasedir(rootDir.getAbsolutePath());
        
        // Listener
        final CheckstyleResultListener listener = new CheckstyleResultListener(rootDir.getName());
        checker.addListener(listener);
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        
        StyleChecks checks = StyleChecks.google().maxLineLen(120);
//        Configuration cfg = ConfigurationLoader.loadConfiguration(checks.location, createPropertyResolver(checks));
        
//        print(cfg);
        
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
        
        DefaultConfiguration needBraces = new DefaultConfiguration("WhitespaceAround");
        treeWalker.addChild(needBraces);
        
        DefaultConfiguration booleanExpressionComplexity = new DefaultConfiguration("BooleanExpressionComplexity");
        treeWalker.addChild(booleanExpressionComplexity);
        
        DefaultConfiguration cyclomaticComplexity = new DefaultConfiguration("CyclomaticComplexity");
        cyclomaticComplexity.addAttribute("max", "1");
        treeWalker.addChild(cyclomaticComplexity);
        
		myCfg.addChild(treeWalker);
		checker.configure(myCfg);
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
