package main.java.coordinates;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author bchappet
 *	@deprecated
 * @param <T>
 */
public class Coor<T extends Number> implements Iterable<T>{

	public static final String X = "X";
	public static final String Y = "Y";
	public static final String Z = "Z";
	
	public static final String[] AXIS = {X,Y,Z}; 


	protected java.util.Map<String,T> coors;
	
	


	public Coor()
	{
		coors = new HashMap<String,T>();
	}

	/**
	 * Copy constructor
	 * @original coor
	 */
	public Coor(Coor<T> coor)
	{
		this.coors = new HashMap<String,T>(coor.coors);
	}

	public Coor(T... axes)
	{
		this();
		try{
			coors.put(X, axes[0]);
			coors.put(Y, axes[1]);
			coors.put(Z, axes[2]);
		}catch (Exception e) {
			// TODO: find a better way
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Coor<T> clone()
	{
		return new Coor(this);
	}

	

	



	public T get(String axis) {
		return coors.get(axis);
	}

	public T put(String axis, T value)
	{
		return coors.put(axis, value);
	}
	
	/**
	 * Add an axis str according to the axis order
	 * @original axis
	 * @original str
	 */
	public void put(int axis, T value) {
		this.put(AXIS[axis], value);
		
	}
	
	
	public boolean containsKey(String time) {
		return coors.containsKey(time);
	}

	/**
	 * Return the discreteSize of the refSpace (without the time)
	 * @return
	 */
	public int getDim() {
		int ret = coors.size();
		
		return ret;
	}

	/**
	 * Product
	 * Return a copy
	 * @original a
	 * @return
	 */
	public Coor<T> multCopy(Double a) {
		Coor<T> ret = new Coor<T>(this);
		ret.mult(a);
		return ret;
	}

	@SuppressWarnings("unchecked")
	public void mult(Double a) {
		for(String s : coors.keySet())
		{
			coors.put(s, (T) new Double(coors.get(s).doubleValue()*a));
		}	
	}

	/**
	 * Addition
	 * Return a copy
	 * @original a
	 * @return
	 */
	public Coor<T> addCopy(Double a) {
		Coor<T> ret = new Coor<T>(this);
		ret.add(a);
		return ret;
	}

	@SuppressWarnings("unchecked")
	public void add(Double a) {
		for(String s : coors.keySet())
		{
			coors.put(s, (T) new Double(coors.get(s).doubleValue()+a));
		}	
	}

	/**
	 * Addition
	 * Return a copy
	 * @original a
	 * @return
	 */
	public Coor<T> addCopy(Coor<T> a) {
		Coor<T> ret = new Coor<T>(this);
		ret.add(a);
		return ret;
	}

	@SuppressWarnings("unchecked")
	public void add(Coor<T> a) {
		for(String s : coors.keySet())
		{
			coors.put(s, (T) new Double(coors.get(s).doubleValue()+
					a.get(s).doubleValue()));
		}	
	}

	/**
	 * Multiplication
	 * Return a copy
	 * @original a
	 * @return
	 */
	public Coor<T> multCopy(Coor<T> a) {
		Coor<T> ret = new Coor<T>(this);
		ret.mult(a);
		return ret;
	}

	@SuppressWarnings("unchecked")
	public void mult(Coor<T> a) {
		for(String s : coors.keySet())
		{
			coors.put(s, (T) new Double(coors.get(s).doubleValue()*
					a.get(s).doubleValue()));
		}	
	}

	/**
	 * Subtraction ret = this-a
	 * Return a copy
	 * @original a
	 * @return
	 */
	public Coor<T> subCopy(Coor<T> a) {
		Coor<T> ret = new Coor<T>(this);
		ret.sub(a);
		return ret;
	}
	/**
	 * this = this - a
	 * @original a
	 */
	@SuppressWarnings("unchecked")
	public void sub(Coor<T> a) {
		for(String s : coors.keySet())
		{
			this.put(s, (T) new Double(this.get(s).doubleValue()-
					a.get(s).doubleValue()));
		}	
	}
	
	/** ret = this ./ a**/
	public Coor<T> divCopy(double a) {
		Coor<T> ret = new Coor<T>(this);
		ret.div(a);
		return ret;
	}
	/** this = this ./ resolution**/
	@SuppressWarnings("unchecked")
	public void div(double a) {
		for(String s : coors.keySet())
		{
			this.put(s, (T) new Double(this.get(s).doubleValue()/a));
		}	
	}

	/**
	 * Transform the coordinate in integer matrix (cast)
	 * Copy
	 * @return
	 */
	public Coor<Integer> toInt() {
		Coor<Integer> ret = new Coor<Integer>();
		ret = new Coor<Integer>();
		for(String s : coors.keySet())
		{
			ret.coors.put(s, new Integer((int) (coors.get(s).doubleValue())));
		}
		return ret;
	}
	
	/**
	 * Transform the coordinate in integer matrix (round)
	 * Copy
	 * @return
	 */
	public Coor<Integer> round() {
		Coor<Integer> ret = new Coor<Integer>();
		ret = new Coor<Integer>();
		for(String s : coors.keySet())
		{
			ret.coors.put(s, new Integer((int) Math.round(coors.get(s).doubleValue())));
		}
		return ret;
	}

	/**
	 * Return a copy of these coor casted in double
	 */
	public Coor<Double> toDouble() {
		Coor<Double> ret = new Coor<Double>();
		ret = new Coor<Double>();
		for(String s : coors.keySet())
		{
			ret.coors.put(s, new Double((double) coors.get(s).doubleValue()));
		}
		return ret;
	}





	public String toString()
	{
		String ret = "(";
		for(int i = 0 ; i < coors.size() -1 ; i++)
		{
			ret += coors.get(AXIS[i]) + ",";
		}
		ret += coors.get(AXIS[coors.size()-1]) + ")";
		
		return ret;
	}

	/**
	 * 
	 * @return X*Y*Z*...
	 */
	public int getSpaceSize() {
		int ret = 1;
		for(String s : coors.keySet())
		{
			ret *= new Integer(coors.get(s).intValue());
		}
		return ret;
	}
	/**
	 * TODO validate
	 * @original a
	 * @original coor
	 * @return
	 */
	public Coor<Integer> decompose(int a)
	{
		Coor<Integer> ret = null;
		List<Integer> list = new LinkedList<Integer>();
		int i = 0;

		while(a > 0)
		{
			int value = new Integer((Integer) this.get(i));
			list.add(a%value);
			a = a/value;
			i++;
		}
		while(i < this.getDim())
		{
			list.add(0);
			i++;
		}
		ret = new Coor<Integer>(list.toArray(new Integer[list.size()]));

		return ret;
	}
	/**
	 * TODO Find an other way
	 * @original i
	 * @return
	 */
	public T get(int i) {
		return this.get(AXIS[i]);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coors == null) ? 0 : coors.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coor<?> other = (Coor<?>) obj;
		if (coors == null) {
			if (other.coors != null)
				return false;
		} else if (!coors.equals(other.coors))
			return false;
		return true;
	}

	public static double distance(Coor<Double> a,Coor<Double> b) {
		double dist = 0;
		for(String key : a.coors.keySet())
		{
			//System.out.println(dist + " =W>" + a.get(key) + " - " + b.get(key) + " " + key);
			dist += Math.pow(a.get(key)-b.get(key), 2);
		}
		dist = Math.sqrt(dist);
		return dist;
	}

	@Override
	public Iterator<T> iterator() {
		return coors.values().iterator();
	}

	/**
	 * Transform a coordinate in an array of int
	 * @return
	 */
	public int[] toArrayInt() {
		int dim = this.getDim();
		int[] ret = new int[dim];
		int i = 0;
		for(T val : coors.values())
		{
			ret[i] = (Integer) val;
			i++;
		}
		
		return ret;
	}

	public static double distance(Double[] a, Double[] b) {
		double dist = 0;
		for(int i = 0 ; i < a.length ; i++)
		{
			//System.out.println(dist + " =W>" + a.get(key) + " - " + b.get(key) + " " + key);
			dist += Math.pow(a[i]-b[i], 2);
		}
		dist = Math.sqrt(dist);
		return dist;
	}

	

	

	





}
