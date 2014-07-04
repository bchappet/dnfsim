package main.java.model;

import java.util.Arrays;
import java.util.List;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.hardSimulator.NullSpikingNeuronHUM;
import main.java.hardSimulator.SpikingNeuronCAPRNGUHM;
import main.java.hardSimulator.SpikingNeuronHUM;
import main.java.maps.AbstractMap;
import main.java.maps.Leaf;
import main.java.maps.Map;
import main.java.maps.NeighborhoodMap;
import main.java.maps.Parameter;
import main.java.maps.SubUnitMap;
import main.java.maps.TrajectoryUnitMap;
import main.java.maps.UnitLeaf;
import main.java.maps.UnitMap;
import main.java.maps.UnitParameter;
import main.java.maps.Var;
import main.java.neigborhood.Neighborhood;
import main.java.neigborhood.V4Neighborhood2D;
import main.java.statistics.StatCNFT;
import main.java.statistics.StatMapCNFT;
import main.java.statistics.StatisticsCNFT;
import main.java.unitModel.RandTrajUnitModel;
import main.java.unitModel.Sum;
import main.java.unitModel.UnitModel;
import main.resources.utils.Hardware;

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
		Parameter proba_frac = command.get(CNFTCommandLine.PROBA_FRAC);
		Var dt = command.get(CNFTCommandLine.DT);

		
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
		
		fp_hpA = new TrajectoryUnitMap("fp_pA_hidden",dt,noDimSpace,hpA,frac){
			@Override
			public double computeTrajectory(double... param)   {
				return Hardware.toFPDouble(param[0], (int)param[1],Hardware.ROUND);
			}
		};
		
		fp_hpB = new TrajectoryUnitMap("fp_pB_hidden",dt,noDimSpace,hpB,frac){
			@Override
			public double computeTrajectory(double... param)   {
				return Hardware.toFPDouble(param[0], (int)param[1],Hardware.ROUND);
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
		this.addParameters(command.get(CNFTCommandLine.BUFF_WIDTH));
		this.addParameters(command.get(CNFTCommandLine.COMPUTE_CLK));
		System.out.println("pa : " + fp_hppa.get());
		System.out.println("pb : " + fp_hppb.get());
		System.out.println("ia : " + fp_hpA.get());
		System.out.println("ib : " + fp_hpB.get());
		System.out.println("th : " + fp_threshold.get());
		
	}
	
	protected void initModel() throws CommandLineFormatException, NullCoordinateException
	{
		Var vdt = command.get(CNFTCommandLine.DT); //integration dt*

		Var compute_clk = command.get(CNFTCommandLine.COMPUTE_CLK);

		//In this main.java.model we use an main.java.input already multiplied by dt/tau
		Map hard_input = new Map("main.java.input*dt/tau",new UnitModel(vdt,space2d,input,pTau) {
			
			@Override
			public double compute() throws NullCoordinateException {
				return getParam(0).getIndex(coord) * dt.get()/getParam(1).getIndex(coord);
			}
		});



		Parameter displayDt = new TrajectoryUnitMap("hard_clk",vdt,noDimSpace,compute_clk) {
			@Override
			public double computeTrajectory(double... param) {
				return dt.get()/param[0];
			}
		};
		displayDt.toStatic();

		potential = new NeighborhoodMap(POTENTIAL,new SpikingNeuronHUM(),displayDt,space2d,
				compute_clk,
				fp_hpA,fp_hpB,fp_hppa,fp_hppb,fp_threshold,
				command.get(CNFTCommandLine.BUFF_WIDTH),
				pn,hard_input,pTau,vdt,new Var(8));
		Neighborhood nei = new V4Neighborhood2D(space2d,
				new UnitLeaf((UnitParameter) potential));
		nei.setNullUnit(new NullSpikingNeuronHUM());
		((NeighborhoodMap)potential).addNeighboors(nei);
		potential.toParallel();


		this.root = potential;

		focus = new SubUnitMap(FOCUS, (UnitMap) potential, SpikingNeuronHUM.SPIKING_UNIT);
		exc = new SubUnitMap(EXCITATORY, (UnitMap) potential, SpikingNeuronHUM.NEURON_EXC);
		inh = new SubUnitMap(INHIBITORY, (UnitMap) potential, SpikingNeuronHUM.NEURON_INH);
		//For display only
		sum = new Map("Cnft",new UnitModel(displayDt, space2d, exc,inh,fp_hpA,fp_hpB){
			@Override
			public double compute() throws NullCoordinateException {
				return getParam(0).getIndex(coord)*getParam(2).getIndex(coord) - getParam(1).getIndex(coord)*getParam(3).getIndex(coord);
			}

		});
		this.addParameters(sum);//to reach it
		//this.addParameters(caMaps.toArray(new Map[]{}));



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

		StatCNFT stat = new StatCNFT(command.get(CNFTCommandLine.INPUT_DT),this);
		List<StatMapCNFT> statMaps = stat.getDefaultStatistics(new Leaf(focus), trackable);
		StatMapCNFT[] array = statMaps.toArray(new StatMapCNFT[]{});
		stats = new StatisticsCNFT("Stats",command.get(CNFTCommandLine.STAT_DT), 
				noDimSpace,array);

	}
	@Override
	public String getText() {
		return "Hardware main.java.model";
	}





}
