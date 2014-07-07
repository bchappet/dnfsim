package main.resources.utils;

/**
 * We define this interface to force the {@link Cloneable} class
 * to implement clone.
 * And to have a public access to the clone method when we cast it in {@link Cloneable}
 * @author bchappet
 *
 */
public interface Cloneable extends java.lang.Cloneable {
	
	/**
	 * @see {@link Object.clone()}
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException;

}
