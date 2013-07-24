package tests;

import static org.junit.Assert.assertTrue;
import gui.ParameterModifier;

import org.junit.Test;

public class ParameterModifierTest {


	@Test
	public void testComputeAmount() {
		
		MemVariable test = new MemVariable("test",-540);
		System.out.println(test.get() + "==>" + ParameterModifier.computeAmount(test));
		assertTrue(ParameterModifier.computeAmount(test) == 10.0);
		
		 test = new MemVariable("test",2245);
		System.out.println(test.get() + "==>" + ParameterModifier.computeAmount(test));
		assertTrue(ParameterModifier.computeAmount(test) == 1.0);
		
		 test = new MemVariable("test",200);
		System.out.println(test.get() + "==>" + ParameterModifier.computeAmount(test));
		assertTrue(ParameterModifier.computeAmount(test) == 100.0);

		test = new MemVariable("test",1.1254);
		System.out.println(test.get() + "==>" + ParameterModifier.computeAmount(test));
		assertTrue(ParameterModifier.computeAmount(test) == 1.0E-4);


		test = new MemVariable("test",0.0345);
		System.out.println(test.get() + "==>" + ParameterModifier.computeAmount(test));
		assertTrue(ParameterModifier.computeAmount(test) == 1.0E-4);
		
		test = new MemVariable("test",0.034666);
		System.out.println(test.get() + "==>" + ParameterModifier.computeAmount(test));
		assertTrue(ParameterModifier.computeAmount(test) == 1.0E-6);
		
		test = new MemVariable("test",0.01);
		System.out.println(test.get() + "==>" + ParameterModifier.computeAmount(test));
		assertTrue(ParameterModifier.computeAmount(test) == 0.01);
		
		test = new MemVariable("test",0.0);
		System.out.println(test.get() + "==>" + ParameterModifier.computeAmount(test));
		assertTrue(ParameterModifier.computeAmount(test) == 1);
		
		test = new MemVariable("test",0.1);
		System.out.println(test.get() + "==>" + ParameterModifier.computeAmount(test));
		assertTrue(ParameterModifier.computeAmount(test) == 0.1);
		
		test = new MemVariable("test",1.0);
		System.out.println(test.get() + "==>" + ParameterModifier.computeAmount(test));
		assertTrue(ParameterModifier.computeAmount(test) == 1);
		
		test = new MemVariable("test",0.7);
		System.out.println(test.get() + "==>" + ParameterModifier.computeAmount(test));
		assertTrue(ParameterModifier.computeAmount(test) == 0.1);
		
		test = new MemVariable("test",0.64);
		System.out.println(test.get() + "==>" + ParameterModifier.computeAmount(test));
		assertTrue(ParameterModifier.computeAmount(test) == 0.01);
	}
	
	@Test
	public void testGetNumberOf(){
		String test = "1000";
		assertTrue(ParameterModifier.getNumberOf('0', test, ParameterModifier.END)
				== 3);
		
		test = "1";
		assertTrue(ParameterModifier.getNumberOf('0', test, ParameterModifier.END)
				== 0);
		
		test = "1303";
		assertTrue(ParameterModifier.getNumberOf('0', test, ParameterModifier.END)
				== 0);
		
		test = "000102";
		assertTrue(ParameterModifier.getNumberOf('0', test, ParameterModifier.START)
				== 3);
		
		test = "0000102";
		assertTrue(ParameterModifier.getNumberOf('0', test, ParameterModifier.START)
				== 4);
		
		test = "102";
		assertTrue(ParameterModifier.getNumberOf('0', test, ParameterModifier.START)
				== 0);
	}

}
