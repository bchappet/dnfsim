package tests;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import junit.framework.TestCase;

import maps.Var;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import coordinates.DefaultRoundedSpace;
import coordinates.NullCoordinateException;
import coordinates.RoundedSpace;
import coordinates.Space;

public class RoundedSpaceTest extends TestCase {

	private Space space;

	@Before
	public void setUp() throws Exception {
		space = new DefaultRoundedSpace(new Var(50), 2,true);
	}

	@After
	public void tearDown() throws Exception {
		space = null;
	}

	@Test
	public void testCoordToIndex() throws NullCoordinateException
	{
		space = new DefaultRoundedSpace(new Var(4), 2,true);
		int[] dimension = {1,1};
		space.setDimension(dimension);

		Double[] coor = new Double[]{0d,0d};
		coor = space.continuousProj(coor);
		int index = space.coordToIndex(coor);
		assertTrue(index == 0);

		coor = new Double[]{2d,1d};
		coor = space.continuousProj(coor);
		index = space.coordToIndex(coor);
		assertTrue(index == 6);

		coor = new Double[]{1d,3d};
		coor = space.continuousProj(coor);
		index = space.coordToIndex(coor);
		assertTrue(index == 13);

		//3D
		space =  new DefaultRoundedSpace(new Var(4), 3,true);
		dimension = new int[]{1,1,1};
		space.setDimension(dimension);

		coor = new Double[]{0d,0d,0d};
		coor = space.continuousProj(coor);
		index = space.coordToIndex(coor);
		System.out.println("index : " + index);
		assertTrue(index == 0);

		coor = new Double[]{2d,2d,2d};
		coor = space.continuousProj(coor);
		index = space.coordToIndex(coor);
		System.out.println("index : " + index);
		assertTrue(index == 42);

		coor = new Double[]{1d,3d,1d};
		coor = space.continuousProj(coor);
		index = space.coordToIndex(coor);
		System.out.println("index : " + index);
		assertTrue(index == 29);

		coor = new Double[]{3d,3d,3d};
		coor = space.continuousProj(coor);
		index = space.coordToIndex(coor);
		System.out.println("index : " + index);
		assertTrue(index == 63);

		coor = new Double[]{1d,2d,3d};
		coor = space.continuousProj(coor);
		index = space.coordToIndex(coor);
		System.out.println("index : " + index);
		assertTrue(index == 57);

		//Weird 1D
		space =  new RoundedSpace(
				new Double[]{0d},new Double[]{2d},
				new Var(4),true);
		dimension = new int[]{1};
		space.setDimension(dimension);

		coor = new Double[]{2d};
		coor = space.continuousProj(coor);
		index = space.coordToIndex(coor);
		assertTrue(index == 2);

		//Weird 2D
		space =  new RoundedSpace(
				new Double[]{0d,0d},new Double[]{2d,2d},
				new Var(4),true);
		dimension = new int[]{1,1};
		space.setDimension(dimension);

		coor = new Double[]{2d,2d};
		coor = space.continuousProj(coor);
		index = space.coordToIndex(coor);
		assertTrue(index == 10);

		//Weird 3D
		space =  new RoundedSpace(
				new Double[]{0d,0d,0d},new Double[]{1d,2d,3d},
				new Var(4),true);
		dimension = new int[]{1,1,1};
		space.setDimension(dimension);

		coor = new Double[]{1d,2d,3d};
		coor = space.continuousProj(coor);
		System.out.println("!!!!!!!!!!!!coord : " + Arrays.toString(coor));
		index = space.coordToIndex(coor);
		System.out.println("!!!!!!!!!!!!index : " + index);
		assertTrue(index == 57);

		//4D
		space =  new DefaultRoundedSpace(new Var(4), 4,true);
		dimension = new int[]{1,1,1,1};
		space.setDimension(dimension);
		coor = new Double[]{0d,0d,0d,0d};
		coor = space.continuousProj(coor);

		index = space.coordToIndex(coor);
		System.out.println("index : " + index);
		assertTrue(index == 0);

		coor = new Double[]{3d,3d,3d,3d};
		coor = space.continuousProj(coor);
		index = space.coordToIndex(coor);
		System.out.println("index : " + index);
		assertTrue(index == 255);

		coor = new Double[]{0d,1d,2d,3d};
		coor = space.continuousProj(coor);
		index = space.coordToIndex(coor);
		System.out.println("index : " + index);
		assertTrue(index == 228);

		///////Sparse refSpace//////
		space =  new DefaultRoundedSpace(new Var(4), 4,true);

		//OD
		dimension = new int[]{0,0,0,0};
		space.setDimension(dimension);
		coor = new Double[]{2d,3d,4d,5d};
		coor = space.continuousProj(coor);
		index = space.coordToIndex(coor);
		System.out.println("index : " + index);
		assertTrue(index == 0);

		//1D
		dimension = new int[]{1,0,0,0};
		space.setDimension(dimension);
		coor = new Double[]{2d,3d,4d,5d};
		coor = space.continuousProj(coor);
		index = space.coordToIndex(coor);
		System.out.println("index : " + index);
		assertTrue(index == 2);

		dimension = new int[]{0,1,0,0};
		space.setDimension(dimension);
		coor = new Double[]{2d,3d,4d,5d};
		coor = space.continuousProj(coor);
		index = space.coordToIndex(coor);
		System.out.println("index : " + index);
		assertTrue(index == 3);

		dimension = new int[]{0,0,1,0};
		space.setDimension(dimension);
		coor = new Double[]{2d,3d,4d,5d};
		coor = space.continuousProj(coor);
		index = space.coordToIndex(coor);
		System.out.println("index : " + index);
		assertTrue(index == 4);

		dimension = new int[]{0,0,0,1};
		space.setDimension(dimension);
		coor = new Double[]{2d,3d,4d,5d};
		coor = space.continuousProj(coor);
		index = space.coordToIndex(coor);
		System.out.println("index : " + index);
		assertTrue(index == 5);

		//2D
		dimension = new int[]{1,1,0,0};
		space.setDimension(dimension);
		coor = new Double[]{1d,3d,4d,5d};
		coor = space.continuousProj(coor);
		index = space.coordToIndex(coor);
		System.out.println("index : " + index);
		assertTrue(index == 13);

		dimension = new int[]{0,1,0,1};
		space.setDimension(dimension);
		coor = new Double[]{2d,1d,4d,3d};
		coor = space.continuousProj(coor);
		index = space.coordToIndex(coor);
		System.out.println("index : " + index);
		assertTrue(index == 13);

		//3D
		dimension = new int[]{1,1,0,1};
		space.setDimension(dimension);
		coor = new Double[]{1d,3d,4d,1d};
		coor = space.continuousProj(coor);
		index = space.coordToIndex(coor);
		System.out.println("index : " + index);
		assertTrue(index == 29);

		dimension = new int[]{0,1,1,1};
		space.setDimension(dimension);
		coor = new Double[]{4d,1d,2d,3d};
		coor = space.continuousProj(coor);
		index = space.coordToIndex(coor);
		System.out.println("index : " + index);
		assertTrue(index == 57);



	}

