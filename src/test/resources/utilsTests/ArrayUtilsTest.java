package test.resources.utilsTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.resources.utils.ArrayUtils;

import org.junit.Before;
import org.junit.Test;

public class ArrayUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testToPrimitiveArrayListOfNumberIntInt() {
		List list = new ArrayList(Arrays.asList(new Double[]{0d,1d,2d,3d,4d,5d}));
		
		double[][] result = ArrayUtils.toPrimitiveArray(list, 3, 4);
		double[][] expected = new double[][]{{0,1,2},
											{3,4,5},
											{0,0,0},
											{0,0,0}};
		
		
		assertTrue("Two array should be the same",ArrayUtils.equals2D(expected, result));
	}

}
