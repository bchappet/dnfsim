package main.java.pfspike;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.network.generic.AccumulationUnitMap;
import main.java.unitModel.UnitModel;

public class PFSInputUnitModel extends UnitModel<BigDecimal>  {
	
	//private AccumulationUnitMap accumulationUnitMap;
	
	//private double coeff;
	
	public static final int ACCUMULATION_MAP = 0;
	public static final int COEFFICIENT = 1;
	
	public PFSInputUnitModel(/*AccumulationUnitMap accumulationUnitMap,double coeff*/) {
		super(BigDecimal.ZERO);
		/*this.accumulationUnitMap = accumulationUnitMap;
		this.coeff = coeff;*/
	}

	@Override
	protected BigDecimal compute(BigDecimal time, int index,
			List<Parameter> params) {
		AccumulationUnitMap accumulationUnitMap = (AccumulationUnitMap) params.get(ACCUMULATION_MAP);
		Var<BigDecimal> coeff =  (Var<BigDecimal>) params.get(COEFFICIENT);
		return new BigDecimal(accumulationUnitMap.getIndex(index) * coeff.get().doubleValue());
	}
}
