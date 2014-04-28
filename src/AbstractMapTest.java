import static org.junit.Assert.assertEquals;
import maps.AbstractMap;
import maps.Map;
import maps.Var;

import org.junit.Before;
import org.junit.Test;

import unitModel.ConstantUnit;
import unitModel.UnitModel;
import coordinates.DefaultRoundedSpace;
import coordinates.DiscreteSpace;
import coordinates.NoDimSpace;
import coordinates.Space;


public class AbstractMapTest {
	
	Var dt = new Var("dt",0.1);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void displayMemoryTestNoDimSpace() {
		Space space = new NoDimSpace();
		double val = 0.3;
		UnitModel um = new ConstantUnit(val);
		System.out.println(um.get());
		AbstractMap uut = new Map("uut", um, dt, space);
		uut.constructMemory();
		
		assertEquals("The memory should be equals to the constant unit value",val,uut.displayMemory());
		
		
		
	}
	
	@Test
	public void displayMemoryTestDiscrete1DSpace() {
		Space space = new DiscreteSpace(new Double[]{10.}, false);
		double val = 0.3;
		UnitModel um = new ConstantUnit(val);
		System.out.println(um.get());
		AbstractMap uut = new Map("uut", um, dt, space);
		uut.constructMemory();
		System.out.println(uut.displayMemory());
		
		//assertEquals("The memory should be equals to the constant unit value",val,uut.displayMemory());
		
	}
	
	@Test
	public void displayMemoryTestDiscrete2DSpace() {
		Space space = new DiscreteSpace(new Double[]{10.,10.}, false);
		double val = 0.3;
		UnitModel um = new ConstantUnit(val);
		System.out.println(um.get());
		AbstractMap uut = new Map("uut", um, dt, space);
		uut.constructMemory();
		System.out.println(uut.displayMemory());
		
		//assertEquals("The memory should be equals to the constant unit value",val,uut.displayMemory());
		
	}
	
	@Test
	public void displayMemoryTestContinuous2DSpace() {
		Space space = new DefaultRoundedSpace(new Var(10), 2, false);
		double val = 0.3;
		UnitModel um = new ConstantUnit(val);
		System.out.println(um.get());
		AbstractMap uut = new Map("uut", um, dt, space);
		uut.constructMemory();
		System.out.println(uut.displayMemory());
		
		//assertEquals("The memory should be equals to the constant unit value",val,uut.displayMemory());
		
	}

}
