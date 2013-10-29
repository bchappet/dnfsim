package tests;

import static org.junit.Assert.assertTrue;
import hardSimulator.NeuronHUM;

import java.util.Arrays;

import junit.framework.TestCase;

import maps.Var;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import precision.PrecisionVar;
import coordinates.DefaultRoundedSpace;

public class NeuronHUMTest  extends TestCase{


	private NeuronHUM neuron;
	private Var precision;
	@Before
	public void setUp() throws Exception {
		precision = new Var(10);
		neuron = new NeuronHUM(new Var(0.1),
				new DefaultRoundedSpace(new Var(19), 2, true),
				new PrecisionVar(1024,precision), //Proba = 1
				precision,
				new Var(10),
				new Var(2));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRouting() {
		neuron.setInports(new int[]{1,0,0,0});
		neuron.computeActivity();
		neuron.setInports(new int[]{0,0,0,0});

		assertTrue(equals(neuron.getOutPorts(),new int[]{0,1,1,1}));
		/////////////////
		neuron.setInports(new int[]{0,1,0,0});
		neuron.computeActivity();
		neuron.setInports(new int[]{0,0,0,0});

		assertTrue(equals(neuron.getOutPorts(),new int[]{1,0,1,1}));

		/////////////////
		neuron.setInports(new int[]{0,0,1,0});
		neuron.computeActivity();
		neuron.setInports(new int[]{0,0,0,0});

		assertTrue(equals(neuron.getOutPorts(),new int[]{0,0,0,1}));

		/////////////////
		neuron.setInports(new int[]{0,0,0,1});
		neuron.computeActivity();
		neuron.setInports(new int[]{0,0,0,0});

		assertTrue(equals(neuron.getOutPorts(),new int[]{0,0,1,0}));
		
	}
	
	@Test
	public void testActivation() {
		
		neuron.setActivate(1);
		neuron.computeActivity();
		neuron.setActivate(0);
		assertTrue(equals(neuron.getOutPorts(),new int[]{1,1,1,1}));
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{1,1,1,1}));
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{1,1,1,1}));
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{1,1,1,1}));
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{1,1,1,1}));
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{1,1,1,1}));
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{1,1,1,1}));
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{1,1,1,1}));
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{1,1,1,1}));
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{1,1,1,1}));
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{0,0,0,0}));
	}
	
	@Test
	public void testBuffers() {
		
		neuron.setInports(new int[]{1,0,0,0});
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{0,1,1,1}));
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{0,1,1,1}));
		neuron.setInports(new int[]{0,0,0,0});
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{0,0,0,0}));
		
		//Several inputs
		neuron.setInports(new int[]{1,1,0,0});
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{1,1,1,1}));
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{1,1,1,1}));
		neuron.setInports(new int[]{0,0,0,0});
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{0,0,1,1}));
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{0,0,1,1}));
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{0,0,0,0}));
		
		//All inputs
		neuron.setInports(new int[]{1,1,1,1});
		neuron.computeActivity();
		neuron.setInports(new int[]{0,0,0,0});
		
		assertTrue(equals(neuron.getOutPorts(),new int[]{1,1,1,1}));
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{0,0,1,1}));
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{0,0,1,1}));
		neuron.computeActivity();
		assertTrue(equals(neuron.getOutPorts(),new int[]{0,0,0,0}));
		
	}

	private static boolean equals(int[] a , int [] b){
		boolean ret = true;
		for(int i = 0 ; i < a.length ; i++){
			ret &= a[i] == b[i];
		}
		return ret;
	}

}
