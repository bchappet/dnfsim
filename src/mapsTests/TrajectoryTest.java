package mapsTests;

import static org.junit.Assert.*;

import java.util.Arrays;

import maps.Trajectory;
import maps.Var;

import org.junit.Before;
import org.junit.Test;

import unitModel.UMWrapper;

public class TrajectoryTest {
	
	private Trajectory<Double> uut;
	private Var<Double> var;

	@Before
	public void setUp() throws Exception {
		var = new Var<Double>("var",10.);
		uut = new Trajectory<Double>("uut",new UMWrapper<Double>(-1d) ,var);
	}

	@Test
	public void testGet() {
		assertEquals("Get should initialy return the init value",new Double(-1d),uut.get());
	}


	@Test
	public void testClone() {
		Trajectory<Double> clone = uut.clone();
		assertSame("The space should be shared",uut.getSpace(),clone.getSpace());
		assertEquals("The name should uut_clone","uut_clone",clone.getName());
		
		//if we change var value, and compute both clone and uut should be updated
		var.set(100.);
		clone.compute();
		uut.compute();
		assertEquals("if we change var value, and compute both clone and uut should be updated",clone.get(),uut.get());
		
		//but if we change a paramer in the clone only the clone is affected
		Var<Double> newVar = new Var<Double>(300d);
		clone.setParameter(0, newVar);
		clone.compute();
		assertEquals("if we change a paramer in the clone, the clone is affected",new Double(300d),clone.get());
		uut.compute();
		assertEquals("if we change a paramer in the clone, the uut is not affected",new Double(100d),uut.get());
	}

	@Test
	public void testCompute() {
		uut.compute();
		assertEquals("Get should return the var value after the computation",new Double(10d),uut.get());
	}

	@Test
	public void testGetIndex() {
		assertEquals("getIndex(0) should return the good value" , new Double(-1d),uut.getIndex(0));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testGetIndexExcpetion() {
		assertEquals("getIndex(2) should rise an exception" , new Double(-1d),uut.getIndex(1));
	}


	@Test
	public void testGetValues() {
		assertEquals("the values should be the initValue",Arrays.asList(new Double[]{-1d}),uut.getValues());
	}
	


}
