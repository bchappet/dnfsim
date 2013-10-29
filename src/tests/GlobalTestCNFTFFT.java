package tests;

import static org.junit.Assert.*;
import gui.Printer;

import java.math.BigDecimal;
import java.net.URL;

import junit.framework.TestCase;

import model.Model;
import model.ModelCNFT;
import model.ModelCNFTFFT;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import plot.Trace;

import statistics.Characteristics;

public class GlobalTestCNFTFFT  extends TestCase{
	
	private Model cnft;
	
	private static int doubleComparisonPrecision = 4;
	private static int testIterations = 100;
	private static double mean_error_result = 0.07368673574438947;
	private static double convergence_result = 8.0;

	@Before
	public void setUp() throws Exception {
		
		cnft = new ModelCNFTFFT("CNFTFFT_test");
		URL contextPath = null;
		contextPath = new URL("file:./src/tests/context/");
		String contextScript = Printer.readFile(new URL("file:"+contextPath.getPath()+cnft.getName()+".dnfs"));
		cnft.initialize(contextScript);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	
	@Test
	public void testCNFTWithMemory() throws Exception{
		System.out.println("With memory.....");
		//Set specific parameters
		String script = 
				"tck_nb=1;" +
				"noise_amp=0;" ;
		//Execute the script
		cnft.getCommandLine().parseCommand(script);
		cnft.getRootParam().constructAllMemories();
	
		
		

		for(int i = 0 ; i < testIterations ; i++)
		{
			cnft.update();
		}
		cnft.getCharac().compute();

		//For 100 iterations
		System.out.println(cnft.getCharac());
		assertTrue(equals(
				cnft.getCharac().getWtrace().getLast(Characteristics.CONVERGENCE),
				convergence_result));
		assertTrue(equals(
				cnft.getCharac().getWtrace().getLast(Characteristics.MEAN_ERROR),
				mean_error_result));
		//The result is a bit different... precision of 4 is good
		
		//compTime = 34.35
		

	}
	
	/**
	 * Test the very result of the normal cnft after 100 iterations
	 * and with different parameters
	 * @throws Exception
	 */
	@Test
	public void testCNFTResult() throws Exception{
		System.out.println("Without memory.....");
		//Set specific parameters
		String script = 
				"tck_nb=1;" +
				"noise_amp=0;" ;
		//Execute the script
		cnft.getCommandLine().parseCommand(script);

		for(int i = 0 ; i < testIterations ; i++)
		{
			cnft.update();
		}
		cnft.getCharac().compute();

		//For 100 iterations
		System.out.println(cnft.getCharac());
		assertTrue(equals(
				cnft.getCharac().getWtrace().getLast(Characteristics.CONVERGENCE),
				convergence_result));
		assertTrue(equals(
				cnft.getCharac().getWtrace().getLast(Characteristics.MEAN_ERROR),
				mean_error_result));
		
		//compTime = 27.07

	}

	/**
	 * Ensure that to double values are equals given the doubleComparisonPrecision precision
	 * @param a
	 * @param b
	 * @return
	 */
	private boolean equals(double a, double b) {
		BigDecimal ba = 
				new BigDecimal(String.valueOf(a)).setScale(
						doubleComparisonPrecision, BigDecimal.ROUND_HALF_UP);
		BigDecimal bb = 
				new BigDecimal(String.valueOf(b)).setScale(
						doubleComparisonPrecision, BigDecimal.ROUND_HALF_UP);

		return ba.equals(bb);


	}
	
	private boolean equals(Characteristics a, Characteristics b) {
		Trace[] wa = a.getWtrace().getCoords();
		Trace[] wb = b.getWtrace().getCoords();
		boolean ret = true;

		for(int i = 0 ; i <  wa.length ; i++)
		{
			Trace ta = wa[i];
			Trace tb = wb[i];

			for(int j = 0 ; j < ta.size() ; j++)
			{
				ret &= (ta.get(j) == tb.get(j));
			}
		}

		return ret;
	}

}
