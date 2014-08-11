package main.scripts.gui;

import java.util.List;

import main.java.console.CommandLineFormatException;
import main.java.controler.ComputableControler;
import main.java.controler.ParameterControler;
import main.java.maps.Parameter;
import main.java.reservoirComputing.LearningWeightMatrix;
import main.resources.utils.ArrayUtils;

public class LearningWeightMatrixControler extends ComputableControler {

	public LearningWeightMatrixControler(Parameter param) {
		super(param);
	}
	
	public void learnWeights() throws CommandLineFormatException{
		((LearningWeightMatrix)this.getParam()).computeOutputWeights();
	}

	public double[] getArray1D(){
		Parameter param = this.getParam();
		List<? extends Number> list = param.getValues(); 
		return ArrayUtils.listToPrimitiveArray1D(list);
	}

}
