package tests;

import static org.junit.Assert.fail;
import hardSimulator.SpikingUnitHUM;
import maps.Var;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import coordinates.DefaultRoundedSpace;

public class test_spiking_unit {
	
	private SpikingUnitHUM um;

	@Before
	public void setUp() throws Exception {
		Var integration_dt = new Var(0.08);
		um = new SpikingUnitHUM(new Var(0.02), new DefaultRoundedSpace(new Var(50), 2, false),
				new Var(4),//compute clock
				new Var(0.203125),
				new Var(0.1015625),
				new Var(0.75),
				new Var(7),
				new Var(0),//input
				new Var(0.64),//tau
				integration_dt
				);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		um.setInPortsExc(new int[]{0,0,0,1});
		um.compute();
	}

}
