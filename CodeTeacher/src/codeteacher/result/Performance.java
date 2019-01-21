package codeteacher.result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import codeteacher.err.Error;
import codeteacher.err.ErrorFixer;

public class Performance {
	
	private String student;
	private float hitsPercentage;
	private Collection<Error> errors;
	private Map<Error, ErrorFixer> errorFixers = new HashMap<Error, ErrorFixer>();
	private Map<String, String> aliases = new HashMap<String, String>();
	private String solutionPath;
	
	public Performance(String student) {
		this.setStudent(student);
		errors = new ArrayList<Error>();
	}

	public String getStudent() {
		return student;
	}

	public void setStudent(String student) {
		this.student = student;
	}
	
	public void addError(Error error){
		errors.add(error);
	}
	
	public void addErrors(Collection<Error> errors) {
		this.errors.addAll(errors);
	}
	
	public int getTotalErrors(){
		int total = 0;
		for (Error error : errors){
			total += error.getValue();
		}
		return total;
	}
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append(student + "\n");
		for (Error error : errors) {
			buff.append(error + "\n");
		}
		buff.append("Total Erros: " + this.getTotalErrors() + "\n");
		buff.append("Perc. Acertos: " + this.getHitsPercentage() + "\n");
		return buff.toString();
	}
	
	public void calcHitsPercentage(int criterios){
		int acertos = getHits(criterios);
		setHintsPecentage( (acertos * 100) / criterios );
	}

	private void setHintsPecentage(float perc) {
		hitsPercentage = perc;		
	}
	
	public float getHitsPercentage(){
		return hitsPercentage;
	}

	private int getHits(int criterios) {
		
		for (Error error : errors) {
			if (error.isFatal()){
				return 0;
			}
		}
		
		int acertos = criterios - this.getTotalErrors();
		return acertos;
	}
	
	public Error getError(String e){
		for (Error error : errors) {
			if (error.getMessage().equals(e)){
				return error;
			} 
		}
		return null;
	}

	public Collection<Error> getErrors() {
		return errors;
	}
	
	public void addErrorFixer(Error error, ErrorFixer fixer) {
		errorFixers.put(error, fixer);
	}

	public Map<Error, ErrorFixer> getErrorFixers() {
		return errorFixers;
	}

	public Map<String, String> getAliases() {
		return aliases;
	}
	
	public String getAlias(String key) {
		return aliases.get(key);
	}
	
	public void addAlias(String key, String value) {
		aliases.put(key, value);
	}

	public String getSolutionPath() {
		return solutionPath;
	}

	public void setSolutionPath(String solutionPath) {
		this.solutionPath = solutionPath;
	}
}
