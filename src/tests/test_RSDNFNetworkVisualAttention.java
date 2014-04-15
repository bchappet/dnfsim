package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import junit.framework.TestCase;
import hardSimulator.NullSpikingNeuronHUM;
import hardSimulator.SpikingNeuronHUM;
import maps.AbstractUnitMap;
import maps.Map;
import maps.NeighborhoodMap;
import maps.UnitLeaf;
import maps.UnitParameter;
import maps.Var;
import neigborhood.Neighborhood;
import neigborhood.V4Neighborhood2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import unitModel.ConstantUnit;
import unitModel.UnitModel;
import coordinates.DefaultRoundedSpace;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class test_RSDNFNetworkVisualAttention  extends TestCase {

	private SpikingNeuronHUM neuron;
	private Var precision = new Var(7);
	private AbstractUnitMap potential;
	private AbstractUnitMap input;

	private static final int COMPUTE_CLK_period = 4;

	@Before
	public void setUp() throws Exception {

		Space space2d = new DefaultRoundedSpace(new Var(3), 2, false);
		input = new Map("input",
				new UnitModel(new Var(0.08), space2d, new Var(0)){

			@Override
			public double compute() throws NullCoordinateException {
				return activity.get();
			}

		});
		input.constructMemory();
		potential = new NeighborhoodMap("map",new SpikingNeuronHUM(),new Var(0.02),
				space2d,
				new Var(COMPUTE_CLK_period),//command.get(CNFTCommandLine.COMPUTE_CLK),
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


		potential.toParallel();
		neuron = (SpikingNeuronHUM) potential.getUnit(0).getUnitModel();
	}

	@After
	public void tearDown() throws Exception {


	}

	@Test
	public void test() {

		compute(13);
		setInput(0, "00000000011");//input0_0 , "00000000011";
		compute(COMPUTE_CLK_period);//compute(COMPUTE_CLK_period);
		setInput(0, "00000000000");//input0_0 , "00000000000";
		System.out.println(potential.display2D());
		assert_potential(0,"00000000011");

		setInput(0,"00001100000")	;
		compute(COMPUTE_CLK_period);
		setInput(0, "00000000000");
		assert_potential(0,"00000000000");
		compute(COMPUTE_CLK_period);
		System.out.println(potential.display2D());
		assert_potential(3 , "0000011010");
		assert_potential(5 , "0000001101");
		assert_potential(8 , "0000000000");
		compute(COMPUTE_CLK_period);
		System.out.println(potential.display2D());
		assert_potential(3 , "0000010111");
		assert_potential(5 , "0000011001");
		assert_potential(8 , "0000011010");

		compute(12*COMPUTE_CLK_period);
		assert_potential(3 , "0000000111");
		assert_potential(5 , "0000000111");
		assert_potential(8 , "0000001000");

		compute(COMPUTE_CLK_period);
		assert_potential(3 , "0000000111");
		assert_potential(5 , "0000000111");
		assert_potential(8 , "0000000111");

	}

	private void compute(int nb){
		for(int i =0 ; i < nb ; i++){
			potential.compute();
		}

	}

	private void setInput(int index,String binary){
		input.setIndex(index, stringToReal(binary));
	}

	private double stringToReal(String binary) {
		int val = Integer.parseInt(binary, 2);
		return val/(Math.pow(2, 7));
	}

	private void  assert_potential(int index,String binary){

		boolean test = (potential.get(index) == Integer.parseInt(binary, 2)/Math.pow(2,7));
		System.out.println("testing : " + "");
		if(test)
			System.out.println("S ==> Succes");
		else
			System.out.println("F ==> Failure : " +potential.get(index)+"=="+ Integer.parseInt(binary, 2)/Math.pow(2,7) );
		assertTrue(test);
	}

}
