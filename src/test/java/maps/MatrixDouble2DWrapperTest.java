package test.java.maps;

import static org.junit.Assert.assertEquals;
import main.java.maps.InfiniteDt;
import main.java.maps.Map;
import main.java.maps.MatrixDouble2D;
import main.java.maps.MatrixDouble2DWrapper;
import main.java.space.Space2D;
import main.resources.utils.ArrayUtils;

import main.resources.utils.Matrix;
import org.junit.Before;
import org.junit.Test;

public class MatrixDouble2DWrapperTest {
	
	private MatrixDouble2DWrapper uut;
	private double[][] values;

	@Before
	public void setUp() throws Exception {
		values = new double[][]{{1,2},{3,4},{5,6}};
		Map<Double,Integer> map = new MatrixDouble2D("map",new InfiniteDt(),values);
		uut = new MatrixDouble2DWrapper(map);
	}

	@Test
	public void testCompute() {
		assertEquals("The value should be good",ArrayUtils.toString(new double[3][2]),uut.toString());
		uut.compute();
		assertEquals("The value should be good",ArrayUtils.toString(values),uut.toString());
	}

    @Test
    public void testComputeSpeed(){ //1063
        int size = 2000;
        double[][] tabA = ArrayUtils.randomMatrix(size/2, size );
        double[][] tabB = ArrayUtils.randomMatrix(size,size/2);
        Map<Double,Integer> map = new MatrixDouble2D("map",new InfiniteDt(),tabA);
        uut = new MatrixDouble2DWrapper(map);
        long start = System.currentTimeMillis();
        uut.compute();
        long end = System.currentTimeMillis();
        System.out.println("ComputeSpeed: " + (end-start));


    }

}
