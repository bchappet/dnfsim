package tests;

import static org.junit.Assert.*;

import java.util.Arrays;

import junit.framework.TestCase;

import hardSimulator.NullSpikingNeuronHUM;
import hardSimulator.SpikingNeuronHUM;

import maps.AbstractUnitMap;
import maps.Matrix;
import maps.NeighborhoodMap;
import maps.UnitLeaf;
import maps.UnitParameter;
import maps.Var;
import neigborhood.Neighborhood;
import neigborhood.V4Neighborhood2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import coordinates.DefaultRoundedSpace;
import coordinates.Space;

public class test_spiking_neuron   extends TestCase{

	private SpikingNeuronHUM neuron;
	private Var precision = new Var(7);
	private AbstractUnitMap potential;
	private Var input;

	@Before
	public void setUp() throws Exception {
		Space space2d = new DefaultRoundedSpace(new Var(2), 2, false);
		input = new Var(0);
		potential = new NeighborhoodMap("map",new SpikingNeuronHUM(),new Var(0.02),
				space2d,
				new Var(4),//command.get(CNFTCommandLine.COMPUTE_CLK),
				new Var(0.203125),
				new Var(0.1015625),
				new Var(1d),//command.get(CNFTCommandLine.P_WA),
				new Var(1d),//command.get(CNFTCommandLine.P_WB),
				new Var(0.75),//command.get(CNFTCommandLine.P_THRESHOLD),
				precision,//command.get(CNFTCommandLine.PRECISION),
				new Var(2),//command.get(CNFTCommandLine.P_N),
				input,//input
				new Var(0.64),//tau
				new Var(0.08) //integration dt
				);
		Neighborhood nei = new V4Neighborhood2D(space2d,
				new UnitLeaf((UnitParameter) potential));
		nei.setNullUnit(new NullSpikingNeuronHUM());
		((NeighborhoodMap)potential).addNeighboors(nei);


		potential.constructMemory();
		neuron = (SpikingNeuronHUM) potential.getUnit(0).getUnitModel();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		compute(22);


		setinPortsExc("1000");
		compute(1);
		setinPortsExc("0000");
		assert_potential("0000000000" , "potential should be null");
		assert_outPortsExc("0111" , "excitation should be transmitted to every other neighbors");
		compute(1);
		assert_outPortsExc("0000" , "excitation last only one clk_period");
		compute(2);
		assert_potential("0000011010" , " potential should be excW");


		setinPortsExc("1000");
		setinPortsInh("0100");
		compute(1);
		setinPortsExc("0000");
		setinPortsInh("0000");
		assert_outPortsExc("0111");
		assert_outPortsInh("1011");
		compute(1);
		assert_outPortsExc("0000");
		assert_outPortsInh("0000");

		//Test activation
		compute(4);
		System.out.println("Test activation");
		setinPortsExc("0111"); 
		compute(1);
		System.out.println("outPortsExc : " + Arrays.toString(neuron.getOutPortsExc()));
		assert_outPortsExc("1011","Juste after 3 incoming spike, there are transmitted");
		setinPortsExc("0000");
		compute(1);
		System.out.println("outPortsExc : " + Arrays.toString(neuron.getOutPortsExc()));
		assert_outPortsExc("1111");
		compute(1);
		System.out.println("outPortsExc : " + Arrays.toString(neuron.getOutPortsExc()));
		assert_outPortsExc("1111");
		compute(1);
		System.out.println("outPortsExc : " + Arrays.toString(neuron.getOutPortsExc()));
		assert_outPortsExc("0011");
		compute(1);
		System.out.println("outPortsExc : " + Arrays.toString(neuron.getOutPortsExc()));
	}

	private void setinPortsExc(String bin){
		neuron.setInPortsExc( stringToArray(bin));
	}

	private void setinPortsInh(String bin){
		neuron.setInPortsInh( stringToArray(bin));
	}

	private void compute(int nb){
		for(int i =0 ; i < nb ; i++)
			neuron.computeActivity();
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

}
