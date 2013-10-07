package tests;

import static org.junit.Assert.fail;
import maps.Leaf;
import maps.Map;
import maps.NeighborhoodMap;
import maps.UnitLeaf;
import maps.UnitParameter;
import maps.Var;
import neigborhood.Neighborhood;
import neigborhood.V4Neighborhood2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import unitModel.RateCodedUnitModel;
import unitModel.SomUM;
import unitModel.Sum;
import console.CNFTCommandLine;

import coordinates.DefaultRoundedSpace;
import coordinates.Space;
import fft.FFTConvolutionMatrix2D;

public class SomUMTest {
	
	
	Map cortical;
	Map potential;
	Space space;
	Var dt;
	Var dt_fast;
	@Before
	public void setUp() throws Exception {

		space = new DefaultRoundedSpace(new Var("res",3), 2, true);
		dt = new Var("dt",0.1);
		dt_fast = new Var("dt_fast",0.01);
		potential = new Map("pot",new RateCodedUnitModel(),dt_fast,space);
		cortical = new NeighborhoodMap("cort", new SomUM(space, dt, space));
		potential.addParameters(new Leaf(potential),new Var("tau",0.64),
				new Leaf(cortical),new Var("cnft",0),new Var("rest_pot",0d));
		cortical.addParameters(new Var("input",1d),potential,new Var("lr",1));
		Neighborhood neigh = new V4Neighborhood2D(space, new UnitLeaf((UnitParameter) cortical));
		((NeighborhoodMap)cortical).addNeighboors(neigh);
		
		potential.constructMemory();
		cortical.constructMemory();
	
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		System.out.println("cortical" + cortical.display2D());
		potential.update(dt.get()*1);
		System.out.println("cortical" + cortical.display2D());
		potential.update(dt.get()*1);
		System.out.println("cortical" + cortical.display2D());
		potential.update(dt.get()*1);
		
	}

}
