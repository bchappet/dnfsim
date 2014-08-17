package test.java.plot;

import static org.junit.Assert.*;
import main.java.plot.Trace;

import org.junit.Before;
import org.junit.Test;

public class TraceTest {
	
	private Trace uut;
	private int nbData = 100;

	@Before
	public void setUp() throws Exception {
		uut = new Trace("uut");
		for(int i = 0 ; i < nbData ; i++){
			uut.add(i*0.01);
		}
		
	}

	@Test
	public void testGetSum() {
		assertEquals("The sum should be 49.5 ", 49.5,uut.getSum(),10E-8);
	}

	@Test
	public void testGetMean() {
		assertEquals("The mean should be 0.495 ", 0.495,uut.getMean(),10E-8);
	}

	@Test
	public void testGetVar() {
		assertEquals("The var should be 0.083325 ", 0.083325,uut.getVar(),10E-8);
	}

	@Test
	public void testGetMeanInt() {
		assertEquals("The mean should be 0.745 ", 0.745,uut.getMean(50),10E-8);
	}

	@Test
	public void testGetSumInt() {
		assertEquals("The sum should be 37.25", 37.25,uut.getSum(50),10E-8);
	}

	@Test
	public void testGetVarInt() {
		assertEquals("The var should be 0.0208250", 0.02082500,uut.getVar(50),10E-8);
	}

}
