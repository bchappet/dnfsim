package maps;

import java.util.List;

import coordinates.NullCoordinateException;

/**
 * A parameter is an object which has a value at given coordinate.
 * 
 * The standard way to access its value is with the get(Double ... coordinates)
 * method. the coordinate are considered to be continuous one, and a null value
 * is possible for some dimension (sparse dimension).
 * 
 * The get(int index) method provide a very fast access to one specific data
 * stored in a linear collection.
 * 
 * The getFast(integer... coor) use discrete coordinate and provide fast access
 * to the specific discrete coordiante
 * 
 * For the last two methods, memory should be constructed first with
 * constructMemory() method.
 * 
 * The previous values can be saved and accessed using addMemories and getDelay.
 * 
 * The type of computation, if they involve intra {@link Parameter}
 * communication can be changed with toParallel and onLine methods.
 * 
 * If the {@link Parameter} computation involve only non dynamic
 * {@link Parameter} it should be set to static using toStatic method : it will
 * avoid to call the compute method unless a non dynamic Parameter changed (user
 * using command line for instance see {@link Var}). In this case, a bottom up
 * signal is sent with signalParent() method, hence a parameter should be
 * registered as parent when it is getting sons.
 * 
 * @param E : stored type of the Parameter
 * 
 * 
 * 
 * 
 * with multiple heritage we would add a List<AbstractMap> parents attributes
 * with getter and setters and implement : <li>public void addParent(AbstractMap
 * updatable);</li> <li>public void signalParents() throws
 * NullCoordinateException;</li>
 * 
 * @author bchappet
 * 
 */
public interface Parameter<E> extends Cloneable {

	

	
	/**
	 * Get activity at specific index
	 * 
	 * @param coor
	 * @return
	 * @throws NullCoordinateException
	 */
	public E getIndex(int index);


	/**
	 * Set value at specific index
	 * 
	 * @param index
	 * @param new Val
	 */
	public void setIndex(int index, E newVal);
	

	/**
	 * Construct an array with each values of the memory Usefull to handle
	 * optimized computation
	 * 
	 * @return
	 */
	public abstract List<E> getValues();


	

	public Parameter<E> clone();


	
	



}
