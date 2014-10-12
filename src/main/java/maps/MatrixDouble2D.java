/*
 * This is a Dynamic Neural Field simulator which is extended to
 *     several other neural networks and extended to hardware simulation.
 *
 *     Copyright (C) 2014  Benoît Chappet de Vangel
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package main.java.maps;

import java.math.BigDecimal;
import java.util.List;

import main.java.space.Coord;
import main.java.space.Coord1D;
import main.java.space.ISpace2D;
import main.java.space.NoDimSpace;
import main.java.space.Space;
import main.java.space.Space1D;
import main.java.space.Space2D;
import main.resources.utils.ArrayUtils;
import main.resources.utils.JamaMatrix;
import main.resources.utils.Matrix;
import main.resources.utils.OpenCVMatrix;


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

	private Matrix mat;
	private Matrix initMat;

    /**
     * Construct a matrix and init a discrete 2D  main.java.space to handle it
     * (x -> rowWidth = values[0].length and y -> columnWidth = values[].length)
     * @param name
     * @param dt
     * @param values
     * @param params
     */
	public MatrixDouble2D(String name,Var<BigDecimal> dt,double[][] values,Parameter... params){
		this(name,dt,new Space2D(values[0].length,values.length),values,params);
	}

    /**
     *
     * @param name
     * @param dt
     * @param mat
     * @param params
     */
	public MatrixDouble2D(String name,Var<BigDecimal> dt,Matrix mat,Parameter... params){
		this(name,dt,new Space2D(mat.getNbColumns(),mat.getNbRows()),mat,params);
	}

    /**
     *
     * @param name
     * @param dt
     * @param values
     * @param params
     */
	public MatrixDouble2D(String name,Var<BigDecimal> dt,double[] values,Parameter... params){
		this(name,dt,new Space1D(values.length),new double[][]{values},params);
	}


    /**
     *
     * @param name
     * @param dt
     * @param space
     * @param values
     * @param params
     */
	public MatrixDouble2D(String name,Var<BigDecimal> dt,Space<Integer> space,double[][] values,Parameter... params){
		this(name,dt,space,new OpenCVMatrix(values),params);
	}

    /**
     *
     * @param name
     * @param dt
     * @param space
     * @param mat
     * @param params
     */
	public MatrixDouble2D(String name, Var<BigDecimal> dt,Space<Integer> space, Matrix mat,Parameter... params) {
		super(name,dt,space,params);
		this.mat = mat;
		this.initMat = (Matrix) this.mat.clone();
	}

    /**
     *
     * @param name
     * @param dt
     * @param space
     * @param cst
     * @param params
     */
	public MatrixDouble2D(String name,Var<BigDecimal> dt,Space<Integer> space,double cst,Parameter... params){
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
		this.mat = new OpenCVMatrix(dimY,dimX,cst);
		this.initMat = (Matrix) this.mat.clone();
	}

    /**
     *  Construct an empty matrix with specified main.java.space
     * @param name
     * @param dt
     * @param space
     * @param params
     */
	public MatrixDouble2D(String name,Var<BigDecimal> dt,Space<Integer> space,Parameter... params){
		this(name,dt,space,0.,params);
	}


	


    public Matrix getMat(){
		return this.mat;
	}
	public void setMat(Matrix mat){
		this.mat = mat;
	}

	@Override
	public Double getFast(int x, int y) {
		return this.mat.get(x,y);
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
		double data[][] = this.mat.getArray();
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
		this.mat.set(x, y, val);
	}
	/**
	 * Row packed copy
	 */
	@Override
	public List<Double> getValues() {
		//return new ArrayList<Double>(Arrays.asList(ArrayUtils.toPrimitive(this.mat.getRowPackedCopy())));
        return this.mat.getValues();
    }

	public Space getSpace(){
		return  super.getSpace();
	}




	@Override
	public MatrixDouble2D clone(){
		MatrixDouble2D clone = (MatrixDouble2D) super.clone();
		clone.mat = this.mat.clone();
		return clone;
	}

	@Override
	public String toString(){
		double[][] val = this.mat.getArray();
		return ArrayUtils.toString(val);
	}
	@Override
	public double[][] get2DArrayDouble() {
		return this.mat.getArray();
	}
	@Override
	public double getFastDouble(int x, int y) {
		return  this.mat.getArray()[y][x];
	}
	@Override
	public void reset() {
		this.mat = (Matrix) this.initMat.clone();
		super.reset();
	}

	@Override
	public void set2DArrayDouble(double[][] newArray) {
		this.mat = new JamaMatrix(newArray);
		
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