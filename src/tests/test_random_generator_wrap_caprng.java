package tests;

import static org.junit.Assert.*;

import java.util.Arrays;

import junit.framework.TestCase;

import hardSimulator.RandomGeneratorCAPRNGHUM;
import hardSimulator.RandomGeneratorHUM;

import maps.Map;
import maps.Matrix;
import maps.NeighborhoodMap;
import maps.Parameter;
import maps.UnitLeaf;
import maps.Var;
import neigborhood.V4Neighborhood2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import console.CNFTCommandLine;
import console.CommandLineFormatException;

import cellularAutomata.CACellUnitModel;
import cellularAutomata.PRNGUnitModel;
import cellularAutomata.PRNGWrapperUM;
import coordinates.DefaultRoundedSpace;
import coordinates.RoundedSpace;
import coordinates.Space;

public class test_random_generator_wrap_caprng  extends TestCase {

	private RandomGeneratorCAPRNGHUM um;
	private Map wrapMap;
	private Var clk;
	@Before
	public void setUp() throws Exception {

		clk = new Var("clk",0.02);
		Var nbVal = new Var("nb_val",10);
		Space space2d = new DefaultRoundedSpace(new Var(3), 2, false);

		wrapMap = getCAPRNGWrapperMap(clk,space2d,nbVal);

		this.um = new RandomGeneratorCAPRNGHUM(clk, space2d, wrapMap, nbVal);

		Map hardMap = new Map("HardMap",this.um);
		hardMap.toParallel();
		this.um = (RandomGeneratorCAPRNGHUM) hardMap.getUnit(0).getUnitModel();







	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {

		wrapMap.update(clk.get() * 1);
		um.computeActivity();

		System.out.println(Arrays.toString(um.getOutputs()));
		assertTrue(Arrays.equals(um.getOutputs(), new double[]{0.85546875, 0.8642578125, 0.505859375, 0.9638671875, 0.3017578125, 0.3837890625, 0.3642578125, 0.5703125, 0.544921875, 0.3330078125}));

		wrapMap.update(clk.get()*2);
		um.computeActivity();

		System.out.println(Arrays.toString(um.getOutputs()));
		assertTrue(Arrays.equals(um.getOutputs(), new double[]{0.623046875, 0.2236328125, 0.232421875, 0.23046875, 0.9091796875, 0.11328125, 0.8212890625, 0.3828125,0.591796875, 0.9599609375}));




	}


	protected Map getCAPRNGWrapperMap(Parameter dtHard,Space space2d,Parameter nbVal) throws CommandLineFormatException{

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
		Space spaceCA = new DefaultRoundedSpace(new Var("res",8), 2, true);

		Matrix rulesMat = new Matrix("RulesMat",dtHard , spaceCA, rules);
		CACellUnitModel caum = new CACellUnitModel(new Var("dt",0.1), spaceCA,rulesMat );
		NeighborhoodMap camap = new NeighborhoodMap("CAMap", caum);
		camap.addNeighboors(new V4Neighborhood2D(spaceCA, new UnitLeaf(camap)));
		camap.toParallel();
		//System.out.println("camap : " + camap.hashCode());
		//System.out.println("Camap neigh : " +camap.getNeighborhood().get(0).getMap());

		Var frac = new Var("Frac",10);

		//We need a specific amount of 8x8 map:
		double res = Math.max(Math.ceil((frac.get() * space2d.getResolution())/8), 
				Math.ceil((nbVal.get()* space2d.getResolution())/8));
		Space prngSpace = new RoundedSpace(new Double[]{0d,0d}, new Double[]{res,res}, new Var(res), false);



		PRNGUnitModel um = new PRNGUnitModel(camap,dtHard, prngSpace,frac );
		Map map = new Map("PRNGMap", um);
		map.constructMemory();

		PRNGWrapperUM wrapUM = new PRNGWrapperUM(dtHard, space2d, map,frac,nbVal);
		Map wrapMap = new Map("WrapPRNGMap",wrapUM);
		wrapMap.constructMemory();

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

		return wrapMap;
	}

}
