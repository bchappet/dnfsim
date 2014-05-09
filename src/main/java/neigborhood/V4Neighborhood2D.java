package main.java.neigborhood;

import java.util.Arrays;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.UnitParameter;

public class V4Neighborhood2D extends Neighborhood {

	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;

	public static final int X = 0;
	public static final int Y = 1;


	public V4Neighborhood2D(Space space) {
		this(space,null);
	}

	public V4Neighborhood2D(Space space,UnitParameter map) {
		super(space,map);
	}
	
	
	

	@Override
	public Double[][] getNeighborhood(Double... coord) throws NullCoordinateException{
		double res = space.getResolution();	//main.java.space résolution
		Double[] unit = new Double[coord.length]; //continuous distance corresponding to discrete distance = 1
		for(int i = 0 ; i < unit.length ; i++){
			unit[i] = space.getSize()[i]/res;
		}
	
		Double[] n = new Double[]{coord[X],coord[Y]-unit[Y]};
		Double[] s = new Double[]{coord[X],coord[Y]+unit[Y]};
		Double[] e = new Double[]{coord[X]+unit[X],coord[Y]};
		Double[] w = new Double[]{coord[X]-unit[X],coord[Y]};
		//Wrap if needed taking account of the frame
		n = space.wrap(n);
		s = space.wrap(s);
		e = space.wrap(e);
		w = space.wrap(w);
		

		return new Double[][]{n,s,e,w};
	}

}
