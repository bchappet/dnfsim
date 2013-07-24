package precision;

import maps.Parameter;
import maps.Var;
import unitModel.SpikingUM;
import unitModel.UnitModel;
import coordinates.NullCoordinateException;

/**
 * Updatable potential, Updatable threshold,Updatable low,Updatable high
 * @author bchappet
 * 
 *  public final static int POTENTIAL =0; --PrecisionMap
	public final static int THRESHOLD = 1;
	public final static int LOW = 2; value affected if the potential is lower than the threshold
	public final static int HIGH = 3; value affected if the potential is higher than the threshold
 *
 */
public class SpikingUMPrecision extends SpikingUM {
	
	
	public final static int PRECISION = 4;
	
	protected PrecisionVar activity;
	protected Parameter precision;

	
	public SpikingUMPrecision(Parameter precision) {
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

	
	public SpikingUMPrecision clone(){
		SpikingUMPrecision clone = (SpikingUMPrecision) super.clone();
		clone.activity =  this.activity.clone();
		return clone;
	}


	@Override
	public double compute() throws NullCoordinateException {
		//System.out.println(this);
		return computation2((PrecisionVar)params.get(POTENTIAL).getVar(coord),
				new PrecisionVar((int) params.get(THRESHOLD).get(coord),params.get(PRECISION)),
				new PrecisionVar((int)params.get(LOW).get(coord),params.get(PRECISION)),
				new PrecisionVar((int)params.get(HIGH).get(coord),params.get(PRECISION)));
	}

	/**
	 * 
	 * @param potential
	 * @param th : threshold
	 * @param low : value affected if the potential is lower than the threshold
	 * @param high : value affected if the potential is higher than the threshold
	 * @return
	 */
	protected double computation2(PrecisionVar potential, PrecisionVar th,
			PrecisionVar low, PrecisionVar high) {
		PrecisionVar ret;
		//System.out.println(potential.get() +">"+ th.get());
		if(potential.get() > th.get())
			ret = new PrecisionVar((int) high.get(),params.get(PRECISION));
		else 
			ret = new PrecisionVar((int) low.get(),params.get(PRECISION));
		
		return ret.get();
	}


}
