package main.java.space;

import main.java.maps.SingleValueParam;
import main.java.maps.Var;

public class Space1D extends Space<Integer> {
	
	public final static int X = 0; //row length
	public Space1D(SingleValueParam<Integer> x) {
		super(x);
	}
	
	public Space1D(int x) {
		super(new Var<Integer>(x));
	}
	
	@Override
	public Coord1D<Integer> indexToCoordInt(int index){
		return new Coord1D<Integer>(index);
	}
	
	@Override
	public int coordIntToIndex(Coord<Integer> coord){
		return coord.getIndex(X);
	}
	public int getDimX(){
		return this.getDimensions().getIndex(X).get().intValue();
	}
	
	@Override
	public int getVolume(){
		return getDimX();
		
	}
	public Coord1D<Integer> wrapCoord(Coord<Integer> coord){
		return new Coord1D<Integer>(coord.getIndex(X)%this.getDimX());
		
	}
	
	@Override
	public Space1D clone(){
		Space1D clone = (Space1D) super.clone();
		return clone;
	}

	@Override
	public Coord1D<Integer> indexToCoord(int index) {
		return new Coord1D<Integer>(index);
	}

	@Override
	public int coordToIndex(Coord<Integer> coord) {
		return coord.getIndex(X);
	}
	@Override
	public Space2D transpose(){
		return new Space2D(1, getDimX());
	}
}
