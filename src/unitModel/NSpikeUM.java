package unitModel;

import java.util.Arrays;

import maps.Precomputation;
import maps.Unit;
import routing.Routing;
import coordinates.NullCoordinateException;

/**
 * In this implementation, each spike will be transmitted in one step throughout the map
 * a map with delay would not make sense here...
 * @author bchappet
 * TODO parallel compatibility
 *
 */
public class NSpikeUM extends NeighborhoodUnitModel implements Precomputation{

	/**Parameter map : probability**/
	public static final int NB_SPIKE=0;//nb spike emmited when the neuron  is excited
	public static final int PROBA = 1;//probability of transmission of one spike
	public static final int INTENSITY = 2;//intensity of each received spike
	public static final int FOCUS = 3;//map of exited neurons
	public static final int THRESHOLD = 4;//Threshold of focus map



	/**Only neighborhood used here**/
	public static final int NEIGHBOORS = 0;

	/**Routing politic for spikes**/
	protected Routing routing;

	public NSpikeUM(Routing routing){
		this.routing = routing;
	}

	@Override
	public void precompute() {
		activity.set(0);
	}

	@Override
	public double compute() throws NullCoordinateException{
		
		
		
		if(params.get(FOCUS).get(coord) > params.get(THRESHOLD).get(coord)){// > 1
			//System.out.println("Spike !!" +  Arrays.toString(space.discreteProj(coord)));
			this.emmit((int) params.get(NB_SPIKE).get(coord));
		}

		return activity.get();
	}

	protected void emmit(int nbSpike){
		int[][] targets = this.routing.getFirstTargets();
		//Send the remaining spikes to the target neighboors
		for(int i = 0 ; i< targets.length ; i++){
		//	System.out.println("target : " + targets[i][Routing.TARGET] + " from : "+targets[i][Routing.DIRECTION]);
			Unit targetUnit = neighborhoods.get(NEIGHBOORS)[targets[i][Routing.TARGET]];
			if(targetUnit != null && targetUnit.getUnitModel() instanceof NSpikeUM){
				((NSpikeUM) targetUnit.getUnitModel()).receive(nbSpike,targets[i][Routing.DIRECTION]);
			}
		}
	}

	/**
	 * 
	 * @param nbSpike : nb spike received
	 * @param from : direction of the sender : incoming direction
	 */
	protected void receive(int nbSpike,int from){
		//System.out.println(Arrays.toString(space.discreteProj(coord))+" received " + nbSpike + " from "+ from);
		//Apply probability on incoming spikes
		int nbSpikeReceived = this.applyProbability(nbSpike,params.get(PROBA).get(coord));
		if(nbSpikeReceived > 0){
			//Increase activity according to the nbSpikeReceived
			//System.out.println(activity.get() + "+" +nbSpikeReceived +"*" +params.get(INTENSITY).get(coord));
			//System.out.println("nb spike received: " + nbSpikeReceived);
			this.activity.set(this.activity.get() + nbSpikeReceived*params.get(INTENSITY).get(coord));
			//System.out.println("activity : " + activity.get());
			//Get the target of spikes knowing type of routing and incoming direction
			int[][] targets = this.routing.getTargets(from);
			//Send the remaining spikes to the target neighboors
			for(int i = 0 ; i< targets.length ; i++){
				Unit targetUnit = neighborhoods.get(NEIGHBOORS)[targets[i][Routing.TARGET]];
				try{
					int direction = targets[i][Routing.DIRECTION];//Direction frome here to target
					//System.out.println("target : " + target + " direction " + direction + " wrap " + space.isWrap());
					//System.out.println("target : " + this.neighborhoods.get(NEIGHBOORS)[target]);
					((NSpikeUM)targetUnit.getUnitModel()).receive(nbSpikeReceived,direction);
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
	protected int applyProbability(int n,double p) {
		int sum = 0;
		for(int i = 0 ; i < n ; i++){
			if(Math.random() <= p){
				sum ++;
			}
		}
		//System.out.println("in : " + n + " p " + p + " sum : " + sum);
		return sum;
	}

	public NSpikeUM clone(){
		NSpikeUM clone = (NSpikeUM) super.clone();
		clone.routing = this.routing;
		return clone;
	}



}
