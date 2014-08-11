package main.java.unitModel;

import java.util.List;

import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.maps.SingleValueParam;
import main.java.maps.Unit;
import main.java.routing.Routing;
import Jama.Matrix;

public class NSpikeUMTransientFault extends NSpikeUM {
	
	
	public static final int TRANSIENT_PROBA = 5;//Transient fault probability
	public static final int PERMANENT_FAULT_MAP = 6;//Permanent fault probability

	public NSpikeUMTransientFault(Routing routing) {
		super(routing);
	}
	
	/**
	 * TODO return Binom(n,p)
	 * @param n
	 * @param p
	 * @return
	 */
	protected int applyProbability(int n,double p,double transientProba) {
		int sum = 0;
		for(int i = 0 ; i < n ; i++){
//			System.out.println("pp:"+p);
			if(Math.random() <= p && Math.random() <= (1-transientProba)){
				sum ++;
			}
		}
		//System.out.println("in : " + n + " p " + p + " sum : " + sum);
		return sum;
	}
	
	/**
	 * 
	 * @param nbSpike : nb spike received
	 * @param from : direction of the sender : incoming direction
	 */
	protected void receive(int nbSpike,int from, List<Parameter> params){
//		System.out.println("Receive spike from " + from +  " @"+System.identityHashCode(this));
		//System.out.println(Arrays.toString(space.discreteProj(coord))+" received " + nbSpike + " from "+ from);
		//Apply probability on incoming spikes
		double proba = (double) ((SingleValueParam) params.get(PROBA)).get();
		double transientFaultProba = (double) ((SingleValueParam) params.get(TRANSIENT_PROBA)).get();
		int nbSpikeReceived = this.applyProbability(nbSpike,proba,transientFaultProba);
		if( nbSpikeReceived > 0){
			//Increase activity according to the nbSpikeReceived
			//System.out.println(activity.get() + "+" +nbSpikeReceived +"*" +params.get(INTENSITY).get(coord));
			//System.out.println("nb spike received: " + nbSpikeReceived);
			this.set(this.get() +  nbSpikeReceived * ((Number)((SingleValueParam) params.get(INTENSITY)).get()).doubleValue()  );
			//System.out.println("activity : " + activity.get());
			//Get the target of spikes knowing type of routing and incoming direction
			int[][] targets = this.routing.getTargets(from);
			//Send the remaining spikes to the target neighboors
			for(int i = 0 ; i< targets.length ; i++){
				Unit targetUnit = neighbors[targets[i][Routing.TARGET]];
				
				
				try{
					int direction = targets[i][Routing.DIRECTION];//Direction frome here to target
//					System.out.println("target : " + " @"+System.identityHashCode(targetUnit.getUnitModel()) + " direction " + direction );
					//System.out.println("target : " + this.neighborhoods.get(NEIGHBOORS)[target]);
					NSpikeUM target = ((NSpikeUM)targetUnit.getUnitModel());
//					if(proba == 1 && detectWrapping(target)){
//						//nothing if proba == 1 no transmission on tore limit
//						//System.out.println("noTransmission");
//					}else{
//						if(params.get(PROBA).get(coord) == 1){
//							//System.out.println("Transmission");
//						}
						Map<Boolean,?> permanentFaults = (Map<Boolean,?>) params.get(PERMANENT_FAULT_MAP);
						if(!isDisabled(permanentFaults)){
							target.receive(nbSpikeReceived,direction,params);
						}
//					}
				}catch(NullPointerException | ClassCastException e){
//					e.printStackTrace();
//					System.exit(-1);
				}
				
				
			}
		}

	}
	
	/**
	 * Permanent faults are always the same.
	 * 
	 * @return
	 */
	private boolean isDisabled(Map<Boolean,?> permanentFault) {
		return permanentFault.getIndex(this.index).booleanValue();
	}
	
	
	

}
