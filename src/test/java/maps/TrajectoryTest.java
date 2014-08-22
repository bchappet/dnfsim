package test.java.maps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.maps.Trajectory;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.unitModel.UMWrapper;

import org.junit.Before;
import org.junit.Test;

public class TrajectoryTest {
	
	private Trajectory<Double> uut;
	private Var<Double> var;
	private Var<BigDecimal> dt;

	@Before
	public void setUp() throws Exception {
		var = new Var<Double>("var",10.);
		dt = new Var<BigDecimal>("dt",new BigDecimal("0.1"));
		uut = new Trajectory<Double>("uut",dt,new UMWrapper<Double>(-1d) ,var);
	}

	@Test
	public void testGet() {
		assertEquals("Get should initialy return the init value",new Double(-1d),uut.get());
	}
	
	@Test
	public void testGetNbMemory(){
		uut.addMemories(10);
		assertEquals("The number of memory should be 10",10,uut.getNbMemory());
		assertEquals("Hence we can access the 10th memory :",new Double(-1), uut.getDelay(0, 10));
	}
	
	@Test
	public void testMemoryNull(){
		uut.addMemories(10);
		assertEquals("the memory should be accessible: ",new Double(-1),uut.getDelay(0, 1));
		assertEquals("the memory should be accessible: ",new Double(-1),uut.getDelay(0, 10));
	}
	
	@Test
	public void testMemoryHistoric(){
		List<UnitMap<Double, Integer>> history = new ArrayList<UnitMap<Double,Integer>>();
		for(int i = 1 ; i <= 10 ; i++){
			history.add(new Trajectory<Double>("traj"+i, dt, new UMWrapper<Double>(new Double(i))));
		}
		uut.addMemories(10, history);
		assertEquals("the memory should be accessible: ",new Double(10),uut.getDelay(0, 1));
		assertEquals("the memory should be accessible: ",new Double(6),uut.getDelay(0, 5));
		assertEquals("the memory should be accessible: ",new Double(1),uut.getDelay(0, 10));
	}
	
	@Test
	public void testMemoryAfterComputation(){
		//We check here that adding a memory after computing save every state
		for(int i = 0 ; i < 10 ; i ++){
			var.set(new Double(i));
			uut.compute();
			uut.addMemories(1);
		}
		
		assertEquals("The last value after computation should be 9",new Double(9),uut.get());
		assertEquals("The last value after computation should be 9",new Double(9),uut.getDelay(0,0));
		assertEquals("The last value after computation should be 9",new Double(9),uut.getIndex(0));
		
		assertEquals("We should save every computation",new Double(8),uut.getDelay(0, 1));
		assertEquals("We should save every computation",new Double(7),uut.getDelay(0, 2));
		assertEquals("We should save every computation",new Double(6),uut.getDelay(0, 3));
		assertEquals("We should save every computation",new Double(5),uut.getDelay(0, 4));
		assertEquals("We should save every computation",new Double(4),uut.getDelay(0, 5));
		assertEquals("We should save every computation",new Double(3),uut.getDelay(0, 6));
		assertEquals("We should save every computation",new Double(2),uut.getDelay(0, 7));
		assertEquals("We should save every computation",new Double(1),uut.getDelay(0, 8));
		assertEquals("We should save every computation",new Double(0),uut.getDelay(0, 9));
	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testMemoryException(){
		uut.addMemories(10);
		uut.getDelay(0, 11);
	}


	@Test
	public void testClone() {
		Trajectory<Double> clone = uut.clone();
		assertSame("The main.java.space should be shared",uut.getSpace(),clone.getSpace());
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
	
	@Test//(expected=IndexOutOfBoundsException.class)
	public void testGetIndexExcpetion() {
		assertEquals("getIndex(2) should not rise exception" , new Double(-1d),uut.getIndex(1));
	}


	@Test
	public void testGetValues() {
		assertEquals("the values should be the initValue",Arrays.asList(new Double[]{-1d}),uut.getValues());
	}
	
	@Test
	public void testAddMemoriesArray(){
		uut.addMemories(10, new Double[]{0.,1.,2.,3.,4.,5.,6.,7.,8.,9.});
		assertEquals("Get should initialy return the init value",new Double(-1d),uut.get());
		assertEquals("Get delay(0,0) should initialy return the init value",new Double(-1d),uut.getDelay(0, 0));
		assertEquals("the memory should be accessible: ",new Double(0),uut.getDelay(0, 1));
		assertEquals("the memory should be accessible: ",new Double(4),uut.getDelay(0, 5));
		assertEquals("the memory should be accessible: ",new Double(9),uut.getDelay(0, 10));
	}
	


}
