package tests;

import static org.junit.Assert.assertTrue;
import junit.framework.TestCase;
import maps.Map;
import maps.Var;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import unitModel.GaussianND;
import coordinates.DefaultRoundedSpace;
import coordinates.NullCoordinateException;

public class GaussianNDTest  extends TestCase{
	
	private Map gauss;

	@Before
	public void setUp() throws Exception {
		GaussianND gaussModel = new GaussianND(new Var(0.1),new DefaultRoundedSpace(new Var(50), 2, true),
				new Var("I",2),
				new Var("W",0.3),
				new Var("centerX",0),
				new Var("centerY",0)
				);
		gauss = new Map("Gauss",gaussModel);
	}

	@After
	public void tearDown() throws Exception {
		gauss = null;
	}

	@Test
	public void test() throws NullCoordinateException {
		double ret = gauss.get(0d,0d);
		System.out.println(ret);
		assertTrue(ret ==2);
		
		ret = gauss.get(0.2d,0d);
		System.out.println(ret);
		assertTrue(ret > 1.282360 && ret < 1.282361 );
		
		ret = gauss.get(0d,0.5d);
		System.out.println(ret);
		assertTrue(ret > 0.124350 && ret < 0.12436 );
		
		
	}
	
	@Test
	public void testDisplay() throws NullCoordinateException {
		
		
		System.out.println(gauss.displayMemory());
	}

}
