package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import maps.Map;
import maps.Matrix;
import maps.NeighborhoodMap;
import maps.UnitLeaf;
import maps.Var;
import neigborhood.V4Neighborhood2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cellularAutomata.CACellUnitModel;
import cellularAutomata.PRNGUnitModel;
import cellularAutomata.PRNGWrapperUM;
import coordinates.DefaultRoundedSpace;
import coordinates.RoundedSpace;
import coordinates.Space;

public class PRNGWrapperUMTest {
	
	private PRNGUnitModel um;
	private Map map;
	private NeighborhoodMap camap;
	private PRNGWrapperUM wrapUM;
	private Map wrapMap;

	@Before
	public void setUp() throws Exception {
		
		Var dt = new Var("dt",0.1);
		Space spaceCA = new DefaultRoundedSpace(new Var("res",8), 2, true);

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
		Matrix rulesMat = new Matrix("RulesMat",dt , spaceCA, rules);
		CACellUnitModel caum = new CACellUnitModel(new Var("dt",0.1), spaceCA,rulesMat );
		camap = new NeighborhoodMap("CAMap", caum);
		camap.addNeighboors(new V4Neighborhood2D(spaceCA, new UnitLeaf(camap)));
		camap.toParallel();
		//System.out.println("camap : " + camap.hashCode());
		//System.out.println("Camap neigh : " +camap.getNeighborhood().get(0).getMap());

		Space space = new DefaultRoundedSpace(new Var("res",3), 2, true);
		Space prngSpace = new RoundedSpace(new Double[]{0d,0d}, new Double[]{3d,7d}, new Var(7), false);
		
		Var frac = new Var("Frac",6);
		Var nbVal = new Var("nb_val",10);
		
		um = new PRNGUnitModel(camap,dt, prngSpace,frac );
		map = new Map("PRNGMap", um);
		map.constructMemory();
		
		wrapUM = new PRNGWrapperUM(dt, space, map,frac,nbVal);
		wrapMap = new Map("Map wrap",wrapUM);
		wrapMap.constructMemory();
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		NeighborhoodMap camap0 = (NeighborhoodMap) map.getUnit(0).getUnitModel().getParam(PRNGUnitModel.MAP);
		camap0.setIndex(0,1d);
		wrapMap.update(0.1);
//		System.out.println(((PRNGWrapperUM) wrapMap.getUnit(0).getUnitModel()).getRandomNumber());
//		System.out.println(((PRNGWrapperUM) wrapMap.getUnit(0).getUnitModel()).getRandomNumber());
//		System.out.println(((PRNGWrapperUM) wrapMap.getUnit(0).getUnitModel()).getRandomNumber());
//		System.out.println(((PRNGWrapperUM) wrapMap.getUnit(0).getUnitModel()).getRandomNumber());
//		System.out.println(((PRNGWrapperUM) wrapMap.getUnit(0).getUnitModel()).getRandomNumber());
//		System.out.println(((PRNGWrapperUM) wrapMap.getUnit(0).getUnitModel()).getRandomNumber());
//		System.out.println(((PRNGWrapperUM) wrapMap.getUnit(0).getUnitModel()).getRandomNumber());
//		System.out.println(((PRNGWrapperUM) wrapMap.getUnit(0).getUnitModel()).getRandomNumber());
//		System.out.println(((PRNGWrapperUM) wrapMap.getUnit(0).getUnitModel()).getRandomNumber());
//		System.out.println(((PRNGWrapperUM) wrapMap.getUnit(0).getUnitModel()).getRandomNumber());
//		
		
		assertTrue(((PRNGWrapperUM) wrapMap.getUnit(0).getUnitModel()).getRandomNumber() == 0.84375);
		assertTrue(((PRNGWrapperUM) wrapMap.getUnit(0).getUnitModel()).getRandomNumber() == 0.859375);
		assertTrue(((PRNGWrapperUM) wrapMap.getUnit(0).getUnitModel()).getRandomNumber() == 0.5);
		assertTrue(((PRNGWrapperUM) wrapMap.getUnit(0).getUnitModel()).getRandomNumber() == 0.953125);
		assertTrue(((PRNGWrapperUM) wrapMap.getUnit(0).getUnitModel()).getRandomNumber() == 0.296875);
		assertTrue(((PRNGWrapperUM) wrapMap.getUnit(0).getUnitModel()).getRandomNumber() == 0.375);
		assertTrue(((PRNGWrapperUM) wrapMap.getUnit(0).getUnitModel()).getRandomNumber() == 0.359375);
		assertTrue(((PRNGWrapperUM) wrapMap.getUnit(0).getUnitModel()).getRandomNumber() == 0.5625);
		assertTrue(((PRNGWrapperUM) wrapMap.getUnit(0).getUnitModel()).getRandomNumber() == 0.59375);
		assertTrue(((PRNGWrapperUM) wrapMap.getUnit(0).getUnitModel()).getRandomNumber() == 0.359375);
		
		
		
	}

}
