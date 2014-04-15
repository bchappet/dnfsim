package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import coordinates.Coor;

public class CoorTest  extends TestCase{

	private Coor<Integer> coorint;
	private Coor<Double> coordouble;
	@Before
	public void setUp() throws Exception {
		this.coorint = new Coor<Integer>();
		this.coordouble = new Coor<Double>();

	}

	@After
	public void tearDown() throws Exception {
		coorint = null;
		coordouble=null;
	}

	

	

	@Test
	public void testToInt() {
		coordouble.put(Coor.X,20.1);
		Coor<Integer> test = coordouble.toInt();
		assertTrue(test.get(Coor.X) instanceof Integer );
		assertTrue(test.get(Coor.X) == 20 );
	}

	@Test
	public void testToDouble() {
		coorint.put(Coor.X,20);
		Coor<Double> test = coorint.toDouble();
		assertTrue(test.get(Coor.X) instanceof Double );
		assertTrue(test.get(Coor.X) == 20d );
	}





	@Test
	public void testGetSpaceSize() {
		coorint = new Coor<Integer>(2,3,4);
		assertTrue(coorint.getSpaceSize() == 6*4);
	}

	@Test
	public void testDecompose() {
		coorint = new Coor<Integer>(2,2,2);

		assertTrue(coorint.decompose(0).equals(new Coor<Integer>(0,0,0)));

		assertTrue(coorint.decompose(1).equals(new Coor<Integer>(1,0,0)));
		assertTrue(coorint.decompose(2).equals(new Coor<Integer>(0,1,0)));
		assertTrue(coorint.decompose(3).equals(new Coor<Integer>(1,1,0)));
		assertTrue(coorint.decompose(4).equals(new Coor<Integer>(0,0,1)));

		assertTrue(coorint.decompose(5).equals(new Coor<Integer>(1,0,1)));
		assertTrue(coorint.decompose(6).equals(new Coor<Integer>(0,1,1)));
		assertTrue(coorint.decompose(7).equals(new Coor<Integer>(1,1,1)));
	}

	@Test
	public void testEquals()
	{
		coorint = new Coor<Integer>(2,3,4);
		Coor<Integer> coorint2 = new Coor<Integer>(2,3,4);
		assertTrue(coorint.equals(coorint2));

		coorint2 = new Coor<Integer>(2,3,3);
		assertFalse(coorint.equals(coorint2));
	}
	
	@Test
	public void testClone()
	{
		Coor<Integer> test = new Coor<Integer>(1,1);
		Coor<Integer> coor2 = new Coor<Integer>(test);
		System.out.println(coor2);
		Coor coor = test.clone();
		System.out.println(coor);
		assertTrue(coor.equals(test));
	}

}
