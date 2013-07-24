package hardSimulator;

import java.util.Arrays;

import maps.Parameter;
import maps.Var;
import unitModel.UnitModel;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class NeuronHUM extends UnitModel {


	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;

	//Parameters
	protected static final int PROBA = 0;
	protected static final int PRECISION = 1;
	protected static final int NB_SPIKE = 2;
	protected static final int COMPUTE_CLK = 3;


	//in
	protected int[] inPorts = {0,0,0,0};
	protected int activate;
	protected double[] randomIn;

	//out
	protected int[] outPorts;

	//var
	protected int sourceSpike;

	//Subunit order
	public static final int SOURCE = 0;
	public static final int RANDOM_GEN = 1;
	public static final int TRANS_N = 2;
	public static final int TRANS_S = 3;
	public static final int TRANS_E = 4;
	public static final int TRANS_W = 5;

	//clk handling
	private	Var clk;
	//number of spike received : will be returned for diplay
	private Var nbReceived;

	//	//subunit
	//	protected SourceHUM theSource;
	//	protected TransmitterHUM transN,transS,transE,transW;
	//	protected NeuronRandomGeneratorHUM randomGen;

	public NeuronHUM(Parameter dt,Space space,Parameter...parameters ){
		super(dt,space,parameters);
		SourceHUM theSource = new SourceHUM(dt,space,params.get(NB_SPIKE),params.get(PRECISION));
		NeuronRandomGeneratorHUM randomGen = new NeuronRandomGeneratorHUM(dt,space,params.get(PROBA));

		TransmitterHUM transN = new TransmitterHUM(dt,space,params.get(PRECISION));
		TransmitterHUM transS = new TransmitterHUM(dt,space,params.get(PRECISION));
		TransmitterHUM transE = new TransmitterHUM(dt,space,params.get(PRECISION));
		TransmitterHUM transW = new TransmitterHUM(dt,space,params.get(PRECISION));

		addSubUnits(theSource,randomGen,
				transN,transS,transE,transW);
		
		clk = new Var(0);
		nbReceived = new Var(0);

	}
	
	@Override
	public NeuronHUM clone(){
		NeuronHUM clone = (NeuronHUM) super.clone();
		clone.clk = clk.clone();
		clone.nbReceived = nbReceived.clone();
		return clone;
	}
	
	@Override
	public NeuronHUM clone2(){
		NeuronHUM clone = (NeuronHUM) super.clone2();
		clone.clk = clk;
		clone.nbReceived = nbReceived;
		return clone;
	}

	@Override
	public double compute() throws NullCoordinateException {

		if(clk.get() % params.get(COMPUTE_CLK).get(coord) == 0){
			//reset the nbreceived
			nbReceived.set(0);
		}


		SourceHUM theSource = (SourceHUM) subUnits.get(SOURCE);
		NeuronRandomGeneratorHUM randomGen = (NeuronRandomGeneratorHUM) subUnits.get(RANDOM_GEN);

		TransmitterHUM transN = (TransmitterHUM) subUnits.get(TRANS_N);
		TransmitterHUM transS = (TransmitterHUM) subUnits.get(TRANS_S);
		TransmitterHUM transE = (TransmitterHUM) subUnits.get(TRANS_E);
		TransmitterHUM transW = (TransmitterHUM) subUnits.get(TRANS_W);

		randomGen.setRandomIn(randomIn);
		randomGen.computeActivity();
		int[] randResults = randomGen.getResults();//NSEW
		//System.out.println("random : " + random);

		theSource.setActivate(activate);
		theSource.computeActivity();
		sourceSpike = theSource.getSpike();
		
//		System.out.println("neuron activate : " + activate);
//		System.out.println("neuron inports : " +Arrays.toString(inPorts));
//		System.out.println("neuron source spike : " + sourceSpike);

		transN.setInput(new int[]{inPorts[SOUTH],sourceSpike});
		transN.setProba(randResults[NORTH]);
		transS.setInput(new int[]{inPorts[NORTH],sourceSpike});
		transS.setProba(randResults[SOUTH]);
		transE.setInput(new int[]{inPorts[NORTH],inPorts[SOUTH],
				inPorts[WEST],sourceSpike});
		transE.setProba(randResults[EAST]);
		transW.setInput(new int[]{inPorts[NORTH],inPorts[SOUTH],
				inPorts[EAST],sourceSpike});
		transW.setProba(randResults[WEST]);

		
		transN.computeActivity();
		transS.computeActivity();
		transE.computeActivity();
		transW.computeActivity();

		outPorts = new int[]{transN.getSpike(),
				transS.getSpike(),
				transE.getSpike(),
				transW.getSpike()};

		nbReceived.set(nbReceived.get() +  inPorts[NORTH] + inPorts[SOUTH] + inPorts[EAST] + inPorts[WEST]);




		clk.set(clk.get() + 1);
		return nbReceived.get();

	}

	public void setInports(int[] inPorts) {
		this.inPorts = inPorts;

	}

	public void setActivate(int activate) {
		this.activate = activate;

	}

	public int[] getOutPorts() {
		return outPorts;
	}

	public void setRandomIn(double[] ds) {
		randomIn = ds;
		
	}

	
}
