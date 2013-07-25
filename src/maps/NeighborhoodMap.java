package maps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import neigborhood.Neighborhood;
import unitModel.NeighborhoodUnitModel;
import unitModel.UnitModel;
import coordinates.NullCoordinateException;
import coordinates.Space;

/**
 * A NeighborhoodMap is constructed with several {@link Neighborhood}.
 * Each {@link Neighborhood} linked {@link UnitParameter} will be added to the {@link NeighborhoodMap} parameter list.
 * 
 *  The {@link UnitModel} of this map are {@link NeighborhoodUnitModel} : they have a list of neighborhood.
 *  In the {@link NeighborhoodUnitModel}, the neighborhoods are arrays of {@link Unit}. These are initilized
 *  during this {@link NeighborhoodMap} constructMemory method execution using the {@link Neighborhood} list of this {@link NeighborhoodMap}
 * 
 * @author bchappet
 *
 */
public class NeighborhoodMap extends Map {

	/**List of linked neigboorhood**/
	protected List<Neighborhood> neighborhood;
	
	public NeighborhoodMap(String name,UnitModel um,Parameter dt, Space space, Parameter... maps){
		super(name,um,dt,space,maps);
		this.neighborhood = new ArrayList<Neighborhood>();
	}
	
	@Override
	public NeighborhoodMap clone(){
		NeighborhoodMap clone = (NeighborhoodMap) super.clone();
		
		clone.neighborhood = new ArrayList<Neighborhood>();
		for(Neighborhood nei : this.neighborhood){
			Neighborhood newNei = nei.clone();
			if(nei.getMap() instanceof Leaf && ((Leaf)nei.getMap()).getMap() == this){
				//if the neighbourhood linked map was this one,
				//we have to replace it by the cloned one
				newNei.setMap(new UnitLeaf(clone));
			}
			clone.neighborhood.add(newNei);
		}
		
		//Add new neighbours to the memory
		
		if(clone.isMemory()){
			for(Unit unit : clone.units)//TODO dirty
			{
				//Share the neighboorhood list within the differents UM
				for(int i = 1 ; i < unit.memories.size() ; i++){
					((NeighborhoodUnitModel)unit.getUnitModel(i)).setNeighborhood(
							((NeighborhoodUnitModel)unit.getUnitModel(0)).getNeighborhood());
				}
			}
			
			for(Neighborhood neigh : clone.neighborhood)
			{
				neigh.getMap().constructMemory();
				for(Unit unit : clone.units)
				{
					Unit[] unitArray = ((Neighborhood) neigh).getNeighborhoodUnits(unit.getCoord());
					((NeighborhoodUnitModel) unit.getUnitModel()).addNeighborhoods(unitArray);
					
				}

			}
		}
		
		
		
		return clone;
	}
	
	/**
	 * One should call constructMemory before using it
	 * @param name
	 * @param unitModel
	 * @param neighborhood
	 * @throws NullCoordinateException
	 */
	public NeighborhoodMap(String name, UnitModel unitModel,Neighborhood... neighborhood) throws NullCoordinateException {
		super(name, unitModel);
		this.neighborhood = new LinkedList<Neighborhood>();
		this.addNeighboors(neighborhood);
	}

	public void constructMemory() throws NullCoordinateException
	{
		if(!isMemory)
		{
			//To avoid recursivity when calling neigh.getMap().constructMemory();
			this.isMemory = true;
			//Create the generic collection
			this.units =  new ArrayList<Unit>();
			//Construct as much unitModel as necessary
			//with the correct coordinate

			//System.out.println(name +" =>Constructing memory from unit model " + unitModel.hashCode());
			for(int i = 0 ; i < space.getDiscreteVolume() ; i++)
			{
				//System.out.println(i);
				NeighborhoodUnitModel u = (NeighborhoodUnitModel)unitModel.clone();
				u.setCoord(space.indexToCoord(i));
				this.units.add(new Unit(u));
			}
			//System.out.println(name +" =>Constructing neighborhood");
			for(Neighborhood neigh : neighborhood)
			{
				neigh.getMap().constructMemory();
				for(Unit unit : units)
				{
					//System.out.println(i);
					Unit[] unitArray = ((Neighborhood) neigh).getNeighborhoodUnits(unit.getCoord());
					((NeighborhoodUnitModel) unit.getUnitModel()).addNeighborhoods(unitArray);
				}

			}
			
		}
	}

	/**
	 * Add neighboors
	 * @param neighs
	 */
	public void addNeighboors(Neighborhood... neighs) {
		neighborhood.addAll(Arrays.asList(neighs));
		//Add neigboorhood map as parameter
		for(Neighborhood nei : neighborhood)
			addParameters(nei.getMap());
	}

	public List<Neighborhood> getNeighborhood() {
		return neighborhood;
	}
	
	

}




