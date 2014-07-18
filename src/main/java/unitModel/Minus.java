package main.java.unitModel;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;
/**
 * Perform an subtraction of A - B of the given parameters
 * @author bchappet
 *
 */
public class Minus extends UnitModel<Double> {

	
	public static final int A = 0;
	public static final int B = 1;
	
	
	public Minus(Double init) {
		super(init);
	}




	@Override
	protected Double compute(BigDecimal time, int index, List<Parameter> params) {
//			System.out.println( "; " + ((Number)params.get(A).getIndex(index)).doubleValue() +" - " +((Number)params.get(B).getIndex(index)).doubleValue());
			return  ((Number)params.get(A).getIndex(index)).doubleValue() - ((Number)params.get(B).getIndex(index)).doubleValue();
               
	}

}
