package main.java.unitModel;


import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.maps.UnitMap;


/**
 * Non-linear AutoRegressive Moving Average
 * see jaeger's adaptative nonlinear system identification with echo state networks
 * 
 * X(t+1) = 0.3 X(t) + 0.05 X(t) [ sum_{i=0}^{n-1} X(t-i)] +1.5X(t-(n-1))*X(t) +0.1
 * 
 * The order is given in parameter
 * You have to init memory of map with the number of order
 * 
 *TODO optimize
 * @author bchappet
 *
 */
public class NARMAnthOrderUM extends UnitModel<Double> {
	
	
	/**
	 * Imput to generate NARMA (usualy random between [0,0.5]
	 */
	protected static final int INPUT = 0;
	/**Map containing this unit model**/
	protected static final int MAP = 1;
	/**
	 * Order of the NARMA system
	 */
	protected static final int ORDER = 2;
	


	public NARMAnthOrderUM(Double initActivity) {
		super(initActivity);
	}

	@Override
	protected Double compute(BigDecimal time, int index, List<Parameter> params) {
		UnitMap<Double,?> map = (UnitMap<Double, ?>) params.get(MAP);
		UnitMap<Double,?> input = (UnitMap<Double,?>) params.get(INPUT);
		int order  = (int) params.get(ORDER).getIndex(index);
		double Xt = map.getIndex(index);
		double Yt_n = input.getDelay(index, order-1);
		double Yt = input.getIndex(index);
		double memSum = 0;
		for(int i = 0 ; i < order ; i++){
			memSum += map.getDelay(index, i);
		}
		
		double Xtp1 = 0.3 * Xt + 0.05 * Xt*memSum + 1.5*Yt_n*Yt+0.1;
		
		return Xtp1;
		
	}

}
