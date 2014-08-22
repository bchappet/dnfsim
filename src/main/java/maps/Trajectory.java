package main.java.maps;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import main.java.space.NoDimSpace;
import main.java.unitModel.UnitModel;

/**
 * A trajectory has only one unitModel
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
	
	/**
	 * Add memories using an historic 
	 * @param nb
	 * @param historic from more recent to oldest
	 */
	public void addMemories(int nb, T[] historic) {
		if(historic.length != nb)
			throw new IllegalArgumentException("The historic size should be the same as nb");
		Unit<T> unit = getUnit(0);
		UnitModel<T> um = unit.getUnitModel();
		List<UnitModel<T>> list = new ArrayList<UnitModel<T>>(historic.length);
		for(int i = historic.length-1 ;i >=0 ; i--){
			UnitModel<T> current = um.clone();
			current.set(historic[i]);
			list.add(current);
		}
		unit.addMemories(nb,list);
		
	}

}
