package functions;

import main.BreastCancerDiagnosisGP;
import main.FloatData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

@SuppressWarnings("serial")
public class FractalDimension_mean extends GPNode {

	public String toString() { return "fractalDimension_mean"; }

    public int expectedChildren() { return 0; }
	
    @Override
    public void eval(final EvolutionState state,
            final int thread,
            final GPData input,
            final ADFStack stack,
            final GPIndividual individual,
            final Problem problem) {
    	FloatData rd = ((FloatData)(input));
    	rd.x = ((BreastCancerDiagnosisGP)problem).fractalDimension_mean;
    }
}