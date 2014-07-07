package main.java.unitModel;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;
/**
 * 
 * @author bchappet
 *
 */
public class RandTrajUnitModel extends UnitModel<Double> {
	
	public RandTrajUnitModel(Double initActivity) {
		super(initActivity);
		
	}
	public static final int CENTER = 0;//double
	public static final int RADIUS = 1;//double
	@Override
	protected Double compute(BigDecimal time, int index,
			List<Parameter> params) {
		return(((Number) params.get(CENTER).getIndex(index))).doubleValue() + 
				(((Number)params.get(RADIUS).getIndex(index))).doubleValue() *(2*Math.random()-1);
	}

	
	

}