	@Test
	public void testIndexToCoor() throws NullCoordinateException
	{
		space = new DefaultRoundedSpace(new Var(4), 2,true);
		int[] dimension = {1,1};
		space.setDimension(dimension);

		Double[] coor = new Double[]{0d,0d};
		int index = 0;
		Double[] res = space.indexToCoord(index);
		res = space.discreteProj(res);
		assertTrue(equals(res,coor));

		coor = new Double[]{2d,1d};
		index = 6;
		res = space.indexToCoord(index);
		System.out.println("res : " + tabString(res));
		res = space.discreteProj(res);
		assertTrue(equals(res,coor));

		coor = new Double[]{1d,3d};
		index = 13;
		res = space.indexToCoord(index);
		System.out.println("res : " + tabString(res));
		res = space.discreteProj(res);
		assertTrue(equals(res,coor));

		//3D
		space =  new DefaultRoundedSpace(new Var(4), 3,true);
		dimension = new int[]{1,1,1};
		space.setDimension(dimension);

		coor = new Double[]{0d,0d,0d};
		index = 0;
		res = space.indexToCoord(index);
		res = space.discreteProj(res);
		assertTrue(equals(res,coor));

		coor = new Double[]{2d,2d,2d};
		index = 42;
		res = space.indexToCoord(index);
		System.out.println("res : " + tabString(res));
		res = space.discreteProj(res);
		assertTrue(equals(res,coor));

		coor = new Double[]{1d,3d,1d};
		index = 29;
		res = space.indexToCoord(index);
		System.out.println("res : " + tabString(res));
		res = space.discreteProj(res);
		assertTrue(equals(res,coor));

		coor = new Double[]{3d,3d,3d};
		index = 63;
		res = space.indexToCoord(index);
		System.out.println("res : " + tabString(res));
		res = space.discreteProj(res);
		assertTrue(equals(res,coor));

		//Weird 3D
		space =  new RoundedSpace(new Double[]{0d,0d,0d},new Double[]{1d,2d,3d},new Var(4),true);
		dimension = new int[]{1,1,1};
		space.setDimension(dimension);
		coor = new Double[]{1d,2d,3d};
		index = 57;
		res = space.indexToCoord(index);
		res = space.discreteProj(res);
		System.out.println("!!!!!!!!res : " + tabString(res));
		assertTrue(equals(res,coor));

		//4D
		space =  new DefaultRoundedSpace(new Var(4), 4,true);
		dimension = new int[]{1,1,1,1};
		space.setDimension(dimension);
		coor = new Double[]{0d,0d,0d,0d};
		index = 0;
		res = space.indexToCoord(index);
		res = space.discreteProj(res);
		System.out.println("res : " + tabString(res));
		assertTrue(equals(res,coor));

		coor = new Double[]{3d,3d,3d,3d};
		index = 255;
		res = space.indexToCoord(index);
		res = space.discreteProj(res);
		System.out.println("res : " + tabString(res));
		assertTrue(equals(res,coor));

		coor = new Double[]{0d,1d,2d,3d};
		index = 228;
		res = space.indexToCoord(index);
		res = space.discreteProj(res);
		System.out.println("res : " + tabString(res));
		assertTrue(equals(res,coor));


		///////Sparse refSpace//////
		space =  new DefaultRoundedSpace(new Var(4), 4,true);

		//OD
		dimension = new int[]{0,0,0,0};
		space.setDimension(dimension);
		coor = new Double[]{null,null,null,null};
		index = 255;
		res = space.indexToCoord(index);
		res = space.discreteProj(res);
		System.out.println("res : " + tabString(res));
		assertTrue(equals(res,coor));

		//1D
		dimension = new int[]{1,0,0,0};
		space.setDimension(dimension);
		coor = new Double[]{2d,null,null,null};
		index = 2;
		res = space.indexToCoord(index);
		res = space.discreteProj(res);
		System.out.println("res : " + tabString(res));
		assertTrue(equals(res,coor));

		dimension = new int[]{0,1,0,0};
		space.setDimension(dimension);
		coor = new Double[]{null,3d,null,null};
		index = 3;
		res = space.indexToCoord(index);
		res = space.discreteProj(res);
		System.out.println("res : " + tabString(res));
		assertTrue(equals(res,coor));

		dimension = new int[]{0,0,1,0};
		space.setDimension(dimension);
		coor = new Double[]{null,null,2d,null};
		index = 2;
		res = space.indexToCoord(index);
		res = space.discreteProj(res);
		System.out.println("res : " + tabString(res));
		assertTrue(equals(res,coor));

		dimension = new int[]{0,0,0,1};
		space.setDimension(dimension);
		coor = new Double[]{null,null,null,3d};
		index = 3;
		res = space.indexToCoord(index);
		res = space.discreteProj(res);
		System.out.println("res : " + tabString(res));
		assertTrue(equals(res,coor));

		//2D
		dimension = new int[]{1,1,0,0};
		space.setDimension(dimension);
		coor = new Double[]{1d,3d,null,null};
		index = 13;
		res = space.indexToCoord(index);
		res = space.discreteProj(res);
		System.out.println("res : " + tabString(res));
		assertTrue(equals(res,coor));

		dimension = new int[]{0,1,0,1};
		space.setDimension(dimension);
		coor = new Double[]{null,1d,null,3d};
		index = 13;
		res = space.indexToCoord(index);
		res = space.discreteProj(res);
		System.out.println("res : " + tabString(res));
		assertTrue(equals(res,coor));

		//3D
		dimension = new int[]{1,1,0,1};
		space.setDimension(dimension);
		coor = new Double[]{1d,3d,null,1d};
		index = 29;
		res = space.indexToCoord(index);
		res = space.discreteProj(res);
		System.out.println("res : " + tabString(res));
		assertTrue(equals(res,coor));

		dimension = new int[]{0,1,1,1};
		space.setDimension(dimension);
		coor = new Double[]{null,1d,2d,3d};
		index = 57;
		res = space.indexToCoord(index);
		res = space.discreteProj(res);
		System.out.println("res : " + tabString(res));
		assertTrue(equals(res,coor));
	}

