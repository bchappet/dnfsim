package main.java.maps;

import java.util.List;

import main.java.coordinates.NullCoordinateException;
import main.resources.utils.Cloneable;

/**
 * A parameter is an object which has a name and an object at given index.
 *
 * @param E : stored type of the Parameter
 * 
 * @author bchappet
 * 
 */
public interface Parameter<T> extends Cloneable {

	

	
	/**
	 * Get activity at specific index
	 * 
	 * @param coor
	 * @return
	 * @throws NullCoordinateException
	 */
	public T getIndex(int index);


	/**
	 * Construct an array with each values of the memory Usefull to handle
	 * optimized computation
	 * 
	 * @return
	 */
	public List<T> getValues();

	/**
	 * Return the name of the parameter
	 * @return
	 */
	public String getName();

	/**
	 * Set parameter to its initial value
	 */
	public void reset();


	
	


	
	



}
