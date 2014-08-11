package test.java.space;

import static org.junit.Assert.assertEquals;
import main.java.space.Coord1D;
import main.java.space.Space1D;

import org.junit.Before;
import org.junit.Test;

public class Space1DTest {
	
	private Space1D uut;

	@Before
	public void setUp() throws Exception {
		uut = new Space1D(10);
	}

	@Test
	public void testVolume() {
		assertEquals("The volume should be 10",10,uut.getVolume());
	}
	
	@Test
	public void testIndexToCoord(){
		assertEquals("The coord should be the same as index ",new Coord1D<Integer>(2),uut.indexToCoord(2));
	}
	
	@Test
	public void testCoordToIndex(){
		assertEquals("The coord should be the same as index ",2,uut.coordIntToIndex(new Coord1D<Integer>(2)));
	}

}
