package tests;

import static org.junit.Assert.assertTrue;
import hardSimulator.NullSpikingNeuronHUM;
import hardSimulator.RandomGeneratorCAPRNGHUM;
import hardSimulator.SpikingNeuronCAPRNGUHM;
import hardSimulator.SpikingNeuronHUM;

import java.util.Arrays;

import junit.framework.TestCase;

import maps.AbstractUnitMap;
import maps.Map;
import maps.Matrix;
import maps.NeighborhoodMap;
import maps.Parameter;
import maps.UnitLeaf;
import maps.UnitParameter;
import maps.Var;
import neigborhood.Neighborhood;
import neigborhood.V4Neighborhood2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import console.CommandLineFormatException;

import cellularAutomata.CACellUnitModel;
import cellularAutomata.PRNGUnitModel;
import cellularAutomata.PRNGWrapperUM;

import coordinates.DefaultRoundedSpace;
import coordinates.RoundedSpace;
import coordinates.Space;

public class test_spiking_neuron_caprng   extends TestCase{

	private SpikingNeuronCAPRNGUHM neuron;
	private Var precision = new Var(7);
	private Var proba_precision = new Var(8);
	private AbstractUnitMap potential;
	private Var input;
	private Var clk;
	private Map wrapMap;
	private Var proba;
	@Before
	public void setUp() throws Exception {
		
		
		clk = new Var("clk",0.02);
		Var nbVal = new Var("nb_val",8);
		Space space2d = new DefaultRoundedSpace(new Var(3), 2, false);
		proba = new Var(0.75d);
		
		wrapMap = getCAPRNGWrapperMap(clk,space2d,nbVal,proba_precision);
		
		input = new Var(0);
		potential = new NeighborhoodMap("map",new SpikingNeuronCAPRNGUHM(), clk ,
				space2d,
				new Var(4),//command.get(CNFTCommandLine.COMPUTE_CLK),
				new Var(0.203125),
				new Var(0.1015625),
				proba,//command.get(CNFTCommandLine.P_WA),
				new Var(1d),//command.get(CNFTCommandLine.P_WB),
				new Var(2000),//dont want to be perutrbed by spiking
				precision,//command.get(CNFTCommandLine.PRECISION),
				new Var(2),//command.get(CNFTCommandLine.P_N),
				input,//input
				new Var(0.64),//tau
				new Var(0.08), //integration dt
				wrapMap, //caprng map
				nbVal
				);
		Neighborhood nei = new V4Neighborhood2D(space2d,
				new UnitLeaf((UnitParameter) potential));
		nei.setNullUnit(new NullSpikingNeuronHUM());
		((NeighborhoodMap)potential).addNeighboors(nei);


		potential.constructMemory();
		neuron = (SpikingNeuronCAPRNGUHM) potential.getUnit(0).getUnitModel();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProba0_75() {
		setinPortsExc("1111");
		compute(1);
		assert_outPortsExc("0010");
		compute(1);
		assert_outPortsExc("1111");
		compute(1);
		assert_outPortsExc("1111");
		compute(1);
		assert_outPortsExc("1111");
		compute(1);
		assert_outPortsExc("1011");
		compute(1);
		assert_outPortsExc("0111");
		compute(1);
		assert_outPortsExc("1010");
		compute(1);
		assert_outPortsExc("1111");
		compute(1);
		assert_outPortsExc("0111");

	}
	
	@Test
	public void testProba0_5() {
		proba.set(0.5d);
		//0.5 = 128fp = 0b10000000
		//According to a caprng init with 1000000,0000000,0000000...
		//We will have for excitation
		//F,F,F,F
		//F,T,T,T
		//F,F,T,T
		//T,T,F,T
		//T,F,T,T
		//F,T,T,T
		//T,F,T,F
		//F,T,T,F
		//F,F,T,T
		
		setinPortsExc("1111");
		compute(1);
		assert_outPortsExc("0000");
		compute(1);
		assert_outPortsExc("0111");
		compute(1);
		assert_outPortsExc("0011");
		compute(1);
		assert_outPortsExc("1101");
		compute(1);
		assert_outPortsExc("1011");
		compute(1);
		assert_outPortsExc("0111");
		compute(1);
		assert_outPortsExc("1010");
		compute(1);
		assert_outPortsExc("0110");
		compute(1);
		assert_outPortsExc("0011");

		
		
	}

	private void setinPortsExc(String bin){
		neuron.setInPortsExc( stringToArray(bin));
	}

	private void setinPortsInh(String bin){
		neuron.setInPortsInh( stringToArray(bin));
	}

	private void compute(int nb){
		
		for(int i =0 ; i < nb ; i++){
			wrapMap.update(wrapMap.getTime() + wrapMap.getDt().get());
			neuron.computeActivity();
		}
	}

	private void  assert_potential(String binary,String msg){

		boolean test = (neuron.get() == Integer.parseInt(binary, 2)/Math.pow(2,7));
		System.out.println("testing : " + msg);
		if(test)
			System.out.println("S ==> Succes");
		else
			System.out.println("F ==> Failure : " +neuron.get()+"=="+ Integer.parseInt(binary, 2)/Math.pow(2,7) );
		assertTrue(test);
	}

	private void  assert_outPortsExc(String binary){
		assert_outPortsExc(binary,"test");
	}

	private void  assert_outPortsInh(String binary){
		assert_outPortsInh(binary,"test");
	}

	private void  assert_outPortsExc(String binary,String msg){
		boolean test = (Arrays.equals(neuron.getOutPortsExc(),stringToArray(binary)));
		System.out.println("testing : " + msg);
		if(test)
			System.out.println("S ==> Succes");
		else
			System.out.println("F ==> Failure : " +Arrays.toString(neuron.getOutPortsExc())+"=="+Arrays.toString(stringToArray(binary)) );
		assertTrue(test);
	}

	private void  assert_outPortsInh(String binary,String msg){
		boolean test = (Arrays.equals(neuron.getOutPortsInh(),stringToArray(binary)));
		System.out.println("testing : " + msg);
		if(test)
			System.out.println("S ==> Succes");
		else
			System.out.println("F ==> Failure : " +Arrays.toString(neuron.getOutPortsInh())+"=="+Arrays.toString(stringToArray(binary)) );
		assertTrue(test);
	}

	private int[] stringToArray(String bin){
		int[] ret = new int[bin.length()];
		for(int i = 0 ; i < bin.length() ; i++){
			ret[i] = (int) bin.charAt(i) - 48;
		}
		return ret;
	}
	
protected Map getCAPRNGWrapperMap(Parameter dtHard,Space space2d,Parameter nbVal,Parameter frac) throws CommandLineFormatException{
		
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

		
		
		//We need a specific amount of 8x8 map:
		double res = Math.max(Math.ceil((frac.get() * space2d.getResolution())/8), 
				Math.ceil((nbVal.get()* space2d.getResolution())/8));
		Space prngSpace = new RoundedSpace(new Double[]{0d,0d}, new Double[]{res,res}, new Var(res), false);
		
		
		
		PRNGUnitModel um = new PRNGUnitModel(camap,dtHard, prngSpace,frac );
		Map map = new Map("PRNGMap", um);
		map.constructMemory();
		
		PRNGWrapperUM wrapUM = new PRNGWrapperUM(dtHard, space2d, map,frac,nbVal);
		Map wrapMap = new Map("WrapPRNGMap",wrapUM);
		wrapMap.constructMemory();
		
		//Different initialization state for the different ca map
		for(int i = 0 ; i < space2d.getResolution() ; i++){
			NeighborhoodMap camap_i = (NeighborhoodMap) ((PRNGUnitModel) map.getUnit(i).getUnitModel()).getMap();
			camap_i.setIndex(i,1d);
		}
		
		return wrapMap;
	}

}
