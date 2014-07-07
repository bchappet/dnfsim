package main.java.unitModel;

import java.util.ArrayList;
import java.util.List;

import main.java.maps.Unit;

/**
 * A {@link NeighborhoodUnitModel} can directly acces the Unit of another map : he has a List of array of Unit
 * Each array of Unit commes from a different map. They represents direct links to other map units
 * 
 * Note that it is very costly in memory for global connection...
 * 
 * 
 * @author bchappet
 *
 */
public abstract class NeighborhoodUnitModel<T> extends UnitModel<T> {
	
	/**List of array of unit : each list contains Units from one map**/
	protected List<Unit[]> neighborhoods;
	
	public NeighborhoodUnitModel(T defaultValue){
		super(defaultValue);
		neighborhoods = new ArrayList<Unit[]>();
	}
	
	
	public void onNeighborhoodAddition(){
		//nothing by default
	}
	
	@Override
	public NeighborhoodUnitModel clone() 
	{
		NeighborhoodUnitModel clone = (NeighborhoodUnitModel) super.clone();
		//  no: it does not work if the wrapping map is cloned
		//the neighbouhood map will update the UM neighbours in clone() method
		//false clone.neighborhoods = copy(this.neighborhoods);
		clone.neighborhoods = new ArrayList<Unit[]>();
		return clone;
	}
	
//	@Override
//	public NeighborhoodUnitModel clone2() 
//	{
//		NeighborhoodUnitModel clone = (NeighborhoodUnitModel) super.clone2();
//		//we are in the same unit: the neighbourhood is the same
//		clone.neighborhoods = this.neighborhoods;
//		clone.onNeighborhoodAddition();
//		return clone;
//	}
//
	

	/**
	 * Add an array of {@link Unit} to the neighborhood list
	 * @param units
	 */
	public void addNeighborhoods(Unit<T>[] units) {
		neighborhoods.add(units);
		onNeighborhoodAddition();
	}
	
	public Unit[] getNeighborhood(int index) {
		return neighborhoods.get(index);
	}

	public List<Unit[]> getNeighborhood() {
		return neighborhoods;
	}
	
	/**
	 * For clonnong we have to share the neighborhood within the memories
	 * @param neig
	 */
	public void setNeighborhood(List<Unit[]> neig){
		this.neighborhoods = neig;
		onNeighborhoodAddition();
	}


	

}
