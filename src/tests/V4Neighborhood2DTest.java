package tests;

import java.util.Arrays;

import maps.Var;
import neigborhood.V4Neighborhood2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import coordinates.DefaultRoundedSpace;
import coordinates.Space;

public class V4Neighborhood2DTest {
	
	private V4Neighborhood2D neighWrap;
	private V4Neighborhood2D neigh;
	private Space spaceWrap;
	private Space space;

	@Before
	public void setUp() throws Exception {
		spaceWrap = new DefaultRoundedSpace(new Var("res",4), 2, true);
		neighWrap = new V4Neighborhood2D(spaceWrap);
		
		space = new DefaultRoundedSpace(new Var("res",4), 2, false);
		neigh = new V4Neighborhood2D(space);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWrap() {
		System.out.println("==================Wrap================");
		double unit = spaceWrap.getSize()[0]/spaceWrap.getResolution();
		System.out.println("Unit : " + unit);
		Double[] discrCoor = {3d,3d};
		System.out.println("coor --> " + Arrays.toString(discrCoor));
		Double[][] res = neighWrap.getNeighborhood(spaceWrap.continuousProj(discrCoor));
		for(Double[] coor : res){
			System.out.println(Arrays.toString(spaceWrap.discreteProj(coor)));
		}
		//////////////////////////////////////////////////
		discrCoor = new Double[]{1d,1d};
		System.out.println("coor --> " + Arrays.toString(discrCoor));
		res = neighWrap.getNeighborhood(spaceWrap.continuousProj(discrCoor));
		for(Double[] coor : res){
			System.out.println(Arrays.toString(spaceWrap.discreteProj(coor)));
		}
	}
	
	@Test
	public void testNonWrap() {
		System.out.println("==================Non wrap==================");
		double unit = space.getSize()[0]/space.getResolution();
		System.out.println("Unit : " + unit);
		Double[] discrCoor = {3d,3d};
		System.out.println("coor --> " + Arrays.toString(discrCoor));
		Double[][] res = neigh.getNeighborhood(space.continuousProj(discrCoor));
		for(Double[] coor : res){
			System.out.println(Arrays.toString(space.discreteProj(coor)));
		}
		//////////////////////////////////////////////////
		discrCoor = new Double[]{1d,1d};
		System.out.println("coor --> " + Arrays.toString(discrCoor));
		res = neigh.getNeighborhood(space.continuousProj(discrCoor));
		for(Double[] coor : res){
			System.out.println(Arrays.toString(space.discreteProj(coor)));
		}
	}
	
	@Test
	public void testWrap50() {
		
		System.out.println("==================Wrap 50================");
		spaceWrap = new DefaultRoundedSpace(new Var("res",50), 2, true);
		neighWrap = new V4Neighborhood2D(spaceWrap);
		
		Double[] discrCoor = new Double[]{20d,20d};
		System.out.println("coor --> " + Arrays.toString(discrCoor));
		Double[][] res = neighWrap.getNeighborhood(spaceWrap.continuousProj(discrCoor));
		for(Double[] coor : res){
			System.out.println(Arrays.toString(spaceWrap.discreteProj(coor)));
		}
	}

}
