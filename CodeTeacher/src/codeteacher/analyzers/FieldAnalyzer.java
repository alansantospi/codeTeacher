package codeteacher.analyzers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;

public class FieldAnalyzer extends MemberAnalyzer {

	private String type;
	
	public FieldAnalyzer(ClassAnalyzer parent, boolean declared, String name, boolean regex, boolean matchCase) {
		this.parent = parent;
		this.declared = declared;
		this.name = name;
		this.regex = regex;
		this.matchCase = matchCase;
	}
	
	public FieldAnalyzer(ClassAnalyzer parent, String type, boolean declared, String name, boolean regex, boolean matchCase) {
		this(parent, declared, name, regex, matchCase);
		this.type = type;
	}
	
	public FieldAnalyzer(ClassAnalyzer classAnalyzer, String fieldType, boolean declared, String fieldName,
			boolean fieldRegex, boolean fieldMatchCase, int value) {
		this(classAnalyzer, fieldType, declared, fieldName, fieldRegex, fieldMatchCase);
		this.value = value;
	}

	public FieldAnalyzer(Class<?> clazz, boolean declared, String name, boolean regex, boolean matchCase) {
		this.klazz = clazz;
		this.declared = declared;
		this.name = name;
		this.regex = regex;
		this.matchCase = matchCase;
	}

	public boolean isError() {
		return !match();
	}
	
	public Error getError() {
		ErrorType errorType = ErrorType.FIELD_NOT_FOUND;
		Error error = new Error(errorType, errorType.getMessage(name, type, getParentName()), getValue());
		return error;
	}

	public String getParentName() {
		return parent.getMemberName();
	}
	
	public boolean isDeclared() {
		return declared;
	}
	
	private boolean match() {
		klazz = parent.getKlazz();
		if (klazz == null) {
			System.err.println("FieldAnalyzer.match(): " + "Class not set!");
			return true;
		}
		List<Field> someFields = new ArrayList<Field>();
		Field[] fields; 
		if (declared) {
			fields = klazz.getDeclaredFields();
		} else {
			fields = klazz.getFields();
		}
		
		for(Field aField : fields) {
			if (checkName(aField) && checkType(aField.getType())) {
				someFields.add(aField);
			}
		}
		
		return someFields.size() == 1;
	}
	
	private boolean checkName(Field aField) {
		String fieldName = aField.getName();
		if (regex) {
			Pattern p = Pattern.compile(name);
			return p.matcher(fieldName).matches(); 
		} else if (matchCase) {
			return name.equals(fieldName);
		} else {
			return name.equalsIgnoreCase(fieldName);
		}
	}
	
	private boolean checkType(Class<?> t1) {
		 
		return t1.getName().equals(type);
	}
	
	public String getType() {
		return type;
	}
	
	public FieldAnalyzer addPrivate() {
		add(new PrivateFieldAnalyzer(this));
		return this;
	}
	
	public FieldAnalyzer addPrivate(int value) {
		add(new PrivateFieldAnalyzer(this, value));
		return this;
	}
	
	public FieldAnalyzer addPublic() {
		add(new PublicFieldAnalyzer(this));
		return this;
	}
	
	public FieldAnalyzer addPublic(int value) {
		add(new PublicFieldAnalyzer(this, value));
		return this;
	}
	
	public FieldAnalyzer addPackage() {
		add(new PackageFieldAnalyzer(this));
		return this;
	}
	
	public FieldAnalyzer addPackage(int value) {
		add(new PackageFieldAnalyzer(this, value));
		return this;
	}
	
	public FieldAnalyzer addProtected() {
		add(new ProtectedFieldAnalyzer(this));
		return this;
	}
	
	public FieldAnalyzer addProtected(int value) {
		add(new ProtectedFieldAnalyzer(this, value));
		return this;
	}
	
	public FieldAnalyzer addStatic() {
		add(new StaticFieldAnalyzer(this));
		return this;
	}
	
	public FieldAnalyzer addStatic(int value) {
		add(new StaticFieldAnalyzer(this, value));
		return this;
	}
	
	public FieldAnalyzer addFinal() {
		add(new FinalFieldAnalyzer(this));
		return this;
	}
	
	public FieldAnalyzer addFinal(int value) {
		add(new FinalFieldAnalyzer(this, value));
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		List<ModifierAnalyzer> children = getChildren();
		for (ModifierAnalyzer child : children) {
			sb.append(child).append(" ");
		}
		sb.append(type + " ").append(name);
		
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (declared ? 1231 : 1237);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldAnalyzer other = (FieldAnalyzer) obj;
		if (declared != other.declared)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		
		return true;
	}

}
