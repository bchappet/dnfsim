package maps;

import gui.Updated;

import java.util.HashSet;
import java.util.Set;

import unitModel.UnitModel;
import utils.Cloneable;
import coordinates.NullCoordinateException;
import coordinates.Space;

/**
 * Basic class to handle a double
 * 
 * There are two kind of Var :
 * <li> Named one : they can be modified from the GUI, consequently a modification will be signaled to the parents of this Var</li>
 * <li> Unamed one : they cannot be modified from the GUI, nothing will be signaled during modification</li>
 * 
 * TODO : separate in two distinct classes to speed up the acces : are the unamed one usefull (could be replaced by double variable)
 * TODO : not delay compatible but it would not be useful,
 * except if we can override this class to save model parameter variable modifications...
 * @author bchappet
 *
 */
public class Var implements Parameter,Cloneable {

	/**Current value **/
	public double val; 
	/**Name (optional)**/
	protected String name;
	
	/**Definition set (optional)**/
	protected double step;
	protected double min;
	protected double max;

	protected Set<AbstractMap> parents;



	/**
	 * 
	 * @param name
	 * @param val
	 */
	public Var(String name,double val)
	{
//		System.out.println("MEM:"+"construct:"+this.getClass());
//		System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
		this.val = val;
		this.name = name;
		this.parents = new HashSet<AbstractMap>();
	}
	
	public Var(String name,double val,double min,double max,double step){
		this(name,val);
		setDefinitionSet(min, max, step);
	}

	public Var(double val)
	{
		this(null,val);
	}
	
	public void setDefinitionSet(double min,double max,double step){
		this.min = min ;
		this.max = max;
		this.step = step;
	}



	public double get() {
		return val;
	}


	public void set(double val) {
		//System.out.println(name + "=" + val + " @" + hashCode());
		this.val = val;
		if(this.name != null){
			signalParents();
		}

	}

	public void signalParents() {
		for(AbstractMap p : parents){
			p.signalParents();
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public double get(Double... coor) {
		return get();
	}

	@Override
	public double getFast(int... coord) {
		return get();
	}

	@Override
	public void reset() {
		//nothing

	}

	@Override
	public Space getSpace() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public double get(int index) {
		return get();
	}

	@Override
	public void constructMemory() throws NullCoordinateException {
		// Nothing to do

	}

	@Override
	public Var getDt() {
		// TODO Auto-generated method stub
		return null;
	}

	public Var clone()
	{
		Var clone = null;
		try {
			clone = (Var) super.clone();
			clone.val = this.val;
			clone.name = this.name;
		} catch (CloneNotSupportedException e) {
			// clone is supported
			e.printStackTrace();
		}
		return clone;
	}

	@Override
	public void addParent(AbstractMap updatable) {
		this.parents.add(updatable);

	}
	
	@Override
	public void removeParent(AbstractMap updatable){
		this.parents.remove(updatable);
	}

	@Override
	public void delete()
	{
		for(AbstractMap  p : parents){
			p.removeParameter(this);
		}
		parents = null;
		name = null;
		val = 0;
	}

	@Override
	public double getDelay(int delay, Double... coord) {
		return get();
	}



	@Override
	public void addMemories(int nb, UnitModel... historic)
			throws NullCoordinateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void toParallel() {
		// nothing todo

	}

	@Override
	public void onLine() {
		// nothing todo

	}

	@Override
	public void compute() throws NullCoordinateException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isStatic() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void toStatic() {
		// TODO Auto-generated method stub

	}

	@Override
	public double[] getValues() {
		return new double[]{get()};
	}

	@Override
	public void constructAllMemories() {
		// nothing to do
		
	}


	@Override
	public String toString(){
		if(name!=null)
			return name;
		else
			return super.toString();
	}

	@Override
	public void compute(int index) {
		// nothing
	}

	@Override
	public Var getVar(Double... coor) {
		return this;
	}

	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Var other = (Var) obj;
		if (Double.doubleToLongBits(val) != Double.doubleToLongBits(other.val))
			return false;
		return true;
	}

	@Override
	public void setIndex(int index, double newVal) {
		this.val = newVal;
		
	}

	public Var mult(int mult) {
		return new Var(this.val*mult);
	}

	public void addThis(Var add) {
		this.val += add.val;
		
	}

	public Var sub(Var sub) {
		return new Var(val - sub.val);
	}

	public Var add(Var sum) {
		return new Var(val + sum.val);
	}

	public void addThis(int nb) {
		val += nb;
		
	}

	public void addThis(double d) {
		val += d;
		
	}

	@Override
	public double getDelay(int delay, int index) {
		return get();
	}
	
	/**
	 * Fonction qui permet de définir comment on peut augmenter/baisser cette Variable.
	 * Elle a pour vocation à être override. 
	 * @return le pas d'incrémentation de la variable en cas de changement. 1 par defaut.
	 */
	public double getStep(){
		return step;
	}
	
	public double getMaximunValue(){
		return max;
	}
	
	public double getMinimunValue(){
		return min;
	}

	@Override
	public void resetState() {
		// nothng
		
	}

	@Override
	public void addVue(Updated u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addParameters(Parameter... params) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isMemory(){
		return true;
	}

	

}
