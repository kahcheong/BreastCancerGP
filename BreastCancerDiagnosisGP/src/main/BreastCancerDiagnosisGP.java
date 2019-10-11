package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;

@SuppressWarnings("serial")

public class BreastCancerDiagnosisGP extends GPProblem implements SimpleProblemForm  {
	
	public static final String P_DATA = "data";
	
	public float radius_mean;
	public float texture_mean;
	public float perimeter_mean;
	public float area_mean;
	public float smoothness_mean;
	public float compactness_mean;
	public float concavity_mean;
	public float concavePoints_mean;
	public float isymmetry_mean;
	public float fractalDimension_mean;
	public float radius_SE;
	public float texture_SE;
	public float perimeter_SE;
	public float area_SE;
	public float smoothness_SE;
	public float compactness_SE;
	public float concavity_SE;
	public float concavePoints_SE;
	public float isymmetry_SE;
	public float fractalDimension_SE;
	public float radius_largest;
	public float texture_largest;
	public float perimeter_largest;
	public float area_largest;
	public float smoothness_largest;
	public float compactness_largest;
	public float concavity_largest;
	public float concavePoints_largest;
	public float isymmetry_largest;
	public float fractalDimension_largest;
	
	public ArrayList<String> readIn  = new ArrayList<String>();
	public ArrayList<String> trainAnswer = new ArrayList<String>();
	public ArrayList<String> testAnswer = new ArrayList<String>();
	public ArrayList<float[]> trainData = new ArrayList<float[]>();
	public ArrayList<float[]> testData = new ArrayList<float[]>();
	public float trainSplit;
	
	public void setup(final EvolutionState state, final Parameter base) {
		// very important, remember this
		super.setup(state,base);
			
		// verify our input is the right class (or subclasses from it)
		if (!(input instanceof FloatData)) {
			state.output.fatal("GPData class must subclass from " + FloatData.class,
				base.push(P_DATA), null);
		}
		readFromFile();
		//creates a random number between 0.25-0.75 to determine the percentage that is used for training.
		trainSplit = (float) ((Math.random()*0.5) + 0.25); 
		createTestTrainSet ();

	}

	public void evaluate(final EvolutionState state, 
            final Individual ind, 
            final int subpopulation,
            final int threadnum) {
		if (!ind.evaluated) {  // don't bother reevaluating
		   
		   FloatData input = (FloatData)(this.input);
		
		   int hits = 0;
		   float sum = 0;
		   
		   String expectedResult;
		   
		   for (int i = 0; i < trainData.size(); i++) {
			   float result = 0;
			   
			   radius_mean = trainData.get(i)[0];
			   texture_mean = trainData.get(i)[1];
			   perimeter_mean = trainData.get(i)[2];
			   area_mean = trainData.get(i)[3];
			   smoothness_mean = trainData.get(i)[4];
			   compactness_mean= trainData.get(i)[5];
			   concavity_mean= trainData.get(i)[6];
			   concavePoints_mean= trainData.get(i)[7];
			   isymmetry_mean= trainData.get(i)[8];
			   fractalDimension_mean= trainData.get(i)[9];
			   radius_SE= trainData.get(i)[10];
			   texture_SE= trainData.get(i)[11];
			   perimeter_SE= trainData.get(i)[12];
			   area_SE= trainData.get(i)[13];
			   smoothness_SE= trainData.get(i)[14];
			   compactness_SE= trainData.get(i)[15];
			   concavity_SE= trainData.get(i)[16];
			   concavePoints_SE= trainData.get(i)[17];
			   isymmetry_SE= trainData.get(i)[18];
			   fractalDimension_SE= trainData.get(i)[19];
			   radius_largest= trainData.get(i)[20];
			   texture_largest= trainData.get(i)[21];
			   perimeter_largest= trainData.get(i)[22];
			   area_largest= trainData.get(i)[23];
			   smoothness_largest= trainData.get(i)[24];
			   compactness_largest= trainData.get(i)[25];
			   concavity_largest= trainData.get(i)[26];
			   concavePoints_largest= trainData.get(i)[27];
			   isymmetry_largest= trainData.get(i)[28];
			   fractalDimension_largest= trainData.get(i)[29];
		       expectedResult = trainAnswer.get(i);
		       
		       ((GPIndividual)ind).trees[0].child.eval(
		           state,threadnum,input,stack,((GPIndividual)ind),this);
		       if (input.x >= 0.0 && expectedResult.equals("M")) {
		    	   hits ++;
		       } else if (input.x < 0.0 && expectedResult.equals("B")) {
		    	   hits ++;
		       }   else {
		    	   //result = Math.max(1, Math.abs(input.x));
		    	   result = 1;
		       };
		       sum += result;
		   }
		   sum /= trainData.size();
		   // the fitness better be KozaFitness!
		   KozaFitness f = ((KozaFitness)ind.fitness);
		   f.setStandardizedFitness(state, sum);
		   f.hits = hits;
		   ind.evaluated = true;
		}
	}
	
