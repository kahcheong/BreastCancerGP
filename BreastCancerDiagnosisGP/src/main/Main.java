package main;

import ec.Evolve;

public class Main {
	public static void main(String[] args) {
		String pathToFiles = "/Users/David/Desktop/WDBC Regression Output/";
		int numberOfJobs = 1;
//		String statisticType = "ec.gp.koza.KozaShortStatistics";
		String[] runConfig = new String[] {
				Evolve.A_FILE, "src/main/regression.params", 
//				"-p", ("stat="+statisticType), 
				"-p", ("stat.file=$"+pathToFiles+"out.stat"), 
				"-p", ("jobs="+numberOfJobs)
				};
		Evolve.main(runConfig);
	}
}