package tests;

import static org.junit.Assert.assertTrue;
import junit.framework.TestCase;
import maps.Matrix;
import maps.Var;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import coordinates.DefaultRoundedSpace;
import coordinates.Space;
import fft.SVD;

public class SVDTest  extends TestCase {
	
	Matrix kernel;

	@Before
	public void setUp() throws Exception {
		double[] kernelV= 
			{
				 7,6,5,
				 3,2,1,
				 9,4,6
			};
		Var dt = new Var(0.1);
		Space space = new DefaultRoundedSpace(new Var(3), 2, true);
		 kernel = new Matrix("kernelM", dt,space,kernelV);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		System.out.println(kernel);
		SVD svd = new SVD(kernel);
		System.out.println(svd.getSingular(0));
		assertTrue(svd.getSingular(0) == 15.880254787977119);
		System.out.println(svd.getSingular(1));
		assertTrue(svd.getSingular(1) == 2.012426899264349);
		System.out.println(svd.getSingular(2));
		assertTrue(svd.getSingular(2) == 0.8761540070161158);
		
		System.out.println(svd.rank());
		
		assertTrue(svd.rank() == 3);
		
		
	}

}
