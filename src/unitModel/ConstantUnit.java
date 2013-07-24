package unitModel;

import maps.Parameter;
import maps.Var;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class ConstantUnit extends UnitModel {
	
	public static final int VAR = 0;

	public ConstantUnit(Var dt, Space space, Parameter... parameters) {
		super(dt, space, parameters);
		this.activity = (Var) params.get(VAR);
	}

	public ConstantUnit(Var var) {
		super();
		this.addParameters(var);
		this.activity = (Var) params.get(VAR);
	}

	@Override
	public double compute() throws NullCoordinateException {
		return params.get(VAR).get();
	}

}
