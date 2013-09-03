package tests;

import static org.junit.Assert.*;
import maps.AbstractMap;
import maps.AbstractUnitMap;
import maps.Map;
import maps.Parameter;
import maps.Var;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import unitModel.CosTraj;
import unitModel.GaussianND;
import unitModel.RandTrajUnitModel;
import unitModel.Sum;
import unitModel.UnitModel;
import console.CommandLineFormatException;
import coordinates.DefaultRoundedSpace;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class TestInputParallel {
	
	AbstractMap input;
	Space space2d;
	Space noDimSpace;
	UnitModel um;

	@Before
	public void setUp() throws Exception {
		space2d = new DefaultRoundedSpace(new Var(3), 2, true);
		noDimSpace = space2d.clone();
		noDimSpace.setDimension(new int[]{0,0});
		initDefaultInput();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		input.toParallel();
		input.update(0.1);
		System.out.println(input.display2D());
		input.update(0.2);
		System.out.println(input.display2D());
		
		System.out.println(input.get(0));
		System.out.println(input.getDelay(1, 0));
		
		
	}
	
	@Test
	public void testAddParameter() {
		input.toParallel();
		
		Parameter p = null;
		try {
			
			input.addParameters(p = constructTrack("tck", 0, 2));
		} catch (NullCoordinateException e) {
			e.printStackTrace();
		} catch (CommandLineFormatException e) {
			e.printStackTrace();
		}
		
		AbstractUnitMap input_ = (AbstractUnitMap) input;
		assertTrue(input_.getParams().contains(p));
		assertTrue(input_.getUnitModel().getParams().contains(p));
		assertTrue(input_.getUnit(0).getUnitModel().getParams().contains(p));
	
		
		
	}
	
	protected void initDefaultInput() throws CommandLineFormatException, NullCoordinateException
	{

		//Construct noise map
		UnitModel noise = new RandTrajUnitModel(new Var(0.1),space2d,
				new Var(0),new Var(0));
		Map mNoise = new Map("Noise",noise);
		mNoise.constructMemory(); //otherwise the noise is changed at each computation step
		//Construct the input as a sum of theses params
		um = new Sum(new Var(0.1),space2d, mNoise);
		this.input = new Map("input",um);
		input.addParameters(constructTrack("tck", 0, 1));
		

	}
	
	/**
	 * TODO : not very nice addition of tracks eg. from 2 to 3. But removing and reconstructing
	 * every tracks would change the statistics and characteristics results 
	 * Construct a trck with specific name
	 * @param name
	 * @param nbTrack 
	 * @param num 
	 * @return
	 * @throws NullCoordinateException
	 * @throws CommandLineFormatException
	 */
	protected AbstractMap constructTrack(String name, int num, int nbTrack) throws NullCoordinateException, CommandLineFormatException{
		Map cx = new Map("CenterX_"+num,new CosTraj(new Var(0.1),noDimSpace,
				new Var("center",0),new Var("radius",0.3),new Var("period",36),new Var("phase",num/(double)nbTrack+0)){
//			@Override
//			public double compute() throws NullCoordinateException {
//				double ret =  params.get(CENTER).get()+params.get(RADIUS).get()*
//						cos(2*PI*(time.get()/params.get(PERIOD).get()-params.get(PHASE).get()));
//				System.out.println(time.get() + "==>" + ret+ "@"+time.hashCode());
//				return ret;
//			}
		}){
//			public void update(double timeLimit) throws NullCoordinateException{
//				super.update(timeLimit);
//				System.out.println(name + ": time : " + time.get() + "@"+time.hashCode());
//			}
		};
		Map cy = new Map("CenterY_"+num,new CosTraj(new Var(0.1),noDimSpace,
				new Var("center",0),new Var("radius",0.3),new Var("period",36),new Var("phase",num/(double)nbTrack + 0.25)));


		UnitModel track = new GaussianND(new Var(0.1),space2d,
				new Var(1), 
				new Var(0.1), 
				cx,cy);
		AbstractMap ret =  new Map(name,track);
		ret.constructMemory();//otherwise the position is changed at each computation step
		return ret;
	}

}
