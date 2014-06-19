package maps;

import coordinates.NullCoordinateException;

public interface Track {

	Double[] getCenter() throws NullCoordinateException;

	Double[] getDimension() throws NullCoordinateException;

}
