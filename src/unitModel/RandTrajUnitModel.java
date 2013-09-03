package unitModel;

import maps.Parameter;
import maps.Var;
import coordinates.NullCoordinateException;
import coordinates.Space;
/**
 * 
 * @author bchappet
 *
 */
public class RandTrajUnitModel extends UnitModel {
	
	public static final int CENTER = 0;
	public static final int RADIUS = 1;

	public RandTrajUnitModel(Var dt, Space space, Parameter... parameters) {
		super(dt, space, parameters);
	}

	@Override
	public double compute() throws NullCoordinateException {
		return params.get(CENTER).get(coord) + params.get(RADIUS).get()*(2*Math.random()-1);
	}
	
	

}
