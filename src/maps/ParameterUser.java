package maps;

import java.util.ArrayList;
import java.util.List;

import coordinates.Space;

public abstract class ParameterUser implements Cloneable {

	/**List of linked map**/
	protected List<Parameter> params;

	/**Space of computation**/
	protected Space space;

	/**Curent (local) time (s) of comutation**/
	protected Var time;

	/**Computaion frequency (s)**/
	protected Parameter dt;


	public ParameterUser(Parameter dt2,Space space,Parameter ... params)
	{
		this.dt = dt2;
		this.space =space;
		this.time = new Var(0);
		this.params = new ArrayList<Parameter>();
		addParameters(params);

		onInitilization();
	}
	/**
	 * Share attributes with param
	 * @param param
	 */
	public ParameterUser(ParameterUser param)
	{
		this.shareAttributesWith(param);
	}

	public ParameterUser clone(){
		ParameterUser clone  = null;
		try{
			clone = (ParameterUser) super.clone();
			clone.dt = this.dt; //shared
			//copy but the parameter are shared!!
		//	clone.params = (List<Parameter>) ((ArrayList)this.params).clone();
			clone.space = this.space;//shared
			clone.time = this.time.clone(); //copy
		}catch(CloneNotSupportedException e){
			e.printStackTrace();
			System.exit(-6);
		}
		return clone;
	}
	


	/**
	 * Return the {@link Parameter} having the specified name
	 * @param keyName
	 * @return
	 */
	public Parameter getParameter(String keyName) {
		Parameter ret = null;

		if(this.dt.getName() != null && this.dt.getName().equals(keyName))
			return this.dt;

		if(this.time.getName() != null && this.time.getName().equals(keyName))
			return this.time;

		int i = 0;
		while(ret == null && i < params.size())
		{
			
			Parameter p = params.get(i);
			//System.out.println(keyName + "==>"+p.getName()+"::");
			if(p.getName() != null && p.getName().equals(keyName))
				ret = p;
			else if(p instanceof ParameterUser)
				ret = ((ParameterUser) p).getParameter(keyName);
			
			i++;
		}
		return ret;

	}

	public ParameterUser() {
		params = new ArrayList<Parameter>();
		//This parameterUser will be initialized with shareAttributesWith
	}
	/**
	 * Share attributes with param
	 * @param param
	 */
	public void shareAttributesWith(ParameterUser param)
	{
		this.dt = param.dt;
		this.space = param.space;
		this.time = param.time;
		this.params = param.params;
		this.onInitilization();
	}

	/**
	 * This method will be call when the parameter are initialized :
	 * <li> When we construct the parameter user with every parameters</li>
	 * <li> When we share attributes with it</li>
	 */
	protected abstract void onInitilization();
	/**
	 * Add parameter to the list
	 * @param params
	 */
	public void addParameters(Parameter ... params)
	{
		for(Parameter p : params)
		{
			this.params.add(p);
		}
	}

	/**
	 * Remove a parameter from the list
	 * @param p
	 */
	protected void removeParameter(Parameter p) {
		params.remove(p);
	}
	
	




	/**
	 * Get the parameter specified in the index
	 * @param index
	 * @return the parameter at index index
	 */
	public Parameter getParam(int index){
		return params.get(index);
	}


	public List<Parameter> getParams() {
		return params;
	}

	public Space getSpace() {
		return space;
	}

	public double getTime() {
		return time.get();
	}

	public Parameter getDt() {
		return dt;
	}


}
