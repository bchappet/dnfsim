package tests;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.Hardware;

public class FixedPointTest  extends TestCase {

	int frac = 8; //factorial part
	int inte = 2; //integer part
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	private void assertFixedPointCompatible(double flP){
		double a = flP * Math.pow(2, frac);
		assertTrue("" + a + " n'est pas entier... flP = " + flP ,(double)((int)a) == a);
		assertTrue(""+ flP + " n'est pas inférieur à " + (Math.pow(2, inte)-1) ,flP <= Math.pow(2, inte)-1 );
		
	}
	
	
	@Test
	public void testAddition(){
		
		double fp = 1.53515625;
		double add = 1/Math.pow(2, frac);
		assertFixedPointCompatible(add);
		assertFixedPointCompatible(fp);
		double x = fp + add;
		assertFixedPointCompatible(x);
		x = fp + add;
		assertFixedPointCompatible(x);
		
		for(int i = 0 ; i <200 ; i++){
			x = fp + add;
			assertFixedPointCompatible(x);
		}
		
		
	}
	
	@Test
	public void testDivision(){
		
		double fp = 1.53515625;
		double div = 3;
		assertFixedPointCompatible(fp);
		double x = Hardware.shiftRight(fp,div,frac);
		assertFixedPointCompatible(x);
		
		div = 2;
		x = Hardware.shiftRight(fp,div,frac);
		assertFixedPointCompatible(x);
		
		div = 8;
		x = Hardware.shiftRight(fp,div,frac);
		assertFixedPointCompatible(x);
		
		div = 16;
		x = Hardware.shiftRight(fp,div,frac);
		assertFixedPointCompatible(x);
		
		div = 32;
		x = Hardware.shiftRight(fp,div,frac);
		assertFixedPointCompatible(x);
		
		div = 64;
		x = Hardware.shiftRight(fp,div,frac);
		assertFixedPointCompatible(x);
		
		
	}

	@Test
	public void test() {
		double fp = 0.01171875;
		double sum = 0;
		int facteur = 100000;
		for(int i = 0 ; i < facteur  ; i++)
		{
			sum += fp;
		}
		
		System.out.println(sum +"=="+ facteur*fp);
		assertTrue(sum == facteur*fp);
		
		int frac = 8;
		int frac_2 = (int) Math.pow(2, frac);
		
		int round = (int) (fp*frac_2);
		System.out.println("round : " + round);
		
		int sum_round = 0;
		for(int i = 0 ; i < facteur  ; i++)
		{
			sum_round +=round;
		}
		
		double sum_round_res = sum_round/(double)frac_2;
		
		System.out.println(sum +"=="+ sum_round_res);
		assertTrue(sum == sum_round_res);
		
		
	
	}

}
