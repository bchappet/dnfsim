package main.java.unitModel.DMADSom;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import main.java.maps.Parameter;
import main.java.space.Coord2D;
import main.java.unitModel.UnitModel;

public class AfferentWeightsUnitModel extends UnitModel<Coord2D<Double>> {
	
	//parameters
	private static final int AFFERENT_LEARNING_RATE = 0;
	private static final int MODULATION_MAP = 1;
	private static final int INPUT = 2;
	private static final int THIS = 3;

	public AfferentWeightsUnitModel(Coord2D<Double> initActivity) {
		super(initActivity);
	}

	@Override
	protected Coord2D<Double> compute(BigDecimal time, int index,
			List<Parameter> params) {
		List<Double> res = new ArrayList<Double>(2);
		double learnrate = (Double) params.get(AFFERENT_LEARNING_RATE).getIndex(index);
		double modulation = (Double) params.get(MODULATION_MAP).getIndex(index);
		
		for(int i = 0 ; i  < 2 ; i++){
			double input = ((Coord2D<Double>) params.get(INPUT).getIndex(index)).getIndex(i);
			double previous = ((Coord2D<Double>) params.get(THIS).getIndex(index)).getIndex(i);
			double deltaW = learnrate * modulation * (input - previous);
			res.add(previous+deltaW);
		}
		return new Coord2D<Double>((ArrayList<Double>) res);
	}

}
