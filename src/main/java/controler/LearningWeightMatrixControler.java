package main.java.controler;

import java.util.List;

import main.java.console.CommandLineFormatException;
import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.reservoirComputing.ComputationOutOfMemoryError;
import main.java.reservoirComputing.DeterminantErrror;
import main.java.reservoirComputing.LearningWeightMatrix;
import main.java.reservoirComputing.RidgeRegressionLearningWeightMatrix;
import main.resources.utils.ArrayUtils;

public class LearningWeightMatrixControler extends MapControler {

	public LearningWeightMatrixControler(Map param) {
		super(param);
	}
	
	public void learnWeights() throws CommandLineFormatException, DeterminantErrror, ComputationOutOfMemoryError{
		((LearningWeightMatrix)this.getParam()).computeOutputWeights();
	}
	
	public void resetStates(){
		((LearningWeightMatrix)this.getParam()).resetStates();
	}

	public double[] getArray1D(){
		Parameter param = this.getParam();
		List<? extends Number> list = param.getValues(); 
		return ArrayUtils.listToPrimitiveArray1D(list);
	}

}
