package main.java.maps;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.space.Coord2D;
import main.java.space.Space;
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
 * Directly inherited from Jama.Matrix to optimize computation
 * @author bchappet
 *
 */
@SuppressWarnings("serial")
public class MatrixDouble2D extends Map<Double,Integer> implements Array2D<Double> {

	private Jama.Matrix jamat;
	
	
	/**
	 * Construct a matrix and init a discrete 2D  main.java.space to handle it
	 * (x -> rowWidth = values[0].length and y -> columnWidth = values[].length)
	 * @param name
	 * @param values
	 */
	public MatrixDouble2D(String name,Var<BigDecimal> dt,double[][] values,Parameter<Double>... params){
		this(name,dt,new Space2D(values[0].length,values.length),values,params);
	}
	/**
	 * Construct a matrix with the specified main.java.space
	 * @param name
	 * @param main.java.space
	 * @param values
	 */
	public MatrixDouble2D(String name,Var<BigDecimal> dt,Space2D space,double[][] values,Parameter<Double>... params){
		super(name,dt,space,params);
		this.jamat = new Matrix(values);
	}
	/**
	 * Construct a matrix with the specified main.java.space and specified value
	 * @param name
	 * @param main.java.space
	 * @param values
	 */
	public MatrixDouble2D(String name,Var<BigDecimal> dt,Space2D space,double cst,Parameter<Double>... params){
		super(name,dt,space,params);
		this.jamat = new Matrix(space.getDimY(),space.getDimX(),cst);
	}
	
	/**
	 * Construct an empty matrix with specified main.java.space
	 * @param name
	 * @param main.java.space
	 */
	public MatrixDouble2D(String name,Var<BigDecimal> dt,Space2D space,Parameter<Double>... params){
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
		Coord2D<Integer> coord = (Coord2D<Integer>) this.getSpace().indexToCoord(index);
		return this.getFast(coord.x, coord.y);
	}




	/**
	 * Row packed copy
	 */
	@Override
	public ArrayList<Double> getValues() {
		return new ArrayList<Double>(Arrays.asList(ArrayUtils.toPrimitive(this.jamat.getRowPackedCopy())));
	}
	
	public Space2D getSpace(){
		return (Space2D) super.getSpace();
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