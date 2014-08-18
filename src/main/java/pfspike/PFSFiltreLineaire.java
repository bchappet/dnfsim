package main.java.pfspike;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.network.generic.AccumulationUnitMap;
import main.java.unitModel.UnitModel;


/**
 * aX+b
 * @author nikolai
 *
 */
public class PFSFiltreLineaire extends UnitModel<BigDecimal>  {

	//private AccumulationUnitMap accumulationUnitMap;

	//private double coeff;

	public static final int X = 0;
	public static final int A = 1;
	public static final int B = 2;

	public PFSFiltreLineaire(/*AccumulationUnitMap accumulationUnitMap,double coeff*/) {
		super(BigDecimal.ZERO);
		/*this.accumulationUnitMap = accumulationUnitMap;
		this.coeff = coeff;*/
	}

	@Override
	protected BigDecimal compute(BigDecimal time, int index,
			List<Parameter> params) {
		double x = ((AccumulationUnitMap) params.get(X)).getIndex(index);
		double a;
		double b;
		double axpb = 0.0;
		if(x > 0){
			//UnitMap x = (UnitMap) params.get(X);
			a =  ((Var<BigDecimal>) params.get(A)).get().doubleValue();
			b =  ((Var<BigDecimal>) params.get(B)).get().doubleValue();
			System.out.println(a+"*"+x+"+"+b+"="+(a*x+b));
			axpb = a * x + b;
		}
		return new BigDecimal(axpb);
	}
}
