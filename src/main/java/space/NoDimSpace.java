package main.java.space;

public class NoDimSpace extends Space {
	
	public NoDimSpace(){
		super();
	}
	
	public  String toString(){
		return "NoDimSpace";
	}
	
	@Override
	public int getVolume(){
		return 1;
	}
	
	@Override
	public Space2D transpose(){
		return new Space2D(1, 1);
	}


	@Override
	public Coord indexToCoordInt(int index) {
		return this.indexToCoord(index);
	}


	@Override
	public Coord indexToCoord(int index) {
		return new Coord1D<Integer>(0);
	}


	@Override
	public int coordIntToIndex(Coord coord) {
		return 0;
	}


	@Override
	public int coordToIndex(Coord coord) {
		return 0;
	}

}
