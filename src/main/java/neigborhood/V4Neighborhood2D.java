package main.java.neigborhood;

import main.java.maps.UnitParameter;
import main.java.space.Coord2D;
import main.java.space.Space;

public class V4Neighborhood2D<C> extends Neighborhood<C> {

	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;

	public static final int X = 0;
	public static final int Y = 1;


	public V4Neighborhood2D(Space<C> space) {
		this(space,null);
	}

	public V4Neighborhood2D(Space<C> space,UnitParameter map) {
		super(space,map);
	}
	
	
	

	@Override
	public int[] getNeighborhood(int index) {
		Coord2D<Integer> castedCoord = (Coord2D<Integer>) space.indexToCoordInt(index);
		Coord2D<Integer> n = new Coord2D<Integer>(castedCoord.x,castedCoord.y-1);
		Coord2D<Integer> s = new Coord2D<Integer>(castedCoord.x,castedCoord.y+1);
		Coord2D<Integer> e = new Coord2D<Integer>(castedCoord.x+1,castedCoord.y);
		Coord2D<Integer> w = new Coord2D<Integer>(castedCoord.x-1,castedCoord.y);
		//Wrap if needed taking account of the frame
		Space<C> space = this.getSpace();
		if(space instanceof WrappedSpace){
			((WrappedSpace<C>) space).checkWrap(n,s,e,w);
		}
		

		return new int[]{space.coordIntToIndex(n),
				space.coordIntToIndex(s),
				space.coordIntToIndex(e),
				space.coordIntToIndex(w)};
	}


}
