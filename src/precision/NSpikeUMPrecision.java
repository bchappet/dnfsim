package precision;

import maps.Parameter;
import maps.Precomputation;
import maps.Unit;
import maps.Var;
import routing.Routing;
import unitModel.NSpikeUM;
import unitModel.UnitModel;
import coordinates.NullCoordinateException;

/**
 * In this implementation, each spike will be transmitted in one step throughout the map
 * a map with delay would not make sense here...
 * @author bchappet
 * TODO parallel compatibility
 *
 */
public class NSpikeUMPrecision extends NSpikeUM implements Precomputation{

	
	protected Parameter precision;
	protected PrecisionVar activity;

	
	public NSpikeUMPrecision(Parameter precision, Routing routing){
		super(routing);
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

	@Override
	public void precompute() {
		activity.set(0);
	}
	
	public NSpikeUMPrecision clone(){
		NSpikeUMPrecision clone = (NSpikeUMPrecision) super.clone();
		clone.activity =  this.activity.clone();
		return clone;
	}


	@Override
	public double compute() throws NullCoordinateException{
		
		
		
		if(params.get(FOCUS).get(coord) >= params.get(THRESHOLD).get(coord)){// == 1
			//System.out.println("Spike !!" +  Arrays.toString(space.discreteProj(coord)));
			this.emmit((int) params.get(NB_SPIKE).get(coord));
		}

		return activity.get();
	}

	@Override
	protected void emmit(int nbSpike){
		int[][] targets = this.routing.getFirstTargets();
		//Send the remaining spikes to the target neighboors
		for(int i = 0 ; i< targets.length ; i++){
		//	System.out.println("target : " + targets[i][Routing.TARGET] + " from : "+targets[i][Routing.DIRECTION]);
			Unit targetUnit = neighborhoods.get(NEIGHBOORS)[targets[i][Routing.TARGET]];
			if(targetUnit != null){
				((NSpikeUMPrecision) targetUnit.getUnitModel()).receive(nbSpike,targets[i][Routing.DIRECTION]);
			}
		}
	}

	/**
	 * 
	 * @param nbSpike : nb spike received
	 * @param from : direction of the sender : incoming direction
	 */
	@Override
	protected void receive(int nbSpike,int from){
		//System.out.println(Arrays.toString(space.discreteProj(coord))+" received " + nbSpike + " from "+ from);
		//Apply probability on incoming spikes
		int nbSpikeReceived = this.applyProbability(nbSpike,new PrecisionVar((int)params.get(PROBA).get(coord),precision));
		if(nbSpikeReceived > 0){
			//Increase activity according to the nbSpikeReceived
			//System.out.println(activity.get() + "+" +nbSpikeReceived +"*" +params.get(INTENSITY).get(coord));
			this.activity.addThis((new PrecisionVar((int) params.get(INTENSITY).get(coord),precision)).mult(new PrecisionVar(nbSpikeReceived,precision)));
			//Get the target of spikes knowing type of routing and incoming direction
			int[][] targets = this.routing.getTargets(from);
			//Send the remaining spikes to the target neighboors
			for(int i = 0 ; i< targets.length ; i++){
				Unit targetUnit = neighborhoods.get(NEIGHBOORS)[targets[i][Routing.TARGET]];
				try{
					int direction = targets[i][Routing.DIRECTION];//Direction frome here to target
					//System.out.println("target : " + target + " direction " + direction + " wrap " + space.isWrap());
					//System.out.println("target : " + this.neighborhoods.get(NEIGHBOORS)[target]);
					((NSpikeUMPrecision)targetUnit.getUnitModel()).receive(nbSpikeReceived,direction);
				}
				catch(ClassCastException e){
				//	System.out.println("Null " + Arrays.toString(coord));
				}
			}
		}

	}

	/**
	 * TODO return Binom(n,p)
	 * @param n
	 * @param p
	 * @return
	 */
	protected int applyProbability(int n,PrecisionVar proba) {
		int sum = 0;
		for(int i = 0 ; i < n ; i++){
			int p = (int) randomGenerator().get();
			//System.out.println(p +" <= " + proba.get());
			if(p <= proba.get()){
				sum ++;
			}
		}
		//System.out.println("in : " + n + " p " + p + " sum : " + sum);
		return sum;
	}

	protected PrecisionVar randomGenerator() {
		return new PrecisionVar((int)(Math.random() * Math.pow(2, precision.get())),precision);
	}

	


}
