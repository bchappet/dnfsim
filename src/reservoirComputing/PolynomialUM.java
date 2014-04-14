package reservoirComputing;

import maps.Parameter;
import maps.ParameterUser;
import maps.Var;
import coordinates.NullCoordinateException;
import coordinates.Space;
import unitModel.UnitModel;

public class PolynomialUM extends UnitModel {
	
	public static final int INPUT = 0;
	/**
	 * Unif distrib between -1,1
	 * Its space should be  p * p-1
	 * It should be static
	 */
	public static final int UNIFORM_DISTRIB = 1;
	public static final int MEMORY = 2;
	public static final int DEGREE = 3;

	public PolynomialUM() {
	}

	public PolynomialUM(ParameterUser paramUser) {
		super(paramUser);
	}

	public PolynomialUM(Parameter dt, Space space, Parameter... parameters) {
		super(dt, space, parameters);
	}

	public PolynomialUM(Var dt, Space space, UnitModel... unitModels) {
		super(dt, space, unitModels);
	}

	@Override
	public double compute() throws NullCoordinateException {
		double res = 0;
		int p = (int) getParam(DEGREE).get(coord);
		int d = (int) getParam(MEMORY).get(coord);
		Parameter u = getParam(INPUT);
		Parameter c = getParam(UNIFORM_DISTRIB);
		
		
		for(int i = 0 ; i <= p ; i++){
			for(int j = 0 ; j < p ; j++){
				res += c.getFast(i,j) * Math.pow(u.get(coord), i) * Math.pow(u.getDelay(d, coord), j);
			}
		}
		
		return res;
	}

}
