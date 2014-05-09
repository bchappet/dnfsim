package main.java.model;

import java.util.Arrays;
import java.util.List;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.AbstractMap;
import main.java.maps.Leaf;
import main.java.maps.Map;
import main.java.maps.NeighborhoodMap;
import main.java.maps.Parameter;
import main.java.maps.TrajectoryUnitMap;
import main.java.maps.UnitLeaf;
import main.java.maps.Var;
import main.java.neigborhood.V4Neighborhood2D;
import main.java.routing.Assymetric2DRouting;
import main.java.unitModel.NSpikeUM;
import main.java.unitModel.SpikingPotentialUM;
import main.java.unitModel.SpikingUM;
import main.java.unitModel.Sum;


/**
 * RSDNF validation main.java.model : spike are truly transmitted with a given probability
 * 
 * @author bchappet
 * 
 */
public class ModelNSpike extends ModelESpike{




	/** Amount of spike emitted when a neuron is firing **/
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
		hppa = new TrajectoryUnitMap("pa_hidden",command.get(CNFTCommandLine.DT),noDimSpace,ppa) {
			@Override
			public double computeTrajectory(double... param)   {
				return Math.pow(param[0],
						1/this.space.getSimulationSpace().getResolution());
			}
		};
		hppa.toStatic();

		Parameter ppb = command.get(CNFTCommandLine.WB);
		hppb = new TrajectoryUnitMap("pb_hidden",command.get(CNFTCommandLine.DT),noDimSpace, ppb) {
			@Override
			public double computeTrajectory(double... param) {
				return Math.pow(param[0],
						1/this.space.getSimulationSpace().getResolution());
			}
		};
		hppb.toStatic();

		Parameter pA =  command.get(CNFTCommandLine.IA);
		hpA = new TrajectoryUnitMap("A_hidden",command.get(CNFTCommandLine.DT),noDimSpace,pA,alphaP,pn) {
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
		hpA.toStatic();
		Parameter pB =  command.get(CNFTCommandLine.IB);
		hpB  = new TrajectoryUnitMap("B_hidden",command.get(CNFTCommandLine.DT),noDimSpace, pB,alphaP,pn) {
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
		hpB.toStatic();
		pTau = command.get(CNFTCommandLine.TAU);
		
		addParameters(ppa,ppb,pA,pB,pTau,pn,alphaP,command.get(CNFTCommandLine.THRESHOLD));
	}

	@Override
	protected void initModel() throws CommandLineFormatException, NullCoordinateException
	{
		Var vdt = command.get(CNFTCommandLine.DT);

		potential = new Map(POTENTIAL,new SpikingPotentialUM(),vdt,extendedComputationSpace);

		AbstractMap resetedPotential = new Map("resetedPotential",new SpikingUM(),
				vdt,extendedFramedSpace);
		focus = new Map(FOCUS,new SpikingUM(),vdt,extendedComputationSpace);

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
				CNFT,command.get(CNFTCommandLine.DT),extendedComputationSpace,
				pn,hpA,hppa,pn,hpB,hppb,focus,new Var("focusThreshold",0));
	}

	/**
	 * 
	 * @param name
	 * @param dt
	 * @param main.java.space
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
	protected  Parameter getLateralWeights(String name,Var dt,Space space,
			Parameter na,Parameter ia,Parameter pa,Parameter nb,Parameter ib,Parameter pb,
			Parameter focus,Parameter threshold) throws CommandLineFormatException
	{
		 NeighborhoodMap cnfta = new NeighborhoodMap(name+"_A",
				new NSpikeUM(new Assymetric2DRouting())
		,dt,space,na,pa,ia,focus,threshold);

		cnfta.addNeighboors(new V4Neighborhood2D(space, new UnitLeaf(cnfta)));
		cnfta.constructMemory();

		 NeighborhoodMap cnftb = new NeighborhoodMap(name+"_B",
				new NSpikeUM(new Assymetric2DRouting())
		,dt,space,nb,pb,ib,focus,threshold);

		cnftb.addNeighboors(new V4Neighborhood2D(space, new UnitLeaf(cnftb)));
		cnftb.constructMemory();

		Map sum = new Map(CNFT,new Sum(),dt,space, cnfta, cnftb);
		sum.constructMemory();
		return sum;
	}

	

	@Override
	public String getText() {
		return "Randomly spiking main.java.model : " +
				"When a neuron fires, N spikes are actually transmitted from neuron to neuron" +
				" with a given probability pa for the exitatory spikes and pb for the inhibitory spikes";
	}



	//	public static void main(String[] args) {
	//		CNFTCommandLine cl;
	//		ModelNSpike main.java.model = null;
	//		try {
	//			cl = new CNFTCommandLine();
	//			 main.java.model = new ModelNSpike("NSpike",
	//					new DefaultRoundedSpace(50, 2, true),0.1,cl);
	//		} catch (CommandLineFormatException e) {
	//			e.printStackTrace();
	//		}
	//		
	//		System.out.println(main.java.model.printTree(0));
	//		System.out.println("linked : " + main.java.model + " list : " +main.java.model.paramNodes);
	//		main.java.model.update();
	//	}






}
