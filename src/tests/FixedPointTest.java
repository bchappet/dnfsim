package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FixedPointTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
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
