package codeteacher.err;

import java.util.ArrayList;
import java.util.List;

public class CompositeError extends Error {

	private List<Error> errors = new ArrayList<>();;
	
	public CompositeError() {
	}
	
	public CompositeError(ErrorType errorType) {
		super(errorType);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		errors.forEach( e -> {
			builder.append(e.toString().concat("\n"));
		});
		return builder.toString();
	}
	
	public void addError(Error err) {
		errors.add(err);
	}
	
	public int getTotal() {
		int total = 
		
		errors.stream().mapToInt(Error::getValue).sum();
		
		return total;
	}
}
