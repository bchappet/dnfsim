package tests;

import static org.junit.Assert.fail;
import junit.framework.TestCase;
import maps.AbstractMap;
import maps.Map;
import maps.Var;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import console.CNFTCommandLine;
import console.CommandLineFormatException;

import unitModel.GaussianND;
import unitModel.RandTrajUnitModel;
import unitModel.Sum;
import unitModel.UnitModel;
import coordinates.DefaultRoundedSpace;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class BullesTest  extends TestCase {

	Map input;
	Var dt;
	Space nodim;
	Space sp;
	@Before
	public void setUp() throws Exception {

		sp = new DefaultRoundedSpace(new Var(50),2,true);
		nodim = sp.clone();
		nodim.setDimension(new int[]{0,0});
		dt = new Var("dt",0.1);
		Map noise = new Map("Noise", new RandTrajUnitModel(dt,nodim,new Var(0.1), new Var(0.1)));

		input = new Map("input",new Sum(dt,sp,noise));

	}

	/**
	 * Construct one distrcater map
	 * @param name
	 * @return
	 * @throws NullCoordinateException
	 * @throws CommandLineFormatException
	 */
	protected AbstractMap constructDistracter(String name) throws NullCoordinateException, CommandLineFormatException{
		//Construct distracters map
		Map cx2 = new Map("CenterX",new RandTrajUnitModel(dt,nodim,
				new Var(0),new Var(0.5)));
		Map cy2 = new Map("CenterY",new RandTrajUnitModel(dt,nodim,
				new Var(0),new Var(0.5)));
		cx2.constructMemory();
		cy2.constructMemory();

		UnitModel distr = new GaussianND(dt,sp,
				new Var(1), 
				new Var(0.1) ,
				cx2,cy2
				);
		Map mDistr = new Map(name,distr);
		mDistr.constructMemory(); //otherwise the position is changed at each computation step
		return mDistr;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws NullCoordinateException, CommandLineFormatException {
		//input.addParameters(new Var(99));

		AbstractMap distr =  constructDistracter("distr");

		input.addParameters(distr);
	}

}
