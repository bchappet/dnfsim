package maps;

import space.Space;

/**
 * Define a map:
 * 	a map has a space, a name and can be computed
 * @author bchappet
 *
 * @param <T>
 * @param <C>
 */
public interface Map<T,C> extends Parameter<T> {
	
	public Space<C> getSpace();
	
	public String getName();
	
	public void compute();
	
}
