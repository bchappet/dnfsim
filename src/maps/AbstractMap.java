package maps;

import gui.Updated;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import unitModel.UnitModel;
import console.CommandLine;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;
import coordinates.Space;
/**
 * A map is a way to acces values within a discret or continuous refSpace.
 * To speed up the access in a discrete refSpace the method 
 * contructMemory() create a data structure which will save the current state and
 * update it at every computation() call.
 * 
 * For now, there are two kind of implementation :
 * <li> The {@link AbstractUnitMap} will use {@link UnitModel} 
 * as smallest computation unit and a {@link List} as data Structure.
 * They are supposed to be compatible with every kind refSpace (n dimension)</li>
 * 
 * <li> The {@link Matrix} (optional) only purpose is to optimize computation
 * for somme specific spaces (eg. 2D convolution).</li>
 * 
 * It is possible to switch from {@link AbstractUnitMap} to {@link Matrix} using {@link MatrixWrapper}
 * and to switch from {@link Matrix} to {@link AbstractUnitMap} using the {@link UnitMatrixMap}.
 * 
 * There are two ways to construct a map : 
 * <li>Every parameter is given to the constructor</li>
 * <li>There are shared with an other {@link ParameterUser}</li>
 * 
 * 
 * @author bchappet
 *
 */
public abstract class AbstractMap extends ParameterUser implements Parameter,Cloneable{


	/**Name of the map**/
	protected String name;

	/**True if this map has memory**/
	protected boolean isMemory;
	/**True if the map is static**/
	protected boolean isStatic;

	/**Set of parent map**/
	protected Set<AbstractMap> parents;
	
	/**Set of vue object to signal***/
	protected Set<Updated> vue;



	/**
	 * Each {@link Parameter} is given to the contructor
	 * @param name
	 * @param dt
	 * @param refSpace
	 * @param maps
	 */
	public AbstractMap(String name,Parameter dt, Space space, Parameter... maps) {
		super(dt, space, maps);
		this.name = name;
		this.isMemory = false;
		this.isStatic = false;
		this.parents = new HashSet<AbstractMap>();
		this.vue = new HashSet<Updated>();
		updateParents();
	}
	
	/**
	 * The parameter will be shared with the {@link ParameterUser}
	 * @param name
	 * @param shareWith
	 */
	public AbstractMap(String name, ParameterUser shareWith) {
		super(shareWith);
		this.name = name;
		this.isMemory = false;
		this.isStatic = false;
		this.parents = new HashSet<AbstractMap>();
		this.vue = new HashSet<Updated>();
		updateParents();
	}
	
	public void addVue(Updated u){
		vue.add(u);
	}
	
	protected void updateVue(){
		for(Updated u : vue){
			u.update();
		}
	}




	@Override
	protected  void onInitilization(){
		//Nothing
	}

	/**
	 * Update the map until the time reaches the given timelimit(s) using the compute() method each dt while time >= timeLimit
	 * If the map is static the only modification is time += dt
	 * @param timeLimit (s)
	 * @throws NullCoordinateException 
	 */
	public void update(double timeLimit) throws NullCoordinateException
	{
//		System.out.println("Update : " + name);
		//		while (time.val<timeLimit) {
		//			if(!isStatic)
		//			{
		//				//Update the children params
		//				for(Parameter m : params) 
		//				{
		//					if(m instanceof AbstractMap){
		//						((AbstractMap) m).update(timeLimit);
		//					}
		//				}
		//				// Compute the new state
		//				this.compute();
		//			}
		//			// Update the time
		//			time.set(time.get()+dt.get());
		//		}
		//time.val >= timeLimit

		if(!isStatic)
		{
			//Update the children params
			for(Parameter m : params) {
				if(m instanceof AbstractMap){
					((AbstractMap) m).update(timeLimit);
				}
			}
		}
		while(time.val < timeLimit){
			if(!isStatic){
			//	System.err.println(params);
				this.compute();
				this.updateVue();
			}
			time.val = time.val + dt.get();
		}

	}

	/**
	 * @return an array with every values contained in the data structure
	 * @precond : isMemory() = true
	 */
	public abstract double[] getValues();

