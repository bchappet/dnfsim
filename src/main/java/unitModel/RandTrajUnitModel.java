package main.java.unitModel;

import java.math.BigDecimal;
import java.util.List;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.Var;
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
		return((Double) params.get(CENTER).getIndex(index)) + ((Double)params.get(RADIUS).getIndex(index)) *(2*Math.random()-1);
	}

	
	

}
