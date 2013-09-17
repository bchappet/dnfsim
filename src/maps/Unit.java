package maps;

import java.util.ArrayList;
import java.util.Arrays;

import unitModel.UnitModel;
import utils.Cloneable;
import coordinates.NullCoordinateException;
import coordinates.Space;

/**
 * A unit will handle the acces to a {@link UnitModel}
 *  If we are accessing a {@link UnitModel} with this class, it means that the map has memory.
 *  <li> The acces to the state of the Unit at iteration t-n 
 *  is handled with get(delay:int) but the method does not verify 
 *  if there are enough memory {@link UnitModel} </li>
 *  <li> The memory {@link UnitModel} are added using addMemories(int,{@link UnitModel}... historic)</li>
 *  <li> The online vs parallel computation option is partially handled here :
 *  Online (default) computation update the currently accessed {@link UnitModel} right after 
 *  the computation (and hence increment the current itetration).
 *  Parallel computation mode is activated using toParallel() method, the only consequence 
 *  will be that the currently accessed charge is not updated after the computation : 
 *  the calling unit (typically the {@link AbstractUnitMap}) will has to call this update method after every {@link Unit} computation
 * 
 * (Note that we use {@link UnitModel} as memories as the {@link Unit} class needs to be
 * compatible with more complex {@link UnitModel} structure that the default one : there could
 *  be other data attributes than the activity in the extending {@link UnitModel} classes) 
 *
 * @author bchappet
 *
 */
public class Unit implements Cloneable {

	/**vector of memories**/
	protected ArrayList<UnitModel> memories; 


	/**Currently accesed memory**/
	protected int current;
	/**True if parallel computation within the map**/
	protected boolean parallel;


	public Unit(UnitModel unitModel)
	{
		unitModel.setUnit(this);
		this.parallel = false;
		this.memories = new ArrayList<UnitModel>();
		this.memories.add(unitModel);
		current = 0;
		//System.err.println("here :" + ((Object)unitModel).hashCode() + " has a unit");
	}	
	
	@Override
	public Unit clone(){
		Unit clone = null;
		try {
			clone = (Unit) super.clone();
		} catch (CloneNotSupportedException e) {
			//no
		}
		
		clone.memories = new ArrayList<UnitModel>()	;
		for(UnitModel um : memories){
			clone.memories.add(um.clone());
		}
		
		//Update Unit references
		for(UnitModel um : clone.memories){
			um.setUnit(clone);
		}
		
		return clone;
		
	}

	/**
	 * We increment the iteration => the memories are swapped
	 * @Post current = wrapAccess(current + 1);
	 * 	
	 */
	public void swap()
	{
		
		current = wrapAccess(current + 1);
		//System.out.println("swap ==> current = " + current);
	}

	/**
	 * Add several UnitModel to keep track of the past values
	 * The memories added will be historic clones (in order to avoid a full {@link UnitModel} construction
	 * for each historic parameter)
	 * (Note that we use {@link UnitModel} as historic as the {@link Unit} class needs to be
	 * compatible with more complex {@link UnitModel} structure that the default one : there could
	 *  be other data attributes than the activity) 
	 * @param nbMemories
	 * @param historic UnitModel[nbMemories] containing the memories (Optionnal :
	 *  if no historic is provided, the setInitActivity() of UnitModel is used)
	 */
	public void addMemories(int nbMemories,UnitModel... historic)
	{
		for(int i = 0 ; i < nbMemories ; i++){
			try{
				UnitModel um =historic[i].clone2();
				um.setUnit(this);
				this.memories.add(um);
			}catch (ArrayIndexOutOfBoundsException e) {
				this.memories.add(this.memories.get(0).clone2().setInitActivity());
			}
		}
	}

	/**
	 * 
	 * @return activity of the current {@link UnitModel}
	 */
	public double get() {
		return memories.get(current).get();
	}

	/**
	 * Version with delay
	 * @return activity of the Unit at iteration t - delay
	 * @exception ArrayIndexOutOfBoundsException if the delay excess memories size
	 */
	public double get(int delay) {
		int access = wrapAccess(current - delay);
		return memories.get(access).get();
	}