	/**
	 * Return the activity at the specific index
	 * Faster if memory
	 * @param index
	 * @return
	 * @throws NullCoordinateException 
	 */
	@Override
	public abstract double get(int index) throws NullCoordinateException;

	/**
	 * Fast version, to get values at discrete coordinates 
	 * @precond the memory is constructed
	 */
	@Override
	public abstract double getFast(int ... coord);

	/**
	 * Generate the collection with as much unit as necessary
	 * @throws NullCoordinateException 
	 * @throws CloneNotSupportedException 
	 */
	@Override
	public abstract void constructMemory();




	/**
	 * One iteration of computation
	 * @throws NullCoordinateException 
	 */
	public abstract void compute() throws NullCoordinateException;



	/**
	 * Transform the map into a static map : the update function will not change the values,
	 *  only a signalParent call can force the computation.
	 * This method call the same method on every child
	 * and compute once and for all the {@link AbstractMap} state
	 * @throws NullCoordinateException 
	 * @Post this.memory = true, this.isStatic = true; 
	 */
	@Override
	public void toStatic() throws NullCoordinateException
	{
		this.constructMemory();
		this.isStatic = true;
		for(Parameter p : params){
			p.toStatic();
		}

		this.compute();
		this.updateVue();

	}
	@Override
	public void reset() {
		if(!isStatic){
			this.time.set(0);
			for(Parameter m : params){
				m.reset();
			}
		}
		resetVue();

	}
	
	@Override
	public void resetState() {
		if(!isStatic){
			for(Parameter m : params){
				m.resetState();
			}
		}
		resetVue();

	}


	private void resetVue() {
		for(Updated u : vue){
			u.reset();
		}
		
	}

	@Override
	public void addParameters(Parameter ... params)
	{
		super.addParameters(params);
		for(Parameter m : params){
			m.addParent(this);
		}
	}

	@Override
	public void addParent(AbstractMap updatable)
	{
		this.parents.add(updatable);
	}
	
