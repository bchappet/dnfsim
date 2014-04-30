package maps;

/**
 * Define acces without coordinates
 * @author bchappet
 * 
 * @param <T> : type of data
 *
 */
public interface NoDimension<T> {
	
	/**
	 * No dimension getter
	 * @return
	 */
	public T get();
	
}