	@Test
	public void testContinuousProj() throws NullCoordinateException {


		space = new DefaultRoundedSpace(new Var(5), 2,true);
		assertTrue(RoundedSpace.round(space.continuousProj(new Double[]{0d,0d})[0],10) == -0.4);

		assertTrue(RoundedSpace.round(space.continuousProj(new Double[]{1d,0d})[0],10) == -0.2);
		assertTrue(RoundedSpace.round(space.continuousProj(new Double[]{2d,0d})[0],10) == 0);
		assertTrue(RoundedSpace.round(space.continuousProj(new Double[]{3d,0d})[0],10) == 0.2);
		assertTrue(RoundedSpace.round(space.continuousProj(new Double[]{4d,0d})[0],10) == 0.4);

		space = new DefaultRoundedSpace(new Var(4), 2,true);
		assertTrue(RoundedSpace.round(space.continuousProj(new Double[]{0d,0d})[0],10) == -0.375);
		assertTrue(RoundedSpace.round(space.continuousProj(new Double[]{1d,0d})[0],10) == -0.125);
		assertTrue(RoundedSpace.round(space.continuousProj(new Double[]{2d,0d})[0],10) == 0.125);
		assertTrue(RoundedSpace.round( space.continuousProj(new Double[]{3d,0d})[0],10) == 0.375);
	}



