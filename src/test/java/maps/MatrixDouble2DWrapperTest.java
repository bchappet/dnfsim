package test.java.maps;

import static org.junit.Assert.*;
import main.java.maps.InfiniteDt;
import main.java.maps.Map;
import main.java.maps.MatrixDouble2D;
import main.java.maps.MatrixDouble2DWrapper;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.space.Space2D;
import main.java.unitModel.UMWrapper;
import main.resources.utils.ArrayUtils;

import org.junit.Before;
import org.junit.Test;

public class MatrixDouble2DWrapperTest {
	
	private MatrixDouble2DWrapper uut;
	private double[][] values;

	@Before
	public void setUp() throws Exception {
		values = new double[][]{{1,2},{3,4},{5,6}};
		Map<Double,Integer> map = new MatrixDouble2D("map",new InfiniteDt(),values);
		uut = new MatrixDouble2DWrapper("uut", new InfiniteDt(), new Space2D(2, 3), map);
	}

	@Test
	public void testCompute() {
		assertEquals("The value should be good",ArrayUtils.toString(new double[3][2]),uut.toString());
		uut.compute();
		assertEquals("The value should be good",ArrayUtils.toString(values),uut.toString());
	}

}
