package codeteacher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.github.marschall.pathclassloader.PathClassLoader;

public class VirtualClassLoader extends ClassLoader implements IClassLoader {

	private final PathClassLoader loader;
	private final Path path;

	public VirtualClassLoader(Path path) {
		super();
		this.path = path;
		this.loader = new PathClassLoader(path);
	}
	
	public PathClassLoader getLoader() {
		return loader;
	}
	
	public Path getPath() {
		return path;
	}
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return loader.loadClass(name);
	}
	
	public List<String> find(String name, String alias, boolean recursive, boolean regex, boolean matchCase) {
		// TODO handle alias
		List<String> result = new ArrayList<>();

		if (Files.isDirectory(path)) {

			BiPredicate<Path, BasicFileAttributes> matcher = (file, attrs) -> {
				boolean match = false;
				
				String fileName = file.toString();
				if (recursive) {
					fileName = file.getFileName().toString();
				}

//				System.out.println(fileName);
				String fileNameToSearch = name.concat(".class");
				if (regex) {
					Pattern pattern = Pattern.compile(fileName);
					Matcher m = pattern.matcher(fileNameToSearch);
					match = m.find();
				} else if (matchCase) {
					match = fileName.equals(fileNameToSearch);
				} else {
					match = fileName.equalsIgnoreCase(fileNameToSearch);
				}

				return match;
			};

			try {
				result = Files.find(path, Integer.MAX_VALUE, matcher)
						.map(Object::toString)
						.collect(Collectors.toList());

//				result.size();
//				result.forEach(System.out::println);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public String getPathSeparator() {
		return path.getFileSystem().getSeparator();
	}
	
	public String getCanonicalName(String name) {
		return getCanonicalName(this.getRootAsString(), name, this.getPathSeparator());
	}
	
	private String getCanonicalName(String src, String fullName, String sep) {
		String replace = fullName
				.replace(src, "");
		String substring = replace
				.substring(replace.indexOf(sep)+1);
		return substring
				.replace(".class", "")
				.replace(".java", "")
				.replace(sep, ".");
//		return canonicalName;
	}

	@Override
	public String getRootAsString() {
		return path.toString();
	}
	
	private String getLastName(String fullName, String sep) {
		int beginIndex = fullName.lastIndexOf(sep) + 1;
		int endIndex = fullName.length();
		String simpleName = fullName.substring(beginIndex, endIndex);
		return simpleName;
	}
}
