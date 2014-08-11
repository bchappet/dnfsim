package main.java.maps;

import static org.junit.Assert.*;
import main.resources.utils.ArrayUtils;

import org.junit.Before;
import org.junit.Test;

public class TransposedMatrixTest {
	
	private MatrixDouble2D uut;
	private MatrixDouble2D mat;

	@Before
	public void setUp() throws Exception {
		mat = new MatrixDouble2D("map", new InfiniteDt(), new double[][]{{1, 2,3},{4,5,6},{7,8,9},{10,11,12}});
		uut = new TransposedMatrix("uut", new InfiniteDt(), mat);
	}

	@Test
	public void test1() {
		uut.compute();
		Double[][] expected = new Double[][]{{1d, 4d,7d,10d},{2d,5d,8d,11d},{3d,6d,9d,12d}};
		Double[][] result = uut.get2DArray();
		
		assertTrue("The returned array should be equal",ArrayUtils.equals2D(expected,result));
	}
	
	@Test
	public void testConstruct2(){
		uut = new TransposedMatrix(mat);
		uut.compute();
		Double[][] expected = new Double[][]{{1d, 4d,7d,10d},{2d,5d,8d,11d},{3d,6d,9d,12d}};
		Double[][] result = uut.get2DArray();
		
		assertTrue("The returned array should be equal",ArrayUtils.equals2D(expected,result));
		
	}

}
