package initRecherche;

import unitModel.UnitModel;
import coordinates.NullCoordinateException;

public class PonderationUnitModel extends UnitModel {
	
	public final static int POTENTIAL = 0;
	public final static int PREDICTION = 1; 
	
	public PonderationUnitModel() {
		super();
	}

	@Override
	public double compute() throws NullCoordinateException {
	 //System.out.println("************"+params.get(PREDICTION).getName());
		double potential = params.get(POTENTIAL).get(coord);
		double prediction = params.get(PREDICTION).get(coord);
		double res = potential-prediction;

		return res*res;
	}

}
