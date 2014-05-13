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
public class Coord2D<T> extends Coord<T> {
	
	public T x;
	public T y;
	
	/**
	 * Specific constructor with x and y
	 * @param x
	 * @param y
	 */
	public Coord2D(T x,T y){
		super((ArrayList<T>) null);
		this.x = x;
		this.y = y;
	}
	
	/**
	 * General constructor
	 * @param values
	 */
	public Coord2D(List<T> values) {
		super(values);
		this.x = values.get(Space2D.X);
		this.y = values.get(Space2D.Y);
	}

	public Coord2D(Coord<T> coord) {
		this(coord.getIndex(Space2D.X),coord.getIndex(Space2D.Y));
	}

	@Override
	public List<T> getValues(){
		List<T> ret = new ArrayList<T>(2);
		ret.add(x);
		ret.add(y);
		return ret;
	}
	
	@Override
	public T getIndex(int index){
		if(index == Space2D.X)
			return x;
		else if(index == Space2D.Y)
			return y;
		else
			throw new IndexOutOfBoundsException("index " + index + " is not available for a two dimentional coordinate.");
	}
	
	public void set(int index, T newVal) {
		if(index == Space2D.X)
			x = newVal;
		else if(index == Space2D.Y)
			y = newVal;
		else
			throw new IndexOutOfBoundsException("index " + index + " is not available for a two dimentional coordinate.");
		
	}
	
	public int getSize() {
		return 2;
	}
	
	public boolean equals(Object o){
		if( o instanceof Coord2D<?>){
			Coord2D<T> coord = (Coord2D<T>) o;
			return coord.x.equals(this.x) && coord.y.equals(this.y);
		}else if(o instanceof Coord<?>){
			Coord<T> coord = (Coord<T>) o;
			return coord.getIndex(Space2D.X).equals(this.x) && coord.getIndex(Space2D.Y).equals(this.y) && coord.getSize() == 2;
		}else{
			return false;
		}
		
	}
	
	/**
	 * X and Y are shared, except if there are {@Number} 
	 */
	@Override
	public Coord2D<T> clone(){
		Coord2D<T> clone = new Coord2D<T>(x,y);
		return clone;
	}
	
	@Override
	public String toString(){
		return "("+this.x+","+this.y+")";
	}
	
	

}
