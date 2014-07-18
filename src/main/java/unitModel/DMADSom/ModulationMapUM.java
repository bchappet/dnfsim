package main.java.unitModel.DMADSom;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;
import main.java.space.Coord;
import main.java.unitModel.UnitModel;

public class ModulationMapUM extends UnitModel<Double> {
	
	//parameters
	private static final int LATERAL_WEIGHTS = 0;
	private static final int POTENTIAL = 1;

	public ModulationMapUM(Double initActivity) {
		super(initActivity);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Double compute(BigDecimal time, int index, List<Parameter> params) {
		double potential = (Double) params.get(POTENTIAL).getIndex(index);
		double prod = 1;
		
		Coord<Double> weights = (Coord<Double>) params.get(LATERAL_WEIGHTS).getIndex(index);
		for(int i = 0 ; i < weights.getSize() ; i++){
			prod *= weights.getIndex(i);
		}
		return potential*prod;
	}

}
