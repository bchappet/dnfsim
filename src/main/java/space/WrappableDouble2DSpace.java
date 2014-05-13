package main.java.space;

import java.util.Arrays;
import java.util.List;

import main.java.maps.SingleValueParam;
import main.java.maps.Var;

/**
 * Standard space for DNF
 * Continuous space defined with double coord origin and length of each axis
 * And a resolution to discretize it
 * @author benoit
 * @version 12/05/2014
 *
 */
public class WrappableDouble2DSpace extends DoubleSpace2D {

	private Var<Boolean> wrapped;

	public WrappableDouble2DSpace(Var<Double>[] origin,
			Var<Double>[] length,Var<Integer> resolution,Var<Boolean> wrapped) {
		super(origin,length,resolution);
		this.wrapped =wrapped;
	}

	@Override
	public Coord<Integer> indexToCoordInt(int index){
		return null; //DoubleODO
	}
	/**
	 * Continuous version
	 * @param index
	 * @return
	 */
	public Coord<Double> indexToCoordContinuous(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Coord<Double> indexToCoord(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int coordIntToIndex(Coord<Integer> coord) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int coordToIndex(Coord<Double> coord) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Coord<Integer> wrapCoordInt(Coord<Integer> coord) {
		// TODO Auto-generated method stub
		return null;
	}

	


	
	

	/**
	 * Return the volume of the main.java.space
	 * @return
	 */
	public int getVolume(){
		return -1;//DoubleODO
	}




	public Coord<Double> wrapCoord(Coord<Double> coord){
		return null;//DoubleODO
	}

	






	@Override
	public Space<Double> clone(){
		Space<Double> clone = null;
		try {
			clone = (Space<Double>) super.clone();
		} catch (CloneNotSupportedException e) {
			//  clone supported
			e.printStackTrace();
		}
		clone.dimensions = Arrays.copyOf(this.dimensions, this.dimensions.length);
		return clone;
	}

	











}
