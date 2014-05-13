package main.java.neigborhood;

import main.java.space.Coord;
import main.java.space.Coord2D;
import main.java.space.Space;

public class WrappedSpace<C> extends Space<C> {

	@Override
	public Coord<Integer> indexToCoordInt(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void checkWrap(Coord<C>... coords){
		//TODO
	}

	@Override
	public Coord<C> indexToCoord(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int coordIntToIndex(Coord<Integer> coord) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int coordToIndex(Coord<C> coord) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getVolume() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Coord<C> wrapCoord(Coord<C> coord) {
		// TODO Auto-generated method stub
		return null;
	}


	public void checkWrap(Coord2D<Integer>... coord) {
		// TODO Auto-generated method stub
		
	}

}
