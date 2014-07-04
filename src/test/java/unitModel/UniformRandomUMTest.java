package test.java.unitModel;

import static org.junit.Assert.*;
import main.java.maps.InfiniteDt;
import main.java.maps.Trajectory;
import main.java.maps.Var;
import main.java.unitModel.UniformRandomUM;

import org.junit.Before;
import org.junit.Test;

public class UniformRandomUMTest {

	private Trajectory<Double> uut;
	private Var<Double> a;
	private Var<Double> b;

	@Before
	public void setUp() throws Exception {
		a = new Var<Double>(0d);
		b = new Var<Double>(10d);
		uut = new Trajectory<Double>("uut",new InfiniteDt(),new UniformRandomUM(-1d),a,b);
	}

	@Test
	public void testComputeBigDecimalBigDecimalIntListOfParameter() {
		for(int i = 0 ; i < 100 ; i++){
			uut.compute();
			assertTrue("the value should be between a and b",(uut.get()>=a.get() && uut.get() < b.get()));
		}
		a.set(-10d);
		b.set(3d);
		
		for(int i = 0 ; i < 100 ; i++){
			uut.compute();
			assertTrue("the value should be between a and b",(uut.get()>=a.get() && uut.get() < b.get()));
		}
	}

}
