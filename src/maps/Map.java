package maps;

import space.Space;

/**
 * Define a map:
 * 	a map has a space, a name and can be computed and as it can be computed, it has parameters
 * @author bchappet
 *
 * @param <T> Type of data in the map
 * @param <C> Type of coordinate use to access the map
 */
public interface Map<T,C> extends Parameter<T> {
	
	public Space<C> getSpace();
	
	public String getName();
	
	/**
	 * Compute the next state with the parameters
	 * 
	 */
	public void compute();
	
	/**
	 * Add parameter to the parameter list
	 * @param params
	 */
	public void addParameters(Parameter<T>... params);
	
	/**
	 * Set a parameter at this index
	 * @param index
	 * @param newParam
	 * @return the paramater previously at this index
	 */
	public Parameter<T> setParameter(int index,Parameter<T> newParam);
	
}
