package tests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.Hardware;

public class HardwareTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testToInteger() {
		int res = Hardware.toInteger(new int[]{0,1,1,1,1,1});
		assertTrue(res == 62);
	}
	
	@Test
	public void testtoFPDouble(){
		assertTrue(Hardware.toFPDouble(0.75, 7) == 0.75);
		assertTrue(Hardware.toFPDouble(0.76, 7) == 0.7578125);
		assertTrue(Hardware.toFPDouble(0.76, 5) == 0.75);
		assertTrue(Hardware.toFPDouble(7.8125E-3, 7) == 7.8125E-3);
	}
	
	@Test 
	public void testshiftRight(){
		double test = Hardware.shiftRight(0.75, 3, 7);
		assertTrue(test == 0.25);
		test = Hardware.shiftRight(0.02, 3, 7);
		assertTrue(test==0.0078125);
		test = Hardware.shiftRight(0.002, 3, 7);
		assertTrue(test == 0);
	}

}