	@Override
	public void describe(EvolutionState state, 
	    	Individual ind,
	    	int subpopulation, 
	    	int threadnum, 
	    	int log) {
		 
	    super.describe(state, ind, subpopulation, threadnum, log);
	 		   
	 		FloatData input = (FloatData)(this.input);
	 		
	 		int hits = 0;
	 		float sum = 0;
	 		String expectedResult;
	 		   
	 		for (int i = 0; i < testData.size(); i++) {
	 			float result = 0;
	 		
				   radius_mean = testData.get(i)[0];
				   texture_mean = testData.get(i)[1];
				   perimeter_mean = testData.get(i)[2];
				   area_mean = testData.get(i)[3];
				   smoothness_mean = testData.get(i)[4];
				   compactness_mean= testData.get(i)[5];
				   concavity_mean= testData.get(i)[6];
				   concavePoints_mean= testData.get(i)[7];
				   isymmetry_mean= testData.get(i)[8];
				   fractalDimension_mean= testData.get(i)[9];
				   radius_SE= testData.get(i)[10];
				   texture_SE= testData.get(i)[11];
				   perimeter_SE= testData.get(i)[12];
				   area_SE= testData.get(i)[13];
				   smoothness_SE= testData.get(i)[14];
				   compactness_SE= testData.get(i)[15];
				   concavity_SE= testData.get(i)[16];
				   concavePoints_SE= testData.get(i)[17];
				   isymmetry_SE= testData.get(i)[18];
				   fractalDimension_SE= testData.get(i)[19];
				   radius_largest= testData.get(i)[20];
				   texture_largest= testData.get(i)[21];
				   perimeter_largest= testData.get(i)[22];
				   area_largest= testData.get(i)[23];
				   smoothness_largest= testData.get(i)[24];
				   compactness_largest= testData.get(i)[25];
				   concavity_largest= testData.get(i)[26];
				   concavePoints_largest= testData.get(i)[27];
				   isymmetry_largest= testData.get(i)[28];
				   fractalDimension_largest= testData.get(i)[29];
			       expectedResult = testAnswer.get(i);
			       
 		       ((GPIndividual)ind).trees[0].child.eval(
 		           state,threadnum,input,stack,((GPIndividual)ind),this);
	 		
 		      if (input.x >= 0.0 && expectedResult.equals("M")) {
		    	   hits ++;
		       } else if (input.x < 0.0 && expectedResult.equals("B")) {
		    	   hits ++;
		       }   else {
		    	   //result = Math.max(1, Math.abs(input.x));
		    	   result = 1;
		       };
		       sum += result;
		   }
		   sum /= testData.size();
		   // the fitness better be KozaFitness!
		   KozaFitness f = ((KozaFitness)ind.fitness);
		   f.setStandardizedFitness(state, sum);
		   f.hits = hits;
		   ind.evaluated = true;
    }
	
	private void createTestTrainSet() {
		Collections.shuffle(readIn);
		int trainNum = (int) (trainSplit * readIn.size());
		
		//building training data into useable data types
		for (int i = 0; i < trainNum; i ++) {
			String[] temp = readIn.get(i).split(",");
			trainAnswer.add(temp[1]);
			float[] temp2 = new float[temp.length-2];
			
			for (int j = 0; j < 30; j ++) {
				temp2[j] = Float.parseFloat(temp[j+2]);
			}
			trainData.add(temp2);
			
		}
		
		//building testing data into useable data types
		for (int i = trainNum; i < readIn.size(); i ++) {
			String[] temp = readIn.get(i).split(",");
			testAnswer.add(temp[1]);
			float[] temp2 = new float[temp.length-2];
			
			for (int j = 0; j < 30; j ++) {
				temp2[0] = Float.parseFloat(temp[j+2]);
			}
			testData.add(temp2);
			
		}
		
		 
		
	}
	
	//reads in data from file into useable lines of input stored into a list
	private void readFromFile() {
        Scanner scan;
        File file = new File("src/main/wdbc.data");
        try {
            scan = new Scanner(file);
            while(scan.hasNextLine())
            {
            	String input = scan.nextLine();
            	readIn.add(input);
            }
            
        } catch (FileNotFoundException e1) {
                e1.printStackTrace();
        }
    }

}
