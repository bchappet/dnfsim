package hardSimulator;

import maps.Parameter;
import maps.Var;
import unitModel.UnitModel;
import utils.ArrayUtils;
import coordinates.NullCoordinateException;
import coordinates.Space;

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
