package tests;

import hardSimulator.RandomGeneratorCAPRNGHUM;

import java.math.BigDecimal;
import java.util.Arrays;

import junit.framework.TestCase;
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

public class test_random_generator_caprng extends TestCase {

	private RandomGeneratorCAPRNGHUM um;
	private Map wrapMap;
	private Var clk;
	@Before
	public void setUp() throws Exception {

		clk = new Var("clk",0.02);
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
		Matrix rulesMat = new Matrix("RulesMat",clk , spaceCA, rules);
		CACellUnitModel caum = new CACellUnitModel( clk , spaceCA,rulesMat );
		NeighborhoodMap camap = new NeighborhoodMap("CAMap", caum);
		camap.addNeighboors(new V4Neighborhood2D(spaceCA, new UnitLeaf(camap)));
		camap.toParallel();
		//System.out.println("camap : " + camap.hashCode());
		//System.out.println("Camap neigh : " +camap.getNeighborhood().get(0).getMap());

		Space space = new DefaultRoundedSpace(new Var("res",3), 2, true);
		Space prngSpace = new RoundedSpace(new Double[]{0d,0d}, new Double[]{3d,7d}, new Var(7), false);

		Var frac = new Var("Frac",8);
		Var nbVal = new Var("nb_val",8);

		PRNGUnitModel um = new PRNGUnitModel(camap,clk, prngSpace,frac );
		Map map = new Map("PRNGMap", um);
		map.constructMemory();


		PRNGWrapperUM wrapUM = new PRNGWrapperUM(clk, space, map,frac,nbVal);
		wrapMap = new Map("Map wrap",wrapUM);
		wrapMap.constructMemory();


		Space space2d = new DefaultRoundedSpace(new Var(2), 2, false);

		this.um = new RandomGeneratorCAPRNGHUM(clk, space2d, wrapMap, nbVal);

		Map hardMap = new Map("HardMap",this.um);
		hardMap.toParallel();
		this.um = (RandomGeneratorCAPRNGHUM) hardMap.getUnit(0).getUnitModel();


		//Different initialization state for the different ca map
		int nbOne=0;
		int posOne = 0;
		for(int i = 0 ; i < prngSpace.getDiscreteVolume() ; i++){
			NeighborhoodMap camap_i = (NeighborhoodMap) ((PRNGUnitModel) map.getUnit(i).getUnitModel()).getMap();

			for(int j = 0 ; j < nbOne ; j++)
				camap_i.setIndex(j,1);

			camap_i.setIndex(posOne, 1);

			posOne ++;

			if(posOne == spaceCA.getDiscreteVolume()){
				nbOne ++;
				posOne = nbOne;
			}
		}



	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {

		wrapMap.update(new BigDecimal(""+clk.get() * 1));
		um.computeActivity();

		System.out.println(Arrays.toString(um.getOutputs()));
		assertTrue(Arrays.equals(um.getOutputs(), new double[]{0.85546875, 0.86328125, 0.50390625, 0.9609375, 0.30078125, 0.3828125, 0.36328125, 0.5703125}));

		wrapMap.update(new BigDecimal(""+clk.get()*2));
		um.computeActivity();

		System.out.println(Arrays.toString(um.getOutputs()));
		assertTrue(Arrays.equals(um.getOutputs(), new double[]{0.62109375, 0.22265625, 0.23046875, 0.23046875, 0.90625, 0.11328125, 0.8203125, 0.3828125}));




	}

}
