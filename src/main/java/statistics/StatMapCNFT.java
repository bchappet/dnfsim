package main.java.statistics;

import java.util.List;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.AbstractMap;
import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.unitModel.UnitModel;

public abstract class StatMapCNFT extends StatMap {
	
	/**List of the trackable objects**/
	protected List<AbstractMap> tracks;
	
	public StatMapCNFT(String theName,Parameter dt, Space space,List<AbstractMap> tracks, Parameter... maps)
	{
		super(theName,dt,space,maps);
		this.tracks = tracks;
		this.unitModel = new UnitModel(this){
			@Override
			public double compute(){
				return computeStatistic();
			}
		};
	}
	
	
	
	/**
	 * Compute activity knowing the values of parameters at specific coord
	 * @param param
	 * @return
	 * @throws NullCoordinateException
	 */
	public abstract double computeStatistic();

}
