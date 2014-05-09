package main.java.neigborhood;

import java.util.ArrayList;
import java.util.List;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.Unit;
import main.java.maps.UnitParameter;
import main.java.maps.Var;
import main.java.unitModel.UMWrapper;
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
public abstract class Neighborhood implements Cloneable {

	protected Space space;
	/**Linked map (can be set later)**/
	protected UnitParameter map;

	/**This unit will be set if we are out of bounds (optional) but should be set if no wrap and special unit acces**/
	protected UnitModel nullUnit = new UMWrapper(new Var(0));

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
	 * Return the neighborhood at specific main.java.coordinates
	 * @param coord
	 * @return The neighboorhood unit or a NullUnit if the neighbour is out of bound
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
	 * For display : return index of neighboors of current index
	 * @param index
	 * @return
	 */
	public  int[] getNeighborhood(int index){

		Double[][] tmp = getNeighborhood(space.indexToCoord(index));
		List<Integer> list = new ArrayList<Integer>(4);
		for(int i = 0 ; i < tmp.length ; i ++){
			boolean noNull = true;
			for(int j = 0 ; j < tmp[i].length ; j++){
				noNull &= tmp[i][j] != null;
			}
			if(noNull){
				list.add( space.coordToIndex(tmp[i]));
			}
		}
		int[] res= new int[list.size()];
		for(int i = 0 ; i < res.length ; i++){
			res[i] = list.get(i);
		}
		
		return res;
	}



	/**
	 * Return the neihgborhood main.java.coordinates at specific continous coordinate
	 * @param coord continuous
	 * @return an array of main.java.coordinates
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
