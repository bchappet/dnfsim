package main.java.maps;

import static java.lang.Math.abs;
import static java.lang.Math.exp;

import java.util.Arrays;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.AbstractMap;
import main.java.maps.Map;
import main.java.maps.Matrix2D;
import main.java.maps.Parameter;
import main.java.maps.SampleMap;
import main.java.maps.Var;
import main.java.maps.VectorMap;
import main.java.unitModel.UnitModel;

/**
 * Return the coordinate of (one) winner
 * @author bchappet
 *
 */
public class CoorWinnerMap extends VectorMap {

	//Parameter
	protected static final int MAP = 0;
	//Shape of the gaussian
	protected static final int INTENSITY = 1;
	protected static final int WIDTH = 2;

	public CoorWinnerMap(String name, Parameter dt, Space space, Parameter... maps) {
		super(name, dt, space, maps);
		setVector(new double[2]);
	}

	@Override
	public void compute() throws NullCoordinateException {
		double max = -1;
		Parameter map = params.get(MAP);
		int index = -1; //index of the maximal value
		for(int i = 0 ; i < space.getDiscreteVolume() ; i++){
			double val = map.getIndex(i);
			if(val > max){

				max = val;
				index = i;
			}
		}

		//System.out.println("index : " + index);
		Double[] center = space.indexToCoord(index);
		if(max == 0){
			setVector(new double[]{-100,-100});
		}else{
		//	System.out.println("center : " + Arrays.toString(main.java.space.discreteProj(center)));
			setVector(new double[]{center[Space.X], center[Space.Y]});
		}

	}

	@Override
	public double[] getVector(int delay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addMemories(int nb, UnitModel... historic)
			throws NullCoordinateException {
		// TODO Auto-generated method stub
		
	}







}
