package model;

import hardSimulator.NullSpikingNeuronHUM;
import hardSimulator.SpikingNeuronHUM;

import java.util.Arrays;
import java.util.List;

import maps.AbstractMap;
import maps.AbstractUnitMap;
import maps.Leaf;
import maps.Map;
import maps.NeighborhoodMap;
import maps.Parameter;
import maps.SubUnitMap;
import maps.TrajectoryUnitMap;
import maps.UnitLeaf;
import maps.UnitParameter;
import maps.Var;
import neigborhood.Neighborhood;
import neigborhood.V4Neighborhood2D;
import statistics.Stat;
import statistics.Statistics;
import unitModel.RandTrajUnitModel;
import unitModel.Sum;
import unitModel.UnitModel;
import utils.Hardware;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class ModelHardware extends ModelNSpike {


	public static final String EXCITATORY = "Excitatory";
	public final static String INHIBITORY = "Inhibitory";

	protected AbstractMap exc;
	protected  AbstractMap inh;
	protected AbstractMap sum;
	
	/**Fixed point transformation of standard lateral parameters with the given frac for fractional fp part**/
	protected Parameter fp_hpA,fp_hpB,fp_hppa,fp_hppb,fp_threshold;

	public ModelHardware(String name) {
		super(name);
	}
	
	@Override
	protected void initLateralWeightParams(final Space extendedSpace) throws CommandLineFormatException
	{
		
		
		super.initLateralWeightParams(extendedSpace);
		
		//We transform each parameter in fp param with frac precision
		Parameter frac = command.get(CNFTCommandLine.FRAC);
		Var dt = command.get(CNFTCommandLine.DT);

		
		fp_hppa = new TrajectoryUnitMap("fp_pa_hidden",dt,extendedSpace,hppa,frac){
			@Override
			public double computeTrajectory(double... param)   {
				return Hardware.toFPDouble(param[0], (int)param[1]);
			}
		};
		
		

		fp_hppb = new TrajectoryUnitMap("fp_pb_hidden",dt,extendedSpace,hppb,frac){
			@Override
			public double computeTrajectory(double... param)   {
				return Hardware.toFPDouble(param[0], (int)param[1]);
			}
		};
		
		fp_hpA = new TrajectoryUnitMap("fp_pA_hidden",dt,extendedSpace,hpA,frac){
			@Override
			public double computeTrajectory(double... param)   {
				return Hardware.toFPDouble(param[0], (int)param[1]);
			}
		};
		
		fp_hpB = new TrajectoryUnitMap("fp_pB_hidden",dt,extendedSpace,hpB,frac){
			@Override
			public double computeTrajectory(double... param)   {
				return Hardware.toFPDouble(param[0], (int)param[1]);
			}
		};
		Parameter threshold = command.get(CNFTCommandLine.THRESHOLD);
		fp_threshold = new TrajectoryUnitMap("fp_threshold",dt,extendedSpace,threshold,frac){
			@Override
			public double computeTrajectory(double... param)   {
				return Hardware.toFPDouble(param[0], (int)param[1]);
			}
		};

		fp_hppa.toStatic();
		fp_hppb.toStatic();
		fp_hpA.toStatic();
		fp_hpB.toStatic();
		fp_threshold.toStatic();
		
	}

	

	protected void initModel() throws CommandLineFormatException, NullCoordinateException
	{
		Var vdt = command.get(CNFTCommandLine.DT); //integration dt*
		
		//In this model we use an input already multiplied by dt/tau
		Map hard_input = new Map("input*dt/tau",new UnitModel(vdt,space2d,input,pTau) {
			
			@Override
			public double compute() throws NullCoordinateException {
				return getParam(0).get(coord) * dt.get()/getParam(1).get(coord);
			}
		});
		
		Var compute_clk = command.get(CNFTCommandLine.COMPUTE_CLK);
		displayDt = new TrajectoryUnitMap("hard_clk",vdt,noDimSpace,compute_clk) {
			@Override
			public double computeTrajectory(double... param) {
				return dt.get()/param[0];
			}
		};
		displayDt.toStatic();
		
		potential = new NeighborhoodMap(POTENTIAL,new SpikingNeuronHUM(),displayDt,space2d,
				compute_clk,
				fp_hpA,fp_hpB,fp_hppa,fp_hppb,fp_threshold,
				command.get(CNFTCommandLine.FRAC),
				pn,hard_input,pTau,vdt);
		Neighborhood nei = new V4Neighborhood2D(space2d,
				new UnitLeaf((UnitParameter) potential));
		nei.setNullUnit(new NullSpikingNeuronHUM());
		((NeighborhoodMap)potential).addNeighboors(nei);
		potential.toParallel();
		
		
		
		this.root = potential;

		focus = new SubUnitMap(FOCUS, (AbstractUnitMap) potential, SpikingNeuronHUM.SPIKING_UNIT);
		exc = new SubUnitMap(EXCITATORY, (AbstractUnitMap) potential, SpikingNeuronHUM.NEURON_EXC);
		inh = new SubUnitMap(INHIBITORY, (AbstractUnitMap) potential, SpikingNeuronHUM.NEURON_INH);
		//For display only
		sum = new Map("Cnft",new UnitModel(displayDt, space2d, exc,inh,fp_hpA,fp_hpB){
			@Override
			public double compute() throws NullCoordinateException {
				return getParam(0).get(coord)*getParam(2).get(coord) - getParam(1).get(coord)*getParam(3).get(coord);
			}
			
		});
		this.addParameters(sum);

		root.constructMemory();
	}

	@Override
	public List<Parameter> getDefaultDisplayedParameter() {
		Parameter[] ret ={input,potential,focus,exc,inh,sum};
		return Arrays.asList(ret);
	}
	
	/**
	 * Here we are intrested in focus map and not the potential map
	 * @throws CommandLineFormatException 
	 */
	protected void initializeStatistics() throws CommandLineFormatException 
	{
		
		Parameter stat_dt = new TrajectoryUnitMap("stats_dt",command.get(CNFTCommandLine.DT),
				noDimSpace,command.get(CNFTCommandLine.COMPUTE_CLK),command.get(CNFTCommandLine.DT)) {
			
			@Override
			public double computeTrajectory(double... param) {
				double compute_clk = param[0];
				double dt = param[1];
				
				return dt;//TODO
			}
		};

		Stat stat = new Stat(command.get(CNFTCommandLine.INPUT_DT),noDimSpace,this);
		stats = new Statistics("Stats",command.get(CNFTCommandLine.INPUT_DT), 
				noDimSpace,stat.getDefaultStatistics(new Leaf(focus),  trackable));

	}
	@Override
	public String getText() {
		return "Hardware model";
	}





}
