package tests;

import static org.junit.Assert.assertTrue;
import maps.AbstractUnitMap;
import maps.Leaf;
import maps.Map;
import maps.Matrix;
import maps.NeighborhoodMap;
import maps.UnitLeaf;
import maps.Var;
import neigborhood.V4Neighborhood2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import unitModel.NeighborhoodUnitModel;
import cellularAutomata.CACellUnitModel;
import cellularAutomata.PRNGUnitModel;
import coordinates.DefaultRoundedSpace;
import coordinates.Space;

public class PRNGUnitModelTest {

	private PRNGUnitModel um;
	private Map map;
	private NeighborhoodMap camap;

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
		System.out.println("camap : " + camap.hashCode());
		System.out.println("Camap neigh : " +camap.getNeighborhood().get(0).getMap());

		Space space = new DefaultRoundedSpace(new Var("res",3), 2, true);
		um = new PRNGUnitModel(camap,dt, space, new Var("Farc",8));
		map = new Map("PRNGMap", um);
		map.constructMemory();
	}



	@After
	public void tearDown() throws Exception {
	}
	
	
	//ok
	public void testCA(){
		camap.setIndex(0,1d);
		System.out.println(camap.display2D());
		camap.compute();
		System.out.println(camap.display2D());
		camap.compute();
		System.out.println(camap.display2D());
		camap.compute();
		System.out.println(camap.display2D());
		camap.compute();
		System.out.println(camap.display2D());

	}
