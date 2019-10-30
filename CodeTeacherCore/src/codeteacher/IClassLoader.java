package codeteacher;

import java.util.List;

public interface IClassLoader {

	String getPathSeparator();
	String getRootAsString();
	String getCanonicalName(String name);
	List<String> find(String name, String alias, boolean recursive, boolean regex, boolean matchCase);
}
