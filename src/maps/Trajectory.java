package maps;

import space.NoDimSpace;
import unitModel.UnitModel;

/**
 * A trajectory has only one unitModel
 * @author bchappet
 *
 * @param <T> computed type
 */
public class Trajectory<T> extends UnitMap<T,Integer> implements NoDimension<T>{

	/**
	 * Will create a map with a NoDim space
	 * @param name
	 * @param unitModel
	 * @param params
	 */
	public Trajectory(String name,UnitModel<T> unitModel, Parameter<T>... params) {
		super(name,new NoDimSpace(),unitModel,params);
	}

	@Override
	public T get() {
		return getIndex(0);
	}


	
	@Override
	public Trajectory<T> clone(){
		Trajectory<T> clone = (Trajectory<T>) super.clone();
		return clone;
	}

}
