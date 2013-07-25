package tests;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import maps.Map;
import maps.Parameter;
import maps.Var;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import unitModel.ConstantUnit;
import unitModel.UnitModel;
import coordinates.DefaultRoundedSpace;
import coordinates.Space;

public class MapTest {
	
	private Map map;

	@Before
	public void setUp() throws Exception {
		Space space = new DefaultRoundedSpace(new Var(3), 2, true);
		UnitModel um = new ConstantUnit(new Var(9));
		Parameter p = new Var("Param",99);
		map = new Map("map",um,new Var(0.1),space,new Var(0.1),p);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testClone() {
		System.out.println("Map UM : " + map.getUnitModel().hashCode());
		
		Map clone = map.clone();
		System.out.println("clone UM : " + clone.getUnitModel().hashCode());
		
		assertTrue(map.getUnitModel().hashCode() != clone.getUnitModel().hashCode());
		
	}
	
	@Test
	public void testCloneParameter(){
		Parameter ori = map.getParam(0);
		System.out.println("param ori " +map.getParam(0).hashCode());
		map.cloneParameter(0);
		Parameter cloned = map.getParam(0);
		System.out.println("param cloned " +map.getParam(0).hashCode());
		assertFalse(ori.hashCode() == cloned.hashCode());
		
		
	}
	
	@Test
	public void testCloneMemory() {
		map.constructMemory();
		System.out.println("Map UM : " + map.getUnit(0).getUnitModel().hashCode());
		
		Map clone = map.clone();
		System.out.println("clone UM : " + clone.getUnit(0).getUnitModel().hashCode());
		
		assertTrue(map.getUnit(0).getUnitModel().hashCode() != clone.getUnit(0).getUnitModel().hashCode());
		
	}

}
