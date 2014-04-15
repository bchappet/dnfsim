package tests;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import junit.framework.TestCase;
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

public class MapTest  extends TestCase{
	
	private Map map;
	Space space;

	@Before
	public void setUp() throws Exception {
		space = new DefaultRoundedSpace(new Var("3",3), 2, true);
		UnitModel um = new ConstantUnit(new Var("9",9));
		Parameter p = new Var("99",99);
		map = new Map("map",um,new Var("0.1",0.1),space,new Var("0.1",0.1),p);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testClone() {
		//System.out.println("Map UM : " + map.getUnitModel().hashCode());
		
		Map clone = map.clone();
		//System.out.println("clone UM : " + clone.getUnitModel().hashCode());
		
		assertTrue(map.getUnitModel().hashCode() != clone.getUnitModel().hashCode());
		
	}
	
	@Test
	public void testDelete(){
		Var var1 = new Var("5",5);
		Map map1 = new Map("map1",new ConstantUnit(var1));
		map.addParameters(map1);
		
		System.out.println(map.toStringRecursive(0));
		
		map1.delete();
		
		System.out.println(map.toStringRecursive(0));
	}
	
	@Test
	public void testCloneMemory() {
		map.constructMemory();
		//System.out.println("Map UM : " + map.getUnit(0).getUnitModel().hashCode());
		
		Map clone = map.clone();
		//System.out.println("clone UM : " + clone.getUnit(0).getUnitModel().hashCode());
		
		assertTrue(map.getUnit(0).getUnitModel().hashCode() != clone.getUnit(0).getUnitModel().hashCode());
		
	}

}
