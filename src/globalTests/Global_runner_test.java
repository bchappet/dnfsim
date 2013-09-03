package globalTests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gui.GUI;
import gui.Printer;
import gui.Runner;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;

import javax.swing.UIManager;

import model.Model;
import model.ModelGSpikeFFT;
import model.Root;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import plot.Trace;

import statistics.Characteristics;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;

import applet.AppletStub;

public class Global_runner_test {

	private ModelGSpikeFFT model;
	private Runner runner;
	private Printer printer;
	
	private double conv = 32;
	private double error = 0.0340219421686182;

	@Before
	public void setUp() throws Exception {

		model = new ModelGSpikeFFT("GSpike_test");
		URL contextPath = null;
		contextPath = new URL("file:./src/tests/context/");
		String contextScript = Printer.readFile(new URL("file:"+contextPath.getPath()+model.getName()+".dnfs"));
		contextScript +="resolution=39;";
		model.initialize(contextScript);
		
		runner = new Runner(model,"",new Printer(0));
		model.getCommandLine().setRunner(runner);

	}

	@After
	public void tearDown() throws Exception {


	}


	


	@Test
	public void testCNFTReset() throws NumberFormatException, NullCoordinateException, CommandLineFormatException
	{
		//Set specific parameters
		String script = 
				"tck_nb=1;" +
						"noise_amp=0;wait=100;" ;
		//Execute the script
		model.getCommandLine().parseCommand(script);

		model.getCharac().compute();

		System.out.println(model.getCharac());
		assertTrue(
				model.getCharac().getWtrace().getLast(Characteristics.CONVERGENCE)== conv);//Statistics.ERROR));
		assertTrue(
				model.getCharac().getWtrace().getLast(Characteristics.MEAN_ERROR)== error);//Statistics.ERROR));

		reinitialize();
		reset();

		
		//Execute the script
		model.getCommandLine().parseCommand(script);
		model.getCharac().compute();

		System.out.println(model.getCharac());
		assertTrue(
				model.getCharac().getWtrace().getLast(Characteristics.CONVERGENCE)==conv);//Statistics.ERROR));
		assertTrue(
				model.getCharac().getWtrace().getLast(Characteristics.MEAN_ERROR)==error);//Statistics.ERROR));


	}

	public boolean equals(Characteristics a, Characteristics b) {
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

	public void reinitialize()
	{
		try{
			model.getCommandLine().reinitialize();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (CommandLineFormatException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public void reset() {
		model.reset();
	}




}
