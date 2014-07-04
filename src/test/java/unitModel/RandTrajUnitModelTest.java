package test.java.unitModel;

import static org.junit.Assert.*;
import main.java.maps.InfiniteDt;
import main.java.maps.Trajectory;
import main.java.maps.Var;
import main.java.unitModel.RandTrajUnitModel;

import org.junit.Before;
import org.junit.Test;

public class RandTrajUnitModelTest {

	private Trajectory<Double> uut;

	@Before
	public void setUp() throws Exception {

		uut = new Trajectory<Double>("uut", new InfiniteDt(), new RandTrajUnitModel(0d),new Var(0.),new Var(1.));
	}

	@Test
	public void testComputeBigDecimalBigDecimalIntListOfParameter() {

		for(int i = 0 ; i < 100 ; i++){
			uut.compute();
			
			assertTrue("the value should be betwwen 0 and 1",uut.get() > -1 && uut.get() <1);
		}
	}

}
