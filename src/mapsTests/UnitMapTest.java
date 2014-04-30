package mapsTests;

import static org.junit.Assert.*;

import java.util.Arrays;

import maps.Trajectory;
import maps.UnitMap;
import maps.Var;

import org.junit.Before;
import org.junit.Test;

import space.Space2D;
import unitModel.UMWrapper;
import unitModel.UnitModel;

public class UnitMapTest {
	
	private UnitMap<Double, Integer> uut;
	private Var<Double> var;

	@Before
	public void setUp() throws Exception {
		 var = new Var<Double>("var",2.);
		Space2D space = new Space2D(3, 4);
		UnitModel<Double> um = new UMWrapper<Double>(-1d);
		uut = new UnitMap<Double, Integer>("uut", space, um, var);
	}

	

	@Test
	public void testClone() {
		UnitMap<Double,Integer> clone = uut.clone();
		assertSame("The space should be shared",uut.getSpace(),clone.getSpace());
		assertEquals("The name should uut_clone","uut_clone",clone.getName());
		
		//if we change var value, and compute both clone and uut should be updated
		var.set(100.);
		clone.compute();
		uut.compute();
		assertEquals("if we change var value, and compute both clone and uut should be updated",clone.getIndex(2),uut.getIndex(2));
		
		//but if we change a paramer in the clone only the clone is affected
		Var<Double> newVar = new Var<Double>(300d);
		clone.setParameter(0, newVar);
		clone.compute();
		assertEquals("if we change a paramer in the clone, the clone is affected",new Double(300d),clone.getIndex(2));
		uut.compute();
		assertEquals("if we change a paramer in the clone, the uut is not affected",new Double(100d),uut.getIndex(2));
	}

	@Test
	public void testGetIndex() {
		assertEquals("Val should be -1",new Double(-1),uut.getIndex(0));
		assertEquals("Val should be -1",new Double(-1),uut.getIndex(1));
		assertEquals("Val should be -1",new Double(-1),uut.getIndex(4));
		assertEquals("Val should be -1",new Double(-1),uut.getIndex(10));
		assertEquals("Val should be -1",new Double(-1),uut.getIndex(8));
		assertEquals("Val should be -1",new Double(-1),uut.getIndex(11));
		
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testGetIndexException() {
		assertEquals("exception",new Double(-1),uut.getIndex(12));
		assertEquals("exception",new Double(-1),uut.getIndex(-1));
		
	}
	
	@Test
	public void testCompute(){
		uut.compute();
		assertEquals("Values should be equals to var",
				Arrays.asList(new Double[]{2d,2d,2d,2d,2d,2d,2d,2d,2d,2d,2d,2d}),uut.getValues());
	}

	
	@Test
	public void testGetValues() {
		assertEquals("Values should be equals to init state",
				Arrays.asList(new Double[]{-1d,-1d,-1d,-1d,-1d,-1d,-1d,-1d,-1d,-1d,-1d,-1d}),uut.getValues());
	}

	

}
