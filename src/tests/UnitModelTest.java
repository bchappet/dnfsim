package tests;

import static org.junit.Assert.assertTrue;
import maps.Unit;
import maps.Var;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import unitModel.GaussianND;
import unitModel.UnitModel;
import coordinates.DefaultRoundedSpace;
import coordinates.Space;

public class UnitModelTest {

	private UnitModel um;

	@Before
	public void setUp() throws Exception {
		Space space2 = new DefaultRoundedSpace(new Var(49),2,true);
		space2.setDimension(new int[]{1,1});

		um = new GaussianND(new Var(0.1), space2, new Var("ia",1), new Var("wa",2), new Var(0),new Var(0));
	}

	@After
	public void tearDown() throws Exception {
		um = null;
	}


	@Test
	public void testClone() throws Exception {
		UnitModel um2 = um.clone();
		System.out.println(um2.getParams());

	}
	
	@Test
	public void testClone2() throws Exception {
		UnitModel um2 = um.clone2();
		assertTrue(um2.getCoord() == um.getCoord());

	}
	
	@Test
	public void testTimeParallel(){
		Unit u= new Unit(um);
		u.setCoord(new Double[]{0d,0d});
		u.toParallel();
		u.compute();
		u.swap();
		u.compute();
		u.swap();
		u.compute();
		u.swap();
		u.compute();
		u.swap();
		assertTrue(u.getUnitModel().getTime() == 0.4);
	}


}
