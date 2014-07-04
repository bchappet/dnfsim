package main.java.neuronBuffer;

import java.util.Arrays;

import main.java.console.CNFTCommandLine;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.Unit;
import main.java.maps.Var;
import main.java.routing.Routing;
import main.java.unitModel.NeighborhoodUnitModel;
import main.java.unitModel.UnitModel;
import main.resources.utils.ArrayUtils;


/**
 * A buffered neuron contains 5 buffer : one source and 4 transmitter 
 * @author bchappet
 *
 */
public class BufferedNeuronUM extends NeighborhoodUnitModel{

	//parameters
	public static final int PROBA = 0;
	public static final int CAPACITY = 1;
	//neighboohood
	public static final int NEIGH = 0;

	//Nb buffer
	public static final int NB_BUFF = 5;

	/**Tab of buffer within the neuron (5)**/
	protected Buffer[] bufs;
	/**Routing behaviour**/
	protected Routing routing;



	/**Id for the 5 memories**/
	public final static int SP = 0;
	public final static int N = 1;
	public final static int S = 2;
	public final static int E = 3;
	public final static int W = 4;
	
	
	public BufferedNeuronUM(Routing routing,Var dt, Space space, Parameter... parameters){
		super(dt,space,parameters);
		this.routing = routing;
		initBuffer();
		
	}
	
	public BufferedNeuronUM(Routing routing)
	{
		super();
		this.routing = routing;
		initBuffer();
	}

	protected void initBuffer()
	{
		Parameter capacity;
		try{
			capacity =  params.getIndex(CAPACITY);
		}catch (IndexOutOfBoundsException e) {
			capacity = new Var(CNFTCommandLine.BUFF_WIDTH,Integer.MAX_VALUE);
		}
		
		bufs = new LeakyBuffer[NB_BUFF];
		bufs[SP] = new LeakyBuffer(SP,this,capacity){
			@Override
			protected boolean testProba(double proba) {
				return true;
			}
		};
		
		for(int i = 1 ; i < bufs.length ; i ++)
		{
			bufs[i] = new LeakyBuffer(i,this,capacity);
		}
		
		
		
		
	}

	public String toString(){
		return Arrays.toString(bufs)+"="+activity.val;
	}

	public int getBuffOutput(int id)
	{
		return bufs[id].getOutput();
	}

	@Override
	public BufferedNeuronUM clone()
	{
		
		BufferedNeuronUM clone = (BufferedNeuronUM)super.clone();
		clone.routing = this.routing;//shared
		clone.bufs = ArrayUtils.deepCopy(this.bufs);//deep copy
		for(int i = 0 ; i < bufs.length ; i ++){
		//	System.err.print("change neuron of "+clone.bufs[i].hashCode()+" from "+clone.bufs[i].neuron.hashCode());
			clone.bufs[i].setNeuron(clone);
			
		//	System.err.println(" to " + clone.bufs[i].neuron.hashCode());
		}
		//System.err.println("End cloning from "+this.hashCode()+" to " + clone.hashCode());
		return clone;
	}
	
	@Override
	public BufferedNeuronUM clone2()
	{
		BufferedNeuronUM clone = (BufferedNeuronUM)super.clone2();
		clone.routing = this.routing;//shared
		clone.bufs = ArrayUtils.deepCopy(this.bufs);//deep copy
		for(int i = 0 ; i < bufs.length ; i ++){
		//	System.err.print("change neuron of "+clone.bufs[i].hashCode()+" from "+clone.bufs[i].neuron.hashCode());
			clone.bufs[i].setNeuron(clone);
			
		//	System.err.println(" to " + clone.bufs[i].neuron.hashCode());
		}
		//System.err.println("End cloning from "+this.hashCode()+" to " + clone.hashCode());
		return clone;
	}

	/**
	 * Set the Sp buffer (source) to produce n spike
	 * @original n
	 */
	public void setExited(int n)
	{
		bufs[SP].setBuffer(n);
	}
	
	


