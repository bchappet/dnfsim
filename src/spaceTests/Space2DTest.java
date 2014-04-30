package spaceTests;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import space.Coord;
import space.Coord2D;
import space.Space2D;

public class Space2DTest {
	
	private Space2D uut;

	@Before
	public void setUp() throws Exception {
		uut = new Space2D(3,4);
	}

	@Test
	public void testIndexToCoord() {
		assertEquals("The coordinate should be good.",new Coord<Integer>(0,0),uut.indexToCoord(0));
		assertEquals("The coordinate should be good.",new Coord<Integer>(1,0),uut.indexToCoord(1));
		assertEquals("The coordinate should be good.",new Coord<Integer>(2,0),uut.indexToCoord(2));
		assertEquals("The coordinate should be good.",new Coord<Integer>(0,1),uut.indexToCoord(3));
		assertEquals("The coordinate should be good.",new Coord<Integer>(1,1),uut.indexToCoord(4));
		assertEquals("The coordinate should be good.",new Coord<Integer>(2,1),uut.indexToCoord(5));
		assertEquals("The coordinate should be good.",new Coord<Integer>(0,3),uut.indexToCoord(9));
		assertEquals("The coordinate should be good.",new Coord<Integer>(1,3),uut.indexToCoord(10));
		assertEquals("The coordinate should be good.",new Coord<Integer>(2,3),uut.indexToCoord(11));

	}
	
	@Test
	public void testCoordToIndex(){
		assertEquals("The index should be good.",0,uut.coordToIndex(new Coord2D<Integer>(0,0)));
		assertEquals("The index should be good.",1,uut.coordToIndex(new Coord<Integer>(1,0)));
		assertEquals("The index should be good.",2,uut.coordToIndex(new Coord<Integer>(2,0)));
		assertEquals("The index should be good.",3,uut.coordToIndex(new Coord<Integer>(0,1)));
		assertEquals("The index should be good.",4,uut.coordToIndex(new Coord<Integer>(1,1)));
		assertEquals("The index should be good.",5,uut.coordToIndex(new Coord<Integer>(2,1)));
		assertEquals("The index should be good.",9,uut.coordToIndex(new Coord<Integer>(0,3)));
		assertEquals("The index should be good.",10,uut.coordToIndex(new Coord<Integer>(1,3)));
		assertEquals("The index should be good.",11,uut.coordToIndex(new Coord<Integer>(2,3)));
	}
	
	@Test
	public void testGetVolume(){
		assertEquals("the volume should be 3*4.",12,uut.getVolume());
	}
	
	@Test
	public void testGetDimension(){
		assertEquals("The dimension shoulbe 3,4",Arrays.toString(new int[]{3,4}),Arrays.toString(uut.getDimensions()));
	}
	
	@Test
	public void testWrap(){
		assertEquals("The wrapping should be good",new Coord<Integer>(0,0),uut.wrapCoord(new Coord<Integer>(0,4)));
		assertEquals("The wrapping should be good",new Coord<Integer>(2,0),uut.wrapCoord(new Coord<Integer>(2,4)));
		assertEquals("The wrapping should be good",new Coord<Integer>(0,0),uut.wrapCoord(new Coord<Integer>(3,0)));
		assertEquals("The wrapping should be good",new Coord<Integer>(0,2),uut.wrapCoord(new Coord<Integer>(3,2)));
		assertEquals("The wrapping should be good",new Coord<Integer>(0,0),uut.wrapCoord(new Coord<Integer>(3,4)));

	}
	
	@Test
	public void testClone(){
		Space2D clone = uut.clone();
		assertEquals("The clone should be equals",uut,clone);
	}
	
	

}
