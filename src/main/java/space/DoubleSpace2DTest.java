package main.java.space;

import static org.junit.Assert.*;
import main.java.maps.Var;

import org.junit.Before;
import org.junit.Test;

public class DoubleSpace2DTest {
	
	private DoubleSpace2D uut;

	@Before
	public void setUp() throws Exception {
		
		uut = new DoubleSpace2D(new Var<Double>(0d), new Var<Double>(0d),
				new Var<Double>(1d), new Var<Double>(1d), new Var<Integer>("res",3));
	}

	@Test
	public void testCoordIntToIndex() {
		int index = uut.coordIntToIndex(new Coord<Integer>(1,2));
		assertEquals("the index should be good",7,index);
	}

	@Test
	public void testToTypeCoord() {
		Coord<Integer> coord = new Coord<Integer>(1,1);
		Coord<Double> expected = new Coord<Double>(0.5,0.5);
		assertEquals("the coord should be good",expected,uut.toTypeCoord(coord)); //TODO validate
	}

	@Test
	public void testIndexToCoordInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testIndexToCoordIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testIndexToCoordIntInt1() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVolume() {
		fail("Not yet implemented");
	}

}
