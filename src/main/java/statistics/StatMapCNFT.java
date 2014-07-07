package main.java.statistics;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.List;

import main.java.maps.Parameter;
import main.java.maps.Var;

public abstract class StatMapCNFT<T> extends StatMap<T> {
	
	/**List of the trackable objects**/
	protected List<AbstractMap> tracks;
	
	public StatMapCNFT(String theName,Var<BigDecimal> dt, T initData, List<AbstractMap> tracks, Parameter... maps)
	{
		super(theName,dt,initData,maps);
		this.tracks = tracks;
	}
	
	
	
	

}
