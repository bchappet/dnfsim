package main.java.maps;

/**
 * Provide a faster access to the double data
 * @author benoit
 * @version 09/06/2014
 *
 */
public interface Array2DDouble extends Array2D<Double> {
	
	/**
	 * Return a 2D array with the values
	 * @return
	 */
	public double[][] get2DArrayDouble();
	
	/**
	 * set the  2D Array
	 */
	public void set2DArrayDouble(double[][] newArray);
	
	/**
	 * Acces the element at discrete coordinate
	 * @param x
	 * @param y
	 * @return
	 */
	public double getFastDouble(int x, int y);
	

}
