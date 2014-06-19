package tests;

import java.util.Arrays;

import junit.framework.TestCase;
import maps.NeighborhoodMap;
import maps.Unit;
import maps.UnitLeaf;
import maps.Var;
import neigborhood.V4Neighborhood2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import unitModel.GaussianND;
import unitModel.NeighborhoodUnitModel;
import unitModel.UnitModel;
import coordinates.DefaultRoundedSpace;
import coordinates.NullCoordinateException;
import coordinates.Space;
public class UnitTest extends TestCase {
	
	private Unit unit;
	private UnitModel um;
	Space space ;

	@Before
	public void setUp() throws Exception {
		space = new DefaultRoundedSpace(new Var(49), 2, true);
		 um = new GaussianND(new Var("dt",1), space, new Var("i",1), new Var("w",1), new Var(0),new Var(0));
		unit = new Unit(um);
		unit.setCoord(0d,0d);
	}

	@After
	public void tearDown() throws Exception {
		unit = null;
	}

	@Test
	public void testOnLine() throws Exception {
		assertTrue(um.compute()==1.0);
		unit.compute();
		assertTrue(unit.get()==1.0);
		unit.swap();
		assertTrue(unit.getCurrent()==0);
		assertTrue(unit.get()==1.0);
	}
	
	
	

	@Test
	public void testParallel() throws Exception {
		
		//Test compute online
		unit.toParallel();
		unit.compute();
		assertTrue(unit.get()==0); //Init activity of the GaussianND
		unit.swap();
		assertTrue(unit.get()==1); //Conputed activity
		
		unit.compute();
		assertTrue(unit.get()==1);
		unit.swap();
		assertTrue(unit.get()==1);
	}
	
	@Test
	public void testMemoriesOnline() throws Exception {
		
		UnitModel u = unit.getUnitModel().clone();
		u.set(10);
		
		unit.addMemories(4,u,u,u,u);
		//Test swap
		assertTrue(unit.getCurrent()==0);
		unit.swap();
		assertTrue(unit.getCurrent()==1);
		unit.swap();
		assertTrue(unit.getCurrent()==2);
		unit.swap();
		assertTrue(unit.getCurrent()==3);
		unit.swap();
		assertTrue(unit.getCurrent()==4);
		unit.swap();
		assertTrue(unit.getCurrent()==0);
		
		//Test compute online
		unit.onLine();
		assertTrue(unit.get()==0); //The init activity defined in GaussianND
		unit.compute();
		assertTrue(unit.get()==1);  //Computed activity
		
		unit.compute();
		assertTrue(unit.get()==1);
		assertTrue(unit.getCurrent()==2);
		//Test get memory
		assertTrue(unit.get(0)==1);
		assertTrue(unit.get(1)==1);
		assertTrue(unit.get(2)==0);//The init activity defined in GaussianND
		assertTrue(unit.get(3)==10);
		assertTrue(unit.get(4)==10);
	}
	@Test
	public void testMemoriesParallel() throws Exception {
		UnitModel u = unit.getUnitModel().clone();
		u.set(10);
		
		unit.addMemories(4,u,u,u,u);
		unit.toParallel();
		
		
		//Test compute
		unit.compute();
		assertTrue(unit.get()==0); //The init activity defined in GaussianND
		unit.swap();
		assertTrue(unit.get()==1);
		
		unit.compute();
		assertTrue(unit.get()==1);
		unit.swap();
		assertTrue(unit.get()==1);
		assertTrue(unit.getCurrent()==2);
		//Test get memory
		assertTrue(unit.get(0)==1);
		assertTrue(unit.get(1)==1);
		assertTrue(unit.get(2)==0);//The init activity defined in GaussianND
		assertTrue(unit.get(3)==10);
		assertTrue(unit.get(4)==10);
	}
	
	@Test
	public void testMemories2() throws Exception {
		UnitModel u = unit.getUnitModel().clone();
		u.set(10);
		
		unit.addMemories(4,u,u,u,u);
		System.out.println(unit);
		System.out.println("result : " + unit.get(0));
		assertTrue(unit.get(0) == 0);
		
	}
	
	@Test
	public void testClone(){
		Unit clone = unit.clone();
		assertTrue(clone.getUnitModel().hashCode() != um.hashCode());
		
		unit.toParallel();
		clone = unit.clone();
		assertTrue(clone.getUnitModel().hashCode() != um.hashCode());
		assertTrue(clone.getUnitModel(1).hashCode() != unit.getUnitModel(1).hashCode());
		
		assertTrue(clone.getUnitModel().hashCode() != um.hashCode());
		assertTrue(clone.getUnitModel(1).hashCode() != unit.getUnitModel(1).hashCode());
	}
	
