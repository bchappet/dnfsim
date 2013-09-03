package tests;

import static org.junit.Assert.assertTrue;

import gui.Printer;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

import maps.Map;
import maps.Parameter;
import model.Model;
import model.ModelCNFT;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import console.CommandLineFormatException;
import coordinates.NullCoordinateException;

import plot.Trace;
import statistics.Characteristics;

public class GlobalTestCNFT {

	private Model cnft;

	private static int doubleComparisonPrecision = 4;
	private static int testIterations = 100;
	private static double mean_error_result = 0.07368673574438947;
	private static double convergence_result = 8.0;

	@Before
	public void setUp() throws Exception {
		cnft = new ModelCNFT("CNFT_test");
		URL contextPath = null;
		contextPath = new URL("file:./src/tests/context/");
		String contextScript = Printer.readFile(new URL("file:"+contextPath.getPath()+cnft.getName()+".dnfs"));
		cnft.initialize(contextScript);
		
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
//	@Test
//	public void testCNFTReset() throws NumberFormatException, NullCoordinateException, CommandLineFormatException
//	{
//		//Set specific parameters
//		String script = 
//				"noise_amp=0;" ;
//		//Execute the script
//		cnft.getCommandLine().parseCommand(script);
//		//cnft.getRootParam().constructAllMemories();
//		
//		cnft.getRootParam().constructAllMemories();
//		for(int i = 0 ; i < 200; i++)
//		{
//			cnft.update();
//			System.out.println(i);
//		}
//		cnft.getCharac().compute();
//
//		//For 500 iterations
//		//TODO, there is a convergence after 327 iteration... the model should not converge...
//		//It is the same on quinton's framework
//		System.out.println(cnft.getCharac());
//		assertTrue(equals(
//				cnft.getCharac().getWtrace().getLast(Characteristics.CONVERGENCE),
//				377));//Statistics.ERROR));
//		assertTrue(equals(
//				cnft.getCharac().getWtrace().getLast(Characteristics.MEAN_ERROR),
//				0.07706247244566543));//Statistics.ERROR));
//		
//		
//	}
	
//	@Test
//	public void testCNFTWith2Track() throws Exception{
//		//Set specific parameters
//		String script = 
//				"noise_amp=0;" ;
//		//Execute the script
//		cnft.getCommandLine().parseCommand(script);
//		//cnft.getRootParam().constructAllMemories();
//		
//		cnft.getRootParam().constructAllMemories();
//		for(int i = 0 ; i < 500; i++)
//		{
//			cnft.update();
//			System.out.println(i);
//		}
//		cnft.getCharac().compute();
//
//		//For 500 iterations
//		//TODO, there is a convergence after 327 iteration... the model should not converge...
//		//It is the same on quinton's framework
//		System.out.println(cnft.getCharac());
//		assertTrue(equals(
//				cnft.getCharac().getWtrace().getLast(Characteristics.CONVERGENCE),
//				377));//Statistics.ERROR));
//		assertTrue(equals(
//				cnft.getCharac().getWtrace().getLast(Characteristics.MEAN_ERROR),
//				0.07706247244566543));//Statistics.ERROR));
//		
//	}

//	@Test
//	public void testCNFTWithMemory() throws Exception{
//		System.out.println("With memory.....");
//		//Set specific parameters
//		String script = 
//				"tck_nb=1;" +
//				"noise_amp=0;" ;
//		//Execute the script
//		cnft.getCommandLine().parseCommand(script);
//		cnft.getRootParam().constructMemory();
//		Map input = (Map) cnft.getRootParam().getParameter(ModelCNFT.INPUT);
//		input.constructMemory();
//		
//		Parameter noise = input.getParameter("Noise");
//		noise.constructMemory();
//		
//		Map tck0 = (Map) input.getParameter(ModelCNFT.TRACK+"_"+0);
//		Map tck1 = (Map) input.getParameter(ModelCNFT.TRACK+"_"+1);
//		
//		tck0.constructMemory();
//		tck1.constructMemory();
//		
//		Parameter centerX0 =  tck0.getParameter("CenterX_0");
//		Parameter centerY0 =  tck0.getParameter("CenterY_0");
//		centerX0.constructMemory();
//		centerY0.constructMemory();
//		
////		Construct memory of Noise
////		Construct memory of CenterX2
////		Construct memory of CenterY2
////		Construct memory of track_0
////		Construct memory of CenterX2
////		Construct memory of CenterY2
////		Construct memory of track_1
////		Construct memory of Inputs
//	
//		
//		
//
//		for(int i = 0 ; i < testIterations ; i++)
//		{
//			cnft.update();
//		}
//		cnft.getCharac().compute();
//
//		//For 100 iterations
//		System.out.println(cnft.getCharac());
//		assertTrue(equals(
//				cnft.getCharac().getWtrace().getLast(Characteristics.CONVERGENCE),
//				convergence_result));
//		assertTrue(equals(
//				cnft.getCharac().getWtrace().getLast(Characteristics.MEAN_ERROR),
//				mean_error_result));
//		//The result is a bit different... precision of 4 is good
//		
//		//compTime = 65.75
//		
//
//	}


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
		
		//compTime = 59.11

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

//	/**
//	 * Ensure that the slow version (with Neighborhood convolution) of cnft is equivalent to the
//	 * classic convolution one
//	 * @throws Exception
//	 */
//		@Test
//		public void testCNFTSlow() throws Exception {
//	
//			Model cnftSlow = new ModelCNFTSlow("CNFTSlow",0.1);
//			cnftSlow.initialize();
//	
//			//Set specific parameters
//			String script = 
//					"tck_nb=1;" +
//							"noise_amp=0;" ;
//			//Execute the script
//			cnft.getCommandLine().parseCommand(script);
//			cnftSlow.getCommandLine().parseCommand(script);
//	
//	
//			for(int i = 0 ; i < testIterations ; i++)
//			{
//				cnft.update();
//				cnftSlow.update();
//				cnft.getCharac().compute();
//				cnftSlow.getCharac().compute();
//				System.out.println("normal :");
//				System.out.println(cnft.getCharac());
//				System.out.println("slow :");
//				System.out.println(cnftSlow.getCharac());
//				assertTrue(equals(cnft.getCharac(),cnftSlow.getCharac()));
//			}
//	
//	
//	
//	
//		}

	private boolean equals(Characteristics a, Characteristics b) {
		List<Trace> wa = a.getWtrace().getCoords();
		List<Trace> wb = b.getWtrace().getCoords();
		boolean ret = true;

		for(int i = 0 ; i <  wa.size() ; i++)
		{
			Trace ta = wa.get(i);
			Trace tb = wb.get(i);

			for(int j = 0 ; j < ta.size() ; j++)
			{
				ret &= (ta.get(j) == tb.get(j));
			}
		}

		return ret;
	}

}
