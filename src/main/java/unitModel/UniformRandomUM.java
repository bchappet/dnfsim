package main.java.unitModel;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;

/**
 * Uniform random dsitribution between a and b
 * @author bchappet
 *
 */
public class UniformRandomUM extends UnitModel<Double> {

	


	public static final int A = 0;
	public static final int B = 1;
	
	
	public UniformRandomUM(Double initActivity) {
		super(initActivity);
	}


	@Override
	protected Double compute(BigDecimal time, int index,List<Parameter> params) {
		Double a = ((Number) params.get(A).getIndex(index)).doubleValue();
		Double b = ((Number) params.get(B).getIndex(index)).doubleValue();
		return a + (b-a)*Math.random();
	}

}
