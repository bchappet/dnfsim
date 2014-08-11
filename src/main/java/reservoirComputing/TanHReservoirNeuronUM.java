package main.java.reservoirComputing;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;
import main.java.unitModel.UnitModel;

/**
 * Classic tanh reservoir neuron
 * X k+1 = f(W*xk + Win * u k+1) WARNING we want U k+1 //TODO
 * @author bchappet
 *
 */
public class TanHReservoirNeuronUM extends UnitModel<Double> {
	
	public static final int LEAK = 0;
	public static final int RESERVOIR = 1;
	public static final int conv_WRR_R = 2;
	public static final int conv_WIR_I = 3;

	public TanHReservoirNeuronUM(Double initActivity) {
		super(initActivity);
	}



	@Override
	protected Double compute(BigDecimal time, int index,List<Parameter> params) {
		double leak = (double) params.get(LEAK).getIndex(index);
		double x_1 = (double) params.get(RESERVOIR).getIndex(index);
		double input = (double) params.get(conv_WIR_I).getIndex(index);
		double lateral = (double) params.get(conv_WRR_R).getIndex(index);
		
		
		return Math.tanh((1-leak)*x_1 +  leak*( input + lateral));
	}

}
