package precision;

import maps.Parameter;
import maps.Var;
import unitModel.SpikingPotentialUM;
import unitModel.UnitModel;
import coordinates.NullCoordinateException;

/**
 * double tau, double input,double cnft,
			double h ,double th,double high
 * @author bchappet
 *
 */
public class SpikingPotentialUMPrecision extends SpikingPotentialUM {
	
	protected PrecisionVar activity;
	protected Parameter precision;

	public SpikingPotentialUMPrecision(Parameter precision) {
		super();
		this.precision = precision;
	}

	
	@Override
	protected  void onInitilization(){
		activity = new PrecisionVar(0,precision);
		super.onInitilization();
	}
	@Override
	public UnitModel setInitActivity() {
		super.setInitActivity();
		this.activity.set(0);
		return this;
	}
	
	@Override
	public Var getActivity(){
		return activity;
	}
	
	@Override
	public void set(double act)
	{
		this.activity.set(act);
	}
	
	@Override
	public double get(){
		return this.activity.get();
	}

	
	public SpikingPotentialUMPrecision clone(){
		SpikingPotentialUMPrecision clone = (SpikingPotentialUMPrecision) super.clone();
		clone.activity =  this.activity.clone();
		return clone;
	}
	
	@Override
	public double compute() throws NullCoordinateException {
		return computation2((PrecisionVar)params.get(POTENTIAL).getVar(coord), 
				params.get(TAU).get(coord),
				new PrecisionVar((int) params.get(INPUT).get(coord),precision),
				new PrecisionVar((int) params.get(CNFT).get(coord),precision), 
				new PrecisionVar((int)params.get(H).get(coord),precision));
	}
	
	
	protected double computation2( PrecisionVar potential,double tau, PrecisionVar input,
			PrecisionVar cnft,PrecisionVar h ) {
		
         // apply the almost standard equation
         // (but for the CNFT component, which is no more to be
         //  integrated over time since spikes are instantaneous)
	//	System.out.println(potential +"+"+ dt.get()+"/"+tau+"*(-"+potential+" +"+ input+"+"+ h +" ) + 1/"+tau+"*"+cnft);
//		System.out.println("input : " + input);
//		System.out.println("cnft: " + cnft );
		PrecisionVar sum = input.add(h).sub(potential);
//		System.out.println("Sum : " + sum);
		PrecisionVar tau_dt = new PrecisionVar(  (int) (tau/dt.get()),precision);
//		System.out.println("tau_dt: " + tau_dt);
		PrecisionVar div = sum.div(tau_dt);
//		System.out.println("div : " + div);
		PrecisionVar pot2 = potential.add(div).add(cnft);
//		System.out.println("pot2 : " + pot2);
		if(pot2.get() < 0)
			pot2 = new PrecisionVar(0,precision);
//		System.out.println("======> : " + pot2);
		
		return pot2.get();
	}

}
