package main.java.unitModel;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;
/**
 * Perform an addition of the given parameters
 * @author bchappet
 *
 */
public class Sum extends UnitModel<Double> {

	public Sum(Double init) {
		super(init);
	}




	@Override
	protected Double compute(BigDecimal time, int index, List<Parameter> params) {
		double ret = 0;
//		System.out.println("params : " + params);
		for(Parameter p : params)
		{
//			System.out.print( "; " + ret +" += " +p.getIndex(index));
			ret += ((Number)p.getIndex(index)).doubleValue();
		}
//		System.out.println();
               
        return ret;
	}

}
