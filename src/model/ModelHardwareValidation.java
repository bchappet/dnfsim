package model;

import hardSimulator.NullSpikingNeuronHUM;
import hardSimulator.SpikingNeuronCAPRNGUHM;
import hardSimulator.SpikingNeuronHUM;

import java.util.Arrays;
import maps.MatrixFileReader;
import java.util.List;

import cellularAutomata.CACellUnitModel;
import cellularAutomata.PRNGUnitModel;
import cellularAutomata.PRNGWrapperUM;

import maps.AbstractMap;
import maps.AbstractUnitMap;
import maps.Leaf;
import maps.Map;
import maps.Matrix;
import maps.MatrixFileReader;
import maps.NeighborhoodMap;
import maps.NullBufferedNeuronUM;
import maps.Parameter;
import maps.SubUnitMap;
import maps.TrajectoryUnitMap;
import maps.UnitLeaf;
import maps.UnitParameter;
import maps.Var;
import neigborhood.Neighborhood;
import neigborhood.OneToOneNeighborhood;
import neigborhood.V4Neighborhood2D;
import neuronBuffer.BufferedNeuronUM;
import routing.Assymetric2DHardRouting;
import routing.Assymetric2DRouting;
import unitModel.BufferedNeuronUMCAPRNG;
import unitModel.NSpikeUM;
import unitModel.PotentialNeuronUM;
import unitModel.SpikingPotentialUM;
import unitModel.SpikingUM;
import unitModel.Sum;
import unitModel.UnitModel;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.DefaultRoundedSpace;
import coordinates.NullCoordinateException;
import coordinates.RoundedSpace;
import coordinates.Space;

/**
 * Global validation for potentials only
 * 
 * The random number are generated with CAPRNG UnitModel initilized with the rules
 * 10000000  01000000  00100000 etc..
 * 00000000  00000000  00000000
 * 
 * The input are read from a file at each computation, an,d should already be 
 * multiplied by dt/tau (dt = 0.08, tau = 0.64) dt/tau = 0.125
 * 
 * ...
 * @author bchappet
 *
 */
public class ModelHardwareValidation extends ModelHardware {

	public ModelHardwareValidation(String name) {
		super(name);
	}
	
	protected Parameter getFilesInputs(String filename) throws CommandLineFormatException{
		Parameter mat = new MatrixFileReader(INPUT,command.get(CNFTCommandLine.INPUT_DT),this.space2d,filename);
		return mat;
	}
	
	protected Map getCAPRNGWrapperMap(Parameter dtHard) throws CommandLineFormatException{
		double[] rules = {
				35,15,15,35,47,17,58,46,
				27,37,30,41,56,39,23,60,
				60,17,23,29,29,10,26,43,
				61,36,57,62,25,41,42,13,
				29,50,4,9,63,39,26,52,
				6,52,58,5,28,11,54,21,
				31,60,11,35,43,46,29,52,
				30,20,19,38,6,22,41,31
		};
		Space spaceCA = new DefaultRoundedSpace(new Var("res",8), 2, true);
		
		Matrix rulesMat = new Matrix("RulesMat",dtHard , spaceCA, rules);
		CACellUnitModel caum = new CACellUnitModel(new Var("dt",0.1), spaceCA,rulesMat );
		NeighborhoodMap camap = new NeighborhoodMap("CAMap", caum);
		camap.addNeighboors(new V4Neighborhood2D(spaceCA, new UnitLeaf(camap)));
		camap.toParallel();
		//System.out.println("camap : " + camap.hashCode());
		//System.out.println("Camap neigh : " +camap.getNeighborhood().get(0).getMap());

		Var frac = command.get(CNFTCommandLine.PROBA_FRAC);
		//We need 8 val for each spiking neuron
		Var nbVal = new Var("nb_val",8);
		//We need a specific amount of 8x8 map:
		double res = Math.max(Math.ceil((frac.get() * space2d.getResolution())/8), 
				Math.ceil((nbVal.get()* space2d.getResolution())/8));
		System.out.println("Resolution : " + res);
		Space prngSpace = new RoundedSpace(new Double[]{0d,0d}, new Double[]{res,res}, new Var(res), false);
		
		
		
		PRNGUnitModel um = new PRNGUnitModel(camap,dtHard, prngSpace,frac );
		Map map = new Map("PRNGMap", um);
		map.constructMemory();
		
		PRNGWrapperUM wrapUM = new PRNGWrapperUM(dtHard, space2d, map,frac,nbVal);
		Map wrapMap = new Map("WrapPRNGMap",wrapUM);
		wrapMap.constructMemory();
		
		//Different initialization state for the different ca map
		for(int i = 0 ; i < space2d.getResolution() ; i++){
			NeighborhoodMap camap_i = (NeighborhoodMap) map.getUnit(i).getUnitModel().getParam(PRNGUnitModel.MAP);
			camap_i.setIndex(i,1d);
		}
		
		return wrapMap;
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
		
		Map caprngMap = getCAPRNGWrapperMap(displayDt);
		potential = new NeighborhoodMap(POTENTIAL,new SpikingNeuronCAPRNGUHM(),displayDt,space2d,
				compute_clk,
				fp_hpA,fp_hpB,fp_hppa,fp_hppb,fp_threshold,
				command.get(CNFTCommandLine.FRAC),
				pn,hard_input,pTau,vdt,caprngMap);
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
	
//	@Override
//	protected void initDefaultInput() throws CommandLineFormatException, NullCoordinateException{
//		this.input =  getFilesInputs(command.getString(CNFTCommandLine.INPUT_FILES));
//	}
//	
	





	

	



}
