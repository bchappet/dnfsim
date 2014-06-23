package main.java.unitModel;

import java.math.BigDecimal;
import java.util.List;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.Var;
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
		//System.out.println("params : " + params);
		for(Parameter p : params)
		{//System.out.print( "; " + ret +" += " +p.get(coord));
			ret += ((Number)p.getIndex(index)).doubleValue();
		}
               
        return ret;
	}

}