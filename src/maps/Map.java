package maps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import unitModel.UnitModel;
import coordinates.NullCoordinateException;
import coordinates.Space;

/**
 * Implementation with an array list
 * @author bchappet
 *
 * @param  : type of {@link UnitModel}
 */
public class Map extends AbstractUnitMap {

	/**
	 * Create a map and share the unitModel attribute with it
	 * @param name
	 * @param unitModel
	 */
	public Map(String name,UnitModel unitModel) {
		super(name,unitModel);
		//		/System.out.println("Cretaing map");
	}

	/**
	 * If we want to add the unit model later
	 * @param name
	 * @param um
	 * @param dt
	 * @param refSpace
	 * @param params
	 */
	public Map(String name,UnitModel um,Parameter dt, Space space, Parameter... maps)
	{
		super(name,um,dt,space,maps);
	}




	/**
	 * Generate the collection with as much unit as necessary
	 * @throws NullCoordinateException 

	/**@bug 14/01/14 : when the model did initial computation and 
	//memory is constructed after, the initial computation result disapear if
	//no more computation are done
	@fixed 14/01/14 : make a copy of the values, and then affect them.
	 */
	synchronized public void constructMemory() 
	{

		if(!isMemory)
		{
			boolean noRecursivity = assertNoRecursivity(); //true if there is no recursivity: no leaf of this map in the parameters
			
			
		//	System.out.println("Construct memory of " + name + Arrays.toString(Thread.currentThread().getStackTrace()));
		//	System.out.println("nRecursivity : " + noRecursivity);
			//Init the coordinate array of this unit

			//Create the generic collection
			this.units =  new ArrayList<Unit>();
			//Construct as much unitModel as necessary
			//with the correct coordinate and the right value...
			//System.out.println(this.name + " construct memory vol = " + space.getDiscreteVolume());
			for(int i = 0 ; i < space.getDiscreteVolume() ; i++)
			{
				UnitModel u = (UnitModel)unitModel.clone();
				u.setCoord(space.indexToCoord(i));
				
				if(noRecursivity){
//					double val = this.get(i);
//					if(val > 0.01 && name.equals("Inputs"))
//						System.out.println("Name : " + name + " Val : " + val + " i : " + i  + " @ " + Arrays.toString(Thread.currentThread().getStackTrace()));
//					u.set(val);
				}else{
					//We did not starded 
				}
				this.units.add(new Unit(u));
			}
			this.isMemory = true;
		}
	}



	/**
	 * Explore the dependence of this map to find a leaf with itself
	 * @return true if no recursivity was found
	 * 
	 */
	protected boolean assertNoRecursivity() {
		boolean ret = true;
		for(Parameter p : params){
			if(p instanceof Leaf){
				if(((Leaf)p).map == this ){
					ret = false;
				}
			}else if(p instanceof Map){
				ret &= ((Map)p).assertNoRecursivity();
			}
		}
		return ret;
	}

	/**
	 * One iteration of computation
	 * @throws NullCoordinateException 
	 */
	@Override
	public  void compute() throws NullCoordinateException
	{
		//System.out.println("Compute map :  " + this.name + " time " + this.time.val);
		if(isMemory)
		{

			Iterator<Unit> it = getComputationIterator();
			if(units.get(0).getUnitModel() instanceof Precomputation){
				while(it.hasNext()){
					Unit u = it.next();
					((Precomputation) u.getUnitModel()).precompute();
				}
			}
			//Compute every unit 
			Iterator<Unit> it2 = getComputationIterator();
			while(it2.hasNext())
			{
				Unit u = it2.next();
				u.compute();
			}
			if(units.get(0).isParallel())
			{
				//System.out.println("Swap : "   + this.name + " time " + this.time.val);
				//Swap memories for every unit
				for(Unit u : units)
				{
					u.swap();
				}
			}
		}
		else
		{
			//NoMemory => nothing to do
		}
	}




	@Override
	public   Map  clone()
	{
		Map clone = (Map) super.clone();

		if(clone.isMemory()){

			clone.units = new ArrayList<Unit>();
			for(Unit u : this.units){
				clone.units.add(u.clone());
			}

		}




		return clone;
	}










}
