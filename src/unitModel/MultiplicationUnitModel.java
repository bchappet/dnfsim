package unitModel;

import java.util.Arrays;

import maps.Parameter;
import maps.ParameterUser;
import maps.Var;
import coordinates.NullCoordinateException;
import coordinates.Space;

/**
 * Params
	A and B
	return A*b
 * @author bchappet
 *
 */
public class MultiplicationUnitModel extends UnitModel {
	
	

	public MultiplicationUnitModel() {
		// TODO Auto-generated constructor stub
	}

	public MultiplicationUnitModel(ParameterUser paramUser) {
		super(paramUser);
	}

	public MultiplicationUnitModel(Parameter dt, Space space,
			Parameter... parameters) {
		super(dt, space, parameters);
		//System.out.println(Arrays.toString(parameters));
	}

	public MultiplicationUnitModel(Var dt, Space space, UnitModel... unitModels) {
		super(dt, space, unitModels);
	}

	@Override
	public double compute() throws NullCoordinateException {
		return getParam(0).get(coord)* getParam(1).get(coord);
	}

}
