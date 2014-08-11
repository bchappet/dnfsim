package main.java.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import main.java.coordinates.NullCoordinateException;
import main.java.maps.Parameter;
import main.java.maps.SingleValueParam;
import main.java.maps.Track;
import main.java.space.Coord;
import main.java.space.DoubleSpace;
import main.java.space.Space;
import main.java.unitModel.UnitModel;

public class ExponentialND extends UnitModel<Double>  {
	
	//Parameters
	public static final int SPACE = 0;
		public static final int INTENSITY = 1;
		public static final int PROBA = 2;
		public static final int COORDS = 3;
		

	public ExponentialND(Double initActivity) {
		super(initActivity);
	}
	
	


	@Override
	protected Double compute(BigDecimal time, int index, List<Parameter> params) {
		Space<Double> space = (Space) params.get(SPACE);
		Coord<Integer> intCoor = space.indexToCoordInt(index);
		Double[] centerTmp = new Double[params.size()-COORDS];
		Coord<Double> center = new Coord<Double>(centerTmp);
		for(int i = 0 ; i < center.getSize(); i++){
			center.set(i,(Double) ((SingleValueParam) params.get(COORDS+i)).get());
		}
		
		Coord<Integer> intCenter = ((DoubleSpace) space).toIntCoord(center);//Discrete value of the center TODO no need to always compute it 
		
		double sumDist = 0;
		for(int i = 0 ; i < center.getSize(); i++){
			sumDist += Math.abs(intCoor.getIndex(i) - intCenter.getIndex(i));
		}
		return  ((Number)params.get(INTENSITY).getIndex(index)).doubleValue() * Math.pow((double) params.get(PROBA).getIndex(index), sumDist);
	}
	
	

}