	@Test
	public void testCloneNeighbour(){
		Space space = new DefaultRoundedSpace(new Var(3), 2, true);
		NeighborhoodUnitModel num = new NeighborhoodUnitModel(new Var(0.1),space) {
			
			@Override
			public double compute() throws NullCoordinateException {
				return unit.get() -1; 
			}
		};
		NeighborhoodMap map = new NeighborhoodMap("map",num);
		map.addNeighboors(new V4Neighborhood2D(space, new UnitLeaf(map)));
		map.toParallel();
	
		NeighborhoodMap mapClone = map.clone();
		
		Unit nu = map.getUnit(1);
		Unit clone = mapClone.getUnit(1);
		
		NeighborhoodUnitModel nnum = (NeighborhoodUnitModel) nu.getUnitModel();
		NeighborhoodUnitModel cnum = (NeighborhoodUnitModel) clone.getUnitModel();
		
		
		//Assert that the neighbourhood is good
		assertTrue(map.getNeighborhoods().hashCode() != mapClone.getNeighborhoods().hashCode());
		
		//Asert that the linked map of neighborhood are different
		assertTrue(((UnitLeaf)map.getNeighborhoods().get(0).getMap()).getMap().hashCode() != ((UnitLeaf)mapClone.getNeighborhoods().get(0).getMap()).getMap().hashCode());
		assertTrue(((UnitLeaf)map.getNeighborhoods().get(0).getMap()).getMap().hashCode() == map.hashCode());
		assertTrue(((UnitLeaf)mapClone.getNeighborhoods().get(0).getMap()).getMap().hashCode() == mapClone.hashCode());
		
		//For original
		System.out.println(Arrays.toString(nnum.getNeighborhood(0)));
		//North should be 7
		assertTrue(nnum.getNeighborhood(0)[0].hashCode() == map.getUnit(7).hashCode());
		//East should be 1,0 => 1
		assertTrue(nnum.getNeighborhood(0)[2].hashCode() == map.getUnit(2).hashCode());
		
		//For clone
		System.out.println(Arrays.toString(cnum.getNeighborhood(0)));
		//North should be 0,2 => 7
		assertTrue(cnum.getNeighborhood(0)[0].hashCode() == mapClone.getUnit(7).hashCode());
		//East should be 1,0 => 2
		assertTrue(cnum.getNeighborhood(0)[2].hashCode() == mapClone.getUnit(2).hashCode());
		
		//Assert the unit attributes are good
		assertTrue(nu.getCurrent() == clone.getCurrent());
		assertTrue(Arrays.equals(nu.getCoord(),clone.getCoord()));
		
		
		
		System.out.println(((NeighborhoodUnitModel) clone.getUnitModel()).getNeighborhood().hashCode() +"=="+
				((NeighborhoodUnitModel) clone.getUnitModel(1)).getNeighborhood().hashCode());
		
		//Clone and nu should be different
		assertTrue(nu.hashCode() != clone.hashCode());
		
		//Memory has the same neighboor List
		assertTrue(((NeighborhoodUnitModel) clone.getUnitModel()).getNeighborhood().hashCode() == 
				((NeighborhoodUnitModel) clone.getUnitModel(1)).getNeighborhood().hashCode());
		assertTrue(((NeighborhoodUnitModel) clone.getUnitModel()).getNeighborhood().get(0).hashCode() == 
				((NeighborhoodUnitModel) clone.getUnitModel(1)).getNeighborhood().get(0).hashCode());
		
		//But each clone has a different neighboorList than the original
		System.out.println(((NeighborhoodUnitModel) clone.getUnitModel()).getNeighborhood().hashCode() +"=="+
				((NeighborhoodUnitModel) nu.getUnitModel()).getNeighborhood().hashCode());
		assertFalse(((NeighborhoodUnitModel) clone.getUnitModel()).getNeighborhood().hashCode() == 
				((NeighborhoodUnitModel) nu.getUnitModel()).getNeighborhood().hashCode());
		
		//Each UM should refer to the good unit
		assertTrue(((NeighborhoodUnitModel) clone.getUnitModel()).getUnit().hashCode() == clone.hashCode());
		assertTrue(((NeighborhoodUnitModel) clone.getUnitModel(1)).getUnit().hashCode() == clone.hashCode());
		assertTrue(((NeighborhoodUnitModel) nu.getUnitModel()).getUnit().hashCode() == nu.hashCode());
		assertTrue(((NeighborhoodUnitModel) nu.getUnitModel(1)).getUnit().hashCode() == nu.hashCode());
		
		
		
	}


}
