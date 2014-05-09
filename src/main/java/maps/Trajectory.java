package main.java.maps;

import java.math.BigDecimal;

import main.java.space.NoDimSpace;
import main.java.unitModel.UnitModel;

/**
 * A trajectory has only one main.java.unitModel
 * @author bchappet
 *
 * @param <T> computed type
 */
public class Trajectory<T> extends UnitMap<T,Integer> implements SingleValueParam<T>{

	/**
	 * Will create a map with a NoDim main.java.space
	 * @param name
	 * @param main.java.unitModel
	 * @param params
	 */
	public Trajectory(String name,Var<BigDecimal> dt,UnitModel<T> unitModel, Parameter... params) {
		super(name,dt,new NoDimSpace(),unitModel,params);
	}

	@Override
	public T get() {
		return super.getIndex(0);
	}
	
	@Override
	public T getIndex(int index){
		return get();
	}
	
	


	
	@Override
	public Trajectory<T> clone(){
		Trajectory<T> clone = (Trajectory<T>) super.clone();
		return clone;
	}

}
