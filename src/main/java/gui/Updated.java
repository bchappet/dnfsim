package main.java.gui;

import main.java.coordinates.NullCoordinateException;

/**
 * GUI element which need to be updated with the main.java.model
 * @author bchappet
 *
 */
public interface Updated {
	
	public void update() throws NullCoordinateException;

	public void reset();

}
