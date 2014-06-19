package tests;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import hardSimulator.SpikingUnitHUM;
import maps.Var;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import coordinates.DefaultRoundedSpace;

public class test_spiking_unit  extends TestCase{

	private SpikingUnitHUM um;
	private Var input;

	@Before
	public void setUp() throws Exception {
		Var integration_dt = new Var(0.08);
		input = new Var(0);
		um = new SpikingUnitHUM(new Var(0.02), new DefaultRoundedSpace(new Var(50), 2, false),
				new Var(4),//compute clock
				new Var(0.203125),
				new Var(0.1015625),
				new Var(0.75),
				new Var(7),
				input,//input
				new Var(0.64),//tau
				integration_dt
				);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		compute(21);
		////--test 1 exc spike, potential = 32 after computeClock_period
		um.setInPortsExc(new int[]{0,0,0,1});
		compute(1);
		um.setInPortsExc(new int[]{0,0,0,0});
		assertTrue(um.getPotential().get() == 0);
		compute(3);
		assertTrue(um.getPotential().get() == 0.203125);
		assert_potential("0000011010","erreur 1 : potential should be excW");

		////--test potential decreasing
		compute(4);
		assert_potential("0000010111", "erreur 2 : potential should be decreasing");
		compute(4);
		assert_potential("0000010101" , "erreur 3 : potential should be decreasing");
		compute(4);
		assert_potential("0000010011" , "erreur 4 : potential should be decreasing");

		//--test 1 inh spike => pot is 4
		um.setInPortsInh(new int[]{0,0,0,1});
		compute(1);
		um.setInPortsInh(new int[]{0,0,0,0});
		compute(3);
		assert_potential("0000000100" , "pot should be 4");
		compute(4);
		assert_potential("0000000100" , "decreasing is not possible anymore (4/8 = 0)");

		//--1 inh and 1 exc spike => potential = 16 - 13 + 4 = 17
		um.setInPortsExc(new int[]{0,0,0,1});; 
		um.setInPortsInh(new int[]{0,0,0,1});
		compute(1);
		um.setInPortsExc(new int[]{0,0,0,0});
		um.setInPortsInh(new int[]{0,0,0,0});
		compute(3);
		assert_potential("0000010001" , "soustraction error");

		//--Test the integration ability of SummedExcitatory
		//--res should be 17 - 2 + 2*26 = 67
		um.setInPortsExc(new int[]{0,0,0,1});
		compute(2);
		um.setInPortsExc(new int[]{0,0,0,0});
		compute(2);
		assert_potential("0001000011" , "Integration error");

		//--Test the spiking ability
		//--res should be : 67 - 8 + 3 * 26 = 137 >(threshold = 96)
		um.setInPortsExc(new int[]{0,1,1,1});
		System.out.println("set 0111");
		compute(1);
		um.setInPortsExc(new int[]{0,0,0,0});
		System.out.println("set 0000");
		compute(1);
		System.out.println("Um . get : " + um.get());
		assertTrue(um.getActivate() == 0);
		compute(1);
		System.out.println("Um . get : " + um.get());
		assertTrue(um.getActivate() == 0);
		compute(1);
		System.out.println("Um . get : " + um.get());
		assertTrue(um.getActivate() == 1);
		compute(1);
		System.out.println("Um . get : " + um.get());
		assertTrue(um.getActivate() == 0);
		assert_potential("0000000000" , "Potentiel reset error");
		//				wait for 2.5 * clock_period; TODO
		//				assert activate = '1' , "Activation error";
		//				wait for   0.5*clock_period;
		//				assert_potential("0000000000" , "Potentiel reset error"; 
		//				assert activate = '0' , "Activation error, activation should last one half top";

		
		
		
		//--Test the input feeding

		input.set(0.125);
		compute(3);
		input.set(0);
		assert_potential("0000010000" , "input feed error");

		//--test inh and exc integration
		//--16 - 2 + 2 * 26 - 2 * 13 = 40
		um.setInPortsExc(new int[]{0,0,0,1});
		um.setInPortsInh(new int[]{0,0,0,1});
		compute(2);
		um.setInPortsExc(new int[]{0,0,0,0});
		um.setInPortsInh(new int[]{0,0,0,0});
		compute(2);
		assert_potential("0000101000" , "Integration exc inh error");


	}

	private void compute(int nb){
		for(int i =0 ; i < nb ; i++)
			um.computeActivity();
	}

	private void  assert_potential(String binary,String msg){
		boolean test = (um.getPotential().get() == Integer.parseInt(binary, 2)/Math.pow(2,7));
		System.out.println("testing : " + msg);
		if(test)
			System.out.println("S ==> Succes");
		else
			System.out.println("F ==> Failure : " +um.getPotential().get()+"=="+ Integer.parseInt(binary, 2)/Math.pow(2,7) );
		assertTrue(test);
	}

}
