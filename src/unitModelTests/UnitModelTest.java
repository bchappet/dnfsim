package unitModelTests;

import static org.junit.Assert.*;
import maps.Var;

import org.junit.Before;
import org.junit.Test;

import unitModel.UMWrapper;
import unitModel.UnitModel;

public class UnitModelTest {
	
	private UnitModel<Double> uut;

	@Before
	public void setUp() throws Exception {
		uut = new UMWrapper<Double>(0d);
		
	}
	
	@Test
	public void testGet(){
		assertEquals("Initial activity is expected",new Double(0d),uut.get());
	}
	
	@Test
	public void testSet(){
		uut.set(10d);
		assertEquals("New activity is expected",new Double(10d),uut.get());
	}
	
	@Test
	public void testClone() {
		UnitModel<Double> clone = uut.clone();
		
		assertSame("The activity ref is the same with Numbers",uut.get(),clone.get());
		
		uut.set(10d);
		assertEquals("New activity is expected for uut",new Double(10d),uut.get());
		assertEquals("New activity is not expected for clone",new Double(0d),clone.get());
		
		UnitModel<Var<Double>> test = new UMWrapper<Var<Double>>(new Var<Double>("var",0d));
		UnitModel<Var<Double>> testClone = test.clone();
		
		assertNotSame("The activity ref should be different with Cloneable objects",test.get(),testClone.get());
		
	}

}
