package main.java.neigborhood;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.UnitParameter;

/**
 * The wrapping depends on the wrap parameter of the linked {@link Space}.
 * If wrapping is active, every coordinate will be in bounds
 * Otherwise, the main.java.coordinates might be out of bound => null main.java.coordinates
 * @author bchappet
 *
 */
public class WrappedGlobalNeigborhood extends Neighborhood {
	
	protected int radius; //Radius of convolution square

	public WrappedGlobalNeigborhood(int radius,Space space) {
		super(space);
		this.radius = radius;
	}

	public WrappedGlobalNeigborhood(int radius,Space space,UnitParameter map) {
		super(space,map);
		this.radius = radius;
	}

	@Override
	public Double[][] getNeighborhood(Double... coord) throws NullCoordinateException {

		int halfSize = radius;
		
		Double[][] result = new Double[(int) Math.pow(halfSize*2+1,2)][space.getDim()];
		Double[] discreteCoord = space.discreteProj(coord);
		List<Double[]> list = new LinkedList<Double[]>();
		for(int i = -halfSize ; i <= halfSize ; i++){
			for(int j = -halfSize ; j <= halfSize ; j++){
				Double[] newCoord = addCoord(discreteCoord,i,j);
				list.add(newCoord);
			}
		}


		result = list.toArray(result);

		////Transform into continuous main.java.coordinates and wrap
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
	
	private static void printCoor(List<Double[]> list)
	{
		for(Double[] d : list)
			System.out.println(Arrays.toString(d)+",");
	}

}
