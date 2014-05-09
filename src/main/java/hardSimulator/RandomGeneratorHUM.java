package main.java.hardSimulator;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.unitModel.UnitModel;
import main.resources.utils.ArrayUtils;

public class RandomGeneratorHUM extends UnitModel {
	
	
	
	//output
	protected double[] outputs; //8 random numbers
	
	public RandomGeneratorHUM(Parameter dt, Space space, Parameter... parameters) {
		super(dt,space,parameters);
		outputs = new double[8];
	}
	

	@Override
	public double compute() throws NullCoordinateException {
		
		 for(int i = 0 ; i < 8 ; i++){
		 	outputs[i] = Math.random();
		 }
		 
		 return ArrayUtils.avg(outputs);
	}


	public double[] getOutputs() {
		return outputs;
	}
	
	
		 
		 

}
