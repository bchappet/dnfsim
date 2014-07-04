package main.java.unitModel;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;

/**
 * Always memory
 * @author benoit
 *
 */
public class RateCodedUnitModel extends UnitModel<Double> {

	public RateCodedUnitModel(Double initActivity) {
		super(initActivity);
	}

	public final static int POTENTIAL = 0;
	public final static int TAU =1;
	public final static int INPUT = 2;
	public final static int CNFT = 3;
	public final static int H = 4;
	public final static int DT = 5;
	
	

	
	
	protected double computation2(double potential,double tau, double input,double cnft,double h ,double dt) {
//		System.out.println(potential +"+" +dt+"/"+tau+"*(-"+potential +"+" +input +"+"+cnft +"+"+ h +")" );
		return Math.max(0, potential + dt/tau*(-potential + input +cnft + h  ));
//		if(input > 0.2)
//			System.out.println(input  + " dt " + dt);
		//double ret =  Math.max(0, input );
//		System.out.println(ret);
	//	return ret;
	}

	@Override
	protected Double compute(BigDecimal time, int index, List<Parameter> params) {
		return computation2(((Number)params.get(POTENTIAL).getIndex(index)).doubleValue(), 
				((Number)params.get(TAU).getIndex(index)).doubleValue(), ((Number)params.get(INPUT).getIndex(index)).doubleValue(),
				((Number)params.get(CNFT).getIndex(index)).doubleValue(), ((Number)params.get(H).getIndex(index)).doubleValue(),
				((Number)params.get(DT).getIndex(index)).doubleValue());
	}

}
