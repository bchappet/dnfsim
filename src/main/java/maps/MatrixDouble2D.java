package main.java.maps;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import main.java.space.Coord;
import main.java.space.Coord1D;
import main.java.space.ISpace2D;
import main.java.space.NoDimSpace;
import main.java.space.Space;
import main.java.space.Space1D;
import main.java.space.Space2D;
import main.resources.utils.ArrayUtils;
import Jama.Matrix;

/**
 * Optimized class for 2D matrix computation
 * 
 * X = rowDimension
 * Y = column dimension
 * 
 * Will handle double matrix data
 * We may want to compute it to update the matrix state
 * @author bchappet
 *
 */
@SuppressWarnings("serial")
public class MatrixDouble2D extends Map<Double,Integer> implements Array2DDouble {

	private Jama.Matrix jamat;
	private Jama.Matrix initJamat;

	/**
	 * Construct a matrix and init a discrete 2D  main.java.space to handle it
	 * (x -> rowWidth = values[0].length and y -> columnWidth = values[].length)

	 * @param name
	 * @param values
	 */
	public MatrixDouble2D(String name,Var<BigDecimal> dt,double[][] values,Parameter<Double>... params){
		this(name,dt,new Space2D(values[0].length,values.length),values,params);
	}

	public MatrixDouble2D(String name,Var<BigDecimal> dt,double[] values,Parameter<Double>... params){
		this(name,dt,new Space1D(values.length),new double[][]{values},params);
	}


	/**
	 * Construct a matrix with the specified main.java.space
	 * @param name
	 * @param main.java.space
	 * @param values
	 */
	public MatrixDouble2D(String name,Var<BigDecimal> dt,Space<Integer> space,double[][] values,Parameter<Double>... params){
		super(name,dt,space,params);
		this.jamat = new Matrix(values);
		this.initJamat = (Matrix) this.jamat.clone();
	}


	/**
	 * Construct a matrix with the specified main.java.space and specified value
	 * @param name
	 * @param main.java.space
	 * @param values
	 */
	public MatrixDouble2D(String name,Var<BigDecimal> dt,Space<Integer> space,double cst,Parameter<Double>... params){
		super(name,dt,space,params);
		int dimY,dimX;
		if(space instanceof ISpace2D){
			dimY = ((ISpace2D) space).getDimY();
			dimX = ((ISpace2D) space).getDimX();
		}else if(space instanceof Space1D){
			dimY = 1;
			dimX = ((Space1D) space).getDimX();
		}else if(space instanceof NoDimSpace){
			dimY = 1;
			dimX = 1;
		}else{
			throw new IllegalArgumentException("The space should be NoDim, 1D or 2D...");
		}
		this.jamat = new Matrix(dimY,dimX,cst);
		this.initJamat = (Matrix) this.jamat.clone();
	}

	/**
	 * Construct an empty matrix with specified main.java.space
	 * @param name
	 * @param main.java.space
	 */
	public MatrixDouble2D(String name,Var<BigDecimal> dt,Space<Integer> space,Parameter<Double>... params){
		this(name,dt,space,0.,params);
	}


	public Jama.Matrix getJamat(){
		return this.jamat;
	}
	public void setJamat(Jama.Matrix mat){
		this.jamat = mat;
	}

	@Override
	public Double getFast(int x, int y) {
		return this.jamat.get(y,x);
	}




	@Override
	public void compute() {
		//nothing by default
	}

	/**
	 * Be careful, should use getArray() for better performance and for direct access (directly double[][])
	 * This is a copy!!
	 */
	@Override
	public Double[][] get2DArray() {
		double data[][] = this.jamat.getArray();
		Double ret[][] = new Double[data.length][data[0].length];

		for(int i = 0 ; i < data.length ; i++){
			for(int j = 0 ; j < data[0].length ; j++){
				ret[i][j] = data[i][j];
			}
		}
		return ret;
	}

	@Override
	public Double getIndex(int index) {
		Coord<Integer> coord =  this.getSpace().indexToCoordInt(index);
		return this.getFast(getX(coord), getY(coord));
	}

	public void setIndex(int index,double val) {
		Coord<Integer> coord = (Coord<Integer>) this.getSpace().indexToCoordInt(index);
		this.setFast(getX(coord), getY(coord),val);
	}

	protected int getY(Coord coord){
		if (coord instanceof Coord1D){
			return 0;
		}else{
			return (int) coord.getIndex(Space2D.Y);
		}
	}

	protected int getX(Coord coord){
		return (int) coord.getIndex(Space2D.X);
	}


	public void setFast(int x, int y, double val) {
		this.jamat.set(y,x,val);
	}
	/**
	 * Row packed copy
	 */
	@Override
	public ArrayList<Double> getValues() {
		return new ArrayList<Double>(Arrays.asList(ArrayUtils.toPrimitive(this.jamat.getRowPackedCopy())));
	}

	public Space getSpace(){
		return  super.getSpace();
	}




	@Override
	public MatrixDouble2D clone(){
		MatrixDouble2D clone = (MatrixDouble2D) super.clone();
		clone.jamat = this.jamat.copy();
		return clone;
	}

	@Override
	public String toString(){
		double[][] val = this.jamat.getArray();
		return ArrayUtils.toString(val);
	}
	@Override
	public double[][] get2DArrayDouble() {
		return this.jamat.getArray();
	}
	@Override
	public double getFastDouble(int x, int y) {
		return  this.jamat.getArray()[x][y];
	}
	@Override
	public void reset() {
		this.jamat= (Matrix) this.initJamat.clone();
		super.reset();
	}

	//	@Override
	//	public boolean equals(Object obj) {
	//		if (this == obj)
	//			return true;
	//		if (!super.equals(obj))
	//			return false;
	//		if (!(obj instanceof MatrixDouble2D))
	//			return false;
	//		MatrixDouble2D other = (MatrixDouble2D) obj;
	//		return other.getValues().equals(this.getValues());
	//	}

	//	@Override
	//	public boolean equals(Object o){
	//		MatrixDouble2D other = (MatrixDouble2D)o;
	//		return other.getValues().equals(this.getValues());
	//	}






}