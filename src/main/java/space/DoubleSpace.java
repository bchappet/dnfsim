package main.java.space;

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
	/**
	 * Transform a coord in type Coord<T> //TODO validate
	 * @param intCoord
	 * @return
	 */
	protected Coord<Double> toTypeCoord(Coord<Integer> intCoord) {
		ArrayList<Double> list = new ArrayList<Double>();
		for(int i = 0 ; i < intCoord.getSize() ; i++){
			int current = intCoord.getIndex(i);
			double ori = origin.getIndex(i).get();
			double size = length.getIndex(i).get();
			double res = getDimensions().getIndex(i).get();
			double coor = (current/res*size) - ori;
			list.add(coor);
		}
		return new Coord<Double>(list,this);
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
