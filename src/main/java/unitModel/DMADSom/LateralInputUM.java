package main.java.unitModel.DMADSom;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;
import main.java.maps.Unit;
import main.java.space.Coord;
import main.java.unitModel.NeighborhoodUnitModel;

public class LateralInputUM extends NeighborhoodUnitModel<Double> {
	
	//params
	private static final int LATERAL_W = 0;
	//neigh
	private static final int AFFERENT_INPUT = 0;

	public LateralInputUM(Double defaultValue) {
		super(defaultValue);
	}

	@Override
	protected Double compute(BigDecimal time, int index, List<Parameter> params) {
		
		Unit[] neigh = this.neighborhoods.get(AFFERENT_INPUT);
		//System.out.println("index : " + index);
		Coord<Double> weights = (Coord<Double>) params.get(LATERAL_W).getIndex(index);
		double res = 0;
		for(int i = 0 ; i < neigh.length ; i++){
			res += (Double)neigh[i].get() *  weights.getIndex(i);
		}
		
		return res;
		
	}

}
