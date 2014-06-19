package model;

import neigborhood.V4Neighborhood2D;
import routing.Assymetric2DRouting;
import maps.AbstractMap;
import maps.Leaf;
import maps.Map;
import maps.NeighborhoodMap;
import maps.Parameter;
import maps.TrajectoryUnitMap;
import maps.UnitLeaf;
import maps.Var;
import unitModel.NSpikeUM;
import unitModel.SpikingPotentialPrecisionUM;
import unitModel.SpikingPotentialUM;
import unitModel.SpikingUM;
import unitModel.Sum;
import unitModel.UnitModel;
import utils.Hardware;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;
import coordinates.Space;


/**
 * RSDNF validation model : spike are truly transmitted with a given probability
 * Here we artificially change the parameter to respect a fixed point representation
 * of a given fractional width. CNFTCommandLine.PROBA_FRAC and CNFTCommandLine.FRAC
 * 
 * DT / TAU should be 2^-x
 * 
 * Input should also be with a fixed point representation: CNFTCommandLine.FRAC
 * 
 * ia and ib are multiplied by 1/tau
 * 
 * input is multiplied by dt/tau
 * 
 * 
 * 
 * @author bchappet
 * 
 */
public class ModelNSpikePrecision extends ModelNSpike{

	/**Fixed point transformation of standard lateral parameters with the given frac for fractional fp part**/
	protected Parameter fp_hpA,fp_hpB,fp_hppa,fp_hppb,fp_threshold;


	public ModelNSpikePrecision(String name) {
		super(name);
		
		
	}
	
	@Override
	protected void initLateralWeightParams(final Space extendedSpace) throws CommandLineFormatException
	{
		
		
		super.initLateralWeightParams(extendedSpace);
		
		//We transform each parameter in fp param with frac precision
		Parameter frac = command.get(CNFTCommandLine.FRAC);
		Parameter proba_frac = command.get(CNFTCommandLine.PROBA_FRAC);
		Var dt = command.get(CNFTCommandLine.DT);
		Var tau = command.get(CNFTCommandLine.TAU);
		double dt_tau = dt.get() / tau.get();
		int tau_dt = (int) (1/dt_tau);
		if((double)tau_dt != 1/dt_tau){
			System.err.println("dt/tau sould be 2^-x "+ dt_tau);
			System.exit(-1);
		}
		
		String bin = Integer.toBinaryString(tau_dt);
		int nb = bin.replaceAll("0","").length();
		if(nb != 1){
			System.err.println("dt/tau sould be 2^-x "+ dt_tau);
			System.exit(-1);
		}
		
		
		
		
		

		
		fp_hppa = new TrajectoryUnitMap("fp_pa_hidden",dt,noDimSpace,hppa,proba_frac){
			@Override
			public double computeTrajectory(double... param)   {
				return Hardware.toFPDouble(param[0], (int)param[1],Hardware.ROUND);
			}
		};
		
		

		fp_hppb = new TrajectoryUnitMap("fp_pb_hidden",dt,noDimSpace,hppb,proba_frac){
			@Override
			public double computeTrajectory(double... param)   {
				return Hardware.toFPDouble(param[0], (int)param[1],Hardware.ROUND);
			}
		};
		
		fp_hpA = new TrajectoryUnitMap("fp_pA_hidden",dt,noDimSpace,hpA,frac,tau){
			@Override
			public double computeTrajectory(double... param)   {
				//ia/tau
				return Hardware.toFPDouble(param[0]/param[2], (int)param[1],Hardware.ROUND);
			}
		};
		
		fp_hpB = new TrajectoryUnitMap("fp_pB_hidden",dt,noDimSpace,hpB,frac,tau){
			@Override
			public double computeTrajectory(double... param)   {
				//ib/tau
				return Hardware.toFPDouble(param[0]/param[2], (int)param[1],Hardware.ROUND);
			}
		};
		Parameter threshold = command.get(CNFTCommandLine.THRESHOLD);
		fp_threshold = new TrajectoryUnitMap("fp_threshold",dt,noDimSpace,threshold,frac){
			@Override
			public double computeTrajectory(double... param)   {
				return Hardware.toFPDouble(param[0], (int)param[1],Hardware.ROUND);
			}
		};

		fp_hppa.toStatic();
		fp_hppb.toStatic();
		fp_hpA.toStatic();
		fp_hpB.toStatic();
		fp_threshold.toStatic();
//		System.out.println("pa : " +fp_hppa.get());
//		System.out.println("pb : " +fp_hppb.get());
//		System.out.println("ia : "+fp_hpA.get());
//		System.out.println("ib : " +fp_hpB.get());
//		System.out.println("th : " + fp_threshold.get());
		
	}
	
	@Override
	protected void initModel() throws CommandLineFormatException, NullCoordinateException
	{
		Var vdt = command.get(CNFTCommandLine.DT);

		potential = new Map(POTENTIAL,new SpikingPotentialPrecisionUM(),vdt,space2d);

		AbstractMap resetedPotential = new Map("resetedPotential",new SpikingUM(),
				vdt,space2d);
		focus = new Map(FOCUS,new SpikingUM(),vdt,space2d);

		Var pth = command.get(CNFTCommandLine.THRESHOLD);
		Var ph = command.get(CNFTCommandLine.RESTING_POTENTIAL);
		//Init cnft
		initLateralWeights();

		potential.addParameters(resetedPotential, pTau,input,cnft,ph,command.get(CNFTCommandLine.FRAC));
		resetedPotential.addParameters(new Leaf(potential),pth,new Leaf(potential),new Var(0));
		focus.addParameters( new Leaf(potential), pth, new Var(0),new Var(1));

		this.root = potential;
		
		root.constructMemory();
		focus.constructMemory();
	}
	
	protected void initDefaultInput() throws CommandLineFormatException, NullCoordinateException{
		super.initDefaultInput();
		
		Parameter oldInput = input;
		input = new Map(INPUT,new UnitModel(command.get(CNFTCommandLine.INPUT_DT),space2d,
				oldInput,command.get(CNFTCommandLine.FRAC),
				command.get(CNFTCommandLine.DT),command.get(CNFTCommandLine.TAU)){

			@Override
			public double compute() throws NullCoordinateException {
				double val = getParam(0).get(coord);
				//input * dt/tau
				val = val * getParam(2).get(coord)/getParam(3).get(coord);
				return Hardware.toFPDouble(val,(int)getParam(1).get(coord),Hardware.ROUND);
			}
			
		});
			
		
		
	}
	
	@Override
	protected void initLateralWeights() throws NullCoordinateException, CommandLineFormatException 
	{
		cnft = (AbstractMap) getLateralWeights(
				CNFT,command.get(CNFTCommandLine.DT),space2d,
				pn,fp_hpA,fp_hppa,pn,fp_hpB,fp_hppb,focus,new Var("focusThreshold",0));
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
	@Override
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



	







}
