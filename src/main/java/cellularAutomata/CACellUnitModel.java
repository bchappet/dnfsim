package main.java.cellularAutomata;

import java.util.Arrays;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.Unit;
import main.java.maps.Var;
import main.java.neigborhood.V4Neighborhood2D;
import main.java.unitModel.NeighborhoodUnitModel;
import main.resources.utils.Hardware;

public class CACellUnitModel extends NeighborhoodUnitModel {
	
	/**Parameters**/
	public static final int RULE = 0;
	
	
	/**Neighborhood**/
	public final static int NEIGH = 0;
	
	
	/**Rule bit order LBV to HBV**/
	public static final int E = 0;
	public static final int S = 1;
	public static final int W = 2;
	public static final int N = 3;
	public static final int C = 4;//this cell state
	public static final int X = 5;//(global rule inverter)


	public CACellUnitModel() {
	}

	public CACellUnitModel(Var dt, Space space, Parameter... parameters) {
		super(dt, space, parameters);
	}

	@Override
	public double compute() throws NullCoordinateException {
		int[] rule = Hardware.toVector((int)params.getIndex(RULE).getIndex(coord),6);
		
		Unit[] neigh = neighborhoods.get(NEIGH);
		
		int east = (int) neigh[V4Neighborhood2D.EAST].get();
		int south = (int) neigh[V4Neighborhood2D.SOUTH].get();
		int west = (int) neigh[V4Neighborhood2D.WEST].get();
		int north = (int) neigh[V4Neighborhood2D.NORTH].get();
		int cell = (int) this.unit.get();
//		System.out.println("unit : " + this.unit.hashCode());
//		System.out.println("@"+this.hashCode());
//		System.out.println("Coord : " + Arrays.toString(main.java.space.discreteProj(coord)) + " @" +hashCode() );
//		System.out.println("Neigh : ("+cell+"," +north+","+west+","+south+","+east+")" );
//		System.out.println("Rule  : "+Arrays.toString(rule));
		int res = rule[X]^(rule[C]&cell)^(rule[N]&north)^(rule[W]&west)^(rule[S]&south)^(rule[E]&east);
		
		return res;
		
	}
	
	
	
	

}