	@Test
	public void testContinuousProj2()
	{
		space = new DefaultRoundedSpace(new Var(5), 2,true);
		assertTrue(RoundedSpace.round(space.continuousProj(0, 0),10) == -0.4);
		assertTrue(RoundedSpace.round(space.continuousProj(1, 0),10) == -0.2);
		assertTrue(RoundedSpace.round(space.continuousProj(2, 0),10) == 0);
		assertTrue(RoundedSpace.round(space.continuousProj(3, 0),10) == 0.2);
		assertTrue(RoundedSpace.round(space.continuousProj(4, 0),10) == 0.4);

		space = new DefaultRoundedSpace(new Var(4), 2,true);
		assertTrue(RoundedSpace.round(space.continuousProj(0, 0),10) == -0.375);
		assertTrue(RoundedSpace.round(space.continuousProj(1, 0),10) == -0.125);
		assertTrue(RoundedSpace.round(space.continuousProj(2, 0),10) == 0.125);
		assertTrue(RoundedSpace.round( space.continuousProj(3, 0),10) == 0.375);


	}

	@Test
	public void testDistContinuousProj()
	{
		space = new DefaultRoundedSpace(new Var(5), 2,true);

		assertTrue( space.distContinuousProj(0, 0) == 0);
		assertTrue( space.distContinuousProj(1, 0) == 0.2);
		assertTrue( space.distContinuousProj(5, 0) == 1);
	}



