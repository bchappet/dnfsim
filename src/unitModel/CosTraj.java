package unitModel;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import maps.Parameter;
import maps.Var;
import coordinates.NullCoordinateException;
import coordinates.Space;

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
		double ret =  params.get(CENTER).get()+params.get(RADIUS).get()*
				cos(2*PI*(time.get()/params.get(PERIOD).get()-params.get(PHASE).get()));
		return ret;
	}

}
