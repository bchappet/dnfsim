package main.java.unitModel;

public class SpikingPotentialUM extends RateCodedUnitModel {

	public SpikingPotentialUM(Double initActivity) {
		super(initActivity);
	}
	
	@Override
	protected double computation2(double potential,double tau, double input,double cnft,double h ,double dt){
         // apply the almost standard equation
         // (but for the CNFT component, which is no more to be
         //  integrated over time since spikes are instantaneous)
		double ret =  Math.max(0, potential + dt/tau*(-potential + input  + h  ) + 1/tau*cnft);
		//double ret = Math.min(tmp,1);
		return ret;
	}

}
