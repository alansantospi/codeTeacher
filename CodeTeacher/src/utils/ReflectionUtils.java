package utils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import sun.reflect.ReflectionFactory;

/**
 * <b>ReflectionUtils</b>
 * <p>
 * This class provides useful methods which makes dealing with reflection much
 * easier, especially when working with Bukkit
 * <p>
 * You are welcome to use it, modify it and redistribute it under the following
 * conditions:
 * <ul>
 * <li>Don't claim this class as your own
 * <li>Don't remove this disclaimer
 * </ul>
 * <p>
 * <i>It would be nice if you provide credit to me if you use this class in a
 * published project</i>
 * 
 * @author DarkBlade12
 * @version 1.1
 */
public final class ReflectionUtils {
	// Prevent accidental construction
	private ReflectionUtils() {
	}

	/**
	 * Returns the constructor of a given class with the given parameter types
	 * 
	 * @param clazz          Target class
	 * @param parameterTypes Parameter types of the desired constructor
	 * @return The constructor of the target class with the specified parameter
	 *         types
	 * @throws NoSuchMethodException If the desired constructor with the specified
	 *                               parameter types cannot be found
	 * @see DataType
	 * @see DataType#getPrimitive(Class[])
	 * @see DataType#compare(Class[], Class[])
	 */
	public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes)
			throws NoSuchMethodException {
		Class<?>[] primitiveTypes = DataType.getPrimitive(parameterTypes);
		for (Constructor<?> constructor : clazz.getConstructors()) {
			if (!DataType.compare(DataType.getPrimitive(constructor.getParameterTypes()), primitiveTypes)) {
				continue;
			}
			return constructor;
		}
		throw new NoSuchMethodException(
				"There is no such constructor in this class with the specified parameter types");
	}

	public static Constructor<?>[] getConstructors(Class<?> clazz) {
		return clazz.getConstructors();
	}

	public static Constructor<?>[] getConstructors(Class<?> clazz, boolean declared) {
		return declared ? clazz.getDeclaredConstructors() : clazz.getConstructors();
	}

	/**
	 * Returns an instance of a class with the given arguments
	 * 
	 * @param clazz     Target class
	 * @param arguments Arguments which are used to construct an object of the
	 *                  target class
	 * @return The instance of the target class with the specified arguments
	 * @throws InstantiationException    If you cannot create an instance of the
	 *                                   target class due to certain circumstances
	 * @throws IllegalAccessException    If the desired constructor cannot be
	 *                                   accessed due to certain circumstances
	 * @throws IllegalArgumentException  If the types of the arguments do not match
	 *                                   the parameter types of the constructor
	 *                                   (this should not occur since it searches
	 *                                   for a constructor with the types of the
	 *                                   arguments)
	 * @throws InvocationTargetException If the desired constructor cannot be
	 *                                   invoked
	 * @throws NoSuchMethodException     If the desired constructor with the
	 *                                   specified arguments cannot be found
	 */
	public static Object instantiateObject(Class<?> clazz, Object... arguments) throws InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		return getConstructor(clazz, DataType.getPrimitive(arguments)).newInstance(arguments);
	}

	/**
	 * Returns a method of a class with the given parameter types
	 * 
	 * @param clazz          Target class
	 * @param methodName     Name of the desired method
	 * @param parameterTypes Parameter types of the desired method
	 * @return The method of the target class with the specified name and parameter
	 *         types
	 * @throws NoSuchMethodException If the desired method of the target class with
	 *                               the specified name and parameter types cannot
	 *                               be found
	 * @see DataType#getPrimitive(Class[])
	 * @see DataType#compare(Class[], Class[])
	 */
	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes)
			throws NoSuchMethodException {
		Class<?>[] primitiveTypes = DataType.getPrimitive(parameterTypes);
		for (Method method : clazz.getMethods()) {
			if (!method.getName().equals(methodName)
					|| !DataType.compare(DataType.getPrimitive(method.getParameterTypes()), primitiveTypes)) {
				continue;
			}
			return method;
		}
		throw new NoSuchMethodException(
				"There is no such method in this class with the specified name and parameter types");
	}

	public static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes)
			throws NoSuchMethodException {

		Class<?>[] primitiveTypes = DataType.getPrimitive(parameterTypes);
		for (Method method : clazz.getDeclaredMethods()) {
			if (!method.getName().equals(methodName)
					|| !DataType.compare(DataType.getPrimitive(method.getParameterTypes()), primitiveTypes)) {
				continue;
			}
			return method;
		}
		throw new NoSuchMethodException(
				"There is no such method in this class with the specified name and parameter types");
	}

	public static Method[] getMethods(Class<?> clazz) {
		return clazz.getMethods();
	}

	public static Method[] getMethods(Class<?> clazz, boolean declared) {
		return declared ? clazz.getDeclaredMethods() : clazz.getMethods();
	}

	public static Method[] getDeclaredMethods(Class<?> clazz) {
		return clazz.getDeclaredMethods();
	}

	/**
	 * Invokes a method on an object with the given arguments
	 * 
	 * @param instance   Target object
	 * @param methodName Name of the desired method
	 * @param arguments  Arguments which are used to invoke the desired method
	 * @return The result of invoking the desired method on the target object
	 * @throws IllegalAccessException    If the desired method cannot be accessed
	 *                                   due to certain circumstances
	 * @throws IllegalArgumentException  If the types of the arguments do not match
	 *                                   the parameter types of the method (this
	 *                                   should not occur since it searches for a
	 *                                   method with the types of the arguments)
	 * @throws InvocationTargetException If the desired method cannot be invoked on
	 *                                   the target object
	 * @throws NoSuchMethodException     If the desired method of the class of the
	 *                                   target object with the specified name and
	 *                                   arguments cannot be found
	 * @see #getMethod(Class, String, Class...)
	 * @see DataType#getPrimitive(Object[])
	 */
	public static Object invokeMethod(Object instance, String methodName, Object... arguments)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		return getMethod(instance.getClass(), methodName, DataType.getPrimitive(arguments)).invoke(instance, arguments);
	}

	public static Object invokeMethod(Class<?> klazz, String methodName, Object... arguments)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		Class<?>[] primitive = DataType.getPrimitive(arguments);
		Method declaredMethod = getDeclaredMethod(klazz, methodName, primitive);
		if (declaredMethod != null) {
			return declaredMethod.invoke(null, arguments);
		}
		return null;
	}

	/**
	 * Invokes a method of the target class on an object with the given arguments
	 * 
	 * @param instance   Target object
	 * @param clazz      Target class
	 * @param methodName Name of the desired method
	 * @param arguments  Arguments which are used to invoke the desired method
	 * @return The result of invoking the desired method on the target object
	 * @throws IllegalAccessException    If the desired method cannot be accessed
	 *                                   due to certain circumstances
	 * @throws IllegalArgumentException  If the types of the arguments do not match
	 *                                   the parameter types of the method (this
	 *                                   should not occur since it searches for a
	 *                                   method with the types of the arguments)
	 * @throws InvocationTargetException If the desired method cannot be invoked on
	 *                                   the target object
	 * @throws NoSuchMethodException     If the desired method of the target class
	 *                                   with the specified name and arguments
	 *                                   cannot be found
	 * @see #getMethod(Class, String, Class...)
	 * @see DataType#getPrimitive(Object[])
	 */
	public static Object invokeMethod(Object instance, Class<?> clazz, String methodName, Object... arguments)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		return getMethod(clazz, methodName, DataType.getPrimitive(arguments)).invoke(instance, arguments);
	}

	/**
	 * Returns a field of the target class with the given name
	 * 
	 * @param clazz     Target class
	 * @param declared  Whether the desired field is declared or not
	 * @param fieldName Name of the desired field
	 * @return The field of the target class with the specified name
	 * @throws NoSuchFieldException If the desired field of the given class cannot
	 *                              be found
	 * @throws SecurityException    If the desired field cannot be made accessible
	 */
	public static Field getField(Class<?> clazz, boolean declared, String fieldName)
			throws NoSuchFieldException, SecurityException {
		Field field = declared ? clazz.getDeclaredField(fieldName) : clazz.getField(fieldName);
		field.setAccessible(true); 
		return field;
	}

	public static Field[] getFields(Class<?> clazz, boolean declared) {
		Field fields[] = declared ? clazz.getDeclaredFields() : clazz.getFields();
		for (Field f : fields) {
			f.setAccessible(true);
		}
		return fields;
	}

	/**
	 * Returns the value of a field of the given class of an object
	 * 
	 * @param instance  Target object
	 * @param clazz     Target class
	 * @param declared  Whether the desired field is declared or not
	 * @param fieldName Name of the desired field
	 * @return The value of field of the target object
	 * @throws IllegalArgumentException If the target object does not feature the
	 *                                  desired field
	 * @throws IllegalAccessException   If the desired field cannot be accessed
	 * @throws NoSuchFieldException     If the desired field of the target class
	 *                                  cannot be found
	 * @throws SecurityException        If the desired field cannot be made
	 *                                  accessible
	 * @see #getField(Class, boolean, String)
	 */
	public static Object getValue(Object instance, Class<?> clazz, boolean declared, String fieldName)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return getField(clazz, declared, fieldName).get(instance);
	}

	/**
	 * Returns the value of a field with the given name of an object
	 * 
	 * @param instance  Target object
	 * @param declared  Whether the desired field is declared or not
	 * @param fieldName Name of the desired field
	 * @return The value of field of the target object
	 * @throws IllegalArgumentException If the target object does not feature the
	 *                                  desired field (should not occur since it
	 *                                  searches for a field with the given name in
	 *                                  the class of the object)
	 * @throws IllegalAccessException   If the desired field cannot be accessed
	 * @throws NoSuchFieldException     If the desired field of the target object
	 *                                  cannot be found
	 * @throws SecurityException        If the desired field cannot be made
	 *                                  accessible
	 * @see #getValue(Object, Class, boolean, String)
	 */
	public static Object getValue(Object instance, boolean declared, String fieldName)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return getValue(instance, instance.getClass(), declared, fieldName);
	}

	/**
	 * Sets the value of a field of the given class of an object
	 * 
	 * @param instance  Target object
	 * @param clazz     Target class
	 * @param declared  Whether the desired field is declared or not
	 * @param fieldName Name of the desired field
	 * @param value     New value
	 * @throws IllegalArgumentException If the type of the value does not match the
	 *                                  type of the desired field
	 * @throws IllegalAccessException   If the desired field cannot be accessed
	 * @throws NoSuchFieldException     If the desired field of the target class
	 *                                  cannot be found
	 * @throws SecurityException        If the desired field cannot be made
	 *                                  accessible
	 * @see #getField(Class, boolean, String)
	 */
	public static void setValue(Object instance, Class<?> clazz, boolean declared, String fieldName, Object value)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		getField(clazz, declared, fieldName).set(instance, value);
	}

	/**
	 * Sets the value of a field with the given name of an object
	 * 
	 * @param instance  Target object
	 * @param declared  Whether the desired field is declared or not
	 * @param fieldName Name of the desired field
	 * @param value     New value
	 * @throws IllegalArgumentException If the type of the value does not match the
	 *                                  type of the desired field
	 * @throws IllegalAccessException   If the desired field cannot be accessed
	 * @throws NoSuchFieldException     If the desired field of the target object
	 *                                  cannot be found
	 * @throws SecurityException        If the desired field cannot be made
	 *                                  accessible
	 * @see #setValue(Object, Class, boolean, String, Object)
	 */
	public static void setValue(Object instance, boolean declared, String fieldName, Object value)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		setValue(instance, instance.getClass(), declared, fieldName, value);
	}

	/**
	 * Represents an enumeration of Java data types with corresponding classes
	 * <p>
	 * This class is part of the <b>ReflectionUtils</b> and follows the same usage
	 * conditions
	 * 
	 * @author DarkBlade12
	 * @since 1.0
	 */
	public enum DataType {
		BYTE(byte.class, Byte.class), SHORT(short.class, Short.class), INTEGER(int.class, Integer.class),
		LONG(long.class, Long.class), CHARACTER(char.class, Character.class), FLOAT(float.class, Float.class),
		DOUBLE(double.class, Double.class), BOOLEAN(boolean.class, Boolean.class);

		private static final Map<Class<?>, DataType> CLASS_MAP = new HashMap<Class<?>, DataType>();
		private final Class<?> primitive;
		private final Class<?> reference;

		// Initialize map for quick class lookup
		static {
			for (DataType type : values()) {
				CLASS_MAP.put(type.primitive, type);
				CLASS_MAP.put(type.reference, type);
			}
		}

		/**
		 * Construct a new data type
		 * 
		 * @param primitive Primitive class of this data type
		 * @param reference Reference class of this data type
		 */
		private DataType(Class<?> primitive, Class<?> reference) {
			this.primitive = primitive;
			this.reference = reference;
		}

		/**
		 * Returns the primitive class of this data type
		 * 
		 * @return The primitive class
		 */
		public Class<?> getPrimitive() {
			return primitive;
		}

		/**
		 * Returns the reference class of this data type
		 * 
		 * @return The reference class
		 */
		public Class<?> getReference() {
			return reference;
		}

		/**
		 * Returns the data type with the given primitive/reference class
		 * 
		 * @param clazz Primitive/Reference class of the data type
		 * @return The data type
		 */
		public static DataType fromClass(Class<?> clazz) {
			return CLASS_MAP.get(clazz);
		}

		/**
		 * Returns the primitive class of the data type with the given reference class
		 * 
		 * @param clazz Reference class of the data type
		 * @return The primitive class
		 */
		public static Class<?> getPrimitive(Class<?> clazz) {
			DataType type = fromClass(clazz);
			return type == null ? clazz : type.getPrimitive();
		}

		/**
		 * Returns the reference class of the data type with the given primitive class
		 * 
		 * @param clazz Primitive class of the data type
		 * @return The reference class
		 */
		public static Class<?> getReference(Class<?> clazz) {
			DataType type = fromClass(clazz);
			return type == null ? clazz : type.getReference();
		}

		/**
		 * Returns the primitive class array of the given class array
		 * 
		 * @param classes Given class array
		 * @return The primitive class array
		 */
		public static Class<?>[] getPrimitive(Class<?>[] classes) {
			int length = classes == null ? 0 : classes.length;
			Class<?>[] types = new Class<?>[length];
			for (int index = 0; index < length; index++) {
				types[index] = getPrimitive(classes[index]);
			}
			return types;
		}

		/**
		 * Returns the reference class array of the given class array
		 * 
		 * @param classes Given class array
		 * @return The reference class array
		 */
		public static Class<?>[] getReference(Class<?>[] classes) {
			int length = classes == null ? 0 : classes.length;
			Class<?>[] types = new Class<?>[length];
			for (int index = 0; index < length; index++) {
				types[index] = getReference(classes[index]);
			}
			return types;
		}

		/**
		 * Returns the primitive class array of the given object array
		 * 
		 * @param object Given object array
		 * @return The primitive class array
		 */
		public static Class<?>[] getPrimitive(Object[] objects) {
			int length = objects == null ? 0 : objects.length;
			Class<?>[] types = new Class<?>[length];
			for (int index = 0; index < length; index++) {
				types[index] = getPrimitive(objects[index].getClass());
			}
			return types;
		}

		/**
		 * Returns the reference class array of the given object array
		 * 
		 * @param object Given object array
		 * @return The reference class array
		 */
		public static Class<?>[] getReference(Object[] objects) {
			int length = objects == null ? 0 : objects.length;
			Class<?>[] types = new Class<?>[length];
			for (int index = 0; index < length; index++) {
				types[index] = getReference(objects[index].getClass());
			}
			return types;
		}

		/**
		 * Compares two class arrays on equivalence
		 * 
		 * @param primary   Primary class array
		 * @param secondary Class array which is compared to the primary array
		 * @return Whether these arrays are equal or not
		 */
		public static boolean compare(Class<?>[] primary, Class<?>[] secondary) {
			if (primary == null || secondary == null || primary.length != secondary.length) {
				return false;
			}
			for (int index = 0; index < primary.length; index++) {
				Class<?> primaryClass = primary[index];
				Class<?> secondaryClass = secondary[index];

				// TODO Isso aqui fui eu
//				if (primaryClass.equals(secondaryClass) || primaryClass.isAssignableFrom(secondaryClass)) {
				if (primaryClass.getName().equals(secondaryClass.getName())
						|| primaryClass.isAssignableFrom(secondaryClass)) {
					continue;
				}
				return false;
			}
			return true;
		}
	}

	public static Class<?> getSuperClass(Class<?> clazz) {
		return clazz.getSuperclass();
	}

	public static Class<?>[] getInterfaces(Class<?> clazz) {
		return clazz.getInterfaces();
	}

	public static boolean isImplemented(Class<?> aClass, Class<?> anInterface) {
		for (Class<?> i : getInterfaces(aClass)) {
			if (i.equals(anInterface)) {
				return true;
			}
		}
		return false;
	}

	public static ClassLoader getClassLoader(String dir) {
		File baseDir = new File(dir);
		ClassLoader cl = null;
		try {
			// Convert File to a URL
			URL url = baseDir.toURL(); // file:/c:/myclasses/
			URL[] urls = new URL[] { url };
			// Create a new class loader with the directory
			cl = new URLClassLoader(urls);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return cl;
	}

	public enum ClassMember {
		CONSTRUCTOR, FIELD, METHOD, CLASS, ALL
	}

	public static boolean isPrivate(Class<?> klazz, String memberName, boolean declared, boolean regex,
			boolean matchCase) {
		return isPrivate(klazz, memberName, declared, regex, matchCase, ClassMember.ALL);
	}

	public static boolean isPublic(Class<?> klazz, String memberName, boolean declared, boolean regex,
			boolean matchCase) {
		return isPublic(klazz, memberName, declared, regex, matchCase, ClassMember.ALL);
	}
	
	public static boolean isProtected(Class<?> klazz, String memberName, boolean declared, boolean regex,
			boolean matchCase) {
		return isProtected(klazz, memberName, declared, regex, matchCase, ClassMember.ALL);
	}
	
	public static boolean isStatic(Class<?> klazz, String memberName, boolean declared, boolean regex,
			boolean matchCase) {
		return isStatic(klazz, memberName, declared, regex, matchCase, ClassMember.ALL);
	}
	
	public static boolean isAbstract(Class<?> klazz, String memberName, boolean declared, boolean regex,
			boolean matchCase) {
		return isAbstract(klazz, memberName, declared, regex, matchCase, ClassMember.ALL);
	}
	
	public static boolean isFinal(Class<?> klazz, String memberName, boolean declared, boolean regex,
			boolean matchCase) {
		return isFinal(klazz, memberName, declared, regex, matchCase, ClassMember.ALL);
	}
	
	public static Collection<Member> getMembers(Class<?> klazz, String memberName, boolean declared, boolean regex,
			boolean matchCase, ClassMember classMember) {
		Member[] members = getMembers(klazz, declared, classMember);
		Pattern p = Pattern.compile(memberName);
		List<Member> someMembers = new ArrayList<Member>();
		for (Member member : members) {
			if (regex && p.matcher(member.getName()).matches()) {
				someMembers.add(member);
			} else if (matchCase && memberName.equalsIgnoreCase(member.getName())) {
				someMembers.add(member);
			} else {
				if (memberName.equals(member.getName())) {
					someMembers.add(member);
				}
			}
		}
		return someMembers;
	}

	public static boolean isPrivate(Class<?> klazz, String memberName, boolean declared, boolean regex,
			boolean matchCase, ClassMember classMember) {
		Collection<Member> members = getMembers(klazz, memberName, declared, regex, matchCase, classMember);
		boolean isPrivate = false;
		for (Member member : members) {
			isPrivate = isPrivate(member);
			if (!isPrivate) {
				break;
			}
		}
		return isPrivate;
	}

	public static boolean isPublic(Class<?> klazz, String memberName, boolean declared, boolean regex, boolean matchCase, ClassMember classMember) {
		Collection<Member> members = getMembers(klazz, memberName, declared, regex, matchCase, classMember);
		boolean isPublic = false;
		for (Member member : members) {
			isPublic = isPublic(member);
			if (!isPublic) {
				break;
			}
		}
		return isPublic;
	}

	public static boolean isProtected(Class<?> klazz, String memberName, boolean declared, boolean regex, boolean matchCase, ClassMember classMember) {
		Collection<Member> members = getMembers(klazz, memberName, declared, regex, matchCase, classMember);
		boolean isProtected = false;
		for (Member member : members) {
			isProtected = isProtected(member);
			if (!isProtected) {
				break;
			}
		}
		return isProtected;
	}
	
	public static boolean isStatic(Class<?> klazz, String memberName, boolean declared, boolean regex, boolean matchCase, ClassMember classMember) {
		Collection<Member> members = getMembers(klazz, memberName, declared, regex, matchCase, classMember);
		boolean isStatic = false;
		for (Member member : members) {
			isStatic = isStatic(member);
			if (!isStatic) {
				break;
			}
		}
		return isStatic;
	}
	
	public static boolean isFinal(Class<?> klazz, String memberName, boolean declared, boolean regex, boolean matchCase, ClassMember classMember) {
		Collection<Member> members = getMembers(klazz, memberName, declared, regex, matchCase, classMember);
		boolean isFinal = false;
		for (Member member : members) {
			isFinal = isFinal(member);
			if (!isFinal) {
				break;
			}
		}
		return isFinal;
	}
	
	public static boolean isAbstract(Class<?> klazz, String memberName, boolean declared, boolean regex, boolean matchCase, ClassMember classMember) {
		Collection<Member> members = getMembers(klazz, memberName, declared, regex, matchCase, classMember);
		boolean isAbstract = false;
		for (Member member : members) {
			isAbstract = isAbstract(member);
			if (!isAbstract) {
				break;
			}
		}
		return isAbstract;
	}

	private static Member[] getMembers(Class<?> klazz, boolean declared, ClassMember classMember) {
		Member[] constructors = new Member[0];
		Member[] fields = new Member[0];
		Member[] methods = new Member[0];

		switch (classMember) {
		case CONSTRUCTOR:
			constructors = getConstructors(klazz, declared);
			break;
		case FIELD:
			fields = getFields(klazz, declared);
			break;
		case METHOD:
			methods = getMethods(klazz, declared);
			break;
		case CLASS:
			// members = getClasses(klazz, declared);
			break;
		case ALL:
			constructors = getConstructors(klazz);
			fields = getFields(klazz, declared);
			methods = getMethods(klazz, declared);

			break;
		default:

		}
		List<Member> memberList = new ArrayList<Member>();
		memberList.addAll(new ArrayList<>(Arrays.asList(constructors)));
		memberList.addAll(new ArrayList<>(Arrays.asList(fields)));
		memberList.addAll(new ArrayList<>(Arrays.asList(methods)));
		return memberList.toArray(new Member[memberList.size()]);
	}

	public static Class<?>[] getClasses(Class<?> klazz, boolean declared) {
		if (declared) {
			return klazz.getDeclaredClasses();
		}
		return klazz.getClasses();
	}

	public static boolean isPrivate(Member member) {
		int mod = member.getModifiers();
		return Modifier.isPrivate(mod);
	}

	public static boolean isPublic(Member member) {
		int mod = member.getModifiers();
		return Modifier.isPublic(mod);
	}

	public static boolean isProtected(Member member) {
		int mod = member.getModifiers();
		return Modifier.isProtected(mod);
	}

	public static boolean isStatic(Member member) {
		int mod = member.getModifiers();
		return Modifier.isStatic(mod);
	}

	public static boolean isFinal(Member member) {
		int mod = member.getModifiers();
		return Modifier.isFinal(mod);
	}

	public static boolean isAbstract(Member member) {
		int mod = member.getModifiers();
		return Modifier.isAbstract(mod);
	}

	public static boolean isOverriden(Method method, Class<?> clazz, String name)
			throws NoSuchMethodException, SecurityException {
		return clazz.getMethod(name).getDeclaringClass().equals(clazz);
	}

	public static Class<?> loadClass(String klazzName) throws ClassNotFoundException {
//		if (!findLoadedClass(klazzName)){
//			Class<?> forName = Class.forName(klazzName);
//			return forName;
//		}
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		return cl.loadClass(klazzName);
	}

	public static boolean findLoadedClass(String klazzName) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		Method m = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
		m.setAccessible(true);
		return (Boolean) m.invoke(null, klazzName);
	}

	public static Object instantiateWithoutConstructor(Class<?> clazz) throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ReflectionFactory rf = ReflectionFactory.getReflectionFactory();
		Constructor<?> objDef = Object.class.getDeclaredConstructor();
		Constructor<?> intConstr = rf.newConstructorForSerialization(clazz, objDef);
		return clazz.cast(intConstr.newInstance());
	}
	
	public List<Field> getFields(Class<?> klazz, Class<?> type, boolean declared) {
		List<Field> fields = new ArrayList<Field>();
		Field[] allFields;
		if (declared) {
			allFields = klazz.getDeclaredFields();
		} else {
			allFields = klazz.getFields();
		}
		for (Field field : allFields) {
			if (field.getType().isAssignableFrom(type)) {
				fields.add(field);
			}
		}
		return fields;
	}
}