//	@Test
	public void testCA2(){
		
		
		
		System.out.println("camap units");
		for(int i = 0 ; i < camap.getSpace().getDiscreteVolume() ; i++)
			System.out.print(" @"+camap.getUnit(i).hashCode());
		System.out.println();
		
		
		NeighborhoodMap camap0 = (NeighborhoodMap) map.getUnit(0).getUnitModel().getParam(PRNGUnitModel.MAP);
		System.out.println("camap0 units");
		for(int i = 0 ; i < camap0.getSpace().getDiscreteVolume() ; i++)
			System.out.print(" @"+camap0.getUnit(i).hashCode());
		System.out.println();
		
		System.out.println("east of 0 : " + ((NeighborhoodUnitModel)camap0.getUnit(0).getUnitModel()).getNeighborhood(0)[0]);
		
		NeighborhoodMap camap1 = (NeighborhoodMap) map.getUnit(1).getUnitModel().getParam(PRNGUnitModel.MAP);
		NeighborhoodMap camap2 = (NeighborhoodMap) map.getUnit(2).getUnitModel().getParam(PRNGUnitModel.MAP);
		System.out.println("camap1 units");
		for(int i = 0 ; i < camap1.getSpace().getDiscreteVolume() ; i++)
			System.out.print(" @"+camap1.getUnit(i).hashCode());
		System.out.println();
		
		System.out.println("camap :  @"+camap.hashCode());
		
		
		System.out.println(" map camap  " +camap.hashCode());
		System.out.println(" map camap0 " +camap0.hashCode());
		System.out.println(" map camap1 " +camap1.hashCode());
		System.out.println(" map camap2 " +camap2.hashCode());
		
		System.out.println("linked map camap  " +((Leaf)camap.getNeighborhood().get(0).getMap()).getMap().hashCode());
		System.out.println("linked map camap0 " +((Leaf)camap0.getNeighborhood().get(0).getMap()).getMap().hashCode());
		System.out.println("linked map camap1 " +((Leaf)camap1.getNeighborhood().get(0).getMap()).getMap().hashCode());
		System.out.println("linked map camap2 " +((Leaf)camap2.getNeighborhood().get(0).getMap()).getMap().hashCode());
		
		System.out.println("linked neigh camap  " +camap.getNeighborhood().get(0).hashCode());
		System.out.println("linked neigh camap0 " +camap0.getNeighborhood().get(0).hashCode());
		System.out.println("linked neigh camap1 " +camap1.getNeighborhood().get(0).hashCode());
		System.out.println("linked neigh camap2 " +camap2.getNeighborhood().get(0).hashCode());
		
		
		
		camap0.setIndex(0,1d);
		System.out.println(camap0.display2D());
		camap0.compute();
		System.out.println(camap0.display2D());
		//		camap2.compute();
		//		System.out.println(camap2.display2D());
		//		camap2.compute();
		//		System.out.println(camap2.display2D());
		//		camap2.compute();
		//		System.out.println(camap2.display2D());

	}

	public void testCompute() {


		for(int i = 0 ; i < map.getSpace().getDiscreteVolume() ; i++){
			map.getUnit(i).getUnitModel().getParam(PRNGUnitModel.MAP).setIndex(i,1d);
			//			System.out.println(((AbstractUnitMap) map.getUnit(i).getUnitModel().getParam(PRNGUnitModel.MAP)).hashCode());
			//			System.out.println(((AbstractUnitMap) map.getUnit(i).getUnitModel().getParam(PRNGUnitModel.MAP)).display2D());
		}


		//System.out.println(map.display2D());
		System.out.println(((AbstractUnitMap) map.getUnit(0).getUnitModel().getParam(PRNGUnitModel.MAP)).display2D());
		map.update(0.1);
		System.out.println(map.display2D());
		System.out.println(((AbstractUnitMap) map.getUnit(0).getUnitModel().getParam(PRNGUnitModel.MAP)).display2D());
		map.update(0.2);
		System.out.println(map.display2D());
		System.out.println(((AbstractUnitMap) map.getUnit(0).getUnitModel().getParam(PRNGUnitModel.MAP)).display2D());
		map.update(0.3);
		System.out.println(map.display2D());
		System.out.println(((AbstractUnitMap) map.getUnit(0).getUnitModel().getParam(PRNGUnitModel.MAP)).display2D());
	}
	
	//@Test
	public void cloneTest(){
		PRNGUnitModel clone = um.clone();
		assertTrue(clone.getParam(PRNGUnitModel.MAP).hashCode() != um.getParam(PRNGUnitModel.MAP).hashCode());
		
		NeighborhoodMap caMap = (NeighborhoodMap) um.getParam(PRNGUnitModel.MAP);
		NeighborhoodMap caMapClone = (NeighborhoodMap) clone.getParam(PRNGUnitModel.MAP);
		assertTrue(clone.getParam(PRNGUnitModel.MAP).hashCode() != um.getParam(PRNGUnitModel.MAP).hashCode());
		
		
		
		System.out.println("Comparing neigh maps");
		System.out.println(((Leaf)caMap.getNeighborhood().get(0).getMap()).getMap().hashCode() + " == " + caMap.hashCode());
		assertTrue(((Leaf)caMap.getNeighborhood().get(0).getMap()).getMap().hashCode() == caMap.hashCode());
		
		System.out.println(((Leaf)caMapClone.getNeighborhood().get(0).getMap()).getMap().hashCode() + " == " + caMapClone.hashCode());
		assertTrue(((Leaf)caMapClone.getNeighborhood().get(0).getMap()).getMap().hashCode() == caMapClone.hashCode());
		
	}

	@Test
	public void testGetRandomNumber() {
		
		NeighborhoodMap camap0 = (NeighborhoodMap) map.getUnit(0).getUnitModel().getParam(PRNGUnitModel.MAP);
		camap0.setIndex(0,1d);
		map.update(0.1);
		System.out.println(camap0.display2D());
		System.out.println(map.display2D());
		map.update(0.2);
		System.out.println(camap0.display2D());
		System.out.println(map.display2D());
		
//		System.out.println(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getRandomNumber());
//		System.out.println(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getRandomNumber());
//		System.out.println(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getRandomNumber());
//		System.out.println(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getRandomNumber());
//		System.out.println(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getRandomNumber());
//		System.out.println(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getRandomNumber());
//		System.out.println(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getRandomNumber());
//		System.out.println(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getRandomNumber());
		
		assertTrue(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getRandomNumber() == 0.62109375);
		assertTrue(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getRandomNumber() == 0.22265625);
		assertTrue(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getRandomNumber() == 0.23046875);
		assertTrue(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getRandomNumber() == 0.23046875);
		assertTrue(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getRandomNumber() == 0.90625);
		assertTrue(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getRandomNumber() == 0.11328125);
		assertTrue(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getRandomNumber() == 0.8203125);
		assertTrue(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getRandomNumber() == 0.3828125);
		assertTrue(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getRandomNumber() == 0.62109375);
		
		
		
	}
	
	@Test
	public void testGetIndex(){

		NeighborhoodMap camap0 = (NeighborhoodMap) map.getUnit(0).getUnitModel().getParam(PRNGUnitModel.MAP);
		camap0.setIndex(0,1d);
		map.update(0.1);
		map.update(0.2);
		assertTrue(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getIndex(0) == 1);
		assertTrue(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getIndex(1) == 0);
		assertTrue(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getIndex(2) == 0);
		assertTrue(((PRNGUnitModel) map.getUnit(0).getUnitModel()).getIndex(3) == 1);
	}

}
