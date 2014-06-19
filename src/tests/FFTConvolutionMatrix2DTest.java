package tests;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import maps.Matrix;
import maps.Matrix2D;
import maps.Var;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import coordinates.DefaultRoundedSpace;
import coordinates.Space;
import fft.FFTConvolutionMatrix2D;

public class FFTConvolutionMatrix2DTest  extends TestCase {

	Matrix2D kernel;
	Matrix2D input;
	FFTConvolutionMatrix2D res;

	@Before
	public void setUp() throws Exception {

		Var dt = new Var(0.1);
		Space space = new DefaultRoundedSpace(new Var(3), 2, true);

		double[] valuesV = 
			{
				1,2,3,
				4,5,6,
				7,8,9

			};


		double[] kernelV= 
			{
				7,6,5,
				3,2,1,
				9,4,6

			};


		double[][] testV = 
			{
				{254.0,261.0,247.0},
				{212.0,219.0,205.0},
				{179.0,186.0,172.0}
			};

		kernel = new Matrix2D("kernelM", dt,space,kernelV);

		input = new Matrix2D("inputM",  dt,space,valuesV);


		res = new FFTConvolutionMatrix2D("out", dt, space,kernel,input);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		System.out.println(kernel);
		System.out.println(input);
		
		long time = System.currentTimeMillis();
		res.compute();
		long time2 = System.currentTimeMillis();
		System.out.println(time2 - time);

		System.out.println(res);

		double[] testV = 
			{
				254.0,261.0,247.0,
				212.0,219.0,205.0,
				179.0,186.0,172.0
			};

		assertTrue(equalsRough(res.getValues(),testV,0.001));

	}

	private boolean equalsRough(double[] test, double[] res,double roughness) {
		boolean ret = true;
		for(int i = 0 ; i < test.length ; i++)
		{
			ret &= (test[i] <= res[i]+roughness && test[i] >= res[i] - roughness);
		}

		return ret;
	}

}
