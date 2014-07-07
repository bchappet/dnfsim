package test.java.space;
import static org.junit.Assert.assertEquals;
import main.java.maps.Var;
import main.java.space.Coord;
import main.java.space.Coord2D;
import main.java.space.DoubleSpace2D;

import org.junit.Before;
import org.junit.Test;

import test.resources.toolsForJunitTests.ToolsJunitTests;

/**
 * The refSpace currently used : 
 * -0.5 -0.3 -0.1 0.1 0.3 0.5  : continous coord                                 
 *   [---------------------[
 *     0  |  1 | 2 | 3 | 4     : discretes coord
 * @author bchappet
 *
 */
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
	public void testCoordTransInt() {
		assertEquals("For index 0 the coord int should be 0 0",new Coord2D<>(0,0),uut.indexToCoordInt(0));
		assertEquals("For index 3 the coord int should be 0 1",new Coord2D<>(0,1),uut.indexToCoordInt(3));
	}
	
	@Test
	public void testToTypeCoord(){
		ToolsJunitTests.assertEqualsRough("TestCoord : ",new Coord<Double>(-0.33333333333333337d,-0.33333333333333337d),uut.toTypeCoord(new Coord<Integer>(0,0)));
		ToolsJunitTests.assertEqualsRough("TestCoord : ",new Coord<Double>(0d,0d),uut.toTypeCoord(new Coord<Integer>(1,1)));
		ToolsJunitTests.assertEqualsRough("TestCoord : ",new Coord<Double>(0.33333333333333337d,0.33333333333333337d),uut.toTypeCoord(new Coord<Integer>(2,2)));
	}
	
	@Test
	public void testcoordToIndexDouble(){
//		System.out.println(uut.coordToIndex(new Coord<Double>(0d,0d)));
		assertEquals("TestCoord : ",4,uut.coordToIndex(new Coord<Double>(0d,0d)));
		assertEquals("TestCoord : ",0,uut.coordToIndex(new Coord<Double>(-0.5d,-0.5d)));
		assertEquals("TestCoord : ",8,uut.coordToIndex(new Coord<Double>(0.49d,0.49d)));
	}
	

}
