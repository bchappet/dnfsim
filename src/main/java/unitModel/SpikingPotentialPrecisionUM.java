package main.java.unitModel;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.ParameterUser;
import main.java.maps.Var;

public class SpikingPotentialPrecisionUM extends RateCodedUnitModel {
	
//	public final static int POTENTIAL = 0;
//	public final static int TAU =1;
//	public final static int INPUT = 2;
//	public final static int CNFT = 3;
//	public final static int H = 4;
	public final static int FRAC = 5;

	@Override
	public double compute() throws NullCoordinateException {
		return computation2(params.getIndex(POTENTIAL).getIndex(coord), 
				params.getIndex(TAU).getIndex(coord), params.getIndex(INPUT).getIndex(coord),
				params.getIndex(CNFT).getIndex(coord), params.getIndex(H).getIndex(coord),params.getIndex(FRAC).getIndex(coord));
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
		//	System.out.println(div+"+"+main.java.input+"+"+cnft);
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
