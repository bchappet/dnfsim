package maps;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utils.Cloneable;

import space.Space;
import unitModel.UnitModel;

/**
 * A unit map is a {@link Map} containing a list of {@link UnitModel}
 * The {@link UnitModel} are in fact interfaced with {@link Unit} which contains
 * a list of {@link UnitModel} to handle delays and parallel computations.
 * 
 * TODO For now we always init memory, is it a good idea? 
 * 
 * 
 * @author bchappet
 * 
 *

 */
public class UnitMap<T,C> implements Map<T,C> 
{

	/**Collection of units**/
	private AbstractList<Unit<T>> units;
	/**Name of the map**/
	private String name;
	
	/**Space of the map**/
	private Space<C> space;
	
	/**List of parameters**/
	private List<Parameter<T>> params;


	/**
	 * Construct an unitMap and initialize the units List with the unitModel
	 * the parameters are also shared with the UnitModel
	 * But only the map can change it
	 * @param name
	 * @param space
	 * @param unitModel
	 * @param params
	 */
	public UnitMap(String name,Space<C> space,UnitModel<T> unitModel, Parameter<T>... params) {
		this.space = space;
		this.name = name;
		this.units = new ArrayList<Unit<T>>(this.space.getVolume());
		this.params = new ArrayList<Parameter<T>>(Arrays.asList(params));
		this.initMemory(unitModel);
	}
	
	
	private void initMemory(UnitModel<T> um) {
		for(int i = 0 ; i < this.space.getVolume() ; i++){
			UnitModel<T> clone = um.clone();
			this.units.add(new Unit<T>(clone));
		}
		
	}


	@Override
	public UnitMap<T,C> clone(){
		UnitMap<T, C> clone = null;
		try {
			clone = (UnitMap<T,C>) super.clone();
			clone.space = this.space; //shared
			clone.name = this.name + "_clone"; //different
			clone.params = (List<Parameter<T>>) ((ArrayList<Parameter<T>>) params).clone(); //Shallow clone 
			//We have to copy the unit states and history => deep cloning 
			for(int i  = 0 ; i < clone.units.size() ; i++){
				clone.units.set(i, this.units.get(i).clone());
			}
		} catch (CloneNotSupportedException e) {
			// supported
			e.printStackTrace();
		}
		return clone;
	}
	
	/**
	 * For now assume that memory is on
	 * And parallel is on
	 */
	@Override
	public void compute() {
		
		for(Unit<T> u : units){
			u.compute(params);
		}
		for(Unit<T> u : units){
			u.swap();
		}
		
		
	}


	@Override
	public T getIndex(int index) {
		return units.get(index).get();
	}



	/**
	 * Warning copy current values into an arrayList => slow
	 */
	@Override
	public List<T> getValues() {
		List<T> ret = new ArrayList<T>(units.size());
		for(Unit<T> unit : units){
			ret.add(unit.get());
		}
		return ret;
	}


	@Override
	public Space<C> getSpace() {
		return space;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void addParameters(Parameter<T>... params) {
		this.params.addAll(Arrays.asList(params));
		
	}

	@Override
	public Parameter<T> setParameter(int index, Parameter<T> newParam) {
		return this.params.set(index, newParam);
	}

}
