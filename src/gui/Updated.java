package gui;

import coordinates.NullCoordinateException;

/**
 * GUI element which need to be updated with the model
 * @author bchappet
 *
 */
public interface Updated {
	
	public void update() throws NullCoordinateException;

	public void reset();

}
