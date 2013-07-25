package unitModel;

import maps.Parameter;
import maps.ParameterUser;
import maps.Var;
import coordinates.NullCoordinateException;
import coordinates.Space;

/**
 * Not very 
 * @author bchappet
 *
 */
public abstract class TrajectoryUnitModel extends UnitModel {

	public TrajectoryUnitModel(ParameterUser paramUser)
	{
		super(paramUser);
		
	}

	public TrajectoryUnitModel(Var dt, Space space, Parameter... parameters) {
		super(dt, space, parameters);
	}

	public TrajectoryUnitModel(Var dt, Space space, UnitModel... unitModels) {
		super(dt, space, unitModels);
	}


	

	@Override
	public abstract double compute() throws NullCoordinateException; 

}
