package main.java.space;

import java.util.ArrayList;
import java.util.List;

/**
 * Use 2 public field for fast access and reduce memory
 * Be careful to overide every Coord method to exploit this ability
 * @author bchappet
 *
 * @param <T>
 */
public class Coord1D<T> extends Coord2D<T> {
	
	public T x;
	
	/**
	 * Specific constructor with x and y
	 * @param x
	 * @param y
	 */
	public Coord1D(T x){
		super((ArrayList<T>) null);
		this.x = x;
	}
	
	/**
	 * General constructor
	 * @param values
	 */
	public Coord1D(ArrayList<T> values) {
		super(values);
		this.x = values.get(Space2D.X);
	}

	@Override
	public List<T> getValues(){
		List<T> ret = new ArrayList<T>(1);
		ret.add(x);
		return ret;
	}
	
	@Override
	public T getIndex(int index){
		if(index == Space1D.X)
			return x;
		else
			throw new IndexOutOfBoundsException("index " + index + " is not available for a two dimentional coordinate.");
	}
	
	public void set(int index, T newVal) {
		if(index == Space1D.X)
			x = newVal;
		else
			throw new IndexOutOfBoundsException("index " + index + " is not available for a two dimentional coordinate.");
		
	}
	
	public int getSize() {
		return 1;
	}
	
	public boolean equals(Object o){
		if( o instanceof Coord1D<?>){
			Coord1D<T> coord = (Coord1D<T>) o;
			return coord.x.equals(this.x);
		}else if(o instanceof Coord<?>){
			Coord<T> coord = (Coord<T>) o;
			return coord.getIndex(Space2D.X).equals(this.x) && coord.getSize() == 1;
		}else{
			return false;
		}
		
	}
	
	/**
	 * X and Y are shared, except if there are {@Number} 
	 */
	@Override
	public Coord1D<T> clone(){
		Coord1D<T> clone = new Coord1D<T>(x);
		return clone;
	}
	
	@Override
	public String toString(){
		return ""+this.x;
	}
	
	

}