	@Test
	public void testDiscreteProj() throws NullCoordinateException {
		space = new DefaultRoundedSpace(new Var(5), 2,true);
		assertTrue(RoundedSpace.round(space.discreteProj(new Double[]{-0.5d,0d})[0],10) == 0);
		assertTrue(RoundedSpace.round(space.discreteProj(new Double[]{-0.4d,0d})[0],10) == 0);
		assertTrue(RoundedSpace.round(space.discreteProj(new Double[]{-0.2d,0d})[0],10) == 1);
		assertTrue(RoundedSpace.round(space.discreteProj(new Double[]{0d,0d})[0],10) == 2);
		assertTrue(RoundedSpace.round(space.discreteProj(new Double[]{0.2d,0d})[0],10) == 3);
		assertTrue(RoundedSpace.round(space.discreteProj(new Double[]{0.4d,0d})[0],10) == 4);
		assertTrue(RoundedSpace.round(space.discreteProj(new Double[]{0.49d,0d})[0],10) == 4);

		space = new DefaultRoundedSpace(new Var(4), 2,true);
		assertTrue(RoundedSpace.round(space.discreteProj(new Double[]{-0.5d,0d})[0],10) == 0);
		assertTrue(RoundedSpace.round(space.discreteProj(new Double[]{-0.375d,0d})[0],10) == 0);
		assertTrue(RoundedSpace.round(space.discreteProj(new Double[]{-0.125d,0d})[0],10) == 1);
		assertTrue(RoundedSpace.round(space.discreteProj(new Double[]{0.125d,0d})[0],10) == 2);
		assertTrue(RoundedSpace.round(space.discreteProj(new Double[]{0.375d,0d})[0],10) == 3);
		assertTrue(RoundedSpace.round(space.discreteProj(new Double[]{0.49d,0d})[0],10) == 3);

		space = new DefaultRoundedSpace(new Var(50), 2, true);
		Double[] coord = {0.4387755102040817, 0.47959183673469385};
		System.out.println(Arrays.toString(space.discreteProj(coord)));
	}

	@Test
	public void testComposition()
	{

	}





	@Test
	public void testGetDimension() {
		//System.out.println("Dimenension : " + refSpace.getDimension());
		assertTrue(equals(space.getDiscreteSize(),new Integer[]{50,50}));
	}

	@Test
	public void testProject()
	{
		Space origin = new DefaultRoundedSpace(new Var(50), 2,true); 
		Space target = new RoundedSpace(new Double[]{0d,0d}, new Double[]{2d,2d},new Var(50),true);

		Double[] test = new Double[]{0d,0.5};
		Double[] res = origin.project(test, target);
		System.out.println("res : " + tabString(res));
		assertTrue(res[0] == 1 && res[1] == 2);
	}

	public boolean equals(Double[] a, Double[] b)
	{
		boolean res = true;
		for(int i = 0 ; i < a.length ; i++)
		{
			if(a[i] == null)
			{
				res &= (b[i] == null);
			}
			else
			{
				res &= (a[i].equals(b[i]));
			}
		}

		return res;
	}

	public boolean equals(Integer[] a, Integer[] b)
	{
		boolean res = true;
		for(int i = 0 ; i < a.length ; i++)
		{
			res &= (a[i].equals(b[i]));
		}

		return res;
	}



	public String tabString(Double[] t)
	{
		String ret = "";
		for(int i = 0 ; i < t.length ; i++)
			ret += t[i] + ",";

		return ret;
	}




	//	@Test
	//	public void  testWrap()
	//	{
	//		Point pt = new Point(50,50);
	//		Point res = new Point(0,0);
	//		boolean b = refSpace.wrap(pt,res);
	//		System.out.println(res + "out of bounds :" + b);
	//	}

	@Test
	public void  testWrapContinuous()
	{
		Double[] coor = new Double[]{2d,-0.75d};
		Double[] res = space.wrap(coor);
		//System.out.println("Avant : " + tabString(coor) + " Apres : " + tabString(res));
		assertTrue(equals(res,new Double[]{0d,0.25d}));

	}

	@Test
	public void getDiscreteVolume()
	{
		int[] dimension = {1,1};
		space = new DefaultRoundedSpace(new Var(50), 2,true); 
		space.setDimension(dimension);
		assertTrue(space.getDiscreteVolume() == 2500);

	}

	@Test
	public void extendsSpace() throws NullCoordinateException{
		space = new DefaultRoundedSpace(new Var(50), 2,true); 
		Space extended = space.extend(2d);
		System.out.println(extended);
		System.out.println(extended.getDiscreteSize()[0] +"=="+ (space.getDiscreteSize()[0]*2));
		assertTrue(extended.getDiscreteSize()[0] == space.getDiscreteSize()[0]*2);
		System.out.println(extended.getDiscreteVolume() +"=="+ (space.getDiscreteVolume()));
		assertTrue(extended.getDiscreteVolume() == (50*2)*(50*2));
		System.out.println(Arrays.toString((extended.indexToCoord(0))));
		assertTrue(Arrays.equals(new Double[]{-0.99,-0.99}, extended.indexToCoord(0)));
		System.out.println(Arrays.toString((extended.indexToCoord(1))));
		System.out.println(Arrays.toString((extended.indexToCoord(2))));

		for(int i = 0 ; i < extended.getDiscreteVolume() ; i++){
			System.out.println(i + " : " + Arrays.toString(extended.indexToCoord(i)) );
		}

		System.out.println(extended);
		System.out.println(Arrays.toString((extended.indexToCoord(extended.getDiscreteVolume()-1))));
		System.out.println(extended.coordToIndex(0.99,0.99));
	}
	
