package codeteacher.struct.eval;

import codeteacher.StructuralAnalyzr;
import utils.ReflectionUtils;

public class ClassEvaluator implements StructuralEvaluator {
	
	private String src;
	private Class<?> klazz;

	public ClassEvaluator(Class<?> clazz, String src) {
		this.klazz = clazz;
		this.src = src;
	}

	public boolean eval(){
		ClassLoader otherLoader = ReflectionUtils.getClassLoader(src);
		try {
			Class<?> otherClazz = otherLoader.loadClass(klazz.getName());
			// TODO consertar isso!
			StructuralAnalyzr.setOtherClazz(otherClazz);
		} catch (ClassNotFoundException e) {
			return true;
		}
		return false;
	}
	
	public String getMessage(){
		return "Classe " + klazz.getName() + " não encontrada!"; 
	}
	
}
