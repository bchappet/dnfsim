package main.java.unitModel;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.Var;

public class CosTraj extends UnitModel {
	
	public final static int CENTER = 0;
	public final static int RADIUS = 1;
	public final static int PERIOD = 2;
	public final static int PHASE = 3;



	public CosTraj(Var dt, Space space, Parameter center,Parameter radius,Parameter period,Parameter phase) {
		super(dt, space, center,radius,period,phase);
	}

	@Override
	public double compute() throws NullCoordinateException {
		double ret =  params.getIndex(CENTER).get()+params.getIndex(RADIUS).get()*
				cos(2*PI*(time.get()/params.getIndex(PERIOD).get()-params.getIndex(PHASE).get()));
		return ret;
	}

}
