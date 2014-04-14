package unitModel;

import maps.Parameter;
import maps.Var;
import coordinates.NoDimSpace;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class ConstantUnit extends UnitModel {
	
	public static final int VAR = 0;

	public ConstantUnit(Parameter dt, Space space, Parameter... parameters) {
		super(dt, space, parameters);
		this.activity = (Var) params.get(VAR);
	}

	public ConstantUnit(Var var) {
		super(new Var(0.),new NoDimSpace(),var);
		this.activity = (Var) params.get(VAR);
	}

	public ConstantUnit(double d) {
		this(new Var(d));
	}

	@Override
	public double compute() throws NullCoordinateException {
		return params.get(VAR).get();
	}

}
