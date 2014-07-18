package main.java.maps;

import main.java.coordinates.NullCoordinateException;

public interface Track {

	Double[] getCenter() throws NullCoordinateException;

	Double[] getDimension() throws NullCoordinateException;

}
