package tests;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import hardSimulator.TransmitterHUM;

import maps.Var;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import coordinates.DefaultRoundedSpace;

public class test_transmitter4   extends TestCase{

	TransmitterHUM um;

	@Before
	public void setUp() throws Exception {
		um = new TransmitterHUM(new Var(0.02), new DefaultRoundedSpace(new Var(50), 2, false),
				new Var(8));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		um.setProba(1);
		compute(22);

		setInput("0001");
		compute(1);
		setInput("0000");
		assert_spike(1);
		compute(1);

		setInput("0010");
		compute(1);
		setInput("0000");
		assert_spike(1);
		compute(1);

		setInput("0011");
		compute(1);
		setInput("0000");
		assert_spike(1);
		compute(1);
		assert_spike(1);
		compute(1);

		setInput("0011");
		compute(1);
		setInput("0011");
		assert_spike(1);
		compute(1);
		setInput("0000");		
		assert_spike(1);
		compute(1);
		assert_spike(1);
		compute(1);
		assert_spike(1);
		compute(1);
		
		//test proba
		um.setProba(0);
		setInput("1111");
		compute(1);
		setInput("0000");
		assert_spike(0);
		compute(1);
		assert_spike(0);
		compute(1);
		assert_spike(0);
		compute(1);
		assert_spike(0);
		
		//test proba
		um.setProba(1);
		setInput("0111");
		compute(1);
		um.setProba(0);
		setInput("0000");
		assert_spike(1);
		compute(1);
		um.setProba(1);
		assert_spike(0);
		compute(1);
		assert_spike(1);
		compute(1);
		assert_spike(0);

		//--Test buffer size 
		setInput("1111");
		compute(85);
		setInput("0000");
		compute(1);
		assert_spike(0);

		//--Test buffer size 
		setInput("1111");
		compute(84);
		setInput("0000");
		assert_spike(1);
		compute(253);
		assert_spike(0);
	}

	private void compute(int nb){
		System.out.println("Compute " + nb);
		for(int i =0 ; i < nb ; i++)
			um.computeActivity();
	}

	private void assert_spike(int val){
		System.out.println("Spike = " + um.getSpike() + " (expected : " + val + ")");
		boolean test = (um.getSpike() == val);
		if(test)
			System.out.println("S ==> Succes");
		else
			System.out.println("F ==> Failure : " +um.getSpike()+"=="+ val);
		assertTrue(test);
	}

	private void setInput(String val){
		System.out.println("SetInput : " + val);
		um.setInput(stringToArray(val));
	}

	private int[] stringToArray(String bin){
		int[] ret = new int[bin.length()];
		for(int i = 0 ; i < bin.length() ; i++){
			ret[i] = (int) bin.charAt(i) - 48;
		}
		return ret;
	}

}
