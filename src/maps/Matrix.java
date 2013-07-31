package maps;

import java.util.Arrays;

import maps.Matrix2D.Function;

import unitModel.UnitModel;
import utils.ArrayUtils;
import coordinates.NullCoordinateException;
import coordinates.Space;

/**
 * Will handle optimized computation
 * TODO : not delay compatible for now...
 * @author bchappet
 *
 */
public class Matrix extends AbstractMap implements Cloneable {

	protected double[] values;



	/**
	 * Init a matrix with zeroes
	 * @param name
	 * @param dt
	 * @param refSpace
	 * @param params
	 */
	public Matrix(String name, Parameter dt, Space space,
			Parameter... params) {
		this(name,dt,space,new double[space.getDiscreteVolume()],params);
		for(int i = 0 ; i < values.length ; i++){
			values[i] = 0;
		}
	}
	
	

	/**
	 * Init the matrix with the given values
	 * @param name
	 * @param dt
	 * @param refSpace
	 * @param values
	 * @param params
	 */
	public Matrix(String name, Parameter dt, Space space,
			double[] values,Parameter... params) {
		super(name, dt, space,params);
		isMemory = true;
		this.values = values;
	}
	
	public Matrix(String name, Var dt,double resolution, Space space) {
		this(name,dt,space.withResolution(resolution));
	}

	public void compute(int index) {
		//TODO not compatible
	}

	@Override
	public void constructMemory() throws NullCoordinateException {
		//Nothing to do
	}

	@Override
	public void compute() throws NullCoordinateException {
		//Nothing to do
	}

	/**
	 * Return the activity at the specific index
	 * Faster if memory
	 * @param index
	 * @return
	 * @throws NullCoordinateException 
	 */
	public double get(int index) throws NullCoordinateException
	{
		return values[index];
	}

	/**
	 * Fast version, 
	 * @precond the memory is constructed
	 */
	public double getFast(int ... coord)
	{
		int index = this.space.coordToIndex(coord);
		return values[index];
	}



	/**
	 * Return the activity at the specific continous coor
	 * Faster if noMemory
	 * @param Double ... coordinates
	 *
	 * @return
	 * @throws NullCoordinateException if a coordinate was null while we excpected a value
	 */
	public double get(Double... coord) throws NullCoordinateException
	{
	
		int index = this.space.coordToIndex(coord);
		return get(index);
	}

	/**
	 * Not  really adapted...
	 */
	public Var getVar(Double ... coord){
		return new Var(get(coord));
	}


	@Override
	public double getDelay(int delay, Double... coord)
			throws NullCoordinateException {
		return get(coord);//TODO
	}

	@Override
	public void reset() {
		super.reset();
		if(!isStatic){
			for(int i = 0 ; i < values.length ; i++)
				values[i] = 0;
		}

	}
	
	
	
	

	@Override
	public Matrix clone() 
	{
		Matrix mat = (Matrix) super.clone();
		mat.values = this.values.clone();
		return mat;
	}

	@Override
	public double[] getValues() {
		return values;
	}

	public void setValues(double[] values) {
		this.values = values;
	}


	@Override
	public void addMemories(int nb, UnitModel... historic)
			throws NullCoordinateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void toParallel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLine() {
		// TODO Auto-generated method stub

	}



	@Override
	public void setIndex(int index, double newVal) {
		values[index] = newVal;
		
	}



	@Override
	public double getDelay(int delay, int index) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

	
	












}
