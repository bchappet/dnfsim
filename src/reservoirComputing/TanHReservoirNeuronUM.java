package reservoirComputing;

import maps.Parameter;
import maps.ParameterUser;
import maps.Var;
import coordinates.NullCoordinateException;
import coordinates.Space;
import unitModel.UnitModel;

/**
 * Classic tanh reservoir neuron
 * X k+1 = f(W*xk + Win * u k+1) WARNING we want U k+1
 * @author bchappet
 *
 */
public class TanHReservoirNeuronUM extends UnitModel {
	
	public static final int conv_WRR_R = 0;
	public static final int conv_WIR_I = 1;

	public TanHReservoirNeuronUM() {
	}

	public TanHReservoirNeuronUM(ParameterUser paramUser) {
		super(paramUser);
	}

	public TanHReservoirNeuronUM(Parameter dt, Space space,
			Parameter... parameters) {
		super(dt, space, parameters);
	}

	public TanHReservoirNeuronUM(Var dt, Space space, UnitModel... unitModels) {
		super(dt, space, unitModels);
	}

	@Override
	public double compute() throws NullCoordinateException {
		return Math.tanh(getParam(conv_WRR_R).get(coord) + getParam(conv_WIR_I).get(coord));
	}

}
