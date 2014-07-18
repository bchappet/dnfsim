package main.java.space;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.maps.Parameter;

/**
 * Basicly a vector of T
 * @author bchappet
 *
 * @param <T>
 */
public class Coord<T> implements Parameter<T> {
	private List<T> values;
	private List<T> initValues;
	private String name;
	/**Optional **/
	private Space space; 
	
	public Space getSpace() {
		return space;
	}
	/**
	 * Constructor with an array
	 * @param values
	 */
	public Coord(T... values){
		this(null,values);
		
	}
	/**
	 * Create an empty vector of the same size as the given coord
	 * and fill it zith zero
	 * @param coord
	 */
	public Coord(Coord coord,T zero) {
		this.values = new ArrayList<T>(coord.getSize());
		for(int i =0 ; i < coord.getSize() ; i++){
			this.values.add(zero);
		}
		this.initValues = (List<T>) ((ArrayList<T>)this.values).clone();
		this.name = null;
	}
	

	public Coord(String name,T... values){
		this.values = new ArrayList<T>(values.length);
		this.values.addAll(Arrays.asList(values));
		this.initValues = (List<T>) ((ArrayList<T>)this.values).clone();
		this.name = name;
	}
	
	/**
	 * Constructor with a list
	 * @param values
	 */
	public Coord(ArrayList<T> values){
		this.values = values;
		this.initValues = (List<T>) ((ArrayList<T>)this.values).clone();
	}
	
	public Coord(ArrayList<T> list, DoubleSpace doubleSpace) {
		this(list);
		this.setSpace(doubleSpace);
	}
	
	public void setSpace(Space space){
		this.space = space;
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
		String ret = "(";
		for(int i = 0 ; i < values.size()-1 ; i++){
			ret += values.get(i) + ",";
		}
		ret += values.get(values.size()-1) + ")";
		return ret;
	}


	public void set(int index, T newVal) {
		this.values.set(index, newVal);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((values == null) ? 0 : values.hashCode());
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
		if (!(obj instanceof Coord))
			return false;
		Coord other = (Coord) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
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


	public void set(T... newVal) {
		for(int i = 0 ; i < newVal.length ; i++){
			this.values.set(i, newVal[i]);
		}
		
	}


	@Override
	public String getName() {
		return name;
	}
	@Override
	public void reset() {
		this.values = (List<T>) ((ArrayList<T>)this.initValues).clone();
		
	}
}
