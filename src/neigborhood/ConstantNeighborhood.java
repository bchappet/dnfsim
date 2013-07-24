package neigborhood;

import java.util.LinkedList;
import java.util.List;

import maps.Unit;
import maps.UnitParameter;
import coordinates.NullCoordinateException;
import coordinates.Space;

/**
 * The resulting Units dont change with coordinates
 * @author bchappet
 *
 */
public class ConstantNeighborhood extends Neighborhood {
	
	protected int radius; //Radius of convolution square

	protected Unit[] neighborhood;//save the result as it is constant

	public ConstantNeighborhood(int radius,Space space) {
		super(space);
		neighborhood = null;
		this.radius = radius;//TODO should be even
	}

	public ConstantNeighborhood(int radius,Space space, UnitParameter map) {
		super(space, map);
		neighborhood = null;
		this.radius = radius;
	}
	
	

	@Override
	public Unit[] getNeighborhoodUnits(Double ... coord ) throws NullCoordinateException
	{
		if(neighborhood == null)
			neighborhood = super.getNeighborhoodUnits(coord);

		return neighborhood;

	}

	@Override
	public Double[][] getNeighborhood(Double... coord)
			throws NullCoordinateException {
//		int halfSize = (int) (refSpace.getResolution()/2);
		int halfSize = radius;
		Double[][] result = new Double[(int) Math.pow(halfSize*2+1,2)][space.getDim()];

		Double[] center = space.getCenter();//Center of the refSpace


		Double[] discreteCoord = space.discreteProj(center);
		List<Double[]> list = new LinkedList<Double[]>();
		for(int i = -halfSize ; i <= halfSize ; i++){
			for(int j = -halfSize ; j <= halfSize ; j++){
				Double[] newCoord = addCoord(discreteCoord,i,j);
				list.add(newCoord);
			}
		}

		result = list.toArray(result);

		////Transform into continuous coordinates and wrap
		for(int i = 0 ; i < result.length ; i++){
			result[i] = space.continuousProj(result[i]);
			result[i] = space.wrap(result[i]);
		}

		return result;
	}

	private Double[] addCoord(Double[] coord , int... toAdd)
	{
		Double[] ret = new Double[coord.length];
		for(int i = 0 ; i < coord.length ; i++)
		{
			ret[i] = coord[i] + toAdd[i];
		}
		return ret;
	}

}
