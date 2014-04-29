package maps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import space.Coord2D;
import space.Space;
import space.Space2D;
import utils.ArrayUtils;

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
public class MatrixDouble2D extends Jama.Matrix implements Parameter<Double>,Map<Double,Integer>,Array2D<Double> {

	/**
	 * Name of the matrix
	 */
	private String name;
	
	/**
	 * Space of the matrix
	 */
	private Space2D space;

	/**
	 * Construct a matrix and init a discrete 2D  space to handle it
	 * (x -> rowWidth = values[0].length and y -> columnWidth = values[].length)
	 * @param name
	 * @param values
	 */
	public MatrixDouble2D(String name,double[][] values){
		this(name,new Space2D(values[0].length,values.length),values);
	}
	/**
	 * Construct a matrix with the specified space
	 * @param name
	 * @param space
	 * @param values
	 */
	public MatrixDouble2D(String name,Space2D space,double[][] values){
		super(values);
		this.name = name;
		this.space =space;
	}
	/**
	 * Construct a matrix with the specified space and specified value
	 * @param name
	 * @param space
	 * @param values
	 */
	public MatrixDouble2D(String name,Space2D space,double cst){
		super(space.getDimensions()[Space2D.Y],space.getDimensions()[Space2D.X],cst);
		this.name = name;
		this.space =space;
	}
	
	/**
	 * Construct an empty matrix with specified space
	 * @param name
	 * @param space
	 */
	public MatrixDouble2D(String name,Space2D space){
		this(name,space,0.);
	}
	
	@Override
	public Double getFast(int x, int y) {
		return this.get(y,x);
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
		double data[][] = getArray();
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
		Coord2D<Integer> coord = space.indexToCoord(index);
		return this.getFast(coord.x, coord.y);
	}


	@Override
	public void setIndex(int index, Double newVal) {
		Coord2D<Integer> coord = space.indexToCoord(index);
		this.setFast(coord.x, coord.y, newVal);
		
	}
	
	@Override
	public void setFast(int x, int y, Double newVal) {
		this.set(y, x, newVal);
	}



	/**
	 * Row packed copy
	 */
	@Override
	public ArrayList<Double> getValues() {
		return new ArrayList<Double>(Arrays.asList(ArrayUtils.toPrimitive(this.getRowPackedCopy())));
	}



	@Override
	public MatrixDouble2D clone(){
		 MatrixDouble2D clone = ( MatrixDouble2D)super.clone();
		 clone.name = this.name+"_clone";
		 clone.space = this.space.clone();
		 return clone;
	}
	
	@Override
	public String toString(){
		double[][] val = this.getArray();
		String ret = "";
		for(int i = 0 ; i < val.length-1 ; i++){ //colulmn Width Y
			for(int j = 0 ; j < val[0].length-1 ; j++){ //row width X
				ret += val[i][j] + ",";
			}
			ret += val[i][val[0].length-1];
			ret += "\n";
		}
		
		for(int j = 0 ; j < val[0].length-1 ; j++){
			ret += val[val.length-1][j] + ",";
		}
		ret += val[val.length-1][val[0].length-1];
		
		return ret;
	}



	@Override
	public Space2D getSpace() {
		return this.space; 
	}



	@Override
	public String getName() {
		return this.name;
	}
	
	
	
	












}
