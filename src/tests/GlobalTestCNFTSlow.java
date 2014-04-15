package tests;

import static org.junit.Assert.assertTrue;

import gui.Printer;

import java.math.BigDecimal;
import java.net.URL;

import junit.framework.TestCase;

import model.Model;
import model.ModelCNFTSlow;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import statistics.CharacteristicsCNFT;

public class GlobalTestCNFTSlow   extends TestCase{
	
	private Model cnft;
	
	private static int doubleComparisonPrecision = 4;
	private static int testIterations = 100;
	
	private static double mean_error_result = 0.073112458992715;
	private static double convergence_result = 7.0;
	
	private static double assyn_mean_error_result = 0.07703199050342559;
	private static double assyn_convergence_result = 5.0;

	@Before
	public void setUp() throws Exception {
		
		cnft = new ModelCNFTSlow("CNFT_test");
		URL contextPath = null;
		contextPath = new URL("file:./src/tests/context/");
		String contextScript = Printer.readFile(new URL("file:"+contextPath.getPath()+cnft.getName()+".dnfs"));
		cnft.initialize(contextScript);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	//Not deterministic
//	@Test
//	public void testCNFTSlowAssynchronous() throws Exception{
//		
//		System.out.println("Assynchronous without memory.....");
//		//Set specific parameters
//		String script = 
//				"tck_nb=1;" +
//				"noise_amp=0;" ;
//		//Execute the script
//		cnft.getCommandLine().parseCommand(script);
//		cnft.setAssynchronousComputation(true);
//
//		for(int i = 0 ; i < testIterations ; i++)
//		{
//			System.out.print(i+".");
//			cnft.update();
//		}
//		System.out.println();
//		cnft.getCharac().compute();
//		
//		//For 100 iterations
//		System.out.println(cnft.getCharac());
//		assertTrue(equals(
//				cnft.getCharac().getWtrace().getLast(Characteristics.CONVERGENCE),
//				assyn_convergence_result));
//		assertTrue(equals(
//				cnft.getCharac().getWtrace().getLast(Characteristics.MEAN_ERROR),
//				assyn_mean_error_result));
//	}

	@Test
	public void testCNFTSlowWithMemory() throws Exception{
		
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
			System.out.print(i+".");
			cnft.update();
		}
		System.out.println();
		cnft.getCharac().compute();
		
		//For 100 iterations
		System.out.println(cnft.getCharac());
		assertTrue(equals(
				cnft.getCharac().getWtrace().getLast(CharacteristicsCNFT.CONVERGENCE),
				convergence_result));
		assertTrue(equals(
				cnft.getCharac().getWtrace().getLast(CharacteristicsCNFT.MEAN_ERROR),
				mean_error_result));
	}
	
	
	/**
	 * Test the very result of the normal cnft after 100 iterations
	 * and with different parameters
	 * @throws Exception
	 */
	@Test
	public void testCNFTSlowResult() throws Exception{
		System.out.println("Without memory.....");
		//Set specific parameters
		String script = 
				"tck_nb=1;" +
				"noise_amp=0;" ;
		//Execute the script
		cnft.getCommandLine().parseCommand(script);

		for(int i = 0 ; i < testIterations ; i++)
		{
			System.out.print(i+".");
			cnft.update();
		}
		System.out.println();
		cnft.getCharac().compute();

		//For 100 iterations
		System.out.println(cnft.getCharac());
		assertTrue(equals(
				cnft.getCharac().getWtrace().getLast(CharacteristicsCNFT.CONVERGENCE),
				convergence_result));
		assertTrue(equals(
				cnft.getCharac().getWtrace().getLast(CharacteristicsCNFT.MEAN_ERROR),
				mean_error_result));

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

}
