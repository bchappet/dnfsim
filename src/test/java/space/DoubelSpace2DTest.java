package test.java.space;

import static org.junit.Assert.assertEquals;
import main.java.maps.Var;
import main.java.space.Coord2D;
import main.java.space.DoubleSpace2D;

import org.junit.Before;
import org.junit.Test;

public class DoubelSpace2DTest {
	
	private DoubleSpace2D uut;

	@Before
	public void setUp() throws Exception {
		
	 uut = new DoubleSpace2D(new Var<Double>("OriX",-0.5),new Var<Double>("OriY",-0.5),
				new Var<Double>("SizeX",1.),new Var<Double>("SizeY",1.),new Var<Integer>(3));
	}

	@Test
	public void testGetDimX() {
		assertEquals("The x dimension should be 3 ",3,uut.getDimX());
		assertEquals("The y dimension should be 3 ",3,uut.getDimY());
		assertEquals("The volume should be 9 ",9,uut.getVolume());
		
		
	}

	
	@Test
	public void testCoordTrans() {
		assertEquals("For index 0 the coord int should be 0 0",new Coord2D<>(0,0),uut.indexToCoordInt(0));
		assertEquals("For index 3 the coord int should be 0 1",new Coord2D<>(0,1),uut.indexToCoordInt(3));
	}
}
