package maps;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import unitModel.UnitModel;
import utils.ArrayUtils;
import coordinates.NullCoordinateException;
import coordinates.Space;

/**
 * 
 * @author bchappet
 *

 */
public abstract class AbstractUnitMap extends AbstractMap implements UnitParameter 
{

	/**the unitModel will define the computation of the map**/
	protected UnitModel unitModel;
	/**Collection of units**/
	protected AbstractList<Unit> units;

	protected List<SubUnitMap> subUnitMaps;




	/**
	 * Share attribute with the unitmodel
	 * @param name
	 * @param unitModel
	 */
	public AbstractUnitMap(String name,UnitModel unitModel)
	{
		super(name,unitModel);
		this.unitModel = unitModel;
		subUnitMaps = new LinkedList<SubUnitMap>();
	}


	/**
	 * Constructor
	 * @param unitModel (optional) suposed to be constructed without parameters
	 * @param name
	 * @param dt
	 * @param refSpace should be specific for each map (at least the dimension)
	 * @param params
	 * @Post : the unitModel attributes will be changed to be shared with this map
	 */
	public AbstractUnitMap(String name,UnitModel unitModel,Parameter dt, Space space, Parameter... maps) {
		super(name,dt, space, maps);
		subUnitMaps = new LinkedList<SubUnitMap>();
		if(unitModel != null)
			initUnitModel(unitModel);
	}

	public void addSubUnitMap(SubUnitMap sum) {
		subUnitMaps.add(sum);

	}

	protected void initUnitModel(UnitModel um) {
		this.unitModel = um;
		this.unitModel.shareAttributesWith(this);
	}



	/**
	 * Return the activity at the specific index
	 * Faster if memory
	 * @param index
	 * @return
	 * @throws NullCoordinateException 
	 */
	@Override
	public double get(int index) throws NullCoordinateException
	{
		if(isMemory)
		{
			//TODO store in an array
			return units.get(index).get();
		}
		else
		{
			//NoMemory
			unitModel.setCoord(this.space.indexToCoord(index));
			return unitModel.computeActivity();
		}
	}

	@Override
	public double getFast(int ... coord)
	{
//		System.out.println("Name : " + getName());
//		System.out.println("Coord : " + Arrays.toString(coord));
		int index = this.space.coordToIndex(coord);
		double ret = units.get(index).get();
		return ret;
	}

	/**
	 * TODO not finished (will be used to change refSpace resolution)
	 * @param oldSpace
	 */
	public void project(Space oldSpace)
	{
		if(isMemory)
		{
			try{
				//Create a new generic collection
				AbstractList<Unit> newUnits = units.getClass().newInstance();


				for(int i = 0 ; i < space.getDiscreteVolume() ; i++)
				{
					Double[] coord = space.indexToCoord(i);
					Unit u = units.get(oldSpace.coordToIndex(coord));
					u.setCoord(coord); //warning : the buffered UnitModel will have different coordinates
					newUnits.add(u);
				}


				System.exit(-1);
			} catch (InstantiationException e) {
				e.printStackTrace();
				System.exit(-1);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				System.exit(-1);
			} catch (NullCoordinateException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}

	protected boolean inComputation = false;//lock to avoid reeentrant computation
	@Override
	public void compute(int index) {
		if(!inComputation){
			inComputation = true;

			for(Parameter p : params){
				p.compute(index);
			}
			if(isMemory){
				try{
					this.units.get(index).compute();
				}catch(IndexOutOfBoundsException e){
					this.units.get(0).compute();
					//TODO handle space differences
				}
			}
			inComputation = false;
		}

	}



	/**
	 * Return the activity at the specific continuous coord
	 * Faster if noMemory
	 * @param Double ... coordinates
	 * @return the activity at the specific continuous coord
	 * @throws NullCoordinateException if a coordinate was null while we excpected a value
	 */
	public double get(Double... coord) throws NullCoordinateException
	{
		//TODO maybe we should handle here parrallel vs online
		/*if(parallel delay = 1) else delay = 0, getDelay(delay,coord)*/
//		System.err.println("Name : " + name);
//		System.err.println(Arrays.toString(coord));
//		System.err.println(space);
		return getDelay(0,coord);
	}

	public Var getVar(Double ... coord){
		if(isMemory)
		{
			int index = this.space.coordToIndex(coord);
			return units.get(index).getVar();
		}
		else
		{
			//NoMemory
			unitModel.setCoord(coord);
			double res  = unitModel.computeActivity();
			unitModel.set(res);
			return unitModel.getActivity();
		}
	}

	@Override
	public double getDelay(int delay, Double... coord) throws NullCoordinateException{

		if(isMemory)
		{
			//System.err.println("Nom : " + this.name + ". space : " + this.space);
			int index = this.space.coordToIndex(coord);
			double ret = units.get(index).get(delay);
			return ret;
		}
		else
		{
			//NoMemory
			unitModel.setCoord(coord);
			return unitModel.computeActivity();
		}

	}

	@Override
	public double getDelay(int delay, int index) {
		if(isMemory)
		{
			//System.err.println("Nom : " + this.name + ". space : " + this.space);
			double ret = units.get(index).get(delay);
			return ret;
		}
		else
		{
			//NoMemory
			unitModel.setCoord(space.indexToCoord(index));
			return unitModel.computeActivity();
		}
	}

	@Override
	public void addMemories(int nb,UnitModel... historic) throws NullCoordinateException{
		if(!isMemory)
			constructMemory();

		for(Unit unit : units)
		{
			unit.addMemories(nb,historic);
		}
	}

	@Override
	public void toParallel() throws NullCoordinateException {
		if(!isMemory)
			constructMemory();

		for(Unit unit : units)
			unit.toParallel();

	}

	@Override
	public void onLine() throws NullCoordinateException {
		if(!isMemory)
			constructMemory();

		for(Unit unit : units)
			unit.onLine();

	}


	public void reset()  {
		super.reset();
		if(isMemory && !isStatic){
			for(Unit u : units)
				u.reset();
		}

	}
	
	public void resetState(){
		super.resetState();
		if(isMemory && !isStatic){
			for(Unit u : units)
				u.resetState();
		}
	}

	public AbstractUnitMap clone() 
	{
		AbstractUnitMap clone =
				(AbstractUnitMap) super.clone();
		clone.unitModel = unitModel.clone();

		return clone;
	}

	/**
	 * Return the unit at the specific index
	 * @param index
	 * @return the unit at the index
	 */
	public Unit getUnit(int index) {
		return units.get(index);
	}


	public UnitModel getUnitModel() {
		return unitModel;
	}

	public String toString()
	{
		return this.name;
	}

	/**
	 * Return a string containing the map caracteristics and values (if memory)
	 * @throws NullCoordinateException 
	 */
	@Override
	public String display2D() throws NullCoordinateException {
		String string = "";
		if(unitModel != null)
			string += "Unit Model : " + unitModel.getClass() + "\n";
		string += super.display2D();
		return string;
	}	

	public double[] getValues() {
		double[] res = new double[units.size()];
		for(int i = 0 ; i < res.length ; i++)
		{
			res[i] = units.get(i).get();
		}
		return res;
	}


	public List<SubUnitMap> getSubUnitMaps() {
		return subUnitMaps;
	}

	public void setIndex(int index, double val) {
		units.get(index).getUnitModel().set(val);

	}

	@Override
	public void delete(){
		super.delete();
		if(isMemory){
			for(Unit u : units){
				u.delete();
			}
		}

		for(SubUnitMap sm : subUnitMaps){
			sm.delete();
		}

		unitModel = null;
		units = null;
		subUnitMaps = null;

	}









}
