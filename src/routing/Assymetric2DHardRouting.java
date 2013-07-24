package routing;

import java.util.Arrays;

public class Assymetric2DHardRouting extends Routing {

	//TODO code repetition with V4Neighborhood
	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;


	@Override
	public int[][] getFirstTargets(){
		return new int[][]{{NORTH,SOUTH},{SOUTH,NORTH},{EAST,WEST},{WEST,EAST}};
	}

	@Override
	public int[][] getTargets(int from) {
		switch(from){
		case(NORTH) :
			return new int[][]	{{SOUTH,NORTH}};
		case(SOUTH) :
			return new int[][]	{{NORTH,SOUTH}};
		case(EAST) :
			return new int[][]	{{SOUTH,NORTH},{NORTH,SOUTH},{WEST,EAST}};
		case(WEST) :
			return new int[][]	{{SOUTH,NORTH},{NORTH,SOUTH},{EAST,WEST}};
		default : System.err.println("bad direction..."+
				Arrays.toString(Thread.currentThread().getStackTrace()));
		return null;
		}
	}

}
