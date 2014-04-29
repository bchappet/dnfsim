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
public class Var<E>  implements Parameter<E>,Cloneable {

	/**Current value **/
	public E val; 
	/**Name (optional) **/
	protected String name;
	
	
	public Var(E val)
	{
		this.val = val;
		this.name = null;
	}
	/**
	 * 
	 * @param name
	 * @param val
	 */
	public Var(String name,E val)
	{
		this.val = val;
		this.name = name;
	}
	
	/**
	 * Return the value
	 * @return
	 */
	public E get() {
		return val;
	}


	/**
	 * Set the value
	 * @param val
	 */
	public void set(E val) {
		this.val = val;

	}

	/**
	 * Get the name
	 * @return
	 */
	public String getName() {
		return name;
	}




	public Var<E> clone()
	{
		Var<E> clone = null;
		try {
			clone = (Var<E>) super.clone();
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
	public E getIndex(int index) {
		return val;
	}

	@Override
	public List<E> getValues() {
		List<E> ret = new ArrayList<E>();
		ret.add(val);
		return ret;
	}
	@Override
	public void setIndex(int index, E newVal) {
		this.val = (E) newVal;
	}

	

	

	


	
	

}
