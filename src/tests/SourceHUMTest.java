package tests;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;
import junit.framework.TestCase;
import hardSimulator.SourceHUM;
import maps.Var;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import precision.PrecisionVar;
import coordinates.DefaultRoundedSpace;

public class SourceHUMTest extends TestCase {
	
	private SourceHUM source;

	@Before
	public void setUp() throws Exception {
		source = new SourceHUM(new Var(0.1),
				new DefaultRoundedSpace(new Var(19), 2, true),
				new Var(5),
				new PrecisionVar(512,new Var(10)));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		source.setActivate(1);
		source.computeActivity();
		source.setActivate(0);
		assertTrue(source.getSpike() == 1);
		source.computeActivity();
		assertTrue(source.getSpike() == 1);
		source.computeActivity();
		assertTrue(source.getSpike() == 1);
		source.computeActivity();
		assertTrue(source.getSpike() == 1);
		source.computeActivity();
		assertTrue(source.getSpike() == 1);
		source.computeActivity();
		assertTrue(source.getSpike() == 0);
	}

}
