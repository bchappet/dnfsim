package unitModel;

import maps.Parameter;
import maps.ParameterUser;
import maps.Var;
import coordinates.NullCoordinateException;
import coordinates.Space;

/**
 * Uniform random dsitribution between a and b
 * @author bchappet
 *
 */
public class UniformRandomUM extends UnitModel {

	public static final int A = 0;
	public static final int B = 1;

	public UniformRandomUM(ParameterUser paramUser) {
		super(paramUser);
	}

	public UniformRandomUM(Parameter dt, Space space, Parameter... parameters) {
		super(dt, space, parameters);
	}

	public UniformRandomUM(Var dt, Space space, UnitModel... unitModels) {
		super(dt, space, unitModels);
	}

	@Override
	public double compute() throws NullCoordinateException {
		double a = getParam(A).get(coord);
		double b = getParam(B).get(coord);
		return a + (b-a)*Math.random();
	}

}
