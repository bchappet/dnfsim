package tests;

import static org.junit.Assert.assertTrue;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.Hardware;

public class HardwareTest   extends TestCase{

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

	private void assertFixedPointCompatible(double flP,int frac,int inte){
		double a = flP * Math.pow(2, frac);
		assertTrue("" + a + " n'est pas entier... flP = " + flP ,(double)((int)a) == a);
		assertTrue(""+ flP + " n'est pas inférieur à " + (Math.pow(2, inte)-1) ,flP <= Math.pow(2, inte)-1 );

	}

	@Test
	public void testtoFPDouble(){
		double a = Hardware.toFPDouble(0.75, 7,Hardware.CAST);
		assertTrue("" + a,a == 0.75);
		a = Hardware.toFPDouble(0.76, 7,Hardware.CAST);
		assertTrue("" + a,a == 0.7578125);
		a = Hardware.toFPDouble(0.76, 5,Hardware.CAST);
		assertTrue("" + a , a == 0.75);
		a = Hardware.toFPDouble(7.8125E-3, 7,Hardware.CAST);
		assertTrue("" + a,a == 7.8125E-3);
	}

	@Test 
	public void testshiftRight(){
		double test = Hardware.shiftRight(0.75, 8, 7);
		assertFixedPointCompatible(0.75, 7, 3);
		assertFixedPointCompatible(test, 7, 3);
		assertTrue(""+test,test == 0.09375);

		test = Hardware.shiftRight(0.0234375, 8, 7);
		assertFixedPointCompatible(0.0234375, 7, 3);
		assertFixedPointCompatible(test, 7, 3);
		assertTrue(""+test,test==0);


	}

	@Test 
	public void testshiftRightComplex(){
		double test = Hardware.shiftRightComplex(0.75, 8, 7);
		assertFixedPointCompatible(0.75, 7, 3);
		assertFixedPointCompatible(test, 7, 3);
		assertTrue(""+test,test == 0.09375);




	}
	
	@Test
	public void testTime(){
		long t1 = System.currentTimeMillis();
		for(int i = 0 ; i < 100000 ; i++){
			Hardware.shiftRightComplex(1.75, 8, 7);
		}
		System.out.println("Time Complex : " + (System.currentTimeMillis() - t1));
		
		t1 = System.currentTimeMillis();
		for(int i = 0 ; i < 100000 ; i++){
			Hardware.shiftRight(1.75, 8, 7);
		}
		System.out.println("Time Simple : " + (System.currentTimeMillis() - t1));
		
	}

	@Test
	public void doubleTestShift(){
		double a = 1;
		double div = 8;
		int frac = 7;
		assertTrue(Hardware.shiftRightComplex(a, div, frac) == Hardware.shiftRight(a, div, frac));

		//Div = 8
		for(int i = 0 ; i < 1000 ; i++){
			try{
				a = Math.random() * 3; 
				Hardware.toFPDouble(a, frac, Hardware.CAST);
				double res = Hardware.shiftRightComplex(a, div, frac);
				assertTrue(res == Hardware.shiftRight(a, div, frac));
				assertFixedPointCompatible(res, frac, 3);
			}catch (Error e) {
				//System.out.println(a + " ==> "+e.getMessage());
			}
		}

		//Div = 2
		div = 2;
		for(int i = 0 ; i < 1000 ; i++){
			try{
				a = Math.random() * 3; 
				//System.out.println("a : " + a);
				Hardware.toFPDouble(a, frac, Hardware.CAST);
				double res = Hardware.shiftRightComplex(a, div, frac);
				//System.out.println("res : " + res);
				assertTrue(res == Hardware.shiftRight(a, div, frac));
				assertFixedPointCompatible(res, frac, 3);
			}catch (Error e) {
				//System.out.println(a + " ==> "+e.getMessage());
			}
		}

		//Div = 2
		div = 16;
		for(int i = 0 ; i < 1000 ; i++){
			try{
				a = Math.random() * 3; 
				//System.out.println("a : " + a);
				Hardware.toFPDouble(a, frac, Hardware.CAST);
				double res = Hardware.shiftRightComplex(a, div, frac);
				//System.out.println("res : " + res);
				assertTrue(res == Hardware.shiftRight(a, div, frac));
				
				assertFixedPointCompatible(res, frac, 3);
			}catch (Error e) {
				//System.out.println(a + " ==> "+e.getMessage());
			}
		}
	}

}
