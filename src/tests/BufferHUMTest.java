package tests;

import static org.junit.Assert.assertTrue;
import junit.framework.TestCase;
import hardSimulator.TransmitterHUM;
import maps.Var;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import coordinates.DefaultRoundedSpace;

public class BufferHUMTest  extends TestCase{
	
	private TransmitterHUM buff;

	@Before
	public void setUp() throws Exception {
		buff = new TransmitterHUM(new Var(0.1),
				new DefaultRoundedSpace(new Var(19), 2, true),
				new Var(10));
	}

	@After
	public void tearDown() throws Exception {
	}
	
	

	@Ignore("Not ready")
	public void test2() {
		buff.setInput(new int[]{1,0,0,0});
		buff.computeActivity();
		buff.setInput(new int[]{0,0,0,0});
		assertEquals("The transmitter should be activated",1,buff.getSpike());//TODO investigate
		buff.computeActivity();
		assertTrue(buff.getSpike()==0);
		
		buff.setInput(new int[]{1,1,1,1});
		buff.computeActivity();
		buff.setInput(new int[]{0,0,0,0});
		assertTrue(buff.getSpike()==1);
		buff.computeActivity();
		assertTrue(buff.getSpike()==1);
		buff.computeActivity();
		assertTrue(buff.getSpike()==1);
		buff.computeActivity();
		assertTrue(buff.getSpike()==1);
		buff.computeActivity();
		assertTrue(buff.getSpike()==0);
	}

}
