package main.java.neigborhood;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.UnitParameter;

/**
 * Only one neighboor at the coord
 * @author bchappet
 *
 */
public class OneToOneNeighborhood extends Neighborhood {

	public OneToOneNeighborhood(Space space) {
		super(space);
	}
	
	public OneToOneNeighborhood(Space space,UnitParameter param) {
		super(space,param);
	}

	@Override
	public Double[][] getNeighborhood(Double... coord)
			throws NullCoordinateException {
		return new Double[][]{coord};
	}

}
