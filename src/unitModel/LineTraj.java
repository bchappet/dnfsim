package unitModel;

import maps.Parameter;
import maps.ParameterUser;
import maps.Var;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class LineTraj extends UnitModel {
	/**
	 * Params :
	 * SPEED in unit per second
	 */
	protected static final int SPEED = 0;
	

	public LineTraj() {
	}


	public LineTraj(Parameter dt, Space space, Parameter... parameters) {
		super(dt, space, parameters);
	}

	public LineTraj(Var dt, Space space, UnitModel... unitModels) {
		super(dt, space, unitModels);
	}

	@Override
	public double compute() throws NullCoordinateException {
		return time.val * getParam(SPEED).get(coord);
	}

}
