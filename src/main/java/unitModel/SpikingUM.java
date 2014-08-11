package main.java.unitModel;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;

public class SpikingUM extends UnitModel<Double> {
	public final static int POTENTIAL =0;
	public final static int THRESHOLD = 1;
	public final static int LOW = 2;
	public final static int HIGH = 3;

	public SpikingUM(Double initActivity) {
		super(initActivity);
	}
	

	/**
	 * 
	 * @param potential
	 * @param th : threshold
	 * @param low : value affected if the potential is lower than the threshold
	 * @param high : value affected if the potential is higher than the threshold
	 * @return
	 */
	protected double computation2(double potential, double th, double low, double high) {
		double ret;
		if(potential > th)
			ret = high;
		else 
			ret = low;
		
		return ret;
	}

	@Override
	protected Double compute(BigDecimal time, int index, List<Parameter> params) {
		return computation2( ((Number) params.get(POTENTIAL).getIndex(index)).doubleValue(),
				((Number)params.get(THRESHOLD).getIndex(index)).doubleValue(),
				((Number)params.get(LOW).getIndex(index)).doubleValue(),
				((Number)params.get(HIGH).getIndex(index)).doubleValue());
	}

}
