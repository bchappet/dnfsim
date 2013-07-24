package maps;

import gui.Node;
import unitModel.UnitModel;
import coordinates.NullCoordinateException;
import coordinates.Space;
import utils.Cloneable;

/**
 * A parameter is an object which has a value at given coordinate.
 * 
 * The standard way to access its value is with the get(Double ... coordinates) method.
 * the coordinate are considered to be continuous one, and a null value is possible
 * for some dimension (sparse dimension).
 * 
 * The get(int index) method provide a very fast access to one specific data stored
 * in a linear collection.
 * 
 * The getFast(integer... coor) use discrete coordinate and provide fast access to the 
 * specific discrete coordiante
 * 
 * For the last two methods, memory should be constructed first with constructMemory() method.
 * 
 * The previous values can be saved and accessed using addMemories and getDelay.
 * 
 * The type of computation, if they involve intra {@link Parameter} communication can
 * be changed with toParallel and onLine methods.
 * 
 * If the {@link Parameter} computation involve only non dynamic {@link Parameter} it should
 * be set to static using toStatic method : it will avoid to call the compute method
 * unless a non dynamic Parameter changed (user using command line for instance see {@link Var}).
 * In this case, a bottom up signal is sent with signalParent() method, hence a parameter
 * should be registered as parent when it is getting sons.
 * 
 * 
 * 
 * 
 * 
 * 
 * with multiple heritage we would add a List<AbstractMap> parents attributes with getter and setters
 * and implement  :
 * <li>public void addParent(AbstractMap updatable);</li>
 * <li>public void signalParents() throws NullCoordinateException;</li>
 * @author bchappet
 *
 */
public interface Parameter extends Node,Cloneable{
	
	/**
	 * One iteration of computation
	 * @throws NullCoordinateException 
	 */
	public void compute() throws NullCoordinateException;
	
	/**
	 * Compute activity at specific coordinates
	 * @param index
	 */
	public void compute(int index);

	
	/**
	 * Get activity at specific continuous coordinates
	 * @param coor
	 * @return
	 * @throws NullCoordinateException
	 */
	public double get(Double... coor) throws NullCoordinateException;
	
	/**
	 * Get the activity @Var at specified coordinates
	 * @param coor
	 * @return
	 */
	public Var getVar(Double... coor);

	/**
	 * Get activity at specific index
	 * @param index
	 * @return
	 * @throws NullCoordinateException 
	 */
	public double get(int index) throws NullCoordinateException;
	
	/**
	 * Get activity at specific discrete coordinates
	 * @precond : isMemory== true
	 * @param coord
	 * @return
	 */
	public double getFast(int ... coord);
	
	/**
	 * Initialize a memory : data structure containing and computing values
	 * for every discrete coordinate
	 * @throws NullCoordinateException
	 */
	public void constructMemory();
	
	/**
	 * Construct an array with each values of the memory
	 * Usefull to handle optimized computation
	 * @return
	 */
	public abstract double[] getValues();

	/**
	 * Get value at specific continuous coordinates with delay
	 * @param delay : activity at time - delay TODO delay of 1 == parallel computation
	 * @param coord : coord of activity
	 * @return the activity
	 * @throws NullCoordinateException 
	 */
	public double getDelay(int delay, Double... coord) throws NullCoordinateException;
	
	
	
	/**
	 * Add memories buffers
	 * @param nb : nb memory to add
	 * @param historic : previous {@link UnitModel} states (optional : 
	 * if no historic is provided, the setInitActivity() of UnitModel is used)
	 * @throws NullCoordinateException 
	 * @Post : isMemory() = true
	 */
	public void addMemories(int nb,UnitModel... historic) throws NullCoordinateException;
	
	/**
	 *  Parallel computation mode is activated 
	 * @throws NullCoordinateException 
	 */
	public void toParallel() throws NullCoordinateException;

	/**
	 * Computation are now on line 
	 * @throws NullCoordinateException 
	 */
	public void onLine() throws NullCoordinateException;
	
	
	/**
	 * 
	 * @return true if the parameter is static
	 */
	public boolean isStatic();

	/**
	 * Transform a {@link Parameter}
	 * @post isStatic() == true
	 * @throws NullCoordinateException
	 */
	public void toStatic() throws NullCoordinateException;
	
	/**
	 * Reset the time and the state
	 */
	public void reset();

	
	/**
	 * Add a parent to the parameter
	 * @param updatable
	 */
	public void addParent(AbstractMap updatable);
	
	/**
	 * Signal that the parameter has changed
	 * @throws NullCoordinateException 
	 */
	public void signalParents() throws NullCoordinateException;
	/**
	 * Delete parameter from the tree
	 */
	public void delete();
	

	
	public Parameter clone();

	public String getName();

	public Space getSpace();
	
	public Parameter getDt();
	
	
	///////////////////////Only used in test
	/**
	 * Recursively construct all memories
	 */
	public void constructAllMemories();

	/**
	 * Set value at specific index
	 * @param index
	 * @param new Val
	 */
	public void setIndex(int index, double newVal);

	

	
	

		



	

}
