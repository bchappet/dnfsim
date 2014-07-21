package test.java.space;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;

import main.java.space.Coord;
import main.java.space.Coord2D;

import org.junit.Before;
import org.junit.Test;

public class Coord2DTest {
	
	
	private Coord2D<Integer> uut;
	@Before
	public void setUp(){
		uut = new Coord2D<Integer>(1,2);
	}

	@Test
	public void testConstructionWithArray() {
		assertEquals("Les coordonées doivent être construte correctement","1,2",uut.toString());
		assertEquals("The value list should be correct",new ArrayList<Integer>(Arrays.asList(new Integer[]{1,2})),uut.getValues());
	}
	
	@Test
	public void testConstructionWithList() {
		uut = new Coord2D<Integer>(new ArrayList(Arrays.asList(new Integer[]{1,2})));
		assertEquals("Les coordonées doivent être construte correctement","1,2",uut.toString());
		assertEquals("The value list should be correct",new ArrayList<Integer>(Arrays.asList(new Integer[]{1,2})),uut.getValues());
	}
	
	@Test
	public void testGetIndex(){
		assertEquals("getIndex should give the good value",new Integer(2),uut.getIndex(1));
		assertEquals("getIndex should give the good value",new Integer(1),uut.getIndex(0));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testGetIndexoutofBoud(){
		assertEquals("exception risen",new Integer(3),uut.getIndex(2));
	}
	
	@Test
	public void testSpecificPublicField(){
		assertEquals("X value should be 1",new Integer(1),uut.x);
		assertEquals("Y value should be 2",new Integer(2),uut.y);
	}
	
	@Test
	public void testModificationSpecificPublicField(){
		uut.x = 3;
		uut.y = 4;
		assertEquals("X value after modification should be 3",new Integer(3),uut.x);
		assertEquals("Y value after modification should be 4",new Integer(4),uut.y);
		
		assertEquals("The value list should be correct even after field modification"
				,new ArrayList<Integer>(Arrays.asList(new Integer[]{3,4})),uut.getValues());
	}
	
	@Test
	public void testSet(){
		uut.set(0, 10);
		assertEquals("setIndex should change the value",new Integer(10),uut.getIndex(0));
		uut.set(1, 10);
		assertEquals("setIndex should change the value",new Integer(10),uut.getIndex(1));
		
	}
	
	@Test
	public void testSize(){
		assertEquals("The size should be 2",2,uut.getSize());
	}
	
	@Test
	public void testEquals(){
		assertEquals("The two coord must be equals:",uut,new Coord2D<Integer>(1,2));
		assertNotEquals("The two coord must be differents:",uut,new Coord2D<Integer>(1,3));
		assertEquals("The two coord must be equals:",uut,new Coord<Integer>(1,2));
	}
	
	@Test
	public void testClone(){
		Coord2D<Integer> newCoord = uut.clone();
		assertEquals("The to coord should be equals in the framework sence (equals method overriden).",uut,newCoord);
		assertNotEquals("But their list ref should be different", System.identityHashCode(uut.getValues()),System.identityHashCode(newCoord.getValues()));
		assertEquals("And their Object should be equals", System.identityHashCode(uut.getValues().get(0)),System.identityHashCode(newCoord.getValues().get(0)));
	}
	
	
	
	
	

}
