package main.java.neigborhood;

import main.java.coordinates.NullCoordinateException;
import main.java.maps.Parameter;
import main.java.maps.Unit;
import main.java.maps.UnitParameter;
import main.java.space.Space;
import main.java.unitModel.UnitModel;
import main.resources.utils.Cloneable;

/**
 * A {@link Neighborhood} instance work is to generate an array of {@link Unit} from a {@link UnitParameter}
 * knowing the refSpace and at specific main.java.coordinates using the getNeighborhoodUnits(Double ... coord ) method.
 * 
 * The array of {@link Unit} will represent the neighborhood for one coordinate.
 * @author bchappet
 *
 */
public abstract class Neighborhood<C> implements Cloneable {

	protected Space<C> space;
	/**Linked map (can be set later)**/
	protected UnitParameter map;

	/**This unit will be set if we are out of bounds (optional) but should be set if no wrap and special unit acces**/
	//protected UnitModel nullUnit = new UMWrapper<T>(initActivity)

	public Neighborhood(Space<C> space)
	{
		this(space,null);
	}


	public Neighborhood(Space<C> space, UnitParameter map)
	{
		this.space = space;
		this.map = map;
	}

	public Neighborhood<C> clone()
	{
		Neighborhood<C> clone = null;
		try {
			clone = (Neighborhood<C>) super.clone(); //everything is shared
		} catch (CloneNotSupportedException e) {
			//no
		}

		return clone;

	}
	/**
	 * Return the neighborhood at specific main.java.coordinates
	 * @param coord
	 * @return The neighboorhood unit or a NullUnit if the neighbour is out of bound
	 * @throws NullCoordinateException
	 */
	public Unit[] getNeighborhoodUnits(int index ) throws NullCoordinateException
	{
		int[] neighCoords = this.getNeighborhood(index);
		Unit[] ret = new Unit[neighCoords.length];

		for(int i = 0 ; i < ret.length ; i++){
			try{
				ret[i] = map.getUnit(neighCoords[neighCoords[i]]);
			}catch (ArrayIndexOutOfBoundsException e) {
				//Out of bounds
				//System.out.println("Out of bound, constructing a null unit");
				ret[i] = new NullUnit(map.getUnit(0));
			}
		}

		return ret;
	}




	/**
	 * Return the neihgborhood main.java.coordinates at specific continous coordinate
	 * @param coord continuous
	 * @return an array of main.java.coordinates
	 * @throws NullCoordinateException coordinate bad format
	 */
	public abstract int[] getNeighborhood(int index);

	public Space getSpace() {
		return space;
	}

	public Parameter getMap() {
		return map;
	}

	public void setMap(UnitParameter map) {
		this.map = map;
	}

	


}
