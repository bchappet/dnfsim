package main.java.model;

import java.util.ArrayList;
import java.util.Arrays;

import main.java.cellularAutomata.CACellUnitModel;
import main.java.cellularAutomata.PRNGUnitModel;
import main.java.cellularAutomata.PRNGWrapperUM;
import main.java.console.CNFTCommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.DefaultRoundedSpace;
import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.RoundedSpace;
import main.java.coordinates.Space;
import main.java.hardSimulator.NullSpikingNeuronHUM;
import main.java.hardSimulator.SpikingNeuronCAPRNGUHM;
import main.java.hardSimulator.SpikingNeuronHUM;
import main.java.maps.AbstractMap;
import main.java.maps.Leaf;
import main.java.maps.Map;
import main.java.maps.Matrix;
import main.java.maps.MatrixFileReader;
import main.java.maps.NeighborhoodMap;
import main.java.maps.NullBufferedNeuronUM;
import main.java.maps.Parameter;
import main.java.maps.SubUnitMap;
import main.java.maps.TrajectoryUnitMap;
import main.java.maps.UnitLeaf;
import main.java.maps.UnitMap;
import main.java.maps.UnitParameter;
import main.java.maps.Var;
import main.java.neigborhood.Neighborhood;
import main.java.neigborhood.OneToOneNeighborhood;
import main.java.neigborhood.V4Neighborhood2D;
import main.java.neuronBuffer.BufferedNeuronUM;
import main.java.routing.Assymetric2DHardRouting;
import main.java.routing.Assymetric2DRouting;
import main.java.unitModel.BufferedNeuronUMCAPRNG;
import main.java.unitModel.NSpikeUM;
import main.java.unitModel.PotentialNeuronUM;
import main.java.unitModel.SpikingPotentialUM;
import main.java.unitModel.SpikingUM;
import main.java.unitModel.Sum;
import main.java.unitModel.UnitModel;

import java.util.List;

/**
 * Global validation for potentials only
 * 
 * The random number are generated with CAPRNG UnitModel initilized with the rules
 * 10000000  01000000  00100000 etc..
 * 00000000  00000000  00000000
 * 
 * The main.java.input are read from a file at each computation, an,d should already be 
 * multiplied by dt/tau (dt = 0.08, tau = 0.64) dt/tau = 0.125
 * 
 * ...
 * @author bchappet
 *
 */
public class ModelHardwareValidation extends ModelHardware {

	protected List<AbstractMap> caMaps;

	public ModelHardwareValidation(String name) {
		super(name);
		caMaps = new ArrayList<AbstractMap>();
	}

	protected AbstractMap getFilesInputs(String filename) throws CommandLineFormatException{
		AbstractMap mat = new MatrixFileReader(INPUT,command.get(CNFTCommandLine.INPUT_DT),this.space2d,filename);
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
		double resCAPRNG = Math.max(Math.ceil((frac.get() * space2d.getResolution())/8), 
				Math.ceil((nbVal.get()* space2d.getResolution())/8));
	//	System.out.println("Resolution : " + resCAPRNG);
		Space prngSpace = new RoundedSpace(new Double[]{0d,0d}, new Double[]{resCAPRNG,resCAPRNG}, new Var(resCAPRNG), false);
		


		PRNGUnitModel um = new PRNGUnitModel(camap,dtHard, prngSpace,frac );
		Map prng_map = new Map("PRNGMap", um);
		prng_map.constructMemory();

		PRNGWrapperUM wrapUM = new PRNGWrapperUM(dtHard, space2d, prng_map,frac,nbVal);
		Map wrapMap = new Map("WrapPRNGMap",wrapUM);
		wrapMap.constructMemory();

		//Different initialization state for the different ca map
		int nbOne=0; 
		int posOne = 0;
		for(int i = 0 ; i < prngSpace.getDiscreteVolume() ; i++){
			NeighborhoodMap camap_i = (NeighborhoodMap) ((PRNGUnitModel) prng_map.getUnit(i).getUnitModel()).getMap();
			
			for(int j = 0 ; j < nbOne ; j++)
				camap_i.setIndex(j,1);
			
			camap_i.setIndex(posOne, 1);
			
			posOne ++;
			
			if(posOne == spaceCA.getDiscreteVolume()){
				nbOne ++;
				posOne = nbOne;
			}
			
			initRandomMap(camap_i);
			//System.out.print(camap_i.display2D() +"");
			caMaps.add(camap_i);
		}

		return wrapMap;
	}

	private void initRandomMap(NeighborhoodMap map) {
		for(int i = 0 ; i < map.getSpace().getDiscreteVolume() ; i++){
			int rand = 0;
			if(Math.random() > 0.5)
				rand = 1;
			map.setIndex(i, rand);
		}
		
	}

	protected void initModel() throws CommandLineFormatException, NullCoordinateException
	{
		Var vdt = command.get(CNFTCommandLine.DT); //integration dt*
		AbstractMap hard_input ;

//		In this main.java.model we use an main.java.input already multiplied by dt/tau
//				hard_input = new Map("main.java.input*dt/tau",new UnitModel(vdt,space2d,main.java.input,pTau) {
//					
//					@Override
//					public double compute() throws NullCoordinateException {
//						return getParam(0).get(coord) * dt.get()/getParam(1).get(coord);
//					}
//				});

		Var compute_clk = command.get(CNFTCommandLine.COMPUTE_CLK);

		input = getFilesInputs("files/main.java.input");
		hard_input = input;
		input.getDt().setIndex(0, vdt.get());



		Parameter displayDt = new TrajectoryUnitMap("hard_clk",vdt,noDimSpace,compute_clk) {
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
				command.get(CNFTCommandLine.BUFF_WIDTH),
				pn,hard_input,pTau,vdt,caprngMap,new Var(8));
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
		List<Parameter> lret = new ArrayList<Parameter>();
		lret.addAll( Arrays.asList(ret));
		//lret.addAll(caMaps);
		
		return lret;
	}












}
