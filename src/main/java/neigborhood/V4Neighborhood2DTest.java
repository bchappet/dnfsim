package main.java.neigborhood;

import static org.junit.Assert.*;

import java.util.Arrays;

import main.java.maps.InfiniteDt;
import main.java.maps.Unit;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.space.DoubleSpace2D;
import main.java.space.Space2D;
import main.java.unitModel.UMWrapper;

import org.junit.Before;
import org.junit.Test;

public class V4Neighborhood2DTest {
	
	private V4Neighborhood2D<Double> neigh;
	private UnitMap map;

	@Before
	public void setUp() throws Exception {
		Var<Double> ori = new Var<Double>("ori",0d);
		Var<Double> length = new Var<Double>("length",1d);
		Var<Integer> res = new Var<Integer>("res",3);
		DoubleSpace2D space = new DoubleSpace2D(ori, ori, length, length, res);
		map = new UnitMap<>("Map", new InfiniteDt(), space, new UMWrapper<Double>(10.));
		neigh = new V4Neighborhood2D<Double>(space,map);
	}

	@Test
	public void testGetNeighborhood() {
		int[] res = neigh.getNeighborhood(2);
		System.out.println( Arrays.toString(res));
		assertTrue("The neighbourhood should be good",Arrays.equals(new int[]{-1,5,-1,1}, res));
	}
	
	@Test
	public void testGetNeighborhoodUnit() {
		
		int[] resi = neigh.getNeighborhood(4);
//		System.out.println("i " + Arrays.toString(resi));
		assertTrue("The neighbourhood should be good",Arrays.equals(new int[]{1,7,5,3}, resi));
		
		
		Unit[] res = neigh.getNeighborhoodUnits(4);
//		System.out.println("Units : " +  Arrays.toString(res));
		assertSame("The unit model on the north should be the index 1",map.getUnit(1) , res[V4Neighborhood2D.NORTH]);
		assertSame("The unit model on the north should be the index 1",map.getUnit(7) , res[V4Neighborhood2D.SOUTH]);
		assertSame("The unit model on the north should be the index 1",map.getUnit(5) , res[V4Neighborhood2D.EAST]);
		assertSame("The unit model on the north should be the index 1",map.getUnit(3) , res[V4Neighborhood2D.WEST]);
		
//		assertTrue("The neighbourhood should be good",Arrays.equals(new int[]{-1,5,-1,1}, res));
	}
	
	
	@Test
	public void testGetNeighborhood2() {
		Space2D space = new Space2D(3, 4);
		V4Neighborhood2D<Integer> neigh = new V4Neighborhood2D<Integer>(space);
		
		int[] res = neigh.getNeighborhood(0);
		System.out.println(Arrays.toString(res));
		assertTrue("The neighbourhood should be good",Arrays.equals(new int[]{-1,3,1,-1}, res));
		
		res = neigh.getNeighborhood(1);
		System.out.println(Arrays.toString(res));
		assertTrue("The neighbourhood should be good",Arrays.equals(new int[]{-1,4,2,0}, res));
		
		res = neigh.getNeighborhood(4);
		System.out.println(Arrays.toString(res));
		assertTrue("The neighbourhood should be good",Arrays.equals(new int[]{1,7,5,3}, res));
	}

}
