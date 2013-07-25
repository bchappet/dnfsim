package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import maps.Var;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import precision.PrecisionVar;

public class PrecisionVarTest {
	
	protected Var precision = new Var(10);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testDecr() {
		PrecisionVar pv = new PrecisionVar(10,precision);
		pv.decr();
		assertTrue(pv.get() == 9);
	}

	public void testNotNull(){
		PrecisionVar pv = new PrecisionVar(10,precision);
		assertTrue(pv.notNull());
		assertTrue(pv.notNull());
		pv = new PrecisionVar(0,precision);
		assertFalse(pv.notNull());
		pv.decr();
		assertTrue(pv.notNull());
	}

	@Test
	public void testShiftRight() {
		PrecisionVar a = new PrecisionVar(-9,precision);
		a.shiftRightThis(3);
		assertTrue(a.get() == -2);
		
		a = new PrecisionVar(-8,precision);
		a.shiftRightThis(3);
		assertTrue(a.get() == -1);
		
		a = new PrecisionVar(-1,precision);
		a.shiftRightThis(3);
		assertTrue(a.get() == -1);
		
		 a = new PrecisionVar(9,precision);
		a.shiftRightThis(3);
		assertTrue(a.get() == 1);
		
		a = new PrecisionVar(8,precision);
		a.shiftRightThis(3);
		assertTrue(a.get() == 1);
		
		a = new PrecisionVar(1,precision);
		a.shiftRightThis(3);
		assertTrue(a.get() == 0);
		
	}
	
	@Test
	public void testMult(){
		PrecisionVar a = new PrecisionVar(-9,precision);
		PrecisionVar b = new PrecisionVar(3,precision);
		
		PrecisionVar c = a.mult(b);
		assertTrue(c.get() == -27);
		
		 a = new PrecisionVar(-9,precision);
		 b = new PrecisionVar(-3,precision);
		
		 c = a.mult(b);
		assertTrue(c.get() == 27);
		
		 a = new PrecisionVar(-255,precision);
		 b = new PrecisionVar(-255,precision);
		
		 c = a.mult(b);
		 System.out.println(c);
		assertTrue(c.get() == 513);
	}
	
	@Test
	public void testDiv(){
		PrecisionVar a = new PrecisionVar(-9,precision);
		PrecisionVar b = new PrecisionVar(3,precision);
		
		PrecisionVar c = a.div(b);
		assertTrue(c.get() == -3);
		
		 a = new PrecisionVar(-9,precision);
		 b = new PrecisionVar(-3,precision);
		
		 c = a.div(b);
		assertTrue(c.get() == 3);
		
		 a = new PrecisionVar(-255,precision);
		 b = new PrecisionVar(-255,precision);
		
		 c = a.div(b);
		 System.out.println(c);
		assertTrue(c.get() == 1);
		
		 a = new PrecisionVar(5,precision);
		 b = new PrecisionVar(3,precision);
		
		 c = a.div(b);
		 System.out.println(c);
		assertTrue(c.get() == 1);
	}

}
