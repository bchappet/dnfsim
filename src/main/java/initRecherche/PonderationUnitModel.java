package main.java.initRecherche;

import main.java.coordinates.NullCoordinateException;
import main.java.unitModel.UnitModel;

public class PonderationUnitModel extends UnitModel {
	
	public final static int POTENTIAL = 0;
	public final static int PREDICTION = 1; 
	
	public PonderationUnitModel() {
		super();
	}

	@Override
	public double compute() throws NullCoordinateException {
	 //System.out.println("************"+params.get(PREDICTION).getName());
		double potential = params.getIndex(POTENTIAL).getIndex(coord);
		double prediction = params.getIndex(PREDICTION).getIndex(coord);
		double res = potential-prediction;

		return res*res;
	}

}
