package test.java.unitModelTests;

import static org.junit.Assert.*;
import main.java.maps.InfiniteDt;
import main.java.maps.Trajectory;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.space.NoDimSpace;
import main.java.unitModel.ComputeUM;

import org.junit.Before;
import org.junit.Test;

public class ComputeUMTest {
	
	private Trajectory<Double> uut;
	private Var<String> script;

	@Before
	public void setUp() throws Exception {
		script = new Var<String>("Script","");
		uut = new Trajectory<Double>("uut", new InfiniteDt(),new ComputeUM(0d),script);
		
	}

	@Test
	public void testComputer() {
		script.set("$1+$2*$1+10*1.99");
		Var<Double> a = new Var<Double>(9d);
		Var<Double> b = new Var<Double>(-5d);
		
		uut.addParameters(a,b);
		
		uut.compute();
		
		assertEquals("Computation should respect the script",new Double(-16.1d),uut.get());
	}

}