	@Test
	public void testGetCenter(){
		space = new DefaultRoundedSpace(new Var(50), 2,true);
		Double[] center = space.getCenter();
		System.out.println("centre : " + Arrays.toString(center));
		assertTrue(Arrays.equals(center, new Double[]{0d,0d}));
	}
	
	@Test
	public void testTranspose(){
		Space sp = new RoundedSpace(new Double[]{-0.5,-0.6}, new Double[]{1d,2d},new Var(20), false);
		Space space2 = sp.transpose();
		System.out.println(sp);
		System.out.println(space2);
	}


	//	@Test
	//	public void testDecompose() throws NullCoordinateException
	//	{
	//		refSpace = new DefaultRoundedSpace(new Var(4), 2,true);
	//		int[] dimension = {1,1};
	//		refSpace.setDimension(dimension);
	//
	//		Double[] coor = new Double[]{0d,0d};
	//		int index = 0;
	//		Double[] res = refSpace.decompose(index);
	//		res = refSpace.discreteProj(res);
	//		System.out.println(Arrays.toString(res));
	//		assertTrue(equals(res,coor));
	//
	//		coor = new Double[]{2d,1d};
	//		index = 6;
	//		res = refSpace.decompose(index);
	//		System.out.println("res : " + tabString(res));
	//		res = refSpace.discreteProj(res);
	//		assertTrue(equals(res,coor));
	//
	//		coor = new Double[]{1d,3d};
	//		index = 13;
	//		res = refSpace.decompose(index);
	//		System.out.println("res : " + tabString(res));
	//		res = refSpace.discreteProj(res);
	//		assertTrue(equals(res,coor));
	//
	//		//3D
	//		refSpace =  new DefaultRoundedSpace(new Var(4), 3,true);
	//		dimension = new int[]{1,1,1};
	//		refSpace.setDimension(dimension);
	//
	//		coor = new Double[]{0d,0d,0d};
	//		index = 0;
	//		res = refSpace.decompose(index);
	//		res = refSpace.discreteProj(res);
	//		assertTrue(equals(res,coor));
	//
	//		coor = new Double[]{2d,2d,2d};
	//		index = 42;
	//		res = refSpace.decompose(index);
	//		System.out.println("res : " + tabString(res));
	//		res = refSpace.discreteProj(res);
	//		assertTrue(equals(res,coor));
	//
	//		coor = new Double[]{1d,3d,1d};
	//		index = 29;
	//		res = refSpace.decompose(index);
	//		System.out.println("res : " + tabString(res));
	//		res = refSpace.discreteProj(res);
	//		assertTrue(equals(res,coor));
	//
	//		coor = new Double[]{3d,3d,3d};
	//		index = 63;
	//		res = refSpace.decompose(index);
	//		System.out.println("res : " + tabString(res));
	//		res = refSpace.discreteProj(res);
	//		assertTrue(equals(res,coor));
	//
	//		//Weird 3D
	//		refSpace =  new RoundedSpace(new Double[]{0d,0d,0d},new Double[]{1d,3d,4d},new Var(1),true);
	//		dimension = new int[]{1,1,1};
	//		refSpace.setDimension(dimension);
	//		coor = new Double[]{0d,2d,2d};
	//		index = 8;
	//		res = refSpace.decompose(index);
	//		res = refSpace.discreteProj(res);
	//		System.out.println("res : " + tabString(res));
	//		assertTrue(equals(res,coor));
	//
	//		//4D
	//		refSpace =  new DefaultRoundedSpace(new Var(4), 4,true);
	//		dimension = new int[]{1,1,1,1};
	//		refSpace.setDimension(dimension);
	//		coor = new Double[]{0d,0d,0d,0d};
	//		index = 0;
	//		res = refSpace.decompose(index);
	//		res = refSpace.discreteProj(res);
	//		System.out.println("res : " + tabString(res));
	//		assertTrue(equals(res,coor));
	//
	//		coor = new Double[]{3d,3d,3d,3d};
	//		index = 255;
	//		res = refSpace.decompose(index);
	//		res = refSpace.discreteProj(res);
	//		System.out.println("res : " + tabString(res));
	//		assertTrue(equals(res,coor));
	//
	//		coor = new Double[]{0d,1d,2d,3d};
	//		index = 228;
	//		res = refSpace.decompose(index);
	//		res = refSpace.discreteProj(res);
	//		System.out.println("res : " + tabString(res));
	//		assertTrue(equals(res,coor));
	//
	//
	//		///////Sparse refSpace//////
	//		refSpace =  new DefaultRoundedSpace(new Var(4), 4,true);
	//
	//		//OD
	//		dimension = new int[]{0,0,0,0};
	//		refSpace.setDimension(dimension);
	//		coor = new Double[]{null,null,null,null};
	//		index = 255;
	//		res = refSpace.decompose(index);
	//		res = refSpace.discreteProj(res);
	//		System.out.println("res : " + tabString(res));
	//		assertTrue(equals(res,coor));
	//
	//		//1D
	//		dimension = new int[]{1,0,0,0};
	//		refSpace.setDimension(dimension);
	//		coor = new Double[]{2d,null,null,null};
	//		index = 2;
	//		res = refSpace.decompose(index);
	//		res = refSpace.discreteProj(res);
	//		System.out.println("res : " + tabString(res));
	//		assertTrue(equals(res,coor));
	//
	//		dimension = new int[]{0,1,0,0};
	//		refSpace.setDimension(dimension);
	//		coor = new Double[]{null,3d,null,null};
	//		index = 3;
	//		res = refSpace.decompose(index);
	//		res = refSpace.discreteProj(res);
	//		System.out.println("res : " + tabString(res));
	//		assertTrue(equals(res,coor));
	//
	//		dimension = new int[]{0,0,1,0};
	//		refSpace.setDimension(dimension);
	//		coor = new Double[]{null,null,2d,null};
	//		index = 2;
	//		res = refSpace.decompose(index);
	//		res = refSpace.discreteProj(res);
	//		System.out.println("res : " + tabString(res));
	//		assertTrue(equals(res,coor));
	//
	//		dimension = new int[]{0,0,0,1};
	//		refSpace.setDimension(dimension);
	//		coor = new Double[]{null,null,null,3d};
	//		index = 3;
	//		res = refSpace.decompose(index);
	//		res = refSpace.discreteProj(res);
	//		System.out.println("res : " + tabString(res));
	//		assertTrue(equals(res,coor));
	//
	//		//2D
	//		dimension = new int[]{1,1,0,0};
	//		refSpace.setDimension(dimension);
	//		coor = new Double[]{1d,3d,null,null};
	//		index = 13;
	//		res = refSpace.decompose(index);
	//		res = refSpace.discreteProj(res);
	//		System.out.println("res : " + tabString(res));
	//		assertTrue(equals(res,coor));
	//
	//		dimension = new int[]{0,1,0,1};
	//		refSpace.setDimension(dimension);
	//		coor = new Double[]{null,1d,null,3d};
	//		index = 13;
	//		res = refSpace.decompose(index);
	//		res = refSpace.discreteProj(res);
	//		System.out.println("res : " + tabString(res));
	//		assertTrue(equals(res,coor));
	//
	//		//3D
	//		dimension = new int[]{1,1,0,1};
	//		refSpace.setDimension(dimension);
	//		coor = new Double[]{1d,3d,null,1d};
	//		index = 29;
	//		res = refSpace.decompose(index);
	//		res = refSpace.discreteProj(res);
	//		System.out.println("res : " + tabString(res));
	//		assertTrue(equals(res,coor));
	//
	//		dimension = new int[]{0,1,1,1};
	//		refSpace.setDimension(dimension);
	//		coor = new Double[]{null,1d,2d,3d};
	//		index = 57;
	//		res = refSpace.decompose(index);
	//		res = refSpace.discreteProj(res);
	//		System.out.println("res : " + tabString(res));
	//		assertTrue(equals(res,coor));
	//	}

}
