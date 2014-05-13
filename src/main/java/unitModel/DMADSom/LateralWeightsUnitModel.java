package main.java.unitModel.DMADSom;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.space.Coord;
import main.java.unitModel.NeighborhoodUnitModel;


/**
 * Update the neurons lateral weight to the four neighboors
 * @author benoit
 *
 */
public class LateralWeightsUnitModel extends NeighborhoodUnitModel<Coord<Double>> {
	//NB data
	private static final int NB_DATA = 4;
	
	//Parameters
	private static final int LEARNING_RATE = 0;
	private static final int LATERAL_ALPHA = 1;
	private static final int AFFERENT_ACTIVITY = 2;
	private static final int THIS = 3;
	//Neighboor
	private static final int AFFERENT_ACTIVITY_NEIGH = 0;
	
	
	

	public LateralWeightsUnitModel(Coord<Double> initActivity) {
		super(initActivity);
	}

	@Override
	protected Coord<Double> compute(BigDecimal time, int index,
			List<Parameter> params) {
		List<Double> values = new ArrayList<Double>(NB_DATA);
		
		double min = 0.4;
		double max = 0.4;
		for(int i_neigh = 0 ; i_neigh < NB_DATA ; i_neigh++){
			
			double Xi = (Double) params.get(AFFERENT_ACTIVITY).getIndex(index);
			double latAlpha = (Double) params.get(LATERAL_ALPHA).getIndex(index);
			double learningrate = (Double)params.get(LEARNING_RATE).getIndex(index);
			
			double lastVal = ((Coord<Double>) params.get(THIS).getIndex(index)).getIndex(i_neigh);
			double Xj = (Double) neighborhoods.get(AFFERENT_ACTIVITY_NEIGH)[i_neigh].get();
			
			double delta_w = learningrate*(Xi*latAlpha-Xj);
			
			values.add(this.born((lastVal + delta_w),min,max));//TODO
			
			
		}
		return new Coord<Double>(values);
	}
	private Double born(double d,double min, double max) {
		if(d > max){
			return max;
		}
		else if(d < min){
			return min;
		}else{
			return d;
		}
	}

}
