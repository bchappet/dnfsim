package main.java.maps;

/**
 * Define acces without main.java.coordinates
 * @author bchappet
 * 
 * @param <T> : type of data
 *
 */
public interface SingleValueParam<T> extends Parameter<T> {
	
	/**
	 * No dimension getter
	 * @return
	 */
	public T get();
	
}
