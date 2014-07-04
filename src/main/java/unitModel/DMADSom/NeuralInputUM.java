package main.java.unitModel.DMADSom;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import main.java.maps.Parameter;
import main.java.space.Coord2D;
import main.java.unitModel.UnitModel;

public class NeuralInputUM extends UnitModel<Double> {
	
	//Parameter
	private static final int INPUT = 0;
	private static final int SIGMA = 1;
	private static final int AFF_W = 2;

	public NeuralInputUM(Double initActivity) {
		super(initActivity);
	}

	@Override
	protected Double compute(BigDecimal time, int index,
			List<Parameter> params) {
		double ret = 1;
		
		Coord2D<Double> input = (Coord2D<Double>) params.get(INPUT).getIndex(index);
		Coord2D<Double> affW = (Coord2D<Double>) params.get(AFF_W).getIndex(index);
		double sigma = (Double) params.get(SIGMA).getIndex(index);
		
		for(int i = 0 ; i < 2 ; i++){
			ret *= Math.exp(-Math.pow(input.getIndex(i)-affW.getIndex(i),2)/(2*sigma*sigma));
		}
		
		
		
		return ret;
	}

}
