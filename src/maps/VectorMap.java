package maps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import unitModel.UnitModel;
import coordinates.NullCoordinateException;
import coordinates.Space;

/**
 * Aggregate return several values
 * @author bchappet
 *
 */
public abstract class VectorMap extends AbstractMap  {
	
	private double[] vect;

	public VectorMap(String name, Parameter dt, Space space, Parameter... maps) {
		super(name, dt, space, maps);
		
	}
	
	/**
	 * Return the vector with the different parameter
	 * @return
	 */
	public double[] getVector(){
		System.out.println("get vect" + Arrays.toString(vect) + Arrays.toString(Thread.currentThread().getStackTrace()));
		return vect;
	
	}
	
	public void setVector(double[] vect){
		System.out.println("set vect" + Arrays.toString(vect));
		this.vect = vect;
	}
	
	/**
	 * Return the vector with the different parameter with delay
	 * @return
	 */
	public double[] getVector(int delay) {
		
		throw new Error("not implemented");
	}



	@Override
	public void compute(int index) {
		// TODO Auto-generated method stub
	}

	@Override
	public double get(Double... coor) throws NullCoordinateException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Var getVar(Double... coor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getDelay(int delay, Double... coord)
			throws NullCoordinateException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDelay(int delay, int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public abstract void addMemories(int nb, UnitModel... historic)
			throws NullCoordinateException ;
	@Override
	public void toParallel() throws NullCoordinateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLine() throws NullCoordinateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setIndex(int index, double newVal) {
		// TODO Auto-generated method stub

	}

	@Override
	public double[] getValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double get(int index) throws NullCoordinateException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getFast(int... coord) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void constructMemory() {
		// TODO Auto-generated method stub

	}

	
}
