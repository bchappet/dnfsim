package main.java.statistics;

import java.util.List;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.AbstractMap;
import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.unitModel.UnitModel;

public abstract class StatMap extends Map {
	
	
	public StatMap(String theName,Parameter dt, Space space, Parameter... maps)
	{
		super(theName,null,dt,space,maps);
		this.unitModel = new UnitModel(this){
			@Override
			public double compute(){
				return computeStatistic();
			}
		};
	}
	
	public void reset(){
		super.reset();
		this.time.set(0);
		//one need to reset here the state of the statMap if any
	}
	
	/**
	 * Compute activity knowing the values of parameters at specific coord
	 * @param param
	 * @return
	 * @throws NullCoordinateException
	 */
	public abstract double computeStatistic();

}
