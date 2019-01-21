package output;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import codeteacher.Config;
import codeteacher.analyzers.Analyzer;
import codeteacher.result.Evaluation;
import codeteacher.result.Performance;
import gui.Project;
import gui.strategy.FileStrategy;
import org.junit.Test;

public class TestFileStrategy {

	@Test
	public void test() {
		String studentDir = "C:\\Users\\edina\\Downloads\\IFMA_CN_2018_1_DSOO-INFO3-M_trabs_Atividade_02\\";
		String projectName = "ComparandoObjetos";
		Config cfg = new Config(studentDir, projectName);
		Map<String, List<Analyzer>> tests = new HashMap<String, List<Analyzer>>();
		
		Project proj = new Project(cfg, tests, false, false, true);
		Evaluation eval = new FileStrategy().eval(proj);
		
		Map<String, Performance> perforMap = eval.getPerforMap();
		for (String a : perforMap.keySet()){
			System.out.println(a);
		}
	}
}
