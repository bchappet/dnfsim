package utils;

/**
 * More practical for deep copy
 * @author bchappet
 *
 */
public interface Cloneable extends java.lang.Cloneable {
	
	public Object clone() throws CloneNotSupportedException;

}
