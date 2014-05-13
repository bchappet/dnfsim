package main.java.maps;

import java.math.BigDecimal;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.space.Coord2D;
import main.java.space.Space;
import main.java.unitModel.UnitModel;
import main.resources.utils.Cloneable;

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
public class UnitMap<T,C> extends Map<T,C> implements UnitParameter<T>
{

	/**Collection of units**/
	private List<Unit<T>> units;
	

	/**
	 * Construct an unitMap and initialize the units List with the main.java.unitModel
	 * the parameters are also shared with the UnitModel
	 * But only the map can change it
	 * @param name
	 * @param main.java.space
	 * @param main.java.unitModel
	 * @param params
	 */
	public UnitMap(String name,Var<BigDecimal> dt,Space<C> space,UnitModel<T> unitModel, Parameter... params) {
		super(name,dt,space,params);
		this.units = new ArrayList<Unit<T>>(space.getVolume());
		this.initMemory(unitModel);
	}
	
	/**
	 * Initialize the memory of the unit map
	 * @param um
	 */
	protected void initMemory(UnitModel<T> um) {
		for(int i = 0 ; i < this.getSpace().getVolume() ; i++){
			UnitModel<T> clone = um.clone();
			this.units.add(new Unit<T>(clone));
		}
		
	}
	
	protected List<Unit<T>> getUnits(){
		return this.units;
	}
	
	/**
	 * Return the number of accessible memory.
	 * 
	 * Note that we can access with a delay of 
	 * @return
	 */
	public int getNbMemory(){
		return this.units.get(0).getNbMemory();
	}
	
	/**
	 * Add memories using an historic (optional)
	 * other wise we use the current value as historic
	 * @param nb
	 * @param historic (optional)
	 */
	public void addMemories(int nb,List<UnitMap<T,C>> historic){
		
		if(historic != null){
			List<List<UnitModel<T>>> umHistoric = new ArrayList<List<UnitModel<T>>>();
			for(int i = 0 ; i < this.getSpace().getVolume() ; i++){
				List<UnitModel<T>> historicIndex = new ArrayList<UnitModel<T>>();
				for(int j = 0 ; j < nb ; j++){
					historicIndex.add(historic.get(j).units.get(i).getUnitModel());
				}
				umHistoric.add(historicIndex);
			}
			
			for(int i = 0 ; i < this.getSpace().getVolume() ; i++){
				this.units.get(i).addMemories(nb,umHistoric.get(i));
			}
		}else{
			for(int i = 0 ; i < this.getSpace().getVolume() ; i++){
				this.units.get(i).addMemories(nb,null);
			}
		}
		
		
	}


	@Override
	public UnitMap<T,C> clone(){
		UnitMap<T, C> clone = null;
		clone = (UnitMap<T,C>) super.clone();
		//We have to copy the unit states and history => deep cloning 
		for(int i  = 0 ; i < clone.units.size() ; i++){
			clone.units.set(i, this.units.get(i).clone());
		}
		return clone;
	}
	
	/**
	 * For now assume that memory is on
	 * And parallel is on
	 */
	@Override
	public void compute() {
		//System.out.println("Compute " + this.getName());
		for(int i = 0 ; i < this.getSpace().getVolume() ; i++){
			
			units.get(i).compute(this.getTime(),i,this.getParameters());
		}
		for(Unit<T> u : units){
			u.swap();
		}
		
		
	}


	@Override
	public T getIndex(int index) {
		//System.err.println("get : " + this.getName() + " index" + index + " size = " +  this.units.size());
		return units.get(index).get();
	}
	
	/**
	 * Access a unit with delay
	 * @param index
	 * @param delay
	 * @return
	 */
	public T getDelay(int index,int delay){
		//System.out.println("get delay " + delay + " index " + index);
		return units.get(index).get(delay);
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
	public Unit<T> getUnit(int index) {
		return this.units.get(index);
	}

	public void set(int index, T val) {
		this.units.get(index).set(val);
		
	}




}
