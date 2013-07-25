package maps;

import java.util.ArrayList;
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
	 */
	public  void constructMemory() 
	{

		if(!isMemory)
		{
			//	System.out.println("Construct memory of " + name + Arrays.toString(Thread.currentThread().getStackTrace()));
			//Init the coordinate array of this unit

			//Create the generic collection
			this.units =  new ArrayList<Unit>();
			//Construct as much unitModel as necessary
			//with the correct coordinate
			for(int i = 0 ; i < space.getDiscreteVolume() ; i++)
			{
				UnitModel u = (UnitModel)unitModel.clone();
				u.setCoord(space.indexToCoord(i));
				this.units.add(new Unit(u));
			}
			this.isMemory = true;
		}
	}



	/**
	 * One iteration of computation
	 * @throws NullCoordinateException 
	 */
	@Override
	public  void compute() throws NullCoordinateException
	{
		if(isMemory)
		{
			if(units.get(0).getUnitModel() instanceof Precomputation){
				for(Unit u : units){
					((Precomputation) u.getUnitModel()).precompute();
				}
			}
			//Compute every unit 
			Iterator<Unit> it = getComputationIterator();
			while(it.hasNext())
			{
				Unit u = it.next();
				u.compute();
			}
			if(units.get(0).isParallel())
			{
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

	/**
	 * 
	 * @return the order for computation. With online computation, one 
	 * could implement assynchronous computation with a random iterator
	 */
	public Iterator<Unit> getComputationIterator()
	{
		return units.iterator();
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
