package main.java.space;

import java.util.Arrays;

import main.java.maps.SingleValueParam;
import main.java.maps.Var;

/**
 * N-dimensional discrete main.java.space.
 * Provide basic until methods to flatten the main.java.space when needed
 * Provide basic iterators to iterate on the main.java.space
 * Provide utilities to wrap main.java.coordinates
 * 
 * TODO finish it
 * 
 * The main.java.coordinates are of type {@Coord<Integer>} 
 *  @param T : type of main.java.coordinates
 *  
 * @author bchappet
 *
 */
public class Space<T> implements Cloneable{
	
	
	/**
	 * N Dimension of the main.java.space (if fixed should be indexed with constants) 
	 */
	private SingleValueParam<Integer>[] dimensions;

	
	/**
	 * Init main.java.space with n = dimension.length and dimension dimension.
	 * @param dimensions (will be copied)
	 */
	public Space(SingleValueParam<Integer>... dimensions){
		this.dimensions = Arrays.copyOf(dimensions,dimensions.length);
		
	}
	
	/**
	 * Transform an index corresponding to the flattened version of the main.java.space to
	 * its coordinate into this main.java.space
	 * @param index
	 * @return
	 */
	public Coord<Integer> indexToCoord(int index){
		return null; //TODO
	}
	
	/**
	 * Transform a coordinate into its flattened main.java.space index
	 * @param coord
	 * @return
	 */
	public int coordToIndex(Coord<T> coord){
		return -1; //TODO
	}
	
	/**
	 * Return the volume of the main.java.space
	 * @return
	 */
	public int getVolume(){
		return -1;//TODO
	}
	
	
	
	/**
	 * Wrap main.java.coordinates to stay within the main.java.space
	 * @param coord
	 * @return wrapped main.java.coordinates
	 */
	public Coord<T> wrapCoord(Coord<T> coord){
		return null;//TODO
	}
	
	public SingleValueParam<Integer>[] getDimensions(){
		return dimensions;
	}
	
	@Override
	public String toString(){
		return "" + this.getClass() + " dim:" + Arrays.toString(this.dimensions);
	
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(dimensions);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Space))
			return false;
		Space other = (Space) obj;
		if (!Arrays.equals(dimensions, other.dimensions))
			return false;
		return true;
	}

	@Override
	public Space<T> clone(){
		Space<T> clone = null;
		try {
			clone = (Space<T>) super.clone();
		} catch (CloneNotSupportedException e) {
			//  clone supported
			e.printStackTrace();
		}
		clone.dimensions = Arrays.copyOf(this.dimensions, this.dimensions.length);
		return clone;
	}
	
	
	
	

}
