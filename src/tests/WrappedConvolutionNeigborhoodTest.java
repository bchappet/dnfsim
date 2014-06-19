package tests;

import junit.framework.TestCase;
import maps.Var;
import neigborhood.WrappedGlobalNeigborhood;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import coordinates.DefaultRoundedSpace;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class WrappedConvolutionNeigborhoodTest  extends TestCase {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws NullCoordinateException {
		Space space = new DefaultRoundedSpace(new Var(4), 2, true);
		WrappedGlobalNeigborhood nei = new WrappedGlobalNeigborhood((int)((space.getResolution()-1)/2),space);
		Double[][] res = nei.getNeighborhood(0d,0d);
		//TODO test
		for(int i = 0 ; i < res.length ; i++)
		{
			for(int j  = 0 ; j < res[i].length ; j++)
			{
				System.out.print(res[i][j] + ",");
			}
			System.out.println();
		}
		
	}

}
