package hardSimulator;

import maps.Parameter;
import maps.Unit;
import maps.Var;
import unitModel.NeighborhoodUnitModel;
import unitModel.UnitModel;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class SpikingNeuronHUM extends NeighborhoodUnitModel {
	
	//neighboohood
	public static final int NEIGH = 0;
	
	//Neighboor direction
	public final static int N = 0;
	public final static int S = 1;
	public final static int E = 2;
	public final static int W = 3;
	
	//Parameter 
	protected static final int COMPUTE_CLK = 0;
	protected static final int EXC_WEIGHT = 1;
	protected static final int INH_WEIGHT = 2;
	protected static final int EXC_PROBA = 3;
	protected static final int INH_PROBA = 4;
	protected static final int THRESHOLD = 5;
	protected static final int BUFFER_WIDTH = 6;
	protected static final int NB_SPIKE = 7;
	protected static final int INPUT = 8;
	protected static final int TAU = 9;
	protected static final int INTEGRATION_DT = 10;

	//Subunit order
	public final static int SPIKING_UNIT = 0;
	public final static int NEURON_EXC = 1;
	public final static int NEURON_INH = 2;
	protected static final int RANDOM_GENERATOR = 3;
	
	//Sub units
//	protected SpikingUnitHUM sp;
//	protected NeuronHUM excN,inhN;
	
	//in
	protected int[] inPortsExc;
	protected int[] inPortsInh;
	
	//out
	protected Var potential;
	protected int[] outPortsExc;
	protected int[] outPortsInh;
	
	//register
	protected Var activateRegister;
	
	
	public SpikingNeuronHUM(){
		super();
	}
	
	public SpikingNeuronHUM(Parameter dt, Space space, Parameter... parameters) {
		super(dt,space,parameters);
		
	}
	
	protected void onInitilization(){
		super.onInitilization();
		//System.out.println("params : " + this.params + "  "+ Arrays.toString(Thread.currentThread().getStackTrace()));
		potential = new Var(0);
		activateRegister = new Var(0);
		outPortsExc = new int[]{0,0,0,0};
		outPortsInh = new int[]{0,0,0,0};
		
		inPortsExc = new int[]{0,0,0,0};
		inPortsInh = new int[]{0,0,0,0};
		
		initSubUnits();
	}
	
	protected void initSubUnits(){

		UnitModel sp = new SpikingUnitHUM(dt,space,
				params.get(COMPUTE_CLK),
				params.get(EXC_WEIGHT),
				params.get(INH_WEIGHT),
				params.get(THRESHOLD),
				params.get(BUFFER_WIDTH),
				params.get(INPUT),
				params.get(TAU),
				params.get(INTEGRATION_DT));
		UnitModel excN = new NeuronHUM(dt,space,params.get(EXC_PROBA),params.get(BUFFER_WIDTH),params.get(NB_SPIKE),params.get(COMPUTE_CLK));
		UnitModel inhN = new NeuronHUM(dt,space,params.get(INH_PROBA),params.get(BUFFER_WIDTH),params.get(NB_SPIKE),params.get(COMPUTE_CLK));
		RandomGeneratorHUM rg = new RandomGeneratorHUM(dt,space);
		
		addSubUnits(sp,excN,inhN,rg);
	}
	
	public SpikingNeuronHUM clone(){
		SpikingNeuronHUM clone = (SpikingNeuronHUM) super.clone();
		clone.potential = this.potential.clone();
		clone.outPortsExc = new int[]{0,0,0,0};
		clone.inPortsExc = new int[]{0,0,0,0};
		clone.outPortsInh = new int[]{0,0,0,0};
		clone.inPortsInh = new int[]{0,0,0,0};
		clone.activateRegister = this.activateRegister.clone();
		return clone;
	}
	
	public SpikingNeuronHUM clone2(){
		SpikingNeuronHUM clone = (SpikingNeuronHUM) super.clone2();
		clone.potential = this.potential;
		clone.outPortsExc = new int[]{0,0,0,0};
		clone.inPortsExc = new int[]{0,0,0,0};
		clone.outPortsInh = new int[]{0,0,0,0};
		clone.inPortsInh = new int[]{0,0,0,0};
		clone.activateRegister = this.activateRegister;
		
		return clone;
		
	}
	
	public UnitModel getSubUnit(int subUnitIndex) {
		//System.out.println("subUnits" + subUnits);
		return super.getSubUnit(subUnitIndex);
	}

	
	

	@Override
	public double compute() throws NullCoordinateException {
		
		SpikingUnitHUM sp = (SpikingUnitHUM) subUnits.get(SPIKING_UNIT);
		NeuronHUM excN = (NeuronHUM) subUnits.get(NEURON_EXC);
		NeuronHUM inhN = (NeuronHUM) subUnits.get(NEURON_INH);
		RandomGeneratorHUM randGen  = (RandomGeneratorHUM) subUnits.get(RANDOM_GENERATOR);
		
		//System.out.println("Inports exc : " + Arrays.toString(inPortsExc));
		
		sp.setInPortsExc(inPortsExc);
		sp.setInPortsInh(inPortsInh);
		
		excN.setInports(inPortsExc);
		inhN.setInports(inPortsInh);
		
		sp.computeActivity();
		
		int activate = getActivate();//To introduce a delay like in hardware
		activateRegister.set(sp.getActivate());
		
//		System.out.println("spikingNeuorn activate : " + activate);
		excN.setActivate(activate);
		inhN.setActivate(activate);
		
		randGen.computeActivity();
		double[] rands = randGen.getOutputs();
		
		excN.setRandomIn(new double[]{rands[0],rands[1],rands[2],rands[3]});
		inhN.setRandomIn(new double[]{rands[4],rands[5],rands[6],rands[7]});
		
		excN.computeActivity();
		inhN.computeActivity();
	
		this.potential = sp.getPotential();
		this.outPortsExc = excN.getOutPorts();
		this.outPortsInh = inhN.getOutPorts();
		Unit[] neigh = neighborhoods.get(NEIGH);
		
		
		((SpikingNeuronHUM) neigh[N].getUnitModel()).setInPortsExc(S,outPortsExc[N]);
		((SpikingNeuronHUM) neigh[N].getUnitModel()).setInPortsInh(S,outPortsInh[N]);
		
		((SpikingNeuronHUM) neigh[S].getUnitModel()).setInPortsExc(N,outPortsExc[S]);
		((SpikingNeuronHUM) neigh[S].getUnitModel()).setInPortsInh(N,outPortsInh[S]);
		
		((SpikingNeuronHUM) neigh[E].getUnitModel()).setInPortsExc(W,outPortsExc[E]);
		((SpikingNeuronHUM) neigh[E].getUnitModel()).setInPortsInh(W,outPortsInh[E]);
		
		((SpikingNeuronHUM) neigh[W].getUnitModel()).setInPortsExc(E,outPortsExc[W]);
		((SpikingNeuronHUM) neigh[W].getUnitModel()).setInPortsInh(E,outPortsInh[W]);
		
		//System.out.println("Outports exc : " + Arrays.toString(outPortsExc));
		
		
		
		return potential.get();
	}

	public void setInPortsExc(int direction,int val) {
		this.inPortsExc[direction] = val;
	}

	public void setInPortsInh(int direction,int val) {
		this.inPortsInh[direction] = val;
	}

	public void setInPortsExc(int[] inPortsExc) {
		this.inPortsExc = inPortsExc;
	}

	public void setInPortsInh(int[] inPortsInh) {
		this.inPortsInh = inPortsInh;
	}

	public int[] getOutPortsExc() {
		return outPortsExc;
	}

	public int[] getOutPortsInh() {
		return outPortsInh;
	}

	public int getActivate() {
		return (int) activateRegister.get();
	}

}
