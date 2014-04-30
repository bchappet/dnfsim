package space;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import maps.Parameter;

/**
 * Basicly a vector of T
 * @author bchappet
 *
 * @param <T>
 */
public class Coord<T> implements Parameter<T> {
	private List<T> values;
	
	/**
	 * Constructor with an array
	 * @param values
	 */
	public Coord(T... values){
		this.values = new ArrayList<T>(values.length);
		this.values.addAll(Arrays.asList(values));
	}
	
	
	/**
	 * Constructor with a list
	 * @param values
	 */
	public Coord(List<T> values){
		this.values = values;
	}
	
	/**
	 * Return coordinate on specific axis
	 * @param index
	 * @throws ArrayIndexOutOfBoundsException if outside the coord length
	 * @return
	 */
	@Override
	public T getIndex(int index){
		return this.values.get(index);
	}
	
	/**
	 * Return the list of values
	 * @return
	 */
	@Override
	public List<T> getValues(){
		return values;
	}
	
	@Override
	public String toString(){
		String ret = "";
		for(int i = 0 ; i < values.size()-1 ; i++){
			ret += values.get(i) + ",";
		}
		ret += values.get(values.size()-1);
		return ret;
	}


	public void set(int index, T newVal) {
		this.values.set(index, newVal);
	}
	
	@Override
	public boolean equals(Object o){
		Coord<T> coord = (Coord<T>) o;
		return values.equals(coord.getValues());
	}
	
	/**
	 * We clone the list => 
	 * The list ref are different but the object ref in the list are equal
	 * The two coords are equals
	 * 
	 */
	@Override
	public Coord<T> clone(){
		Coord<T> clone = null;
		try {
			clone = (Coord<T>) super.clone();
			clone.values = new ArrayList<T>(this.values);
		} catch (CloneNotSupportedException e) {
			// supported
			e.printStackTrace();
		}
		return clone;
	}

	/**
	 * Return the size of the coordinate
	 * @return
	 */
	public int getSize() {
		return this.values.size();
	}
}
