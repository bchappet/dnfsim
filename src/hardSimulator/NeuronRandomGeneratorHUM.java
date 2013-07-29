package hardSimulator;

import maps.Parameter;
import maps.Var;
import precision.PrecisionVar;
import unitModel.UnitModel;
import utils.ArrayUtils;
import utils.Hardware;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class NeuronRandomGeneratorHUM extends UnitModel {


	//Parameters
	protected static final int PROBA = 0;

	
	
	//out (the bit for : NSEW)
	protected int[] results;
	
	//in (the real for NSEW)
	protected double[] randomIn;

	public NeuronRandomGeneratorHUM(Parameter dt,Space space,Parameter... params){
		super(dt,space,params);
		results = new int[4];
		randomIn = new double[4];

	}

	

	@Override
	public double compute() throws NullCoordinateException {
		for(int i = 0 ; i < 4 ; i++){
			double proba =  params.get(PROBA).get(coord);
			double rand = randomIn[i];
			///System.out.println("Proba : " + proba + " rand " + rand);
			if(proba >= rand){
				//	System.out.println("Res = 1");
				results[i] = 1;
			}else{
				results[i] = 0;
			}
		}

		return ArrayUtils.sum(results);
	}

	

	public int[] getResults() {
		return results;
	}

	public void setRandomIn(double[] randomIn) {
		this.randomIn = randomIn;
	}

}
