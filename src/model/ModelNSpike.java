package model;

import java.util.Arrays;
import java.util.List;

import maps.AbstractMap;
import maps.Leaf;
import maps.Map;
import maps.NeighborhoodMap;
import maps.Parameter;
import maps.TrajectoryUnitMap;
import maps.UnitLeaf;
import maps.Var;
import neigborhood.V4Neighborhood2D;
import routing.Assymetric2DRouting;
import unitModel.NSpikeUM;
import unitModel.SpikingPotentialUM;
import unitModel.SpikingUM;
import unitModel.Sum;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;
import coordinates.Space;


/**
 * RSDNF validation model : spike are truly transmitted with a given probability
 * 
 * @author bchappet
 * 
 */
public class ModelNSpike extends ModelESpike{




	/** Amount of spike emitted when a neuron is firing **/
	protected int n; 
	protected Parameter pn;
	




	public ModelNSpike(String name) {
		super(name);
	}


	@Override
	protected void initLateralWeightParams(final Space extendedSpace) throws CommandLineFormatException
	{

		Parameter alphaP = command.get(CNFTCommandLine.ALPHA);

		pn = command.get(CNFTCommandLine.N);

		Parameter ppa = command.get(CNFTCommandLine.WA);
		hppa = new TrajectoryUnitMap("pa_hidden",command.get(CNFTCommandLine.DT),extendedSpace,ppa) {
			@Override
			public double computeTrajectory(double... param)   {
				return Math.pow(param[0],
						1/this.space.getSimulationSpace().getResolution());
			}
		};

		Parameter ppb = command.get(CNFTCommandLine.WB);
		hppb = new TrajectoryUnitMap("pb_hidden",command.get(CNFTCommandLine.DT),extendedSpace, ppb) {
			@Override
			public double computeTrajectory(double... param) {
				return Math.pow(param[0],
						1/this.space.getSimulationSpace().getResolution());
			}
		};

		Parameter pA =  command.get(CNFTCommandLine.IA);
		hpA = new TrajectoryUnitMap("A_hidden",command.get(CNFTCommandLine.DT),extendedSpace,pA,alphaP,pn) {
			//A = A /(res*res*n)*(40*40)/alpha
			@Override
			public double computeTrajectory(double... param) {
				double res = this.space.getSimulationSpace().getResolution();
				int n = (int) param[2];
				return param[0] / 
						(res*res*n)*
						(40*40)/param[1];
			}
		};
		Parameter pB =  command.get(CNFTCommandLine.IB);
		hpB  = new TrajectoryUnitMap("B_hidden",command.get(CNFTCommandLine.DT),extendedSpace, pB,alphaP,pn) {
			//B = B /(res*res*n)*(40*40)/alpha
			@Override
			public double computeTrajectory(double... param) {
				double res =  this.space.getSimulationSpace().getResolution();
				int n = (int) param[2];
				return param[0]/ 
						(res*res*n)*
						(40*40)/param[1];
			}
		};
		pTau = command.get(CNFTCommandLine.TAU);

		addParameters(ppa,ppb,pA,pB,pTau,pn,alphaP);
	}

	@Override
	protected void initModel() throws CommandLineFormatException, NullCoordinateException
	{
		Var vdt = command.get(CNFTCommandLine.DT);

		potential = new Map(POTENTIAL,new SpikingPotentialUM(),vdt,space2d);

		AbstractMap resetedPotential = new Map("resetedPotential",new SpikingUM(),
				vdt,space2d);
		focus = new Map(FOCUS,new SpikingUM(),vdt,space2d);

		Var pth = command.get(CNFTCommandLine.THRESHOLD);
		Var ph = command.get(CNFTCommandLine.RESTING_POTENTIAL);
		//Init cnft
		initLateralWeights();

		potential.addParameters(resetedPotential, pTau,input,cnft,ph,pth,new Var(0));
		resetedPotential.addParameters(new Leaf(potential),pth,new Leaf(potential),new Var(0));
		focus.addParameters( new Leaf(potential), pth, new Var(0),new Var(1));

		this.root = potential;
		
		root.constructMemory();
		focus.constructMemory();
	}





	@Override
	public List<Parameter> getDefaultDisplayedParameter() {
		Parameter[] ret ={input,cnft,potential,focus};
		return Arrays.asList(ret);
	}


	@Override
	protected void initLateralWeights() throws NullCoordinateException, CommandLineFormatException 
	{
		cnft = (AbstractMap) getLateralWeights(
				CNFT,command.get(CNFTCommandLine.DT),space2d,
				pn,hpA,hppa,pn,hpB,hppb,focus,new Var("focusThreshold",0));
	}

	/**
	 * 
	 * @param name
	 * @param dt
	 * @param space2D
	 * @param na
	 * @param ia
	 * @param pa
	 * @param nb
	 * @param ib
	 * @param pb
	 * @param focus
	 * @return
	 * @throws CommandLineFormatException 
	 */
	protected  Parameter getLateralWeights(String name,Var dt,Space space2D,
			Parameter na,Parameter ia,Parameter pa,Parameter nb,Parameter ib,Parameter pb,
			Parameter focus,Parameter threshold) throws CommandLineFormatException
	{
		 NeighborhoodMap cnfta = new NeighborhoodMap(name+"_A",
				new NSpikeUM(new Assymetric2DRouting())
		,dt,space2D,na,pa,ia,focus,threshold);

		cnfta.addNeighboors(new V4Neighborhood2D(space2D, new UnitLeaf(cnfta)));
		cnfta.constructMemory();

		 NeighborhoodMap cnftb = new NeighborhoodMap(name+"_B",
				new NSpikeUM(new Assymetric2DRouting())
		,dt,space2D,nb,pb,ib,focus,threshold);

		cnftb.addNeighboors(new V4Neighborhood2D(space2D, new UnitLeaf(cnftb)));
		cnftb.constructMemory();

		Map sum = new Map(CNFT,new Sum(),dt,space2D, cnfta, cnftb);
		sum.constructMemory();
		return sum;
	}

	

	@Override
	public String getText() {
		return "Randomly spiking model : " +
				"When a neuron fires, N spikes are actually transmitted from neuron to neuron" +
				" with a given probability pa for the exitatory spikes and pb for the inhibitory spikes";
	}



	//	public static void main(String[] args) {
	//		CNFTCommandLine cl;
	//		ModelNSpike model = null;
	//		try {
	//			cl = new CNFTCommandLine();
	//			 model = new ModelNSpike("NSpike",
	//					new DefaultRoundedSpace(50, 2, true),0.1,cl);
	//		} catch (CommandLineFormatException e) {
	//			e.printStackTrace();
	//		}
	//		
	//		System.out.println(model.printTree(0));
	//		System.out.println("linked : " + model + " list : " +model.paramNodes);
	//		model.update();
	//	}






}
