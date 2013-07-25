package tests;

import static org.junit.Assert.*;
import hardSimulator.NullSpikingNeuronHUM;
import hardSimulator.SpikingNeuronHUM;
import hardSimulator.SpikingUnitHUM;

import java.util.Arrays;

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

import precision.PrecisionVar;
import coordinates.DefaultRoundedSpace;
import coordinates.Space;

public class SpikingNeuronHUMTest {

	
	private SpikingNeuronHUM neuron;
	private Var precision = new Var(7);
	private AbstractUnitMap potential;
	@Before
	public void setUp() throws Exception {
		
		Space space2d = new DefaultRoundedSpace(new Var(2), 2, false);
		potential = new NeighborhoodMap("map",new SpikingNeuronHUM(),new Var(0.08),
						space2d,
						new Var(1),//command.get(CNFTCommandLine.COMPUTE_CLK),
						new Var(0.1484375),//command.get(CNFTCommandLine.P_IA),
						new Var(0.078125),//command.get(CNFTCommandLine.P_IB),
						new Var(1d),//command.get(CNFTCommandLine.P_WA),
						new Var(1d),//command.get(CNFTCommandLine.P_WB),
						new Var(0.75),//command.get(CNFTCommandLine.P_THRESHOLD),
						precision,//command.get(CNFTCommandLine.PRECISION),
						new Var(2),//command.get(CNFTCommandLine.P_N),
						new Matrix("input", new Var(0.1), space2d, new double[]{
								0,0,
								0,0}),//input
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
	
	//@Test
	public void testClone(){
		SpikingNeuronHUM clone = neuron.clone();
		assertTrue(equals(clone.getOutPortsInh(),new int[]{0,0,0,0}));
		neuron.setInPortsInh(new int[]{1,1,1,1});
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPortsInh(),new int[]{1,1,1,1}));
		assertTrue(equals(clone.getOutPortsInh(),new int[]{0,0,0,0}));
		
		
	}
	
	

	//@Test
	public void test() {
		neuron.setInPortsExc(new int[]{1,0,0,0});
		neuron.computeActivity();
		System.out.println(Arrays.toString(neuron.getOutPortsExc()));
		assertTrue(equals(neuron.getOutPortsExc(),new int[]{0,1,1,1}));
		
		neuron.setInPortsExc(new int[]{0,0,0,1});
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPortsExc(),new int[]{0,0,1,0}));
		
		neuron.setInPortsExc(new int[]{0,0,1,0});
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPortsExc(),new int[]{0,0,0,1}));
		
		
		neuron.setInPortsExc(new int[]{0,1,0,0});
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPortsExc(),new int[]{1,0,1,1}));
		
		
	}
	
	@Test
	public void testActivation() {
		double act = 0.1484375;
		neuron.setInPortsExc(new int[]{1,0,0,0});
		neuron.computeActivity();
		assertTrue(((SpikingNeuronHUM)neuron).getActivate() == 0);
		assertTrue(neuron.getActivity().get() == act);
		neuron.computeActivity();
		assertTrue(neuron.getActivity().get() == 0.28125);
		neuron.computeActivity();
		assertTrue(neuron.getActivity().get() == 0.390625);
		neuron.computeActivity();
		neuron.computeActivity();
		neuron.computeActivity();
		neuron.computeActivity();
		neuron.computeActivity();
		assertTrue(((SpikingNeuronHUM)neuron).getActivate() == 1);
		assertTrue(neuron.getActivity().get() == 0);
		neuron.computeActivity();
		assertTrue(((SpikingNeuronHUM)neuron).getActivate() == 0);
		assertTrue(neuron.getActivity().get() == act);
	}
	
	
	
	private static boolean equals(int[] a , int [] b){
		boolean ret = true;
		for(int i = 0 ; i < a.length ; i++){
			ret &= a[i] == b[i];
		}
		return ret;
	}

}
