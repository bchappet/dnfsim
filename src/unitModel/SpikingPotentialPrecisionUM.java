package unitModel;

import maps.Parameter;
import maps.ParameterUser;
import maps.Var;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class SpikingPotentialPrecisionUM extends RateCodedUnitModel {
	
//	public final static int POTENTIAL = 0;
//	public final static int TAU =1;
//	public final static int INPUT = 2;
//	public final static int CNFT = 3;
//	public final static int H = 4;
	public final static int FRAC = 5;

	@Override
	public double compute() throws NullCoordinateException {
		return computation2(params.get(POTENTIAL).get(coord), 
				params.get(TAU).get(coord), params.get(INPUT).get(coord),
				params.get(CNFT).get(coord), params.get(H).get(coord),params.get(FRAC).get(coord));
	}

	protected double computation2(double potential,double tau, double input,double cnft,
			double h,double frac ) {
		
		
         // apply the almost standard equation
         // (but for the CNFT component, which is no more to be
         //  integrated over time since spikes are instantaneous)
		double div = dt.get()/tau*(-potential);
		if(div * Math.pow(2,frac) < 0.0){
			//System.out.println("div:" + div + " was inf 0");
			div = 0;
		}
		
		double res = Math.max(0, potential + div + input  + h   + cnft);
		if(potential > 0){
		//	System.out.println(div+"+"+input+"+"+cnft);
		//	System.out.println("res : " + res);
			double test = res * Math.pow(2,frac);
			if((int) test != test ){
				System.err.println("Potential error : " + (int)test + " == " + test +" frac= " + frac);
				System.exit(-1);
			}
		}
		
		return res;
	}

}
