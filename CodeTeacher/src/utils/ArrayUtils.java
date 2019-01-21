package utils;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ArrayUtils {

	public static <T> T[] newArrayOf(T[] t, int len){
		return (T[]) Array.newInstance(t.getClass().getComponentType(), len);
    }
	
    //For array of int
    public static int[] newArrayOf(int[] t, int len){
        return new int[len];
    }
    
    public static Object[] addElement(Object[] org, Object added) {
        Object[] result = Arrays.copyOf(org, org.length +1);
        result[org.length] = added;
        return result;
    }
}
