package main.java.space;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import main.java.maps.SingleValueParam;
import main.java.maps.Var;




public class DoubleSpace extends Space<Double> {


	private Coord<Var<Double>> origin;
	private Coord<Var<Double>> length;




	public DoubleSpace(Coord<Var<Double>> origin,Coord<Var<Double>> length,Var<Integer> resolution) {
		super(getArrayOfResolution(resolution,origin.getSize()));
		this.origin = origin;
		this.length = length;
	}

	private static Coord<SingleValueParam<Integer>> getArrayOfResolution(
			Var<Integer> resolution, int size) {
		List<SingleValueParam<Integer>> list = new ArrayList<SingleValueParam<Integer>>(size);
		for(int i = 0 ; i < size ; i++){
			list.add(resolution);
		}
		return new Coord<SingleValueParam<Integer>>(list);

	}

	public SingleValueParam<Integer> getResolution() {
		return this.getDimensions().getIndex(0);
	}



	/**
	 * @return the origin
	 */
	public Coord<Var<Double>> getOrigin() {
		return origin;
	}

	/**
	 * @return the length
	 */
	public Coord<Var<Double>> getLength() {
		return length;
	}

	protected Double getLenght(int axis){
		return this.length.getIndex(axis).get();
	}

	protected Double getOrigin(int axis){
		return this.origin.getIndex(axis).get();
	}

	@Override
	public Coord<Integer> indexToCoordInt(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Coord<Double> indexToCoord(int index) {
		return toTypeCoord(this.indexToCoordInt(index));
	}
//	/**
//	 * Transform a coord in type Coord<T> //TODO validate
//	 * @param intCoord
//	 * @return
//	 */
//	protected Coord<Double> toTypeCoord(Coord<Integer> intCoord) {
//		ArrayList<Double> list = new ArrayList<Double>();
//		for(int i = 0 ; i < intCoord.getSize() ; i++){
//			int current = intCoord.getIndex(i);
//			double ori = origin.getIndex(i).get();
//			double size = length.getIndex(i).get();
//			double res = getDimensions().getIndex(i).get();
//			double coor = (current/res*size) - ori;
//
//			list.add(coor);
//		}
//		return new Coord<Double>(list,this);
//	}

	protected static double round(double d, int decimalPlace){
		//source : http://www.rgagnon.com/javadetails/java-0016.html
		// see the Javadoc about why we use a String in the constructor
		// http://java.sun.com/j2se/1.5.0/docs/api/java/math/BigDecimal.html#BigDecimal(double)
		BigDecimal bd = new BigDecimal(Double.toString(d));
		bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}

	/**
	 * Project coord from double space to integer
	 * @param coord
	 * @return
	 */

	protected Coord<Integer> toIntCoord(Coord<Double> typeCoord) {
		Coord<Integer> ret = new Coord<Integer>(typeCoord,0);
		for(int i = 0 ; i < ret.getSize() ; i++)
		{
			double fact = this.length.getIndex(i).get()/this.getIndex(i).get();
			//The problem is that without the rounding 
			//we have for instance 0.6/0.2 that gives 2.9999999999 which will be casted in 2
			//But we should have 3. => that is why we have to use a precision rounding
			int val = (int) round(((typeCoord.getIndex(i) - origin.getIndex(i).get()) / fact),10);
			ret.set(i, val); 
		}
		return ret;
	}

	/**
	 * Transform a coord in type Coord<T> //TODO validate
	 * @param intCoord
	 * @return
	 */
	public Coord<Double> toTypeCoord(Coord<Integer> intCoord) {
		Coord<Double> ret = new Coord<Double>(intCoord,0d);
		for(int i = 0 ; i < ret.getSize() ; i++)
		{
			double fact = this.length.getIndex(i).get()/this.getIndex(i).get();
			double val = origin.getIndex(i).get() + fact/2 + fact*intCoord.getIndex(i);
			ret.set(i, val); 
		}

		return ret;
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
	public int getVolume() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Coord<Double> wrapCoord(Coord<Double> coord) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Coord<Integer> wrapCoordInt(Coord<Integer> coord) {
		// TODO Auto-generated method stub
		return null;
	}


}
