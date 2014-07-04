package main.java.unitModel;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.ParameterUser;
import main.java.maps.Var;

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
		return time.val * getParam(SPEED).getIndex(coord);
	}

}
