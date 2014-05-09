package main.java.unitModel;

import java.util.Arrays;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.Var;

/**
 * Always memory
 * @author benoit
 *
 */
public class RateCodedUnitModel extends UnitModel {

	public final static int POTENTIAL = 0;
	public final static int TAU =1;
	public final static int INPUT = 2;
	public final static int CNFT = 3;
	public final static int H = 4;
	
	public RateCodedUnitModel(){
		super();
	}

	public RateCodedUnitModel(Var dt, Space space, Parameter... parameters) {
		super(dt, space, parameters);
	}

	@Override
	public double compute() throws NullCoordinateException {
		//System.out.println("Space : " + main.java.space + " coord : " + Arrays.toString(coord));
		return computation2(params.getIndex(POTENTIAL).getIndex(coord), 
				params.getIndex(TAU).getIndex(coord), params.getIndex(INPUT).getIndex(coord),
				params.getIndex(CNFT).getIndex(coord), params.getIndex(H).getIndex(coord));
	}
	
	protected double computation2(double potential,double tau, double input,double cnft,double h ) {
		//System.out.println(potential +"+" +dt.get()+"/"+tau+"*(-"+potential +"+" +main.java.input +"+"+cnft +"+"+ h +")" );
		return Math.max(0, potential + dt.get()/tau*(-potential + input +cnft + h  ));
	}

}
