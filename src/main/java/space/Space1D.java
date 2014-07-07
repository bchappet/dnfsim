package main.java.space;

import main.java.maps.SingleValueParam;
import main.java.maps.Var;

public class Space1D extends Space2D {
	public Space1D(SingleValueParam<Integer> x) {
		super(x,new Var<Integer>(1));
	}
	
	public Space1D(int x) {
		super(new Var<Integer>(x),new Var<Integer>(1));
	}
	
	@Override
	public Coord1D<Integer> indexToCoordInt(int index){
		return new Coord1D<Integer>(index);
	}
	
	@Override
	public int coordIntToIndex(Coord<Integer> coord){
		return coord.getIndex(X);
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

}
