package gui.strategy;

import java.util.Map;

import codeteacher.Config;
import codeteacher.result.Evaluation;
import codeteacher.result.Performance;
import gui.Project;

public abstract class OpenResourceStrategy {

	protected static final String SEPARATOR = Config.SEPARATOR;
	protected Evaluation eval = new Evaluation();
	protected String fileType;

	public abstract Evaluation eval(Project proj);

	protected void calcPerformance() {
		// TODO calcular os criterios
		Map<String, Performance> perforMap = eval.getPerforMap();
		for (String a : perforMap.keySet()) {
			Performance performance = perforMap.get(a);
			performance.calcHitsPercentage(Config.CRITERIA);
		}
	}
	
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
