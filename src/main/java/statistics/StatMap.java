package main.java.statistics;

import java.math.BigDecimal;
import java.util.List;

import main.java.coordinates.NullCoordinateException;
import main.java.maps.Parameter;
import main.java.maps.Trajectory;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.space.Space;
import main.java.unitModel.UnitModel;

public abstract class StatMap<T> extends Trajectory<T> {
	
	
	public StatMap(String theName,Var<BigDecimal> dt,T initData, Parameter... maps)
	{
		super(theName,dt, null,maps);
		
		this.initMemory(new UnitModel<T>(initData){
			@Override
			protected Object compute(BigDecimal time, int index, List params) {
				return computeStatistic(time,index,params);
			}
		});
		
	}
	
//	public void reset(){
//		super.reset();
//		this.time.set(0);
//		//one need to reset here the state of the statMap if any
//	}
	
	/**
	 * Compute activity knowing the values of parameters at specific coord
	 * @param param
	 * @return
	 * @throws NullCoordinateException
	 */
	public abstract  T computeStatistic(BigDecimal time, int index, List<Parameter> params);

}