	/**Calculate the next outputs of memories
	 * TODO optimize : 
	 * 	selftime 56.2% (10/04)
	 **/
	@Override
	public synchronized double compute()
	{
		
		double proba = params.getIndex(PROBA).getIndex(coord);

		Unit[] neigh = neighborhoods.get(NEIGH);
		//The source has no main.java.input
		bufs[SP].compute(proba,0);

		//TODO
				//Using int[][] main.java.routing.getTargets(int from) : 
				//we have to reverse the thinking : 
				//N send to S (from N) --> buff N receive from neuron S, buff N
				//N send to E (from W) --> buff W receive from neuron E, buff N
				//....
				
//				int[] nbReceived = new int[bufs.length-1];//nb main.java.input received per buffer
//				Arrays.fill(nbReceived, bufs[SP].getOutput());
//				
//				for(int i = 0 ; i < nbReceived.length ; i++){
//					int[][] targets = main.java.routing.getTargets(i);
//					for(int j = 0 ; j < targets.length ; j++){
//						//System.out.println(i +" += " +targets[j][Routing.TARGET]+" buff "+targets[j][Routing.DIRECTION]);
//						nbReceived[i] +=  ((BufferedNeuronUM) neigh[targets[j][Routing.TARGET]].getUnitModel()).getBuffOutput(targets[j][Routing.DIRECTION]+1);
//					}
//				}
//				
//				for(int i = 1; i  < nbReceived.length + 1  ; i++)
//					bufs[i].compute(proba,nbReceived[i-1]);

		bufs[N].compute(proba,bufs[SP].getOutput(),((BufferedNeuronUM) neigh[S-1].getUnitModel()).getBuffOutput(N));
		bufs[S].compute(proba,bufs[SP].getOutput(),((BufferedNeuronUM) neigh[N-1].getUnitModel()).getBuffOutput(S));
		bufs[E].compute(proba,bufs[SP].getOutput(),((BufferedNeuronUM) neigh[S-1].getUnitModel()).getBuffOutput(N),((BufferedNeuronUM) neigh[N-1].getUnitModel()).getBuffOutput(S),
				((BufferedNeuronUM) neigh[W-1].getUnitModel()).getBuffOutput(E));

		bufs[W].compute(proba,bufs[SP].getOutput(),((BufferedNeuronUM) neigh[S-1].getUnitModel()).getBuffOutput(N),((BufferedNeuronUM) neigh[N-1].getUnitModel()).getBuffOutput(S),
				((BufferedNeuronUM) neigh[E-1].getUnitModel()).getBuffOutput(W));
//
		
		int nbSpikeReceived = ((BufferedNeuronUM) neigh[S-1].getUnitModel()).getBuffOutput(N)+
				((BufferedNeuronUM) neigh[N-1].getUnitModel()).getBuffOutput(S)+
				((BufferedNeuronUM) neigh[W-1].getUnitModel()).getBuffOutput(E)+
				((BufferedNeuronUM) neigh[E-1].getUnitModel()).getBuffOutput(W);
		
		
		
		//Here we access either the old value(if parallel) or the current (on line)
		//System.out.println("previous " + this.unit.get()+" nb received "  + nbSpikeReceived);
		return  this.unit.get() + nbSpikeReceived;

	}

	@Override
	public void reset() 
	{
		super.reset();
		for(Buffer b : bufs){
			b.reset();
		}
		
	}

	

	public String printBuff()
	{
		String ret = "";
		ret +="[";
		for(int k = 0 ; k < 5 ; k++)
		{
			ret += getBuffer(k).printBuff() + ";";
		}
		ret += "]";
		return ret;
	}
	



	public Buffer getBuffer(int k) {
		return bufs[k];
	}



	/**
	 * Get the most charged buffer charge
	 * @return
	 */
	public int maxBuffer() {
		int max = 0;
		for(Buffer b : bufs)
		{
			int m = b.getCharge();
			if(m > max)
				max = m;
		}
		return max;
	}

	public synchronized void resetActivity() {
		//bit dirty to reset Activity of every memory
		for(UnitModel um : unit.getMemories())
		{
			um.set(0);
		}
		
	}





}
