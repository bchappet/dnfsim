package main.java.maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
	* A Var is an object which has a name and can be modified from the controller
	* via the name
 *
 */
public class Var<T> extends Observable  implements Parameter<T>,SingleValueParam<T>{

	/**Current value **/
	private T val; 
	/**Name (optional) **/
	private String name;
	
	private T initialValue;
	
	/**
	 * 
	 * @param val
	 */
	public Var(T val)
	{
		this.val = val;
		this.initialValue = val;
		this.name = null;
//		System.out.println("init val = " + val + " name " + name);
	}
	/**
	 * 
	 * @param name
	 * @param val
	 */
	public Var(String name,T val)
	{
		this.val = val;
		this.initialValue = val;
		this.name = name;
//		System.out.println("init val = " + val + " name " + name);
	}
	
	/**
	 * Return the value
	 * @return
	 */
	@Override
	public T get() {
		return val;
	}


	/**
	 * Set the value
	 * will notify the observers
	 * @param val
	 */
	public void set(T val) {
		this.val = val;
		this.setChanged();
		this.notifyObservers(this.val);
//		System.out.println("set " + name + " new val : " + val);

	}

	/**
	 * Get the name
	 * @return
	 */
	public String getName() {
		return name;
	}




	public Var<T> clone()
	{
		Var<T> clone = null;
		try {
			clone = (Var<T>) super.clone();
			clone.val = this.val;
			clone.name = this.name;
		} catch (CloneNotSupportedException e) {
			// clone is supported
			e.printStackTrace();
		}
		return clone;
	}

	

	public String toString(){
		if(name!=null)
			return name;
		else
			return super.toString();
	}

	@Override
	public T getIndex(int index) {
		return val;
	}

	@Override
	public List<T> getValues() {
		List<T> ret = new ArrayList<T>();
		ret.add(val);
		return ret;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((val == null) ? 0 : val.hashCode());
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
		if (!(obj instanceof Var))
			return false;
		Var other = (Var) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (val == null) {
			if (other.val != null)
				return false;
		} else if (!val.equals(other.val))
			return false;
		return true;
	}
	@Override
	public void reset() {
		this.set(this.initialValue);
		
	}
	
	

	

	

	


	
	

}
