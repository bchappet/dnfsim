package statistics;

import java.util.List;

import maps.AbstractMap;
import maps.Map;
import maps.Parameter;
import maps.Var;
import unitModel.UnitModel;
import coordinates.NullCoordinateException;
import coordinates.Space;

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