	/**
	 * Get current {@link UnitModel}
	 * @return
	 */
	public UnitModel getUnitModel()
	{
		return memories.get(current);
	}

	/**
	 * Get the {@link UnitModel} at iteration t - delay
	 * @return
	 */
	public UnitModel getUnitModel(int delay)
	{
		int access = wrapAccess(current - delay);
		return memories.get(access);
	}

	/**
	 * wrap access value within the memories range
	 * @param access
	 * @return
	 */
	private int wrapAccess(int access){
		int size = memories.size();
		int ret =  (access  )%size;
		if(ret < 0)
			ret += size;

		return ret;
	}

	/**
	 * Compute the state of the current {@link UnitModel}
	 * @throws NullCoordinateException : if the coordinates are not compatible
	 */
	public void compute() throws NullCoordinateException {
		//System.err.println("compute : " + memories.get(wrapAccess(current+1)).hashCode());
		UnitModel currentUM = memories.get(wrapAccess(current+1));
		
		double result = currentUM.computeActivity();
		currentUM.set(result);
		currentUM.incrTime();
		if(!parallel)
			this.swap();

	}
	
	
	/**
	 * Reset memories
	 * @throws NullCoordinateException  : if the coordinates are not compatible
	 */
	public void reset(){
		for(UnitModel u : memories)
			u.reset();

	}


	public Double[] getCoord() {
		return getUnitModel().getCoord();
	}

	/**
	 * Change the coord of <b>every</b> {@link UnitModel} : current and memories
	 * @param coord : the new coordinate
	 */
	public void setCoord(Double... coord){
		for(UnitModel u : memories)
			u.setCoord(coord);
	}



	public String toString(){
		Space space = this.getUnitModel().getSpace();
		return "Unit of " + this.getUnitModel() + " at " + Arrays.toString(space.discreteProj(getCoord())) + " @"+hashCode();
//		String ret = "curent : " + current +"\n";
//		ret += "charge size : " + memories.size() + "\n";
//		for(int i = 0 ; i < memories.size() ; i++)
//			ret += i+":"+memories.get(i).get() + "\n";
//
//		return ret.substring(0, ret.length()-1);
	}
	/**
	 * For test uniquely
	 * @return current index of memory array
	 */
	public int getCurrent() {
		return current;
	}
	/**
	 * 
	 * @return true if parallel computation is enable
	 */
	public boolean isParallel() {
		return parallel;
	}

	/**
	 *  Parallel computation mode is activated using toParallel() method, the only consequence 
	 *  will be that the currently accessed charge is not updated after the computation : 
	 *  the calling unit (typically the {@link AbstractUnitMap}) will has to call this update 
	 *  method after every {@link Unit} computation
	 *  
	 *  If there is no memory, construct a memory. The constructed memory {@link UnitModel} 
	 *  is a clone of the current {@link UnitModel} and is initilized with setInitActivity() method
	 * 
	 * @post parallel = true
	 */
	public void toParallel()
	{
		if(!this.parallel  && memories.size()==1)
			//We need to construct one memory
			addMemories(1);

		this.parallel = true;

	}

	/**
	 * Computation are not on line :
	 * computation update the currently accessed {@link UnitModel} right after 
	 *  the computation (and hence increment the current itetration).
	 * @post this.parallel = false;
	 */
	public void onLine()
	{
		this.parallel = false;
	}

	public ArrayList<UnitModel> getMemories() {
		return memories;
	}

	public Var getVar() {
		return getUnitModel().getActivity();
	}

	/**
	 * Return the subunit i of the current unitmodel
	 * @param subUnitIndex
	 * @return
	 */
	public UnitModel getSubUnit(int subUnitIndex) {
		return getSubUnit(subUnitIndex,0);
	}

	public UnitModel getSubUnit(int subUnitIndex, int delay) {
		return getUnitModel(delay).getSubUnit(subUnitIndex);
	}

	public void delete() {
		for(UnitModel um : memories){
			um.delete();
		}
		
	}

}
