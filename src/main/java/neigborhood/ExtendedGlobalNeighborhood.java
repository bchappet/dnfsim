package main.java.neigborhood;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.UnitParameter;

/**
 * 
 * Used for non wrapped connectivity : the {@link WrappedGlobalNeigborhood} 
 * TODO implemen
 * @author bchappet
 *
 */
public class ExtendedGlobalNeighborhood extends Neighborhood {

	public ExtendedGlobalNeighborhood(Space space) {
		super(space);
		// TODO Auto-generated constructor stub
	}

	public ExtendedGlobalNeighborhood(Space space, UnitParameter map) {
		super(space, map);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Double[][] getNeighborhood(Double... coord)
			throws NullCoordinateException {
		// TODO Auto-generated method stub
		return null;
	}

}
