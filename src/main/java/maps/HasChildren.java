package main.java.maps;

import java.util.List;

/**
 * Interface for @{Parameter} which can be in the parameter tree
 * @author bchappet
 *
 * @param <T>
 */
public interface HasChildren<T> extends Parameter<T> {
	
	/**
	 * Return the children
	 * @return
	 */
	public List<Parameter> getParameters();

}
