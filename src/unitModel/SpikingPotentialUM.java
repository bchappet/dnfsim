package unitModel;


/**
 * double tau, double input,double cnft,
			double h ,double th,double high
 * @author bchappet
 *
 */
public class SpikingPotentialUM extends RateCodedUnitModel {
	
	
	
	
	@Override
	protected double computation2(double potential,double tau, double input,double cnft,
			double h ) {
		
		
         // apply the almost standard equation
         // (but for the CNFT component, which is no more to be
         //  integrated over time since spikes are instantaneous)
	//	System.out.println(potential +"+"+ dt.get()+"/"+tau+"*(-"+potential+" +"+ input+"+"+ h +" ) + 1/"+tau+"*"+cnft);
		return Math.max(0, potential + dt.get()/tau*(-potential + input  + h  ) + 1/tau*cnft);
	}

}
