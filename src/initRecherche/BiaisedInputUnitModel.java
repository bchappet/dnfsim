package initRecherche;

import unitModel.UnitModel;
import coordinates.NullCoordinateException;

public class BiaisedInputUnitModel extends UnitModel{

	public static double ALPHA = 0.3;
	
	public static int INPUT = 0;
	public static int PREDICTION = 1;
	
	public BiaisedInputUnitModel() {
		
	}
	
	@Override
	public double compute() throws NullCoordinateException {
		
		double input = params.get(INPUT).get(coord);
		double predict = params.get(PREDICTION).get(coord);
		
		//double h = ALPHA*predict+(1-ALPHA)*input;
		//System.out.println("+++++"+h+"++++"+input+"+++++"+predict);
		
		return ALPHA*predict+(1-ALPHA)*input;
	}

	
}
