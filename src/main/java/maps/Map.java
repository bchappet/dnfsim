package main.java.maps;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.space.Space;
import main.java.space.Space1D;
import main.java.space.Space2D;
import main.resources.utils.ArrayUtils;

/**
 * Define a map:
 * 	a map has a main.java.space, a name and can be computed and as it can be computed, it has parameters
 * @author bchappet
 *
 * @param <T> Type of data in the map
 * @param <C> Type of coordinate use to access the map
 */
public abstract class Map<T,C> implements HasChildren<T>,Computable {
	
	/**Name of the map**/
	private String name;
	
	/**Space of the map**/
	private Space<C> space;
	
	/**List of parameters**/
	private List<Parameter> params;
	/**Update time for this map**/
	private Var<BigDecimal> dt;
	/**Current time for this map**/
	private BigDecimal time;

	
	
	public Map(String name,Var<BigDecimal> dt,Space<C> space, Parameter... params) {
		this.space = space;
		this.name = name;
		this.params = new ArrayList<Parameter>(Arrays.asList(params));
		this.time = new BigDecimal("0");
		this.dt = dt;
	}
	
	
	/**
	 * Compute the next state with the parameters
	 * 
	 */
	public abstract void compute();
	
	public void reset(){
		this.time = BigDecimal.ZERO;
	}
	
	
	@Override
	public Map<T,C> clone(){
		Map<T, C> clone = null;
		try {
			clone = (Map<T,C>) super.clone();
			clone.space = this.space; //shared
			clone.dt = this.dt.clone(); //clone
			clone.time = this.time.add(new BigDecimal("0")); //clone
			clone.name = this.name + "_clone"; //different
			clone.params = (List<Parameter>) ((ArrayList<Parameter>) params).clone(); //Shallow clone 
		} catch (CloneNotSupportedException e) {
			// supported
			e.printStackTrace();
		}
		return clone;
	}
	
	
	
	public String getName(){
		return this.name;
	}
	
	
	public Var<BigDecimal> getDt(){
		return this.dt;
	}
	
	public BigDecimal getTime(){
		return this.time;
	}
	
	/**
	 * return the para�eter at the index index
	 * @param index
	 * @return
	 */
	public Parameter getParam(int index){
		return this.params.get(index);
	}
	
	
	
	/**
	 * Add parameter to the parameter list
	 * @param params
	 */
	public void addParameters(Parameter... params) {
		this.params.addAll(Arrays.asList(params));
		
	}

	/**
	 * Set a parameter at this index
	 * @param index
	 * @param newParam
	 * @return the paramater previously at this index
	 */
	public Parameter setParameter(int index, Parameter newParam) {
		return this.params.set(index, newParam);
	}

	
	
	
	/**
	 * Getter for the parameter list
	 * @return
	 */
	public List<Parameter> getParameters(){
		return this.params;
	}

	public void setTime(BigDecimal newTime){
		this.time = newTime;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dt == null) ? 0 : dt.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		//result = prime * result + ((params == null) ? 0 : params.hashCode());
		result = prime * result + ((space == null) ? 0 : space.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
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
		if (!(obj instanceof Map))
			return false;
		Map other = (Map) obj;
		if (dt == null) {
			if (other.dt != null)
				return false;
		} else if (!dt.equals(other.dt))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (params == null) {
			if (other.params != null)
				return false;
		} else if (!params.equals(other.params))
			return false;
		if (space == null) {
			if (other.space != null)
				return false;
		} else if (!space.equals(other.space))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}
	
	
	public String toString(){
		if(this.space instanceof Space2D){
			return ArrayUtils.toString2D(getValues(),getSpace().getDimensions().getIndex(Space2D.X).get(),
				getSpace().getDimensions().getIndex(Space2D.Y).get());
		}else if (this.space instanceof Space1D){
			return ArrayUtils.toString1D(getValues(),getSpace().getDimensions().getIndex(Space2D.X).get());
		}else{
			return this.name + "space no displayable?";
		}
	}


	public Space<C> getSpace() {
		return this.space; 
	}


	

	
	
	
	
}
