package main.java.maps;


/**
 * If a unit need a global precomputation throughout the map
 * before the real computation, it should implements
 * this interface
 * @author bchappet
 *
 */
public interface Precomputation {
	
	/**
	 * First computation applied throughout the map
	 */
	public void precompute();

}
