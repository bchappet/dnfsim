package test.java.space;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import main.java.space.Coord;
import main.java.space.Coord2D;
import main.java.space.Space2D;

import org.junit.Before;
import org.junit.Test;

public class Space2DTest {
	
	private Space2D uut;

	@Before
	public void setUp() throws Exception {
		uut = new Space2D(3,4);
	}

	@Test
	public void testIndexToCoord() {
		assertEquals("The coordinate should be good.",new Coord<Integer>(0,0),uut.indexToCoordInt(0));
		assertEquals("The coordinate should be good.",new Coord<Integer>(1,0),uut.indexToCoordInt(1));
		assertEquals("The coordinate should be good.",new Coord<Integer>(2,0),uut.indexToCoordInt(2));
		assertEquals("The coordinate should be good.",new Coord<Integer>(0,1),uut.indexToCoordInt(3));
		assertEquals("The coordinate should be good.",new Coord<Integer>(1,1),uut.indexToCoordInt(4));
		assertEquals("The coordinate should be good.",new Coord<Integer>(2,1),uut.indexToCoordInt(5));
		assertEquals("The coordinate should be good.",new Coord<Integer>(0,3),uut.indexToCoordInt(9));
		assertEquals("The coordinate should be good.",new Coord<Integer>(1,3),uut.indexToCoordInt(10));
		assertEquals("The coordinate should be good.",new Coord<Integer>(2,3),uut.indexToCoordInt(11));

	}
	
	@Test
	public void testCoordToIndex(){
		assertEquals("The index should be good.",0,uut.coordIntToIndex(new Coord2D<Integer>(0,0)));
		assertEquals("The index should be good.",1,uut.coordIntToIndex(new Coord<Integer>(1,0)));
		assertEquals("The index should be good.",2,uut.coordIntToIndex(new Coord<Integer>(2,0)));
		assertEquals("The index should be good.",3,uut.coordIntToIndex(new Coord<Integer>(0,1)));
		assertEquals("The index should be good.",4,uut.coordIntToIndex(new Coord<Integer>(1,1)));
		assertEquals("The index should be good.",5,uut.coordIntToIndex(new Coord<Integer>(2,1)));
		assertEquals("The index should be good.",9,uut.coordIntToIndex(new Coord<Integer>(0,3)));
		assertEquals("The index should be good.",10,uut.coordIntToIndex(new Coord<Integer>(1,3)));
		assertEquals("The index should be good.",11,uut.coordIntToIndex(new Coord<Integer>(2,3)));
	}
	
	@Test
	public void testGetVolume(){
		assertEquals("the volume should be 3*4.",12,uut.getVolume());
	}
	
	@Test
	public void testGetDimension(){
		assertEquals("The dimension shoulbe 3,4",Arrays.toString(new int[]{3,4}),Arrays.toString(new int[]{uut.getDimX(),uut.getDimY()}));
	}
	
//	@Test
//	public void testWrap(){
//		assertEquals("The wrapping should be good",new Coord<Integer>(0,0),uut.wrapCoord(new Coord<Integer>(0,4)));
//		assertEquals("The wrapping should be good",new Coord<Integer>(2,0),uut.wrapCoord(new Coord<Integer>(2,4)));
//		assertEquals("The wrapping should be good",new Coord<Integer>(0,0),uut.wrapCoord(new Coord<Integer>(3,0)));
//		assertEquals("The wrapping should be good",new Coord<Integer>(0,2),uut.wrapCoord(new Coord<Integer>(3,2)));
//		assertEquals("The wrapping should be good",new Coord<Integer>(0,0),uut.wrapCoord(new Coord<Integer>(3,4)));
//
//	}
	
	@Test
	public void testClone(){
		Space2D clone = uut.clone();
		assertEquals("The clone should be equals",uut,clone);
	}
	
	

}
