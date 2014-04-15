package unitModel;

import java.util.Arrays;

import maps.Parameter;
import maps.Var;
import coordinates.NullCoordinateException;
import coordinates.Space;

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
		//System.out.println("Space : " + space + " coord : " + Arrays.toString(coord));
		return computation2(params.get(POTENTIAL).get(coord), 
				params.get(TAU).get(coord), params.get(INPUT).get(coord),
				params.get(CNFT).get(coord), params.get(H).get(coord));
	}
	
	protected double computation2(double potential,double tau, double input,double cnft,double h ) {
		//System.out.println(potential +"+" +dt.get()+"/"+tau+"*(-"+potential +"+" +input +"+"+cnft +"+"+ h +")" );
		return Math.max(0, potential + dt.get()/tau*(-potential + input +cnft + h  ));
	}

}
