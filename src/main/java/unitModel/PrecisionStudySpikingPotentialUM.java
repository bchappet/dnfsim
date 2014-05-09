package main.java.unitModel;

/**
 * It is the same exept that we remove tau from the lateral weights as they should be
 * already included in the cnft factor
 * @author bchappet
 *
 */
public class PrecisionStudySpikingPotentialUM extends SpikingPotentialUM {

	@Override
	protected double computation2(double potential,double tau_dt, double input,double cnft,
			double h ) {
		
         // apply the almost standard equation
         // (but for the CNFT component, which is no more to be
         //  integrated over time since spikes are instantaneous)
	//	System.out.println(potential +"+"+ dt.get()+"/"+tau+"*(-"+potential+" +"+ main.java.input+"+"+ h +" ) + 1/"+tau+"*"+cnft);
		return Math.max(0, potential + (-potential + input  + h  )/tau_dt + cnft);
	}

}
