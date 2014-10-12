package test.resources.utilsTests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.resources.utils.ArrayUtils;

import org.junit.Before;
import org.junit.Test;

public class ArrayUtilsTest {

    double delta = 1e-7;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testToPrimitiveArrayListOfNumberIntInt() {
		List list = new ArrayList(Arrays.asList(new Double[]{0d,1d,2d,3d,4d,5d}));
		
		double[][] result = ArrayUtils.toPrimitiveDoubleArray2D(list, 3, 4);
		double[][] expected = new double[][]{{0,1,2},
											{3,4,5},
											{0,0,0},
											{0,0,0}};
		
		
		assertTrue("Two array should be the same",ArrayUtils.equals2D(expected, result,delta));
	}
	
	
	@Test
	public void testHorizontalConcatenation(){
		double[][] a = new double[][]{{0,1,2},
									{3,4,5}};
		double[][] b = new double[][]{{9,10},
									{11,12}};
		
		double[][] expected  = new double[][]{{0,1,2,9,10},
											{3,4,5,11,12}};
		
		
		double[][] result = ArrayUtils.horizontalConcatenation(a, b);
		
		//System.out.println(ArrayUtils.toString(result));

		
		assertTrue("Two array should be the same",ArrayUtils.equals2D(expected, result,delta));
	}
	
	

}
