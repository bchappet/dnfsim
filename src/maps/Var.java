package maps;

import java.util.ArrayList;
import java.util.List;

import utils.Cloneable;
import coordinates.NullCoordinateException;

/**
	* A Var is an object which has a name and can be modified from the controller
	* via the name
 *
 */
public class Var<T>  implements Parameter<T>,NoDimension<T>{

	/**Current value **/
	public T val; 
	/**Name (optional) **/
	protected String name;
	
	
	public Var(T val)
	{
		this.val = val;
		this.name = null;
	}
	/**
	 * 
	 * @param name
	 * @param val
	 */
	public Var(String name,T val)
	{
		this.val = val;
		this.name = name;
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
	 * @param val
	 */
	public void set(T val) {
		this.val = val;

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
	
	

	

	

	


	
	

}
