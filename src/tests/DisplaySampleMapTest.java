package tests;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.util.Arrays;

import gui.DisplaySampleMap;
import maps.Var;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import coordinates.RoundedSpace;
import coordinates.Space;

public class DisplaySampleMapTest {
	
	/**2 dimension only**/
	public static final int X = 0;
	public static final int Y = 1;

	private Space space;
	private double sx,sy; //size (continuous) x ,y space
	private int dx,dy;
	
	
	@Before
	public void setUp() throws Exception {
		
		sx = 2;
		sy = 2;
		dx = 100;
		dy= 100;
		space = new RoundedSpace(new Double[]{-1d,-1d}, 
				new Double[]{sx,sy},new Var("res",50), true);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	


	@Test
	public void test() {
		
		double[] coord = new double[]{0,0};
		int[] res =  getCoord(coord);
		System.out.println(Arrays.toString(res));
		assertTrue(Arrays.equals(res,new int[]{50,50}));
		
		
		coord = new double[]{1,1};
		res =  getCoord(coord);
		System.out.println(Arrays.toString(res));
		assertTrue(Arrays.equals(res,new int[]{100,100}));
		
		coord = new double[]{-1,-1};
		res =  getCoord(coord);
		System.out.println(Arrays.toString(res));
		assertTrue(Arrays.equals(res,new int[]{0,0}));
		

	}

	/**
	 * Calculate pixel coordinate
	 * @param coord
	 */
	protected int[] getCoord(double[] coord) {
		System.out.println(space);
		double oriX = space.getOrigin()[X];
		double oriY = space.getOrigin()[Y];
		double factX = this.sx/dx;
		System.out.println(factX);
		int x = (int) Math.round(((coord[X] - oriX) / factX));

		double factY = this.sy/dy;
		int y = (int) Math.round(((coord[Y] - oriY) / factY));




		return new int[]{x,y};
	}

}
