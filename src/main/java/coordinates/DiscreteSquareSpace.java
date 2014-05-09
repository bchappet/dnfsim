package main.java.coordinates;

import java.util.Arrays;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.RoundedSpace;
import main.java.coordinates.Space;
import main.java.maps.Var;

/**
 * TODO the main.java.space hierarchy is dirty, we should have continuous main.java.space which inherit of discrete main.java.space
 * In this main.java.space, the continuous and discrete main.java.coordinates will be the same
 * @author bchappet
 * TODO check
 *
 */
public class DiscreteSquareSpace extends Space {

	/**
	 * The resolution will be the size,
	 * only compatible with squared main.java.space
	 * The orgigin will be 0,0,...
	 * @param res
	 * @param wrap
	 */
	public DiscreteSquareSpace( Var res,int dim,
			boolean wrap) {
		super(getUniformDouble(dim,0), getUniformDouble(dim,res.get()), res, wrap);
	}
	
	
	
	/**
	 * In this implementation we just call wrap discrete
	 */
	@Override
	public Double[] wrap(Double[] coord) {
		Double[] res = new Double[dim];
		wrapDiscrete(coord, res);
		return res;
	}
	
	
	
	/**
	 * We just call coorToIndex in its discretized version
	 */
	@Override
	public int coordToIndex(Double... coord) throws NullCoordinateException {
		int[] coordInt = new int[coord.length];
		for(int i = 0 ; i < coordInt.length ; i++){
			coordInt[i] = coord[i].intValue();
		}
		return coordToIndex(coordInt);
	}

	/**
	 * In this implementation return the exact same coord
	 */
	@Override
	public Double[] discreteProj(Double[] coor) throws NullCoordinateException {
		return coor;
	}

	/**
	 * In this implementation return the exact same coord
	 */
	@Override
	public Double[] continuousProj(Double[] coor)
			throws NullCoordinateException {
		return coor;
	}

	/**
	 * In this implementation return val
	 */
	@Override
	public double continuousProj(double val, int axis) {
		return val;
	}

	

	@Override
	//TODO a generic version
	public Space extend(double factor,boolean framed){
		Double[] newOrig = new Double[origin.length];
		Double[] newSize = new Double[size.length];

		for(int i = 0 ; i < newSize.length ; i++){
			newSize[i] = size[i]*factor;
			newOrig[i] = origin[i] - ((newSize[i] - size[i])/2.0);
		}

		//TODO : the new res should be a TrajectoryMap with the "main" resolution as parameter
		//=> when the "main" resolution changes, the new res will change automatically
		double newRes = (int)(this.res*factor);
		if(newRes%2==0)
			newRes++;
		Space ret=  new DiscreteSquareSpace( new Var("res",newRes), dim, this.wrap);
		ret.setSimulationSpace(this); //keep the simulation refSpace for future usage
		
		if(framed){
			ret.frameSpace = this; 
		}else{
			ret.frameSpace = ret;
		}
		return ret;
	}

	@Override
	public DiscreteSquareSpace transpose() {
		//TODO generic
		Double[] transOrigin = new Double[origin.length];
		for(int i = 0 ; i < origin.length ; i++)
			transOrigin[transOrigin.length-i-1] = origin[i];
		
		Double[] transSize = new Double[size.length];
		for(int i = 0 ; i < size.length ; i++)
			transSize[transSize.length-i-1] = size[i];
		
		return new DiscreteSquareSpace(	this.resolution,dim,this.wrap);
		
		
	}

	/**
	 * Return val in this implementation
	 */
	@Override
	public Double continusousProj(double val, int axis)
			throws NullCoordinateException {
		return val;
	}

}
