package maps;

/**
 * This element can be read as a 2D array
 * @author bchappet
 *
 */
public interface Array2D<E> {
	
	/**
	 * Return a 2D array with the values
	 * @return
	 */
	public E[][] get2DArray();
	
	/**
	 * Acces the element at discrete coordinate
	 * @param x
	 * @param y
	 * @return
	 */
	public E getFast(int x, int y);
	
	/**
	 * Set the element at coordinate x,y
	 * @param x
	 * @param y
	 * @param newVal
	 */
	public void setFast(int x, int y,E newVal);

}
