package test.java.space;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;

import main.java.space.Coord;

import org.junit.Before;
import org.junit.Test;

public class CoordTest {
	
	private Coord<Integer> uut;
	@Before
	public void setUp(){
		uut = new Coord<Integer>(1,2,3);
	}

	@Test
	public void testConstructionWithArray() {
		assertEquals("Les coordonées doivent être construte correctement","1,2,3",uut.toString());
		assertEquals("The value list should be correct",new ArrayList<Integer>(Arrays.asList(new Integer[]{1,2,3})),uut.getValues());
	}
	
	@Test
	public void testConstructionWithList() {
		uut = new Coord<Integer>(new ArrayList(Arrays.asList(new Integer[]{1,2,3})));
		assertEquals("Les coordonées doivent être construte correctement","1,2,3",uut.toString());
		assertEquals("The value list should be correct",new ArrayList<Integer>(Arrays.asList(new Integer[]{1,2,3})),uut.getValues());
	}
	
	@Test
	public void testGetIndex(){
		assertEquals("getIndex should give the good value",new Integer(2),uut.getIndex(1));
		assertEquals("getIndex should give the good value",new Integer(1),uut.getIndex(0));
		assertEquals("getIndex should give the good value",new Integer(3),uut.getIndex(2));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testGetIndexoutofBoud(){
		assertEquals("exception risen",new Integer(4),uut.getIndex(3));
	}
	
	@Test
	public void testSet(){
		uut.set(1, 10);
		assertEquals("The index value should be good after set.", new Integer(10),uut.getIndex(1));
		assertEquals("The value list should be correct after set.",new ArrayList<Integer>(Arrays.asList(new Integer[]{1,10,3})),uut.getValues());
	}
	
	@Test
	public void testGetSize(){
		assertEquals("the size should be 3",3,uut.getSize());
	}
	
	@Test
	public void testEquals(){
		assertEquals("The two coord must be equals:",new Coord<Integer>(1,2,3),uut);
		assertNotEquals("The two coord must be differents:",new Coord<Integer>(1,3,3),uut);
	}
	
	@Test
	public void testClone(){
		Coord<Integer> newCoord = uut.clone();
		assertEquals("The to coord should be equals in the framework sence (equals method overriden).",uut,newCoord);
		assertNotEquals("But their list ref should be different", System.identityHashCode(uut.getValues()),System.identityHashCode(newCoord.getValues()));
		assertEquals("And their Object should be equals", System.identityHashCode(uut.getValues().get(0)),System.identityHashCode(newCoord.getValues().get(0)));
	}

}
