package space;

import java.util.Arrays;

/**
 * N-dimensional discrete space.
 * Provide basic until methods to flatten the space when needed
 * Provide basic iterators to iterate on the space
 * Provide utilities to wrap coordinates
 * 
 * TODO finish it
 * 
 * The coordinates are of type {@Coord<Integer>} 
 *  @param T : type of coordinates
 *  
 * @author bchappet
 *
 */
public class Space<T> implements Cloneable{
	
	
	/**
	 * N Dimension of the space (if fixed should be indexed with constants) 
	 */
	private int[] dimensions;

	
	/**
	 * Init space with n = dimension.length and dimension dimension.
	 * @param dimensions (will be copied)
	 */
	public Space(int... dimensions){
		this.dimensions = Arrays.copyOf(dimensions,dimensions.length);
		
	}
	
	/**
	 * Transform an index corresponding to the flattened version of the space to
	 * its coordinate into this space
	 * @param index
	 * @return
	 */
	public Coord<Integer> indexToCoord(int index){
		return null; //TODO
	}
	
	/**
	 * Transform a coordinate into its flattened space index
	 * @param coord
	 * @return
	 */
	public int coordToIndex(Coord<T> coord){
		return -1; //TODO
	}
	
	/**
	 * Return the volume of the space
	 * @return
	 */
	public int getVolume(){
		return -1;//TODO
	}
	
	
	
	/**
	 * Wrap coordinates to stay within the space
	 * @param coord
	 * @return wrapped coordinates
	 */
	public Coord<T> wrapCoord(Coord<T> coord){
		return null;//TODO
	}
	
	public int[] getDimensions(){
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
