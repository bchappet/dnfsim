package main.java.unitModel;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.ParameterUser;
import main.java.maps.Var;

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
