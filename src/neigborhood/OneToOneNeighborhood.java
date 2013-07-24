package neigborhood;

import maps.UnitParameter;
import coordinates.NullCoordinateException;
import coordinates.Space;

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
