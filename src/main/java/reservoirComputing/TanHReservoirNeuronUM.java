package main.java.reservoirComputing;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;
import main.java.unitModel.UnitModel;

/**
 * 
 * @author bchappet
 *
 */
public class TanHReservoirNeuronUM extends UnitModel<Double> {
	
	
	protected static final int conv_WRR_R = 0;
	protected static final int conv_WIR_I = 1;
	protected static final int NOISE = 2;
	
//	public static final int ALPHA = 4; //lateral influence over input 0.5 = neutral

	public TanHReservoirNeuronUM(Double initActivity) {
		super(initActivity);
	}



	@Override
	protected Double compute(BigDecimal time, int index,List<Parameter> params) {
		double input = (double) params.get(conv_WIR_I).getIndex(index);
		double lateral = (double) params.get(conv_WRR_R).getIndex(index);
		double noise = (double) params.get(NOISE).getIndex(index);
		
		return Math.tanh(  input +  lateral + noise);
		
		
	}

}
