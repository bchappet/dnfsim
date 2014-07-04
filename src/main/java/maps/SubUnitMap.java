package main.java.maps;

import main.java.coordinates.NullCoordinateException;
import main.java.unitModel.UnitModel;

public class SubUnitMap extends UnitMap  {
	
	/**
	 * Linked map
	 */
	protected UnitMap  map;
	/**
	 * Index of the accessed subunit
	 */
	protected int subUnitIndex;

	public SubUnitMap(String name, UnitMap  map,int subUnitIndex) {
		super(name, null,map.dt,map.space);
		this.map = map;
		this.subUnitIndex = subUnitIndex;
		this.map.addSubUnitMap(this);
	}

	
	@Override
	public void constructMemory() {
		map.constructMemory();
		this.isMemory = true;

	}
	
	/**
	 * Return the activity at the specific index
	 * Faster if memory
	 * @param index
	 * @return
	 * @throws NullCoordinateException 
	 */
	@Override
	public double getIndex(int index) throws NullCoordinateException
	{
		if(map.isMemory)
		{
			return map.units.get(index).getSubUnit(subUnitIndex).get();
		}
		else
		{
			//NoMemory TODO compatible?
			map.unitModel.setCoord(this.space.indexToCoordInt(index));
			return map.unitModel.getSubUnit(subUnitIndex).computeActivity();
		}
	}

	@Override
	public double getFast(int ... coord)
	{
		int index = this.space.coordIntToIndex(coord);
		double ret = map.units.get(index).getSubUnit(subUnitIndex).get();
		return ret;
	}

	@Override
	public void compute() throws NullCoordinateException {
		// nothing
	}
	
	/**
	 * Return the activity at the specific continuous coord
	 * Faster if noMemory
	 * @param Double ... main.java.coordinates
	 * @return the activity at the specific continuous coord
	 * @throws NullCoordinateException if a coordinate was null while we excpected a value
	 */
	public double get(Double... coord) throws NullCoordinateException
	{
		//TODO maybe we should handle here parrallel vs online
		/*if(parallel delay = 1) else delay = 0, getDelay(delay,coord)*/
		return getDelay(0,coord);
	}
	
	public Var getVar(Double ... coord){
		if(map.isMemory)
		{
			int index = this.space.coordIntToIndex(coord);
			return map.units.get(index).getSubUnit(subUnitIndex).getVar();
		}
		else
		{
			//NoMemory
			map.unitModel.setCoord(coord);
			double res  = map.unitModel.getSubUnit(subUnitIndex).computeActivity();
			map.unitModel.getSubUnit(subUnitIndex).set(res);
			return map.unitModel.getSubUnit(subUnitIndex).getActivity();
		}
	}

	@Override
	public double getDelay(int delay, Double... coord) throws NullCoordinateException{

		if(map.isMemory)
		{
			//System.err.println("Nom : " + this.name + ". main.java.space : " + this.space);
			int index = this.space.coordIntToIndex(coord);
			double ret = map.units.get(index).getSubUnit(subUnitIndex,delay).get();
			return ret;
		}
		else
		{
			//NoMemory
			map.unitModel.getSubUnit(subUnitIndex).setCoord(coord);
			return map.unitModel.getSubUnit(subUnitIndex).computeActivity();
		}

	}
	
	@Override
	public void addMemories(int nb,UnitModel... historic) throws NullCoordinateException{
		//TODO
	}

	@Override
	public void toParallel() throws NullCoordinateException {
		//TODO

	}

	@Override
	public void onLine() throws NullCoordinateException {
		//TODO

	}


	public void reset()  {
		map.reset();

	}

	public SubUnitMap clone() 
	{
		//TODO
		return null;
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
		return map.unitModel.getSubUnit(subUnitIndex);
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
		String string = "Unit Model : " + map.unitModel.getSubUnit(subUnitIndex).getClass() + "\n";
		string += super.display2D();
		return string;
	}	

	public double[] getValues() {
		double[] res = new double[units.size()];
		for(int i = 0 ; i < res.length ; i++)
		{
			res[i] = units.get(i).getSubUnit(subUnitIndex).get();
		}
		return res;
	}
	
	@Override
	public void delete(){
		super.delete();
		map = null;
		
	}
	
	

}
