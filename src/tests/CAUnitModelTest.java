package tests;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import maps.Leaf;
import maps.Map;
import maps.Matrix;
import maps.NeighborhoodMap;
import maps.UnitLeaf;
import maps.Var;
import neigborhood.Neighborhood;
import neigborhood.V4Neighborhood2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.ArrayUtils;
import utils.Hardware;
import cellularAutomata.CACellUnitModel;
import coordinates.DefaultRoundedSpace;
import coordinates.Space;

public class CAUnitModelTest {

	private CACellUnitModel caum;
	private NeighborhoodMap map;

	@Before
	public void setUp() throws Exception {

		Var dt = new Var("dt",0.1);
		boolean wrap = true;
		Var res = new Var("res",8);
		Space space = new DefaultRoundedSpace(res, 2, wrap);

//		double[] rules = {
//				5,43,27,6,13,57,57,38,
//				59,21,61,10,32,25,0,37,
//				14,46,37,29,25,31,52,52,
//				34,61,44,43,4,63,46,2,
//				6,2,3,51,59,54,42,12,
//				47,61,63,36,33,24,45,5,
//				54,53,37,7,59,15,60,36,
//				55,28,25,19,44,22,49,39
//		};
		
		double[] rules = {
				35,15,15,35,47,17,58,46,
				27,37,30,41,56,39,23,60,
				60,17,23,29,29,10,26,43,
				61,36,57,62,25,41,42,13,
				29,50,4,9,63,39,26,52,
				6,52,58,5,28,11,54,21,
				31,60,11,35,43,46,29,52,
				30,20,19,38,6,22,41,31
		};
		Matrix rulesMat = new Matrix("RulesMat",dt , space, rules);
		caum = new CACellUnitModel(new Var("dt",0.1), space,rulesMat);
		map = new NeighborhoodMap("CAMap", caum);
		map.addNeighboors(new V4Neighborhood2D(space, new UnitLeaf(map)));
		map.constructMemory();
		map.toParallel();
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testCompute(){
			map.setIndex(0,1d);
			System.out.println(map.display2D());
			map.compute();
			System.out.println(map.display2D());
			map.compute();
			System.out.println(map.display2D());
			map.compute();
			System.out.println(map.display2D());
			map.compute();
			System.out.println(map.display2D());
		
	}

	@Test
	public void testRuleToArray() {
		int[] res = Hardware.toVector(20,6);
		assertTrue(Arrays.equals(res,ArrayUtils.reverse(new int[]{0,1,0,1,0,0})));

		res = Hardware.toVector(62,6);
		assertTrue(Arrays.equals(res,ArrayUtils.reverse(new int[]{1,1,1,1,1,0})));
	}

}
