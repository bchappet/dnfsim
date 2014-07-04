package main.java.unitModel;

import static java.lang.Math.PI;
import static java.lang.Math.cos;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;

public class CosTraj extends UnitModel<Double> {



	public final static int CENTER = 0;
	public final static int RADIUS = 1;
	public final static int PERIOD = 2;
	public final static int PHASE = 3;

	public CosTraj(Double initActivity) {
		super(initActivity);
	}




	@Override
	protected Double compute(BigDecimal time, int index, List<Parameter> params) {
		return this.compute2(
				((Number)params.get(CENTER).getIndex(index)).doubleValue(),
				((Number)params.get(RADIUS).getIndex(index)).doubleValue(),
				((Number)params.get(PERIOD).getIndex(index)).doubleValue(),
				((Number)params.get(PHASE).getIndex(index)).doubleValue(),
				time.doubleValue());
	}

	protected Double compute2(double center,double radius,double period,double phase,double time){
		double ret =  center+radius*
				cos(2*PI*(time/period-phase));
		return ret;
	}

}