	@Override
	public void removeParent(AbstractMap updatable){
		this.parents.remove(updatable);
	}
	@Override
	public void signalParents()
	{
		//System.out.println("Receive here : " + name);
		try {
			if(isStatic)
				this.compute();
				this.updateVue();
		} catch (NullCoordinateException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		for(AbstractMap p : parents)
			p.signalParents();
	}
	@Override
	public  void delete()
	{
		for(Parameter p : params){
			p.removeParent(this);
		}
		
		super.delete();
		
		
		for(AbstractMap p : parents){
			p.removeParameter(this);
		}
			
	//	ensure null (accelerate GB collection?)
		name = null;
		parents = null;
	}


	/**
	 * Construct the memory of every linked parameters
	 * @throws NullCoordinateException
	 */
	public void constructParametersMemory() {
		for(Parameter p :params)
			p.constructMemory();
	}

	/**
	 * Update the parent {@link Set}. Should be called when new {@link Parameter} are added
	 */
	protected void updateParents()
	{
//		System.out.println(name + " ==> " + params);
		for(Parameter p : params){
			p.addParent(this);
		}
	}







	/**
	 * Return a string containing the map caracteristics and values (if memory)
	 * @throws NullCoordinateException 
	 */
	public String display2D() throws NullCoordinateException {
		String string = "Name : " + name + "\n";
		string += "Memory : " + isMemory +"\n";
		if(isMemory)
		{
			int index = 0;
			for(int i = 0 ; i < space.getResolution() ; i++){
				for(int j = 0 ; j < space.getResolution() ; j++){
					string += this.get(index) +",";
					index ++;
				}
				string +="\n";
			}
		}
		return string;
	}

	/**
	 * Return a string containing the map caracteristics and values (if memory)
	 * @throws NullCoordinateException 
	 */
//	public String displayMemory() throws NullCoordinateException {
//		String string = "";
//		int index = 0;
//		for(int i = 0 ; i < space.getResolution()-1 ; i++){
//			for(int j = 0 ; j < space.getResolution()-1 ; j++){
//				string += ((int)(this.get(index)*10000))/10000. + ",";
//				index ++;
//			}
//			string += ((int)(this.get(index)*10000))/10000.;
//			index ++;
//
//			string +="\n";
//		}
//
//		for(int j = 0 ; j < space.getResolution()-1 ; j++){
//			string += ((int)(this.get(index)*10000))/10000. + ",";
//			index ++;
//		}
//		string += ((int)(this.get(index)*10000))/10000.;
//		index ++;
//
//		return string;
//	}
	
	public String displayMemory() throws NullCoordinateException {
		String string = "";
		int index = 0;
		for(int i = 0 ; i < space.getResolution()-1 ; i++){
			for(int j = 0 ; j < space.getResolution()-1 ; j++){
				string += this.get(index) + ",";
				index ++;
			}
			string += this.get(index);
			index ++;

			string +="\n";
		}

		for(int j = 0 ; j < space.getResolution()-1 ; j++){
			string += this.get(index) + ",";
			index ++;
		}
		string += this.get(index);
		index ++;

		return string;
	}



	public String getName()
	{
		return name;
	}

	public AbstractMap clone() 
	{
		AbstractMap clone = null;
		clone = (AbstractMap) super.clone();
		clone.name = new String(this.name + "_clone");
		clone.isMemory = this.isMemory;
		clone.isStatic = this.isStatic;
		clone.parents = new HashSet<AbstractMap>();
		clone.parents.addAll(this.parents);



		return clone;
	}



	/**
	 * Print nice view of parameters tree
	 * @param offset
	 * @return
	 */
	public String toStringRecursive(int offset)
	{
		String ret = "";
		ret+= printoffset(offset) + this.name + "\n";
		
		for(Parameter p : params)
		{
			if(p instanceof AbstractMap)
				ret += ((AbstractMap) p).toStringRecursive(offset+1) + "\n";
			else
				ret += printoffset(offset+1) + p.getName() +"\n";
		}
		//System.out.println(ret);

		return ret;
	}

	private String printoffset(int offset)
	{
		String ret = "";
		for(int i = 0 ; i < offset ; i++)
			ret += "\t";
		return ret;
	}

	/**
	 * Return the {@link Parameter} having the specified name
	 * @param keyName
	 * @return
	 */
	public Parameter getParameter(String keyName) {
		if(this.name.equals(keyName))
			return this;
		else
			return super.getParameter(keyName);
	}


	public boolean isStatic() {
		return isStatic;
	}


	@Override
	public void constructAllMemories() {
		for(Parameter p : params){
			p.constructAllMemories();
		}
		//	System.out.println("Construct memory of " + name);
		this.constructMemory();

	}


	public boolean isMemory() {
		return isMemory;
	}

	/**
	 * to find a parameter with the given path a.b.c...
	 * We cann add .clone to set a clone of this parameter and return it
	 * @param path
	 * @param level : begin at 0
	 * @param name (optional) name of the clone
	 * @return
	 * @throws BadPathException
	 * @throws CommandLineFormatException 
	 */
	public Parameter getPath(String path,int level,String name,CommandLine cl) throws BadPathException, CommandLineFormatException {
		String[] pa = path.split("\\.");


		Parameter p =  getParameter(pa[level++]);
		if(p != null){
			if(p instanceof AbstractMap){
				return ((AbstractMap)p).getPath(path, level,name,cl);
			}else{
				if(level == pa.length ){
					return p;
				}else if(p instanceof Var && pa[level].equals("clone")){
					//we clone the var and return it
					Var newP = (Var) ((Var)p).clone();
					newP.setName(name); 
					if(p.equals(dt)){
						dt = newP;
					}else{
						this.params.set(this.params.indexOf(p),newP);
					}
					return newP;
				}else if(p instanceof Var && pa[level].equals("share")){
					if(p.equals(dt)){
						dt = cl.get(name);
					}else
						this.params.set(this.params.indexOf(p),cl.get(name));
					return p;
				}else{
					throw new BadPathException("The path " + path + " was bad. Stopped at " + this.name + ".");
				}
			}
		}else{
			throw new BadPathException("The path " + path + " was bad.Because the parameter " + pa[level-1] + " was not found.");
		}
	}


	public Set<AbstractMap> getParents() {
		return parents;
	}
	
	




}



