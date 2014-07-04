package main.java.initRecherche;

import main.java.coordinates.NullCoordinateException;
import main.java.unitModel.UnitModel;

public class BiaisedInputUnitModel extends UnitModel{

	public static double ALPHA = 0.3;
	
	public static int INPUT = 0;
	public static int PREDICTION = 1;
	
	public BiaisedInputUnitModel() {
		
	}
	
	@Override
	public double compute() throws NullCoordinateException {
		
		double input = params.getIndex(INPUT).getIndex(coord);
		double predict = params.getIndex(PREDICTION).getIndex(coord);
		
		//double h = ALPHA*predict+(1-ALPHA)*main.java.input;
		//System.out.println("+++++"+h+"++++"+main.java.input+"+++++"+predict);
		
		return ALPHA*predict+(1-ALPHA)*input;
	}

	
}
