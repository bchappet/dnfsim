package main.java.reservoirComputing;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;
import main.java.maps.UnitMap;
import main.java.space.Coord;
import main.java.unitModel.UnitModel;

public class PolynomialUM extends UnitModel<Double> {
	
	public static final int INPUT = 0;
	/**
	 * Unif distrib between -1,1
	 * Its main.java.space should be  p * p-1
	 * It should be static
	 */
	public static final int UNIFORM_DISTRIB = 1;
	public static final int MEMORY = 2;
	public static final int DEGREE = 3;

	public PolynomialUM(Double initActivity) {
		super(initActivity);
	}



	@Override
	protected Double compute(BigDecimal time, int index, List<Parameter> params) {
		double res = 0;
		int p = (Integer) params.get(DEGREE).getIndex(index);
		int d = (Integer) params.get(MEMORY).getIndex(index);
		UnitMap<Double,Integer> u = (UnitMap<Double,Integer>) params.get(INPUT);
		UnitMap<Double,Integer> c = (UnitMap<Double,Integer>) params.get(UNIFORM_DISTRIB);
		
		
		for(int i = 0 ; i <= p ; i++){
			for(int j = 0 ; j < p ; j++){
//				System.out.println("i:"+i + " j" + j);
//				System.out.println("index : " +c.getSpace().coordToIndex(new Coord<Integer>(i,j)));
//				System.out.println("delay : " + d);
//				
				res += c.getIndex(c.getSpace().coordToIndex(new Coord<Integer>(i,j))) * Math.pow(u.getIndex(index), i) * Math.pow(u.getDelay(index, d), j);
			}
		}
		
		return res;
	}

}
