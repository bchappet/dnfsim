package main.java.maps;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.coordinates.NullCoordinateException;
import main.java.neigborhood.Neighborhood;
import main.java.space.Space;
import main.java.unitModel.NeighborhoodUnitModel;
import main.java.unitModel.UnitModel;

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
public class NeighborhoodMap<T,C> extends UnitMap<T, C> {

	/**List of linked neigboorhood**/
	protected List<Neighborhood> neighborhood;

	public NeighborhoodMap(String name,Var<BigDecimal> dt,Space<C> space,NeighborhoodUnitModel<T> unitModel, Parameter... params) {
		super(name,dt,space,unitModel,params);
		this.neighborhood = new ArrayList<Neighborhood>();
	}

	/**
	 * One should call constructMemory before using it
	 * @param name
	 * @param main.java.unitModel
	 * @param neighborhood
	 * @throws NullCoordinateException
	 */
	public NeighborhoodMap(String name,Var<BigDecimal> dt,Space<C> space, NeighborhoodUnitModel<T> unitModel,Neighborhood... neighborhood) throws NullCoordinateException {
		this(name,dt,space,unitModel,(Parameter) null);
		this.addNeighboors(neighborhood);
	}

	/**
	 * Called when we finnish to set neighboors
	 */
	public void initNeighboorhood()
	{
		//Construct as much main.java.unitModel as necessary
		//with the correct coordinate

		//System.out.println(name +" =>Constructing neighborhood");
		for(Neighborhood neigh : neighborhood)
		{
			for(int i = 0 ; i < getUnits().size() ; i++)
			{
				Unit<T> unit = getUnits().get(i);
				//System.out.println(i);
				Unit<T>[] unitArray = ((Neighborhood) neigh).getNeighborhoodUnits(i);
				((NeighborhoodUnitModel) unit.getUnitModel()).addNeighborhoods(unitArray);
			}

		}

	}


	@Override
	public NeighborhoodMap<T,C> clone(){
		NeighborhoodMap clone = (NeighborhoodMap) super.clone();
		clone.neighborhood = new ArrayList<Neighborhood>();
		for(Neighborhood nei : this.neighborhood){
			Neighborhood newNei = nei.clone();
			clone.neighborhood.add(newNei);
		}

		//Add new neighbours to the memory

		for(int j = 0 ; j< clone.getUnits().size() ; j++)//TODO dirty
		{
			Unit unit = (Unit ) clone.getUnits().get(j);
			//Share the neighboorhood list within the differents UM
			for(int i = 1 ; i < unit.getMemories().size() ; i++){
				((NeighborhoodUnitModel)unit.getUnitModel(i)).setNeighborhood(
						((NeighborhoodUnitModel)unit.getUnitModel(0)).getNeighborhood());
			}
		}

		for(int i = 0 ; i < clone.neighborhood.size() ; i++)
		{
			Neighborhood neigh = (Neighborhood) clone.neighborhood.get(i);
			for(int j = 0 ;j < clone.getUnits().size() ; j++)
			{
				Unit unit = (Unit) clone.getUnits().get(j);
				Unit[] unitArray = ((Neighborhood) neigh).getNeighborhoodUnits(j);
				((NeighborhoodUnitModel) unit.getUnitModel()).addNeighborhoods(unitArray);

			}

		}



		return clone;
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

	public List<Neighborhood> getNeighborhoods() {
		return neighborhood;
	}



}




