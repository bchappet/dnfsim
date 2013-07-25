package neigborhood;

import maps.Parameter;
import maps.Unit;
import maps.UnitParameter;
import maps.Var;
import unitModel.ConstantUnit;
import unitModel.UnitModel;
import utils.Cloneable;
import coordinates.NullCoordinateException;
import coordinates.Space;

/**
 * A {@link Neighborhood} instance work is to generate an array of {@link Unit} from a {@link UnitParameter}
 * knowing the refSpace and at specific coordinates using the getNeighborhoodUnits(Double ... coord ) method.
 * 
 * The array of {@link Unit} will represent the neighborhood for one coordinate.
 * @author bchappet
 *
 */
public abstract class Neighborhood implements Cloneable {
	
	protected Space space;
	/**Linked map (can be set later)**/
	protected UnitParameter map;
	
	/**This unit will be set if we are out of bounds (optional) but should be set if no wrap and special unit acces**/
	protected UnitModel nullUnit = new ConstantUnit(new Var(0));
	
	public Neighborhood(Space space)
	{
		this(space,null);
	}
	
	
	public Neighborhood(Space space, UnitParameter map)
	{
		this.space = space;
		this.map = map;
	}
	
	public Neighborhood clone()
	{
		Neighborhood clone = null;
		try {
			clone = (Neighborhood) super.clone(); //everything is shared
		} catch (CloneNotSupportedException e) {
			//no
		}
		
		return clone;
		
	}
	/**
	 * Return the neighborhood at specific coordinates
	 * @param coord
	 * @return
	 * @throws NullCoordinateException
	 */
	public Unit[] getNeighborhoodUnits(Double ... coord ) throws NullCoordinateException
	{
		Double[][] neighCoords = this.getNeighborhood(coord);
		Unit[] ret = new Unit[neighCoords.length];
		
		for(int i = 0 ; i < ret.length ; i++){
			try{
				ret[i] = map.getUnit(space.coordToIndex(neighCoords[i]));
			}catch (NullCoordinateException e) {
				//Out of bounds
				//System.out.println("Out of bound, constructing a null unit");
				ret[i] = new Unit(nullUnit);
			}
		}
		
		return ret;
	}
	
	

	/**
	 * Return the neihgborhood coordinates at specific continous coordinate
	 * @param coord continuous
	 * @return an array of coordinates
	 * @throws NullCoordinateException coordinate bad format
	 */
	public abstract Double[][] getNeighborhood(Double... coord) throws NullCoordinateException;

	public Space getSpace() {
		return space;
	}

	public Parameter getMap() {
		return map;
	}

	public void setMap(UnitParameter map) {
		this.map = map;
	}

	public void setNullUnit(UnitModel um) {
		this.nullUnit = um;
	}

	
}
