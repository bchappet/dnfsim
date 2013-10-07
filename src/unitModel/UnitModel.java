package unitModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import maps.AbstractUnitMap;
import maps.Parameter;
import maps.ParameterUser;
import maps.Unit;
import maps.Var;
import utils.ArrayUtils;
import utils.Cloneable;
import coordinates.NullCoordinateException;
import coordinates.Space;

/**
 * The instances of this class stores the states of the smallest unit of the model with the activity (Var).
 * The extending class must implements the compute() (double) method which gives the
 * next state of the unit.
 * 
 * As a {@link ParameterUser}, a UnitModel has acces to 
 * <li> a list of parameters : params (List<Parameter>)</li>
 * <li> the time of simulation (seconds) time (Var)</li>
 * <li> the computation frequency (seconds) dt (Var)</li>
 * <li> the refSpace of computation : refSpace (Space)</li> 
 * 
 * In addition, the {@link UnitModel} should knows its coordinates (continuous refSpace) if it is necessary.
 * 
 * With the defaults attributes a {@link UnitModel} only compute its activity using the computation implementation.
 * 
 * There is a possibility to construct {@link UnitModel} as a composition of several {@link UnitModel} stocked
 * in the  subUnits (List<UnitModel>) list.
 * 
 * <p>Basically there are two mode of computation (they are handled by the {@link AbstractUnitMap} and the {@link Unit} :
 * No memory mode : the map "artificially" set the coordinates and compute the result of compute() method which is directly returned :
 * nothing is stored here, the activity attribute is useless.</p>
 * <p>Memory mode : there is a {@link UnitModel} for each discrete positions available in the refSpace, each {@link UnitModel} knows its 
 * corresponding continuous coordinates, and each {@link UnitModel} stores its state in the activity attributes</p>
 * 
 * 
 * 
 * 
 * @author bchappet
 *
 */
public abstract class UnitModel extends ParameterUser implements Cloneable  {


	/**Coordinate : position of the unit (discrete or contiuous)**/
	protected Double[] coord; 

	/**Activity of the unit**/
	protected Var activity;

	/**List of composing unitModel**/
	protected List<UnitModel> subUnits;

	/**The unit which handle this {@link UnitModel} (optional)**/
	protected Unit unit;
	


	public UnitModel() {
		super();
	}


	public UnitModel(ParameterUser paramUser)
	{
		super(paramUser);

	}

	/**
	 * Constructor with parameters
	 * @param dt
	 * @param refSpace
	 */
	public UnitModel(Parameter dt, Space space,Parameter...parameters ) {
		super(dt,space,parameters);

	}

	@Override
	protected  void onInitilization(){
		coord = new Double[space.getDim()];
		subUnits = new ArrayList<UnitModel>();
		activity = new Var(0);
		setInitActivity();
	}
	/**
	 * Activity at computation start-1 (optional)
	 * set activity to 0 by default
	 * each state related attributes should be initilized here
	 * @return this UnitModel
	 */
	public UnitModel setInitActivity() {
		this.activity.set(0);
		return this;
	}

	/**
	 * Constructor with suunits
	 * @param dt
	 * @param refSpace
	 * @param unitModels
	 */
	public UnitModel(Var dt, Space space,UnitModel...unitModels ) {
		this(dt,space,(Parameter)null);
		this.subUnits.addAll(Arrays.asList(unitModels));
	}






	/**
	 * Add unit model to the subUnits list
	 * @param units
	 */
	public void addSubUnits(UnitModel ...units )
	{
		for(UnitModel u : units){
			this.subUnits.add(u);
		}
	}

	@Override
	public UnitModel clone()
	{
		UnitModel o = null;
		try {
			o = (UnitModel) super.clone();
			o.activity = this.activity.clone();//Copy : we need the coord to be different within the map
			o.coord = this.coord.clone();//Copy : we need the coord to be different within the map
			o.subUnits = ArrayUtils.deepCopy(this.subUnits);//Copy
			o.unit = this.unit;//shared
			o.onInitilization();
			
		} catch (CloneNotSupportedException e){
			//Clone is supported
			e.printStackTrace();
		} 
		return o;

	}

	/**
	 * Will be used to create memories within the Unit 
	 * (for parallel computation and delayed access)
	 * The only aim is to share some Object within these memories if needed 
	 * @return 
	 */
	public UnitModel clone2()
	{
		UnitModel o = (UnitModel) super.clone();
		o.activity = this.activity.clone();//Copy : we need the coord to be different within the map
		o.coord = this.coord;//share : the coord is the same for each memory
		o.subUnits = deepCopyUnitModel2(this.subUnits);//Copy (with clone2)
		o.unit = this.unit;//shared
		o.time = this.time;//shared
		return o;
	}

	/**
	 * set coordinates of this unitModel
	 * @param indexToCoord
	 */
	public void setCoord(Double... coord) {
		//Set the coordinate of this UnitModel
		for(int i = 0 ; i < coord.length ; i++)
			this.coord[i] = coord[i];
		//Set the coordinate for the subunits
		for(UnitModel um : subUnits){

			um.setCoord(coord);
		}

	}

	public double computeActivity() throws NullCoordinateException{
		double ret = compute();
		this.set(ret);
		return ret;
	}


	/**
	 * Return the activity resulting of a computation with the differents parameters
	 * 
	 * @return
	 * @throws NullCoordinateException 
	 */
	public abstract  double compute() throws NullCoordinateException;

	/**
	 * Get current activity
	 * @return
	 */
	public double get() {
		return activity.get();
	}

	/**
	 * Set the activity
	 * @param act
	 * @throws NullCoordinateException 
	 */
	public void set(double act)
	{
		//System.out.println("set " + act +" @"+hashCode());
		this.activity.val = act;
	}

	/**
	 * Copy the attribute value of source in this class attributes
	 * @param unitModel
	 * @throws NullCoordinateException 
	 */
	public void copy(UnitModel source) throws NullCoordinateException {
		this.activity.val = source.get();

	}


	public void reset() 
	{
		
		this.time.set(0);
		setInitActivity() ;
	}

	
	public void resetState() {
		setInitActivity() ;
	}

	public Double[] getCoord() {
		return coord;
	}

	//	@Override
	//	public String toString() {
	//		return "UnitModel [coord=" + Arrays.toString(coord) + ", activity="
	//				+ activity + ", subUnits=" + subUnits + ", params=" + params
	//				+ ", refSpace=" + space + ", time=" + time + ", dt=" + dt + "]";
	//	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Unit getUnit() {
		return unit;
	}

	public Var getActivity() {
		return activity;
	}

	public UnitModel getSubUnit(int subUnitIndex) {
		//System.out.println("this.class" + this.getClass());
		return subUnits.get(subUnitIndex);
	}

	public Var getVar() {
		return activity;
	}
	
	/**
	 * To ensure that  the subunit are clonned with the clone2 method
	 * @param subUnits
	 * @return
	 */
	private static List<UnitModel> deepCopyUnitModel2(List<UnitModel> subUnits) {
		List<UnitModel> ret = new ArrayList<UnitModel>();
		for(UnitModel um : subUnits){
			ret.add(um.clone2());
		}
		return ret;
	}


	public void incrTime() {
		this.time.set(this.time.get() + dt.get());
		
	}


	


















}
