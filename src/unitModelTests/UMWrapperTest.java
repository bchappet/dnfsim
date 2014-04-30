package unitModelTests;

import static org.junit.Assert.*;
import maps.Var;

import org.junit.Before;
import org.junit.Test;

import space.Space2D;
import unitModel.UMWrapper;
import unitModel.UnitModel;

public class UMWrapperTest {
	
	private UnitModel<Double, Integer> uut;

	@Before
	public void setUp() throws Exception {
		Var<Double> var = new Var<Double>("var",2.);
		Space2D space = new Space2D(3, 4);
		uut = new UMWrapper<Double, Integer>();
	}

	@Test
	public void testClone() {
		fail("Not yet implemented");
	}

}
